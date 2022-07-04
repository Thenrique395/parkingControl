package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.services.interfaceService.IParkingSpotService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService implements IParkingSpotService {

   private final ParkingSpotRepository parkingSpotRepository;

   public ParkingSpotService(ParkingSpotRepository parkingSpotRepository){
       this.parkingSpotRepository = parkingSpotRepository;
   }

    @Override
    @Transactional
    public Object save(ParkingSpotModel parkingSportModel) {
        return  parkingSpotRepository.save(parkingSportModel);
    }

    @Override
    public boolean existsByLicensePlateCar(String licensePlateCar) {
       return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    @Override
    public boolean existsByParkingSpotNuber(String parkingSpotNuber) {
        return parkingSpotRepository.existsByParkingSpotNuber(parkingSpotNuber);
    }

    @Override
    public boolean existsByApartamentAndBlock(String apartament, String block) {
        return parkingSpotRepository.existsByApartamentAndBlock(apartament,block);
    }

    @Override
    public List<ParkingSpotModel> findAll() {
        return parkingSpotRepository.findAll();
    }

    @Override
    public Optional<ParkingSpotModel> findByfId(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    @Override
    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }
}
