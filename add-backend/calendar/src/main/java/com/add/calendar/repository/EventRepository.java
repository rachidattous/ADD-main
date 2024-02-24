package com.add.calendar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.add.calendar.model.Event;

public interface EventRepository extends JpaRepository<Event, String> {

	public List<Event> findByStartGreaterThanAndEndLessThan(LocalDateTime start, LocalDateTime end);

	public List<Event> findByStartGreaterThan(LocalDateTime start);
}