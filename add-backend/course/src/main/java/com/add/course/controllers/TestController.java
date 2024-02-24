package com.add.course.controllers;

import com.add.course.repository.CourseRepository;
import com.add.course.repository.WeekRepository;
import com.add.course.services.impl.WeekService;
import org.springframework.web.bind.annotation.*;

// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

  private final CourseRepository courseRepository;
  private final WeekRepository weekRepository;

  private final WeekService weekService;

  @GetMapping("/hi")
  // @SecurityRequirement(name = "Bearer Authentication")
  public String getHi() {
    return "Hi";
  }


}
