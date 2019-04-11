package vn.com.nev.odoo.reporting.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.nev.odoo.reporting.dao.PartnerDao;
import vn.com.nev.odoo.reporting.model.Partner;

@Service
public class PartnerServiceImpl implements PartnerService {

  @Autowired
  private PartnerDao partnerDao;

  @Override
  public List<Partner> findAll() {
    return partnerDao.findAll();
  }
}
