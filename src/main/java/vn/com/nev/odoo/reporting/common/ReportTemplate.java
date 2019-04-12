package vn.com.nev.odoo.reporting.common;

public enum  ReportTemplate {
    TS_REPORT("TS_Upload.xls"),
    PROFIT_REPORT("ProfitReport.xlsx");

    private String propertyName;

    ReportTemplate(String name) {
        this.propertyName = name;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}
