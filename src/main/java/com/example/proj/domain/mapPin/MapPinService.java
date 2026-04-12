package com.example.proj.domain.mapPin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapPinService {
    private final MapPinRepository  mapPinRepository;

    public void addMapPin(MapPinSaveRequestDto mapPinDto) {
        MapPinModel pin =  new MapPinModel();

        pin.setLon(mapPinDto.getLon());
        pin.setLat(mapPinDto.getLat());
        pin.setTitle(mapPinDto.getTitle());
        pin.setDescription(mapPinDto.getDescription());

        mapPinRepository.save(pin);

    }
    public void deletePinById(Long id){

        mapPinRepository.deleteById(id);

    }
    public List<MapPinModel> findAllMapPin(){
        return mapPinRepository.findAll();
    }

    public List<MapPinModel> findAllMapPinByTagName(String tagName){

        return mapPinRepository.findAllByTag(tagName);

    }
}