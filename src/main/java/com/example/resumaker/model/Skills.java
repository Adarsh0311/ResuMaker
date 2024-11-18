package com.example.resumaker.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Skills {
    Map<String, List<String>> skills;
}
