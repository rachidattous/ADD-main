package com.add.course.model;

import com.add.course.constants.Category;
import com.add.course.constants.Language;
import com.add.course.constants.State;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalTime createdTime;

    @UpdateTimestamp
    private LocalDate lastModifiedDate;

    @UpdateTimestamp
    private LocalTime lastModifiedTime;

    @Version
    private Long version;

    private String title;

    private String summary;

    @Builder.Default
    private Category category = Category.A;

    @Builder.Default
    private Language language = Language.ENGLISH;

    private String imageId;

    private String userId;

    @NonNull
    @Builder.Default
    private State state = State.DRAFT;

    @OneToMany
    private List<Week> weeks;

    @OneToOne
    private CourseSetting courseSetting;
}
