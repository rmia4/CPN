package com.example.proj.domain.mapPin;

import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapPinService {
    private final MapPinRepository  mapPinRepository;
    private final UserRepository userRepository;

    public void addMapPin(MapPinSaveRequestDto mapPinDto) {
        MapPinModel pin =  new MapPinModel();

        pin.setLon(mapPinDto.getLon());
        pin.setLat(mapPinDto.getLat());
        pin.setTitle(mapPinDto.getTitle());
        pin.setDescription(mapPinDto.getDescription());
        pin.setTag(mapPinDto.getTag());
        UserModel user = userRepository.findByUserId(mapPinDto.getUserId());
        if(user==null) {
            throw new IllegalArgumentException("userId error" + mapPinDto.getUserId());
        }
        pin.setUser(user);

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