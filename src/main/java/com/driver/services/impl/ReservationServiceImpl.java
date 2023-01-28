package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        try {
            if (!userRepository3.findById(userId).isPresent()) {
                //return null;
                throw new Exception("Cannot make reservation");
            }

            User user = userRepository3.findById(userId).get();

            if (!parkingLotRepository3.findById(parkingLotId).isPresent()) {
                //return null;
                throw new Exception("Cannot make reservation");
            }

            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

            Spot spot = findSpot(parkingLot.getSpotList(), numberOfWheels);

            if (spot == null) throw new Exception("Cannot make reservation");

            Reservation reservation = new Reservation();
            reservation.setNumberOfHours(timeInHours);
            reservation.setUser(user);
            reservation.setSpot(spot);

            spot.setOccupied(true);
            spot.getReservationList().add(reservation);

            user.getReservationList().add(reservation);

            userRepository3.save(user);
            spotRepository3.save(spot);

            return reservation;
        }
        catch(Exception e){
            return null;
        }
    }

    public Spot findSpot(List<Spot> spotList, int numberOfWheels){
        try {
            Spot ReservedSpot = null;
            List<Spot> availableSpots = new ArrayList<>();
            int lowestPrice = Integer.MAX_VALUE;

            for(Spot spot:spotList){
                if(!spot.getOccupied()) {

                    if (numberOfWheels>4 && spot.getSpotType().equals(SpotType.OTHERS)) {
                        availableSpots.add(spot);
                    }
                    else if ((numberOfWheels > 2 && numberOfWheels <= 4) && (spot.getSpotType().equals(SpotType.FOUR_WHEELER) || spot.getSpotType().equals(SpotType.OTHERS))) {
                        availableSpots.add(spot);
                    }
                    else if(numberOfWheels<=2) {
                        availableSpots.add(spot);
                    }
                }
            }

            if(availableSpots.size()==0) return null;

            for(Spot spot:availableSpots){
                if(spot.getPricePerHour()<lowestPrice){
                    ReservedSpot = spot;
                    lowestPrice=spot.getPricePerHour();
                }
            }

            return ReservedSpot;
        }
        catch (Exception e){
            return null;
        }
    }
}
