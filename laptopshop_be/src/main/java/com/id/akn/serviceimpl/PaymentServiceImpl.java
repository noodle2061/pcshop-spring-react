
package com.id.akn.serviceimpl;

import com.id.akn.config.VNPAYConfig;
import com.id.akn.exception.OrderException;
import com.id.akn.model.Order;
import com.id.akn.response.PaymentLinkRes;
import com.id.akn.service.OrderService;

import com.id.akn.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private VNPAYConfig vnpayConfig;
    private OrderService orderService;

    @Override
    public PaymentLinkRes createPaymentLink(Long amount, String bankCode, String vnp_IpAddr, String vnp_TxnRef) throws IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TmnCode = vnpayConfig.getVnp_TmnCode();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Đặt thời gian tạo và hết hạn
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Tạo chuỗi tham số và ký kết an toàn
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return new PaymentLinkRes(paymentUrl, String.valueOf(vnp_TxnRef));
    }

    @Override
    public String handleVnpayIpn(Map<String, String> params) throws OrderException {
        long vnp_TxnRef = Long.parseLong(params.get("vnp_TxnRef"));
        String vnp_ResponseCode = params.get("vnp_ResponseCode");

        // Kiểm tra checksum và xác thực tính toàn vẹn của dữ liệu
        Map<String, String> fields = new HashMap<>(params);
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        String calculatedHash = vnpayConfig.hashAllFields(fields);
        String test = params.get("vnp_SecureHash");
//        System.out.println(test);
        if (!calculatedHash.equals(params.get("vnp_SecureHash"))) {
            return "97"; // Invalid Checksum
        }

        Order order = orderService.findOrderById(vnp_TxnRef);
        if(order==null){
            return "01";
        }
        if(order.getPaymentStatus() != Order.PaymentStatus.COMPLETED ){
            if ("00".equals(vnp_ResponseCode)) {
                // Giao dịch thành công, cập nhật trạng thái thanh toán
                orderService.updatePaymentStatus(vnp_TxnRef, Order.PaymentStatus.COMPLETED);
            } else {
                // Giao dịch thất bại, cập nhật trạng thái thanh toán
                orderService.updatePaymentStatus(vnp_TxnRef, Order.PaymentStatus.FAILED);
            }
        }else{
            return "02";
        }
        return vnp_ResponseCode;
    }
}

