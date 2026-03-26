package com.example.proj.repository;

import com.example.proj.model.MapPinModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapPinRepository extends JpaRepository<MapPinModel, Long> {


}
