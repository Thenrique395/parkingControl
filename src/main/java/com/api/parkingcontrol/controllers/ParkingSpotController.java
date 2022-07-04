package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.interfaceService.IParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

   private final IParkingSpotService iParkingSpotService;

    public ParkingSpotController(IParkingSpotService iParkingSpotService){
        this.iParkingSpotService = iParkingSpotService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
        if (iParkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if (iParkingSpotService.existsByParkingSpotNuber(parkingSpotDto.getParkingSpotNuber())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot is already in use!");
        }
        if (iParkingSpotService.existsByApartamentAndBlock(parkingSpotDto.getApartament(), parkingSpotDto.getBlock())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot already registered for this apartament/block!");
        }

        var parkingSportModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto,parkingSportModel);
        parkingSportModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return  ResponseEntity.status(HttpStatus.CREATED).body(iParkingSpotService.save(parkingSportModel));
    }

    @GetMapping()
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return  ResponseEntity.status(HttpStatus.OK).body(iParkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpots(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = iParkingSpotService.findByfId(id);
        if (!parkingSpotModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Sport not found");
        }
        return  ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = iParkingSpotService.findByfId(id);
        if (!parkingSpotModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Sport not found");
        }
        iParkingSpotService.delete(parkingSpotModelOptional.get());
        return  ResponseEntity.status(HttpStatus.OK).body("Parking Sport deleted successfully");
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto ParkingSpotDto){

        Optional<ParkingSpotModel> parkingSpotModelOptional = iParkingSpotService.findByfId(id);
        if (!parkingSpotModelOptional.isPresent()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Sport not found");
        }
      var parkingSpotModel=parkingSpotModelOptional.get();

        var parkingSportModel = new ParkingSpotModel();
        BeanUtils.copyProperties(ParkingSpotDto,parkingSportModel);
        parkingSportModel.setId(parkingSpotModelOptional.get().getId());
        parkingSportModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
        return  ResponseEntity.status(HttpStatus.OK).body("Parking Sport deleted successfully");
    }

}
