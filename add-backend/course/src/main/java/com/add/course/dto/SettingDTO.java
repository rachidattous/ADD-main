package com.add.course.dto;

import com.add.course.constants.Category;
import com.add.course.constants.Language;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SettingDTO {

    private String category;

    private String language;

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
