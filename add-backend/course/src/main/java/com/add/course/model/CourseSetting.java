package com.add.course.model;

import com.add.course.constants.Category;
import com.add.course.constants.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseSetting {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Builder.Default
    private Category category = Category.A;

    @Builder.Default
    private Language language = Language.ENGLISH;

    private int requiredScore;
    private int durationHour;

    private int durationMinute;

    private int bronzeMin;

    private int bronzeMax;

    private int silverMin;

    private int silverMax;

    private int goldMin;

    private int goldMax;

}
