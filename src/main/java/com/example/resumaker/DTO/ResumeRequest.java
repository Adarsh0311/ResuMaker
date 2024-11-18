package com.example.resumaker.DTO;

import com.example.resumaker.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeRequest {
    private Contact contact;
    private WorkExperience[] workExperience;
    private Education[] education;
    private Projects[] projects;
    private Skills skills;


}
