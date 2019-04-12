package vn.com.nev.odoo.reporting.dao;

import vn.com.nev.odoo.reporting.model.TsRecord;

import java.util.List;

public interface TsUploadDao {

  List<TsRecord> createSalesReport();
  List<TsRecord> createPurchasesReport();
  List<TsRecord> createStockTransferReport();

}
