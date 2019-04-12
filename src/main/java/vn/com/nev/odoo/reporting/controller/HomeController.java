package vn.com.nev.odoo.reporting.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.nev.odoo.reporting.common.Constants;
import vn.com.nev.odoo.reporting.common.DocumentResponse;
import vn.com.nev.odoo.reporting.common.exception.NEException;
import vn.com.nev.odoo.reporting.service.TsReportService;

@Controller
public class HomeController {
    @Autowired
    private Environment environment;

    @Autowired
    private TsReportService tsReportService;

//    @GetMapping
//    public String index(Model model) {
//
//        List<Partner> partners = partnerService.findAll();
//        model.addAttribute("partners", partners);
//
//        return "home/index";
//    }

    /**
     * @param id
     * @param reportType TS or Profit
     * @param response   http://localhost:8080/download?id=123&reportType=101
     */
    @GetMapping(value = "download", produces = Constants.APPLICATION_EXCEL)
    public void download(String id, String reportType, HttpServletResponse response) {
        // TODO: Authenticate and check role before generate report
        DocumentResponse doc;
        int rType = Integer.parseInt(reportType);
        switch (rType) {
            case 101:
            case 102:
            case 103:
                doc = tsReportService.generateTsReport(rType);
                break;
            default:
                doc = new DocumentResponse();
                break;
        }
        if (doc.getStatus()) {
            try {
                downloadFile(response, doc.getOutputPath(), doc.getOutputFilename());
            } catch (IOException e) {
                throw new NEException(e);
            }
        }
    }

    private void downloadFile(HttpServletResponse response, String outputPath, String filename) throws IOException {
        response.setContentType(Constants.APPLICATION_EXCEL);
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        File file = Paths.get(outputPath).toFile();
        response.setHeader("Content-Length", String.valueOf(file.length()));

        try (InputStream invoiceFile = new FileInputStream(file)) {
            // copy it to response's OutputStream
            FileCopyUtils.copy(invoiceFile, response.getOutputStream());
            response.flushBuffer();
        }
    }


}
