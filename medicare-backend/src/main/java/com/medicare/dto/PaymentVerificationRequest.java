package com.medicare.dto;

import jakarta.validation.constraints.NotBlank;


public class PaymentVerificationRequest {


    @NotBlank(
            message =
                    "Razorpay payment ID is required"
    )
    private String razorpayPaymentId;


    @NotBlank(
            message =
                    "Razorpay signature is required"
    )
    private String razorpaySignature;


    public PaymentVerificationRequest() {
    }


    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }


    public void setRazorpayPaymentId(
            String razorpayPaymentId) {

        this.razorpayPaymentId =
                razorpayPaymentId;
    }


    public String getRazorpaySignature() {
        return razorpaySignature;
    }


    public void setRazorpaySignature(
            String razorpaySignature) {

        this.razorpaySignature =
                razorpaySignature;
    }
}