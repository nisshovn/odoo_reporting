package vn.com.nev.odoo.reporting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import vn.com.nev.odoo.reporting.common.NEUtils;
import vn.com.nev.odoo.reporting.common.TsColumns;
import vn.com.nev.odoo.reporting.model.TsRecord;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TsUploadDaoImpl implements TsUploadDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

//    private final String SQL_FIND_ALL = "SELECT * FROM res_partner WHERE id < :maxId";
    private final String SQL_SALES_REPORT = "SELECT 'HCM' as ofccd, 'PF' as dept";

    @Override
    public List<TsRecord> createSalesReport() {
        SqlParameterSource parameters = new MapSqlParameterSource();
//                .addValue("maxId", 50);

        return namedJdbcTemplate.queryForList(SQL_SALES_REPORT, parameters)
                .stream()
                .map(row -> {
                    TsRecord record = new TsRecord();

                    // Map DB's data stream to Java object
                    record.setOfccd(NEUtils.getString(row, TsColumns.OFCCD));
                    record.setDept(NEUtils.getString(row, TsColumns.DEPT));
                    record.setApl(NEUtils.getString(row, TsColumns.APL));
                    record.setStype(NEUtils.getString(row, TsColumns.STYPE));
                    record.setDtaid(NEUtils.getString(row, TsColumns.DTAID));
                    record.setBtr(NEUtils.getString(row, TsColumns.BTR));
                    record.setFcnty(NEUtils.getString(row, TsColumns.FCNTY));
                    record.setTcnty(NEUtils.getString(row, TsColumns.TCNTY));
                    record.setMcust(NEUtils.getString(row, TsColumns.MCUST));
                    record.setTsect(NEUtils.getString(row, TsColumns.TSECT));
                    record.setLino(NEUtils.getString(row, TsColumns.LINO));
                    record.setAtno(NEUtils.getString(row, TsColumns.ATNO));
                    record.setCont(NEUtils.getString(row, TsColumns.CONT));
                    record.setOrdr(NEUtils.getString(row, TsColumns.ORDR));
                    record.setActyy(NEUtils.getString(row, TsColumns.ACTYY));
                    record.setActmm(NEUtils.getString(row, TsColumns.ACTMM));
                    record.setActdd(NEUtils.getString(row, TsColumns.ACTDD));
                    record.setInvno(NEUtils.getString(row, TsColumns.INVNO));
                    record.setInvyy(NEUtils.getString(row, TsColumns.INVYY));
                    record.setInvmm(NEUtils.getString(row, TsColumns.INVMM));
                    record.setInvdd(NEUtils.getString(row, TsColumns.INVDD));
                    record.setCmdty(NEUtils.getString(row, TsColumns.CMDTY));
                    record.setWhcd(NEUtils.getString(row, TsColumns.WHCD));
                    record.setPytrm(NEUtils.getString(row, TsColumns.PYTRM));
                    record.setDueyy(NEUtils.getString(row, TsColumns.DUEYY));
                    record.setDuemm(NEUtils.getString(row, TsColumns.DUEMM));
                    record.setDuedd(NEUtils.getString(row, TsColumns.DUEDD));
                    record.setBank(NEUtils.getString(row, TsColumns.BANK));
                    record.setChqno(NEUtils.getString(row, TsColumns.CHQNO));
                    record.setDraft(NEUtils.getString(row, TsColumns.DRAFT));
                    record.setDscrp(NEUtils.getString(row, TsColumns.DSCRP));
                    record.setGname(NEUtils.getString(row, TsColumns.GNAME));
                    record.setAtnby(NEUtils.getString(row, TsColumns.ATNBY));
                    record.setAtnon(NEUtils.getString(row, TsColumns.ATNON));
                    record.setRtypb(NEUtils.getString(row, TsColumns.RTYPB));
                    record.setTrn(NEUtils.getString(row, TsColumns.TRN));
                    record.setFccd2(NEUtils.getString(row, TsColumns.FCCD2));
                    record.setFcam2(NEUtils.getString(row, TsColumns.FCAM2));
                    record.setFxrt2(NEUtils.getString(row, TsColumns.FXRT2));
                    record.setDrac(NEUtils.getString(row, TsColumns.DRAC));
                    record.setLamt(NEUtils.getString(row, TsColumns.LAMT));
                    record.setCrac(NEUtils.getString(row, TsColumns.CRAC));
                    record.setQty(NEUtils.getString(row, TsColumns.QTY));
                    record.setQtyun(NEUtils.getString(row, TsColumns.QTYUN));
                    record.setGstmk(NEUtils.getString(row, TsColumns.GSTMK));
                    record.setGstcd(NEUtils.getString(row, TsColumns.GSTCD));
                    record.setTaxmt(NEUtils.getString(row, TsColumns.TAXMT));
                    record.setGstrt(NEUtils.getString(row, TsColumns.GSTRT));
                    record.setOrgac(NEUtils.getString(row, TsColumns.ORGAC));
                    record.setSfxrt(NEUtils.getString(row, TsColumns.SFXRT));
                    record.setSivam(NEUtils.getString(row, TsColumns.SIVAM));
                    record.setSgsam(NEUtils.getString(row, TsColumns.SGSAM));
                    record.setRisk(NEUtils.getString(row, TsColumns.RISK));
                    record.setCrrnt(NEUtils.getString(row, TsColumns.CRRNT));
                    record.setIntcd(NEUtils.getString(row, TsColumns.INTCD));
                    record.setIntam(NEUtils.getString(row, TsColumns.INTAM));
                    record.setIntrt(NEUtils.getString(row, TsColumns.INTRT));
                    record.setDays(NEUtils.getString(row, TsColumns.DAYS));
                    record.setCtyp(NEUtils.getString(row, TsColumns.CTYP));
                    record.setStryy(NEUtils.getString(row, TsColumns.STRYY));
                    record.setStrmm(NEUtils.getString(row, TsColumns.STRMM));
                    record.setStrdd(NEUtils.getString(row, TsColumns.STRDD));
                    record.setEndyy(NEUtils.getString(row, TsColumns.ENDYY));
                    record.setEndmm(NEUtils.getString(row, TsColumns.ENDMM));
                    record.setEnddd(NEUtils.getString(row, TsColumns.ENDDD));
                    record.setBdept(NEUtils.getString(row, TsColumns.BDEPT));
                    record.setBcust(NEUtils.getString(row, TsColumns.BCUST));
                    record.setBcont(NEUtils.getString(row, TsColumns.BCONT));
                    record.setEdpno(NEUtils.getString(row, TsColumns.EDPNO));
                    record.setItmc1(NEUtils.getString(row, TsColumns.ITMC1));
                    record.setItmn1(NEUtils.getString(row, TsColumns.ITMN1));
                    record.setItmc2(NEUtils.getString(row, TsColumns.ITMC2));
                    record.setItmn2(NEUtils.getString(row, TsColumns.ITMN2));
                    record.setItmc3(NEUtils.getString(row, TsColumns.ITMC3));
                    record.setItmn3(NEUtils.getString(row, TsColumns.ITMN3));

                    return record;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TsRecord> createPurchasesReport() {
        return null;
    }

    @Override
    public List<TsRecord> createStockTransferReport() {
        return null;
    }
}
