package com.example.proj.domain.mapPin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapPinRepository extends JpaRepository<MapPinModel, Long> {


}
