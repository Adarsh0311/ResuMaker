package com.example.resumaker.model;

import lombok.Data;

@Data
public class Education {
    private String university;
    private String degree;
    private String date;
    private String[] relevantCourses;
    private String location;
}
