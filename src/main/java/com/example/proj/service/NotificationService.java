package com.example.proj.service;


import com.example.proj.model.NotificationModel;
import com.example.proj.model.UserModel;
import com.example.proj.repository.NotificationRepository;
import com.example.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

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
