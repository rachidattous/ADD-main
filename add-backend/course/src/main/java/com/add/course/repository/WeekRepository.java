package com.add.course.repository;

import com.add.course.model.Activity;
import com.add.course.model.Week;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeekRepository extends JpaRepository<Week, String> {

    Optional<Week> findWeekByActivities(Activity activity);

}
