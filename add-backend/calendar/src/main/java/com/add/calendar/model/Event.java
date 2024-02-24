package com.add.calendar.model;

import java.io.Serializable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.add.calendar.model.converter.DaysOfWeekConverter;
import com.add.calendar.validation.EventValidator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EventValidator
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE event SET deleted = true WHERE id=?")
public class Event implements Serializable {

    public static final String JOB_GROUP = "event-jobs";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @Version
    private Long version;

    private String text;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime start;

    @NotNull
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime end;

    @NotNull
    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    private String color;

    private String userId;

    @Builder.Default
    private boolean deleted = false;

    private boolean reccurent;

    @Convert(converter = DaysOfWeekConverter.class)
    private Set<DayOfWeek> days;

    public String daysToString() {
        return days.stream().map(e -> String.valueOf(e.ordinal() + 1)).sorted().collect(Collectors.joining(","));
    }
}
