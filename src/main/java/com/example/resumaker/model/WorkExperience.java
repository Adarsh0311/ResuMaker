package com.example.resumaker.model;

import lombok.Data;

@Data
public class WorkExperience {
    private String title;
    private String company;
    private String date;
    private String[] points; //describes bullet points in work description
    private String location;
}
