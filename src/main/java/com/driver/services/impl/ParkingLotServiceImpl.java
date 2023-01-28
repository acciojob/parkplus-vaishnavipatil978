package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot(name,address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        try {

            ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();

            Spot spot = new Spot();
            spot.setParkingLot(parkingLot);
            spot.setPricePerHour(pricePerHour);
            spot.setOccupied(false);

            if(numberOfWheels==2){
                spot.setSpotType(SpotType.TWO_WHEELER);
            }
            else if(numberOfWheels==4){
                spot.setSpotType(SpotType.FOUR_WHEELER);
            }
            else {
                spot.setSpotType(SpotType.OTHERS);
            }

            parkingLot.getSpotList().add(spot);

            parkingLotRepository1.save(parkingLot);

            return spot;
        }
        catch (Exception e){
            return null;
        }

    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot = spotRepository1.findById(spotId).get();
        spot.getParkingLot().getSpotList().remove(spot);
        parkingLotRepository1.save(spot.getParkingLot());
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository1.findById(spotId).get();
        spot.setPricePerHour(pricePerHour);

        spotRepository1.save(spot);

        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
