package com.driver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean PaymentCompleted;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    //reservation
    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties("payment")
    private Reservation reservation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaymentCompleted() {
        return PaymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        PaymentCompleted = paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Payment(int id, boolean paymentCompleted, PaymentMode paymentMode, Reservation reservation) {
        this.id = id;
        PaymentCompleted = paymentCompleted;
        this.paymentMode = paymentMode;
        this.reservation = reservation;
    }

    public Payment() {
    }
}
