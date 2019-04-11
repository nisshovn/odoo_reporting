package vn.com.nev.odoo.reporting.dao;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import vn.com.nev.odoo.reporting.common.ColumnNames;
import vn.com.nev.odoo.reporting.common.NEUtils;
import vn.com.nev.odoo.reporting.model.Partner;

@Repository
public class PartnerDaoImpl implements PartnerDao {

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  private final String SQL_FIND_ALL = "SELECT * FROM res_partner WHERE id < :maxId";

  @Override
  public List<Partner> findAll() {

    // TODO: sample to pass parameters to SQL query
    SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("maxId", 50);

    return namedJdbcTemplate.queryForList(SQL_FIND_ALL, parameters)
        .stream()
        .map(row -> {
          Partner partner = new Partner();

          // Map DB's data stream to Java object
          partner.setId(NEUtils.getInt(row, ColumnNames.ID));
          partner.setCompanyId(NEUtils.getInt(row, ColumnNames.COMPANY_ID));
          partner.setName(NEUtils.getString(row, ColumnNames.NAME));
          partner.setDisplayName(NEUtils.getString(row, ColumnNames.DISPLAY_NAME));
          partner.setStreet(NEUtils.getString(row, ColumnNames.STREET));
          partner.setZip(NEUtils.getString(row, ColumnNames.ZIP));
          partner.setCity(NEUtils.getString(row, ColumnNames.CITY));
          partner.setEmail(NEUtils.getString(row, ColumnNames.EMAIL));
          partner.setPhone(NEUtils.getString(row, ColumnNames.PHONE));

          return partner;
        })
        .collect(Collectors.toList());
  }

}
