package com.add.notification.services;

import com.add.notification.constants.State;
import com.add.notification.dto.NotificationDTO;
import com.add.notification.model.Notification;
import com.add.notification.repository.NotificationRepository;

import com.add.notification.util.StringTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final RestService restService;

    public Page<Notification> getByUserId(String userId, Pageable pageable) {

        return notificationRepository.findByUserIdAndDeleted(userId, false, pageable);
    }

    public Optional<Notification> sendToUser(String userId, NotificationDTO notificationDTO) {
        Notification notification = Notification.builder()
                .userId(userId)
                .content(notificationDTO.getContent())
                .build();

        String url = StringTemplate.template("/api/notifications/topic/notify/user/${userId}")
                .addParameter("userId", userId)
                .build();
        notificationRepository.save(notification);
        log.info("sending notification to user {}", userId);
        messagingTemplate.convertAndSend(url, notification);
        log.info("notification was sent to user {}", userId);
        return Optional.of(notification);
    }

    public List<Notification> sendToAll(NotificationDTO notificationDTO) {

        return restService.getActiveUserIds()
                .stream()
                .map(userId -> sendToUser(userId, notificationDTO))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .collect(Collectors.toList());
    }

    public List<Notification> sendToGroup(String groupeName, NotificationDTO notificationDTO) {
        return restService.getUserIdsByGroupName(groupeName)
                .stream()
                .map(userId -> sendToUser(userId, notificationDTO))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .collect(Collectors.toList());
    }

    public void softDelete(String notificationId) {
        Optional.ofNullable(notificationId)
                .map(notificationRepository::findById)
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .ifPresent(this::softDelete);
    }

    public void softDelete(Notification notification) {
        notification.setDeleted(true);
        notificationRepository.save(notification);

    }

    public Long countNotOpendNotification(String userId) {
        return notificationRepository.countByUserIdAndDeletedAndState(userId, false, State.NOT_OPENED);
    }

    public Optional<Notification> markAsRead(Notification notification) {

        notification.setState(State.OPENED);
        return Optional.of(notificationRepository.save(notification));

    }

    public Optional<Notification> markAsRead(String notificationId) {

        return Optional.ofNullable(notificationId)
                .map(notificationRepository::findById)
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(this::markAsRead)
                .orElse(Optional.empty());

    }

    public List<Notification> markAllAsRead(String userId) {
        return notificationRepository.findByUserIdAndDeletedAndState(userId, false, State.NOT_OPENED)
                .stream()
                .map(this::markAsRead)
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .collect(Collectors.toList());
    }

}
