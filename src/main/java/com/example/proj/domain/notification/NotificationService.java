package com.example.proj.domain.notification;


import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


//TODO: null 반환 시의 에러처리
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void addNotification(NotificationSaveRequestDto dto){
        NotificationModel noti =  new NotificationModel();

        UserModel user = userRepository.findByUserId(dto.getUserId());
        if(user == null){

        }
        noti.setUser(user);
        noti.setDescription(dto.getDescription());
        noti.setTitle(dto.getTitle());

        notificationRepository.save(noti);
    }

    public List<NotificationModel> getAllNotificationByUserId(String userId){
        List<NotificationModel> list = notificationRepository.findAllByUser_userId(userId);

        return list;
    }


    public void deleteNotificationById(Long id){
        NotificationModel noti = notificationRepository.findById(id).get();
        if(noti == null){

        }
        notificationRepository.delete(noti);
    }


    public List<NotificationModel> findAll(){
        return notificationRepository.findAll();
    }






}
