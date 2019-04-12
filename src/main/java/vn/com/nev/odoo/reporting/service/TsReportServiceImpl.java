package vn.com.nev.odoo.reporting.service;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import vn.com.nev.odoo.reporting.common.DocumentResponse;
import vn.com.nev.odoo.reporting.common.ReportTemplate;
import vn.com.nev.odoo.reporting.common.exception.NEException;
import vn.com.nev.odoo.reporting.dao.TsUploadDao;
import vn.com.nev.odoo.reporting.model.TsRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class TsReportServiceImpl implements TsReportService {

    @Autowired
    private Environment environment;

    @Autowired
    private TsUploadDao tsUploadDao;

    @Override
    public DocumentResponse generateTsReport(int reportType) {
        final String templatePath = String.format("templates/excel/%s", ReportTemplate.TS_REPORT);

        DocumentResponse response;
        switch (reportType) {
            case 101: // Sale report
                response = generateSaleReport(templatePath);
                break;
            case 102: // Purchase report
                response = generatePurchaseReport(templatePath);
                break;
            case 103: // Stock transfer report
                response = generateStockTransferReport(templatePath);
                break;
            default:
                response = new DocumentResponse();
                break;
        }
        return response;
    }

    private DocumentResponse generateSaleReport(String templatePath) {
        DocumentResponse result = new DocumentResponse();

        // Get data from DAL
        List<TsRecord> records = tsUploadDao.createSalesReport();
        if (!records.isEmpty()) {
            Resource resource = new ClassPathResource(templatePath);
            final String outFilename = String.format("%s_%s", "Sales", ReportTemplate.TS_REPORT);
            final String outputPath = Paths.get(environment.getProperty("application.path.output"), outFilename).toString();

            try (Workbook workbook = WorkbookFactory.create(resource.getInputStream())) {

                Sheet worksheet = workbook.getSheetAt(0);
                int rowIndex = 1;
                int colIndex = 0;

                // Write data to rows in the worksheet
                for (TsRecord record:records) {
                    createTsRecordRow(worksheet, record, rowIndex, colIndex);

                    //Go to the next row and reset column index
                    rowIndex++;
                    colIndex = 0;
                }

                // Save editing to output directory
                try (OutputStream outputStream = new FileOutputStream(outputPath)) {
                    workbook.write(outputStream);
                }
            } catch (IOException e) {
                throw new NEException(e);
            }
            result.setStatus(Boolean.TRUE);
            result.setOutputPath(outputPath);
            result.setOutputFilename(outFilename);
        }
        return result;
    }

    private void createTsRecordRow(Sheet worksheet, TsRecord record, int rowIndex, int colIndex) {
        Row row = worksheet.getRow(rowIndex);
        if (row == null) {
            row = worksheet.createRow(rowIndex);
        }

        // Write value for each cell in row
        getCell(row, colIndex++).setCellValue(record.getOfccd());  // Col: OFCCD
        getCell(row, colIndex++).setCellValue(record.getDept());  // Col: DEPT
        getCell(row, colIndex++).setCellValue(record.getApl());  // Col: APL
        getCell(row, colIndex++).setCellValue(record.getStype());  // Col: STYPE
        getCell(row, colIndex++).setCellValue(record.getDtaid());  // Col: DTAID
        getCell(row, colIndex++).setCellValue(record.getBtr());  // Col: BTR
        getCell(row, colIndex++).setCellValue(record.getFcnty());  // Col: FCNTY
        getCell(row, colIndex++).setCellValue(record.getTcnty());  // Col: TCNTY
        getCell(row, colIndex++).setCellValue(record.getMcust());  // Col: MCUST
        getCell(row, colIndex++).setCellValue(record.getTsect());  // Col: TSECT
        getCell(row, colIndex++).setCellValue(record.getLino());  // Col: LINO
        getCell(row, colIndex++).setCellValue(record.getAtno());  // Col: ATNO
        getCell(row, colIndex++).setCellValue(record.getCont());  // Col: CONT
        getCell(row, colIndex++).setCellValue(record.getOrdr());  // Col: ORDR
        getCell(row, colIndex++).setCellValue(record.getActyy());  // Col: ACTYY
        getCell(row, colIndex++).setCellValue(record.getActmm());  // Col: ACTMM
        getCell(row, colIndex++).setCellValue(record.getActdd());  // Col: ACTDD
        getCell(row, colIndex++).setCellValue(record.getInvno());  // Col: INVNO
        getCell(row, colIndex++).setCellValue(record.getInvyy());  // Col: INVYY
        getCell(row, colIndex++).setCellValue(record.getInvmm());  // Col: INVMM
        getCell(row, colIndex++).setCellValue(record.getInvdd());  // Col: INVDD
        getCell(row, colIndex++).setCellValue(record.getCmdty());  // Col: CMDTY
        getCell(row, colIndex++).setCellValue(record.getWhcd());  // Col: WHCD
        getCell(row, colIndex++).setCellValue(record.getPytrm());  // Col: PYTRM
        getCell(row, colIndex++).setCellValue(record.getDueyy());  // Col: DUEYY
        getCell(row, colIndex++).setCellValue(record.getDuemm());  // Col: DUEMM
        getCell(row, colIndex++).setCellValue(record.getDuedd());  // Col: DUEDD
        getCell(row, colIndex++).setCellValue(record.getBank());  // Col: BANK
        getCell(row, colIndex++).setCellValue(record.getChqno());  // Col: CHQNO
        getCell(row, colIndex++).setCellValue(record.getDraft());  // Col: DRAFT
        getCell(row, colIndex++).setCellValue(record.getDscrp());  // Col: DSCRP
        getCell(row, colIndex++).setCellValue(record.getGname());  // Col: GNAME
        getCell(row, colIndex++).setCellValue(record.getAtnby());  // Col: ATNBY
        getCell(row, colIndex++).setCellValue(record.getAtnon());  // Col: ATNON
        getCell(row, colIndex++).setCellValue(record.getRtypb());  // Col: RTYPB
        getCell(row, colIndex++).setCellValue(record.getTrn());  // Col: TRN
        getCell(row, colIndex++).setCellValue(record.getFccd2());  // Col: FCCD2
        getCell(row, colIndex++).setCellValue(record.getFcam2());  // Col: FCAM2
        getCell(row, colIndex++).setCellValue(record.getFxrt2());  // Col: FXRT2
        getCell(row, colIndex++).setCellValue(record.getDrac());  // Col: DRAC
        getCell(row, colIndex++).setCellValue(record.getLamt());  // Col: LAMT
        getCell(row, colIndex++).setCellValue(record.getCrac());  // Col: CRAC
        getCell(row, colIndex++).setCellValue(record.getQty());  // Col: QTY
        getCell(row, colIndex++).setCellValue(record.getQtyun());  // Col: QTYUN
        getCell(row, colIndex++).setCellValue(record.getGstmk());  // Col: GSTMK
        getCell(row, colIndex++).setCellValue(record.getGstcd());  // Col: GSTCD
        getCell(row, colIndex++).setCellValue(record.getTaxmt());  // Col: TAXMT
        getCell(row, colIndex++).setCellValue(record.getGstrt());  // Col: GSTRT
        getCell(row, colIndex++).setCellValue(record.getOrgac());  // Col: ORGAC
        getCell(row, colIndex++).setCellValue(record.getSfxrt());  // Col: SFXRT
        getCell(row, colIndex++).setCellValue(record.getSivam());  // Col: SIVAM
        getCell(row, colIndex++).setCellValue(record.getSgsam());  // Col: SGSAM
        getCell(row, colIndex++).setCellValue(record.getRisk());  // Col: RISK
        getCell(row, colIndex++).setCellValue(record.getCrrnt());  // Col: CRRNT
        getCell(row, colIndex++).setCellValue(record.getIntcd());  // Col: INTCD
        getCell(row, colIndex++).setCellValue(record.getIntam());  // Col: INTAM
        getCell(row, colIndex++).setCellValue(record.getIntrt());  // Col: INTRT
        getCell(row, colIndex++).setCellValue(record.getDays());  // Col: DAYS
        getCell(row, colIndex++).setCellValue(record.getCtyp());  // Col: CTYP
        getCell(row, colIndex++).setCellValue(record.getStryy());  // Col: STRYY
        getCell(row, colIndex++).setCellValue(record.getStrmm());  // Col: STRMM
        getCell(row, colIndex++).setCellValue(record.getStrdd());  // Col: STRDD
        getCell(row, colIndex++).setCellValue(record.getEndyy());  // Col: ENDYY
        getCell(row, colIndex++).setCellValue(record.getEndmm());  // Col: ENDMM
        getCell(row, colIndex++).setCellValue(record.getEnddd());  // Col: ENDDD
        getCell(row, colIndex++).setCellValue(record.getBdept());  // Col: BDEPT
        getCell(row, colIndex++).setCellValue(record.getBcust());  // Col: BCUST
        getCell(row, colIndex++).setCellValue(record.getBcont());  // Col: BCONT
        getCell(row, colIndex++).setCellValue(record.getEdpno());  // Col: EDPNO
        getCell(row, colIndex++).setCellValue(record.getItmc1());  // Col: ITMC1
        getCell(row, colIndex++).setCellValue(record.getItmn1());  // Col: ITMN1
        getCell(row, colIndex++).setCellValue(record.getItmc2());  // Col: ITMC2
        getCell(row, colIndex++).setCellValue(record.getItmn2());  // Col: ITMN2
        getCell(row, colIndex++).setCellValue(record.getItmc3());  // Col: ITMC3
        getCell(row, colIndex).setCellValue(record.getItmn3());  // Col: ITMN3
    }

    private DocumentResponse generatePurchaseReport(String templatePath) {
        return null;
    }

    private DocumentResponse generateStockTransferReport(String templatePath) {
        return null;
    }

    private Cell getCell(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }
        return cell;
    }
}
