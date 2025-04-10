package com.id.akn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PaymentLinkRes {
    private String paymentLinkUrl;
    private String paymentLinkId;
}
