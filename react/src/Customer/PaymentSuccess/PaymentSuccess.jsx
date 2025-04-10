import React, { useEffect, useState } from "react";
import { Alert, AlertTitle, Grid } from "@mui/material";
import { useLocation } from "react-router-dom";
import { getInfoPayment } from "../../until/common/index.js";
import api from "../../Config/api.js";

const PaymentSuccess = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const [info, setInfo] = useState({});

  const code = queryParams.get("code");
  const id = queryParams.get("id");
  const cancel = queryParams.get("cancel");
  const status = queryParams.get("status");
  const orderCode = queryParams.get("orderCode");

  useEffect(() => {
    const get = async () => {
      const payment = await getInfoPayment(orderCode);
      setInfo(payment);
      
      if (payment.status === "PAID") {
        const parts = payment.transactions[0].description.split(" ");
        const order_id = parts[3];
        const res = await api.put(
          `/api/orders/${order_id}?paymentStatus=COMPLETED`
        );
      }
    };
    get();
  }, []);

  return (
    <div className="px-2 lg:px-36">
      <div className="flex flex-col justify-center items-center">
        <Alert
          variant="filled"
          severity="success"
          sx={{ mb: 6, width: "fit-content" }}
        >
          <AlertTitle>Payment Success</AlertTitle>
          You have successfully paid {info?.amount?.toLocaleString('vi-VN')} VND
        </Alert>
      </div>
    </div>
  );
};

export default PaymentSuccess;
