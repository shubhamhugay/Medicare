package com.medicare.dto;



public class PaymentOrderResponse {

    private Long appointmentId;

    private String razorpayOrderId;

    private long amount;

    private String currency;

    private String keyId;


    public PaymentOrderResponse(
            Long appointmentId,
            String razorpayOrderId,
            long amount,
            String currency,
            String keyId) {

        this.appointmentId =
                appointmentId;

        this.razorpayOrderId =
                razorpayOrderId;

        this.amount =
                amount;

        this.currency =
                currency;

        this.keyId =
                keyId;
    }


    public Long getAppointmentId() {
        return appointmentId;
    }


    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }


    public long getAmount() {
        return amount;
    }


    public String getCurrency() {
        return currency;
    }


    public String getKeyId() {
        return keyId;
    }
}