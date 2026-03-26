package com.example.proj.service;


import com.example.proj.model.NotificationModel;
import com.example.proj.model.UserModel;
import com.example.proj.repository.NotificationRepository;
import com.example.proj.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;
    NotificationService(NotificationRepository notificationRepository,
                        UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void addNotification(String userId, String description){
        NotificationModel noti =  new NotificationModel();
        UserModel user = userRepository.findByUserId(userId);

        noti.setUser(user);
        noti.setDescription(description);

        notificationRepository.save(noti);
    }


    public void deleteNotificationById(Long id){
        notificationRepository.deleteById(id);
    }

    public List<NotificationModel> findAll(){
        return notificationRepository.findAll();
    }




}
