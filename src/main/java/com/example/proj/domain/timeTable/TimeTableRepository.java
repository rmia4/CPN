package com.example.proj.domain.timeTable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTableModel, Long> {

    List<TimeTableModel> findAllByUser_userId(String userId);
}
