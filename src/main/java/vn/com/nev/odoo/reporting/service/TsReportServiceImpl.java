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
        setCell(colIndex++, row, record.getOfccd());  // Col: OFCCD
        setCell(colIndex++, row, record.getDept());  // Col: DEPT
        setCell(colIndex++, row, record.getApl());  // Col: APL
        setCell(colIndex++, row, record.getStype());  // Col: STYPE
        setCell(colIndex++, row, record.getDtaid());  // Col: DTAID
        setCell(colIndex++, row, record.getBtr());  // Col: BTR
        setCell(colIndex++, row, record.getFcnty());  // Col: FCNTY
        setCell(colIndex++, row, record.getTcnty());  // Col: TCNTY
        setCell(colIndex++, row, record.getMcust());  // Col: MCUST
        setCell(colIndex++, row, record.getTsect());  // Col: TSECT
        setCell(colIndex++, row, record.getLino());  // Col: LINO
        setCell(colIndex++, row, record.getAtno());  // Col: ATNO
        setCell(colIndex++, row, record.getCont());  // Col: CONT
        setCell(colIndex++, row, record.getOrdr());  // Col: ORDR
        setCell(colIndex++, row, record.getActyy());  // Col: ACTYY
        setCell(colIndex++, row, record.getActmm());  // Col: ACTMM
        setCell(colIndex++, row, record.getActdd());  // Col: ACTDD
        setCell(colIndex++, row, record.getInvno());  // Col: INVNO
        setCell(colIndex++, row, record.getInvyy());  // Col: INVYY
        setCell(colIndex++, row, record.getInvmm());  // Col: INVMM
        setCell(colIndex++, row, record.getInvdd());  // Col: INVDD
        setCell(colIndex++, row, record.getCmdty());  // Col: CMDTY
        setCell(colIndex++, row, record.getWhcd());  // Col: WHCD
        setCell(colIndex++, row, record.getPytrm());  // Col: PYTRM
        setCell(colIndex++, row, record.getDueyy());  // Col: DUEYY
        setCell(colIndex++, row, record.getDuemm());  // Col: DUEMM
        setCell(colIndex++, row, record.getDuedd());  // Col: DUEDD
        setCell(colIndex++, row, record.getBank());  // Col: BANK
        setCell(colIndex++, row, record.getChqno());  // Col: CHQNO
        setCell(colIndex++, row, record.getDraft());  // Col: DRAFT
        setCell(colIndex++, row, record.getDscrp());  // Col: DSCRP
        setCell(colIndex++, row, record.getGname());  // Col: GNAME
        setCell(colIndex++, row, record.getAtnby());  // Col: ATNBY
        setCell(colIndex++, row, record.getAtnon());  // Col: ATNON
        setCell(colIndex++, row, record.getRtypb());  // Col: RTYPB
        setCell(colIndex++, row, record.getTrn());  // Col: TRN
        setCell(colIndex++, row, record.getFccd2());  // Col: FCCD2
        setCell(colIndex++, row, record.getFcam2());  // Col: FCAM2
        setCell(colIndex++, row, record.getFxrt2());  // Col: FXRT2
        setCell(colIndex++, row, record.getDrac());  // Col: DRAC
        setCell(colIndex++, row, record.getLamt());  // Col: LAMT
        setCell(colIndex++, row, record.getCrac());  // Col: CRAC
        setCell(colIndex++, row, record.getQty());  // Col: QTY
        setCell(colIndex++, row, record.getQtyun());  // Col: QTYUN
        setCell(colIndex++, row, record.getGstmk());  // Col: GSTMK
        setCell(colIndex++, row, record.getGstcd());  // Col: GSTCD
        setCell(colIndex++, row, record.getTaxmt());  // Col: TAXMT
        setCell(colIndex++, row, record.getGstrt());  // Col: GSTRT
        setCell(colIndex++, row, record.getOrgac());  // Col: ORGAC
        setCell(colIndex++, row, record.getSfxrt());  // Col: SFXRT
        setCell(colIndex++, row, record.getSivam());  // Col: SIVAM
        setCell(colIndex++, row, record.getSgsam());  // Col: SGSAM
        setCell(colIndex++, row, record.getRisk());  // Col: RISK
        setCell(colIndex++, row, record.getCrrnt());  // Col: CRRNT
        setCell(colIndex++, row, record.getIntcd());  // Col: INTCD
        setCell(colIndex++, row, record.getIntam());  // Col: INTAM
        setCell(colIndex++, row, record.getIntrt());  // Col: INTRT
        setCell(colIndex++, row, record.getDays());  // Col: DAYS
        setCell(colIndex++, row, record.getCtyp());  // Col: CTYP
        setCell(colIndex++, row, record.getStryy());  // Col: STRYY
        setCell(colIndex++, row, record.getStrmm());  // Col: STRMM
        setCell(colIndex++, row, record.getStrdd());  // Col: STRDD
        setCell(colIndex++, row, record.getEndyy());  // Col: ENDYY
        setCell(colIndex++, row, record.getEndmm());  // Col: ENDMM
        setCell(colIndex++, row, record.getEnddd());  // Col: ENDDD
        setCell(colIndex++, row, record.getBdept());  // Col: BDEPT
        setCell(colIndex++, row, record.getBcust());  // Col: BCUST
        setCell(colIndex++, row, record.getBcont());  // Col: BCONT
        setCell(colIndex++, row, record.getEdpno());  // Col: EDPNO
        setCell(colIndex++, row, record.getItmc1());  // Col: ITMC1
        setCell(colIndex++, row, record.getItmn1());  // Col: ITMN1
        setCell(colIndex++, row, record.getItmc2());  // Col: ITMC2
        setCell(colIndex++, row, record.getItmn2());  // Col: ITMN2
        setCell(colIndex++, row, record.getItmc3());  // Col: ITMC3
        setCell(colIndex, row, record.getItmn3());  // Col: ITMN3
    }

    private void setCell(int colIndex, Row row, String strVal) {
        if (strVal != null && !strVal.isEmpty())
            getCell(row, colIndex).setCellValue(strVal);
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
