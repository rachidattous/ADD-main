package com.add.notification.repository;

import com.add.notification.constants.State;
import com.add.notification.model.Notification;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    Page<Notification> findByUserIdAndDeleted(String userId, boolean isDeleted, Pageable pageable);

    List<Notification> findByUserIdAndDeletedAndState(String userId, boolean isDeleted, State state);

    Long countByUserIdAndDeletedAndState(String userId, boolean isDeleted, State state);

}
