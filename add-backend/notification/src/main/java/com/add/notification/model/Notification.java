package com.add.notification.model;

import com.add.notification.constants.State;
import com.add.notification.util.DateUtility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import javax.persistence.Id;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE notification SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Notification {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String userId;

    private String content;

    @Builder.Default
    private boolean deleted = false;

    @Builder.Default
    private LocalDateTime date = DateUtility.nowDateTime();

    @Builder.Default()
    private State state = State.NOT_OPENED;
}
