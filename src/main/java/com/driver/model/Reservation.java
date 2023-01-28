package com.driver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numberOfHours;

    //user
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("reservationList")
    private User user;

    //spot
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("reservationList")
    private Spot spot;

    //Payment
    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("reservation")
    private Payment payment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Reservation() {
    }
}
