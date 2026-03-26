package com.example.proj.repository;

import com.example.proj.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {




}
