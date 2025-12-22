//
package com.myways.automatedtestexecutionframework.service;
//
//import com.myways.automatedtestexecutionframework.entity.TestExecution;
//import com.myways.automatedtestexecutionframework.repository.TestExecutionRepository;
//import com.myways.automatedtestexecutionframework.util.ReportGenerator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.nio.file.Path;
//import java.util.List;
//
//@Service
//public class ReportService {
//
//    private final TestExecutionRepository repo;
//
//    @Value("${app.reports.path:./reports}")
//    private String reportsPath;
//
//    public ReportService(TestExecutionRepository repo) {
//        this.repo = repo;
//    }
//
//    // -------- Generate HTML report --------
//    public Path generateHtmlReport() throws Exception {
//        List<TestExecution> list = repo.findAll();
//        if (list.isEmpty()) {
//            throw new RuntimeException("No test executions found to generate report.");
//        }
//        return ReportGenerator.generateHtmlReport(list, Path.of(reportsPath));
//    }
//
//    // -------- Generate CSV report --------
//    public Path generateCsvReport() throws Exception {
//        List<TestExecution> list = repo.findAll();
//        if (list.isEmpty()) {
//            throw new RuntimeException("No test executions found to generate report.");
//        }
//        return ReportGenerator.generateCsvReport(list, Path.of(reportsPath));
//    }
//}


import com.myways.automatedtestexecutionframework.entity.TestExecution;
import com.myways.automatedtestexecutionframework.repository.TestExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private TestExecutionRepository executionRepo;

    public File generateHtmlReport() {

        List<TestExecution> list = executionRepo.findAll();
        File file = new File("reports/execution-report.html");

        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h2>Automation Execution Report</h2>");
        html.append("<table border='1'>");
        html.append("<tr><th>ID</th><th>Test</th><th>Status</th><th>Started</th><th>Completed</th></tr>");

        for (TestExecution e : list) {
            html.append("<tr>")
                    .append("<td>").append(e.getId()).append("</td>")
                    .append("<td>").append(e.getTriggeredBy()).append("</td>")
                    .append("<td>").append(e.getStatus()).append("</td>")
                    .append("<td>").append(e.getStartedAt()).append("</td>")
                    .append("<td>").append(e.getCompletedAt()).append("</td>")
                    .append("</tr>");
        }

        html.append("</table></body></html>");

        write(file, html.toString());
        return file;
    }

    public File generateCsvReport() {

        List<TestExecution> list = executionRepo.findAll();
        File file = new File("reports/execution-report.csv");

        StringBuilder csv = new StringBuilder();
        csv.append("ID,Test,Status,Started,Completed\n");

        for (TestExecution e : list) {
            csv.append(e.getId()).append(",")
                    .append(e.getTriggeredBy()).append(",")
                    .append(e.getStatus()).append(",")
                    .append(e.getStartedAt()).append(",")
                    .append(e.getCompletedAt()).append("\n");
        }

        write(file, csv.toString());
        return file;
    }

    private void write(File file, String content) {
        try {
            file.getParentFile().mkdirs();
            Files.write(file.toPath(), content.getBytes());//Cannot resolve symbol 'Files'
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report");
        }
    }
}
