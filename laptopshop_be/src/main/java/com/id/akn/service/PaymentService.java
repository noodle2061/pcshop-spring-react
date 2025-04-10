package com.id.akn.service;

import com.id.akn.exception.OrderException;
import com.id.akn.response.PaymentLinkRes;

import java.io.IOException;
import java.util.Map;

public interface PaymentService {
    PaymentLinkRes createPaymentLink(Long amount, String bankCode, String ipAddress, String vnp_TxnRef) throws OrderException ,IOException;
    String handleVnpayIpn(Map<String, String> params) throws OrderException;
}

