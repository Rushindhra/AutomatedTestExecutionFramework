//
package com.myways.automatedtestexecutionframework.controller;
import java.io.File;
import java.io.FileInputStream;

import com.myways.automatedtestexecutionframework.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.nio.file.Path;
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/generate/html")
    public ResponseEntity<Resource> downloadHtml() throws Exception {
        File file = reportService.generateHtmlReport();
        return buildResponse(file, "text/html");
    }

    @GetMapping("/generate/csv")
    public ResponseEntity<Resource> downloadCsv() {
        File file = reportService.generateCsvReport();
        return buildResponse(file, "text/csv");
    }

    private ResponseEntity<Resource> buildResponse(File file, String contentType) {
        try {
            InputStreamResource resource =
                    new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + file.getName())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("File download failed");
        }
    }
}


//
//import com.myways.automatedtestexecutionframework.service.ReportService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.nio.file.Path;
//
//@RestController
//@RequestMapping("/reports")
//public class ReportController {
//
//
//
//    //*****************1
//    private final ReportService reportService;
//
//    public ReportController(ReportService reportService) {
//        this.reportService = reportService;
//    }
//
//
//
////    // -----------------------------------------------------
////    // Generate HTML Report
////    // -----------------------------------------------------
////    @GetMapping("/generate/html")
////    public ResponseEntity<?> generateHtml() {
////        try {
////            Path file = reportService.generateHtmlReport();
////            return ResponseEntity.ok(
////                    "HTML Report Generated Successfully\nFile: " + file.toAbsolutePath()
////            );
////        } catch (Exception e) {
////            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
////        }
////    }
////
////    // -----------------------------------------------------
////    // Generate CSV Report
////    // -----------------------------------------------------
////    @GetMapping("/generate/csv")
////    public ResponseEntity<?> generateCsv() {
////        try {
////            Path file = reportService.generateCsvReport();
////            return ResponseEntity.ok(
////                    "CSV Report Generated Successfully\nFile: " + file.toAbsolutePath()
////            );
////        } catch (Exception e) {
////            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
////        }
////    }
//}
