package com.learnx.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discussion {
    private int id;
    private Course course;
    private AppUserDto appUserDto;
    private String title;
    private String content;
}
