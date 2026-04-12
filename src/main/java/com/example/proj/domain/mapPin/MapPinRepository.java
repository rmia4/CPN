package com.example.proj.domain.mapPin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapPinRepository extends JpaRepository<MapPinModel, Long> {


    List<MapPinModel> findAllByTag_Tag(String tag);

}
