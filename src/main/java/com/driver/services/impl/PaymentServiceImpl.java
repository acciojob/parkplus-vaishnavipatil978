package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.findById(reservationId).get();

        int hours = reservation.getNumberOfHours();
        int pricePerHour = reservation.getSpot().getPricePerHour();

        int totalAmount = hours*pricePerHour;

        if(amountSent<totalAmount) throw new Exception("Insufficient Amount");

        PaymentMode myMode = null;

        for(PaymentMode paymentMode:PaymentMode.values()){
            if(paymentMode.toString().equalsIgnoreCase(mode)) myMode = paymentMode;
        }

        if(myMode==null) throw new Exception("Payment mode not detected");

        Payment payment = new Payment();
        payment.setPaymentCompleted(true);
        payment.setPaymentMode(myMode);
        payment.setReservation(reservation);

        reservation.setPayment(payment);
        reservationRepository2.save(reservation);

        return payment;
    }
}
