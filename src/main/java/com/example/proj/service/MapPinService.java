package com.example.proj.service;

import com.example.proj.model.MapPinModel;
import com.example.proj.repository.MapPinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapPinService {
    MapPinRepository  mapPinRepository;
    MapPinService(MapPinRepository mapPinRepository) {
        this.mapPinRepository = mapPinRepository;
    }
    public void addMapPin(float lon, float lat, String title, String description){
        MapPinModel pin =  new MapPinModel();
        pin.setLon(lon);
        pin.setLat(lat);
        pin.setTitle(title);
        pin.setDescription(description);
        mapPinRepository.save(pin);


    }
    public void deletePinById(Long id){
        mapPinRepository.deleteById(id);

    }
    public List<MapPinModel> findAllMapPin(){
        return mapPinRepository.findAll();
    }





}
