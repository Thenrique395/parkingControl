package com.api.parkingcontrol.services.interfaceService;

import com.api.parkingcontrol.models.ParkingSpotModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IParkingSpotService {

    Object save(ParkingSpotModel parkingSportModel);

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSpotNuber(String parkingSpotNuber);

    boolean existsByApartamentAndBlock(String apartament, String block);

    List<ParkingSpotModel> findAll();

    Optional<ParkingSpotModel> findByfId(UUID id);

    void delete(ParkingSpotModel parkingSpotModel);
}
