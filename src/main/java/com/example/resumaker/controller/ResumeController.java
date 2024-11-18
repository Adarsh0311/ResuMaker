package com.example.resumaker.controller;

import com.example.resumaker.DTO.ResumeRequest;
import com.example.resumaker.service.PDFGeneratorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResumeController {

    private final PDFGeneratorService pdfGeneratorService;

    public ResumeController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }


    @PostMapping("/resume")
    public ResponseEntity<byte[]> createResume(@RequestBody ResumeRequest resume) {
        byte[] bytes = pdfGeneratorService.generatePDF(resume);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}
