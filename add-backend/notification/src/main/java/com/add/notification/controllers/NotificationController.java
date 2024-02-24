package com.add.notification.controllers;

import com.add.notification.dto.NotificationDTO;

import com.add.notification.model.Notification;

import com.add.notification.services.NotificationService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public Page<Notification> getNotificationsByUserId(@PathVariable String userId, Pageable pageable) {
        return notificationService.getByUserId(userId, pageable);
    }

    @GetMapping("/notOpend/{userId}")
    public Long countNotOpendNotifications(@PathVariable String userId) {
        return notificationService.countNotOpendNotification(userId);
    }

    @PatchMapping("/markAsRead/{notificationId}")
    public Optional<Notification> markAsRead(@PathVariable String notificationId) {
        return notificationService.markAsRead(notificationId);
    }

    @PatchMapping("/markAllRead/{userId}")
    public List<Notification> markAllRead(@PathVariable String userId) {
        return notificationService.markAllAsRead(userId);
    }

    @DeleteMapping("/{notificationId}")
    public void softDelete(@PathVariable String notificationId) {
        notificationService.softDelete(notificationId);
    }

    @PostMapping("/send")
    public List<Notification> sendToUser(@RequestBody NotificationDTO notificationDTO) {
        return notificationService.sendToAll(notificationDTO);
    }

    @PostMapping("/send/{userId}")
    public Optional<Notification> sendToUser(@PathVariable String userId,
            @RequestBody NotificationDTO notificationDTO) {
        return notificationService.sendToUser(userId, notificationDTO);
    }

    @PostMapping("/send/group/{groupName}")
    public List<Notification> sendToGroup(@PathVariable String groupName,
            @RequestBody NotificationDTO notificationDTO) {
        return notificationService.sendToGroup(groupName, notificationDTO);
    }

}
