package vn.com.nev.odoo.reporting.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.nev.odoo.reporting.common.Constants;
import vn.com.nev.odoo.reporting.common.exception.NEException;
import vn.com.nev.odoo.reporting.model.Partner;
import vn.com.nev.odoo.reporting.service.PartnerService;

@Controller
public class HomeController {

  private static final String REPORT_TYPE_TS = "TS";

  @Autowired
  private Environment environment;

  @Autowired
  private PartnerService partnerService;

  @GetMapping
  public String index(Model model) {

    List<Partner> partners = partnerService.findAll();
    model.addAttribute("partners", partners);

    return "home/index";
  }

  /**
   *
   * @param id
   * @param reportType TS or Profit
   * @param response
   */
  @GetMapping(value = "download", produces = Constants.APPLICATION_EXCEL)
  public void download(String id, String reportType, HttpServletResponse response) {

    final String name = REPORT_TYPE_TS.equals(reportType) ? "TS_Upload" : "ProfitReport";
    final String ext = REPORT_TYPE_TS.equals(reportType) ? "xls" : "xlsx";

    final String templatePath = String.format("templates/excel/%s.%s", name, ext);
    final String outFilename = String.format("%s_%s.%s", name, id, ext);
    final String outputPath = Paths.get(environment.getProperty("application.path.output"), outFilename).toString();

    try {
      if (REPORT_TYPE_TS.equals(reportType)) {
        createTSReport(templatePath, outputPath);
      } else {
        createProfitReport(templatePath, outputPath);
      }

      downloadFile(response, outputPath, outFilename);
    } catch (IOException ex) {
      throw new NEException(ex);
    }
  }

  /**
   * Get template from TemplatePath, edit data and save to outputPath
   * @param templatePath
   * @param outputPath
   */
  private String createProfitReport(String templatePath, String outputPath) {

    Resource resource = new ClassPathResource(templatePath);

    try (Workbook workbook = WorkbookFactory.create(resource.getInputStream())) {

      Sheet worksheet = workbook.getSheetAt(1); // sheet Principal

      // Edit data in worksheet
      final int rowIndex = 599; // edit row 600
      int colIndex = 0;
      Row row = worksheet.getRow(rowIndex);
      if (row == null) {
        row = worksheet.createRow(rowIndex);
      }
      getCell(row, colIndex++).setCellValue("201902-201902"); // Col A: Year&Month
      getCell(row, colIndex++).setCellValue("HCM0"); // Col B: Office Code
      getCell(row, colIndex++).setCellValue("SB"); // Col C: Cell Code
      getCell(row, colIndex++).setCellValue("3234"); // Col D: Item Code
      getCell(row, colIndex++).setCellValue("ONIGIRI - COM NAM"); // Col E: Item Name
      getCell(row, colIndex++).setCellValue("SO033"); // Col F: Customer Code
      getCell(row, colIndex++).setCellValue("SOUTHERN AIRPORTS SERVICES JSC"); // Col G: Customer Name
      getCell(row, colIndex++).setCellValue("ONI2002"); // Col H: Contract No
      getCell(row, colIndex++).setCellValue("PC"); // Col I: Unit
      getCell(row, colIndex++).setCellValue("60"); // Col J: Quantity
      getCell(row, colIndex++).setCellValue("VD"); // Col K: Domestic Currency
      getCell(row, colIndex++).setCellValue("648000"); // Col L: Sales
      colIndex++;//      getCell(row, colIndex++).setCellValue("CONT_TEST"); // Col M: T.Comm
      getCell(row, colIndex++).setCellValue("604490"); // Col N: Cost
      getCell(row, colIndex++).setCellValue("43510"); // Col O: Profit
      getCell(row, colIndex++).setCellValue(0.067145061728395); // Col P: Profit/Sales
//      getCell(row, colIndex++).setCellValue("21"); // Col Q: Foreign Exchange/Profit and Loss

      // Save editing to output directory
      try (OutputStream outputStream = new FileOutputStream(outputPath)) {
        workbook.write(outputStream);
      }

      return outputPath;
    } catch (IOException e) {
      throw new NEException(e);
    }

  }

  private String createTSReport(String templatePath, String outputPath) {

    Resource resource = new ClassPathResource(templatePath);

    try (Workbook workbook = WorkbookFactory.create(resource.getInputStream())) {

      Sheet worksheet = workbook.getSheetAt(0); // sheet TEST

      // Edit data in worksheet
      final int rowIndex = 2; // edit row 3
      int colIndex = 0;
      Row row = worksheet.getRow(rowIndex);
      if (row == null) {
        row = worksheet.createRow(rowIndex);
      }
      getCell(row, colIndex++).setCellValue("HCM"); // Col A: OFCCD
      getCell(row, colIndex++).setCellValue("PG"); // Col B: DEPT
      getCell(row, colIndex++).setCellValue("00293"); // Col C: APL
      getCell(row, colIndex++).setCellValue("TT"); // Col D: STYPE
      getCell(row, colIndex++).setCellValue("TEST001"); // Col E: DTAID
      getCell(row, colIndex++).setCellValue("9"); // Col F: BTR
      getCell(row, colIndex++).setCellValue("724"); // Col G: FCNTY
      getCell(row, colIndex++).setCellValue("822"); // Col H: TCNTY
      getCell(row, colIndex++).setCellValue("6NW1P"); // Col I: MCUST
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col J: TSECT
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col K: LINO
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col L: ATNO
      getCell(row, colIndex++).setCellValue("CONT_TEST"); // Col M: CONT
      getCell(row, colIndex++).setCellValue("ORDER_TEST"); // Col N: ORDR
      getCell(row, colIndex++).setCellValue("2019"); // Col O: ACTYY
      getCell(row, colIndex++).setCellValue("02"); // Col P: ACTMM
      getCell(row, colIndex++).setCellValue("21"); // Col Q: ACTDD
      getCell(row, colIndex++).setCellValue("INV_TEST"); // Col R: INVNO
      getCell(row, colIndex++).setCellValue("2019"); // Col S: INVYY
      getCell(row, colIndex++).setCellValue("02"); // Col T: INVMM
      getCell(row, colIndex++).setCellValue("21"); // Col U: INVDD
      getCell(row, colIndex++).setCellValue("6019"); // Col V: CMDTY
      getCell(row, colIndex++).setCellValue("SA001"); // Col W: WHCD
      getCell(row, colIndex++).setCellValue("ZZ"); // Col X: PYTRM
      getCell(row, colIndex++).setCellValue("2019"); // Col Y: DUEYY
      getCell(row, colIndex++).setCellValue("05"); // Col Z: DUEMM
      getCell(row, colIndex++).setCellValue("21"); // Col AA: DUEDD
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AB: BANK
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AC: CHQNO
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AD: DRAFT
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AE: DSCRP
      getCell(row, colIndex++).setCellValue("Stock on hand/ABS XT-04"); // Col AF: GNAME
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AG: ATNBY
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AH: ATNON
      getCell(row, colIndex++).setCellValue("02"); // Col AI: RTYPB
      getCell(row, colIndex++).setCellValue("MDS"); // Col AJ: TRN
      getCell(row, colIndex++).setCellValue("UD"); // Col AK: FCCD2
      getCell(row, colIndex++).setCellValue("1000"); // Col AL: FCAM2
      getCell(row, colIndex++).setCellValue("23160"); // Col AM: FXRT2
      getCell(row, colIndex++).setCellValue("201"); // Col AN: DRAC
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AO: LAMT  (Formula)
      getCell(row, colIndex++).setCellValue("503"); // Col AP: CRAC
      getCell(row, colIndex++).setCellValue("2000"); // Col AQ: QTY
      getCell(row, colIndex++).setCellValue("KG"); // Col AR: QTYUN
      getCell(row, colIndex++).setCellValue("X"); // Col AS: GSTMK
      getCell(row, colIndex++).setCellValue("UD"); // Col AT: GSTCD
      getCell(row, colIndex++).setCellValue("0"); // Col AU: TAXMT
      getCell(row, colIndex++).setCellValue("0"); // Col AV: GSTRT
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AW: ORGAC
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AX: SFXRT
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col AY: SIVAM (Formula)
      getCell(row, colIndex++).setCellValue("0"); // Col AZ: SGSAM
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col BA: RISK
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col BB: CRRNT
      colIndex++;//      getCell(row, colIndex++).setCellValue("xxx"); // Col BC: INTCD
      getCell(row, colIndex++).setCellValue("0"); // Col BD: INTAM
      getCell(row, colIndex++).setCellValue("0"); // Col BE: INTRT
      getCell(row, colIndex++).setCellValue("0"); // Col BF: DAYS
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BG: CTYP
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BH: STRYY
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BI: STRMM
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BJ: STRDD
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BK: ENDYY
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BL: ENDMM
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BM: ENDDD
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BN: BDEPT
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BO: BCUST
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BP: BCONT
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BQ: EDPNO
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BR: ITMC1
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BS: ITMN1
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BT: ITMC2
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BU: ITMN2
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BV: ITMC3
//      getCell(row, colIndex++).setCellValue("xxx"); // Col BW: ITMN3

      // Save editing to output directory
      try (OutputStream outputStream = new FileOutputStream(outputPath)) {
        workbook.write(outputStream);
      }

      return outputPath;
    } catch (IOException e) {
      throw new NEException(e);
    }

  }

  private Cell getCell(Row row, int cellIndex) {
    Cell cell = row.getCell(cellIndex);
    if (cell == null) {
      cell = row.createCell(cellIndex);
    }

    return cell;
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
