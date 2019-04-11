package vn.com.nev.odoo.reporting.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import vn.com.nev.odoo.reporting.model.Partner;

public interface PartnerDao {

  List<Partner> findAll();

}
