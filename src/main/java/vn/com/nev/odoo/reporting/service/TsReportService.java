package vn.com.nev.odoo.reporting.service;

import vn.com.nev.odoo.reporting.common.DocumentResponse;

public interface TsReportService {

  DocumentResponse generateTsReport(int reportType);

}
