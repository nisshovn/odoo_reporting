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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TsUploadDaoImpl implements TsUploadDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

//    private final String SQL_FIND_ALL = "SELECT * FROM res_partner WHERE id < :maxId";
    private final String SQL_SALES_REPORT = "SELECT 'HCM' as ofccd, 'PF' as dept, '00###' as apl, 'TT' as stype, " +
        "'ODOO #00000003' as dtaid, '6' as btr, '725' as fcnty, '822' as tcnty, 'FO006' as mcust, '' as tsect, " +
        "'' as lino, '' as atno, 'FBN006603' as cont, 'FB-145009-SP' as ordr, '2019' as actyy, '03' as actmm, " +
        "'08' as actdd, 'D701012-03' as invno, '2019' as invyy, '03' as invmm, '08' as invdd, '5752' as cmdty, " +
        "'' as whcd, 'ZZ' as pytrm, '2019' as dueyy, '05' as duemm, '31' as duedd, '' as bank, '' as chqno, " +
        "'' as draft, '#00000003' as dscrp, '' as gname, '' as atnby, '' as atnon, '01' as rtypb, 'MDS' as trn, " +
        "x_cd as fccd2, '63196500' as fcam2, '1' as fxrt2, '' as drac, '63196500' as lamt, '741' as crac, " +
        "'500' as qty, 'KG' as qtyun, 'T' as gstmk, '' as gstcd, '' as taxmt, '' as gstrt, '' as orgac, " +
        "'1' as sfxrt, '63196500' as sivam, '6319650' as sgsam, '' as risk, '' as crrnt, '' as intcd, '' as intam, " +
        "'' as intrt,'' as days, '' as ctyp, '' as stryy, '' as strmm, '' as strdd, '' as endyy, '' as endmm, " +
        "'' as enddd, '' as bdept, '' as bcust, '' as bcont, '' as edpno, '' as itmc1, '' as itmn1, '' as itmc2," +
        "'' as itmn2, '' as itmc3, '' as itmn3 FROM res_currency WHERE name like '%USD%'";

    private final String SQL_SALES_REPORT_1 = "SELECT 'HCM' as ofccd,'PF' as dept,'00###' as apl,'TT' as stype,'ODOO #00000003' as dtaid,'6' as btr,'725' as fcnty,'822' as tcnty,'FO006' as mcust,'' as tsect,'' as lino,'' as atno,'FBN006603' as cont,'FB-145009-SP' as ordr,'2019' as actyy,'03' as actmm,'08' as actdd,'D701012-03' as invno,'2019' as invyy,'03' as invmm,'08' as invdd,'5752' as cmdty,'' as whcd,'ZZ' as pytrm,'2019' as dueyy,'05' as duemm,'31' as duedd,'' as bank,'' as chqno,'' as draft,'#00000003' as dscrp,'' as gname,'' as atnby,'' as atnon,'02' as rtypb,'VAT' as trn,'VD' as fccd2,'6319650' as fcam2,'1' as fxrt2,'' as drac,'6319650' as lamt,'528' as crac,'0' as qty,'' as qtyun,'' as gstmk,'' as gstcd,'' as taxmt,'' as gstrt,'' as orgac,'' as sfxrt,'' as sivam,'' as sgsam,'' as risk,'' as crrnt,'' as intcd,'' as intam,'' as intrt,'' as days,'' as ctyp,'' as stryy,'' as strmm,'' as strdd,'' as endyy,'' as endmm,'' as enddd,'' as bdept,'' as bcust,'' as bcont,'' as edpno,'' as itmc1,'' as itmn1,'' as itmc2,'' as itmn2,'' as itmc3,'' as itmn3";
    private final String SQL_SALES_REPORT_2 = "SELECT 'HCM' as ofccd,'PF' as dept,'00###' as apl,'TT' as stype,'ODOO #00000003' as dtaid,'6' as btr,'725' as fcnty,'822' as tcnty,'FO006' as mcust,'' as tsect,'' as lino,'' as atno,'FBN006603' as cont,'FB-145009-SP' as ordr,'2019' as actyy,'03' as actmm,'08' as actdd,'D701012-03' as invno,'2019' as invyy,'03' as invmm,'08' as invdd,'5752' as cmdty,'' as whcd,'ZZ' as pytrm,'2019' as dueyy,'05' as duemm,'31' as duedd,'' as bank,'' as chqno,'' as draft,'#00000003' as dscrp,'' as gname,'' as atnby,'' as atnon,'03' as rtypb,'MDS' as trn,'VD' as fccd2,'69516150' as fcam2,'1' as fxrt2,'131' as drac,'69516150' as lamt,'' as crac,'0' as qty,'' as qtyun,'' as gstmk,'' as gstcd,'' as taxmt,'' as gstrt,'' as orgac,'' as sfxrt,'' as sivam,'' as sgsam,'' as risk,'' as crrnt,'' as intcd,'' as intam,'' as intrt,'' as days,'' as ctyp,'' as stryy,'' as strmm,'' as strdd,'' as endyy,'' as endmm,'' as enddd,'' as bdept,'' as bcust,'' as bcont,'' as edpno,'' as itmc1,'' as itmn1,'' as itmc2,'' as itmn2,'' as itmc3,'' as itmn3";
    private final String SQL_SALES_REPORT_3 = "SELECT 'HCM' as ofccd,'PF' as dept,'00###' as apl,'TT' as stype,'ODOO #00000003' as dtaid,'6' as btr,'725' as fcnty,'822' as tcnty,'FO006' as mcust,'' as tsect,'' as lino,'' as atno,'FBN006603' as cont,'FB-145009-SP' as ordr,'2019' as actyy,'03' as actmm,'08' as actdd,'D701012-03' as invno,'2019' as invyy,'03' as invmm,'08' as invdd,'5752' as cmdty,'' as whcd,'ZZ' as pytrm,'2019' as dueyy,'05' as duemm,'31' as duedd,'' as bank,'' as chqno,'' as draft,'#00000003' as dscrp,'' as gname,'' as atnby,'' as atnon,'04' as rtypb,'MDS' as trn,'UD' as fccd2,'916' as fcam2,'23150' as fxrt2,'731' as drac,'21205400' as lamt,'' as crac,'200' as qty,'KG' as qtyun,'X' as gstmk,'' as gstcd,'' as taxmt,'' as gstrt,'' as orgac,'23150' as sfxrt,'21205400' as sivam,'0' as sgsam,'' as risk,'' as crrnt,'' as intcd,'' as intam,'' as intrt,'' as days,'' as ctyp,'' as stryy,'' as strmm,'' as strdd,'' as endyy,'' as endmm,'' as enddd,'' as bdept,'831007' as bcust,'' as bcont,'' as edpno,'' as itmc1,'' as itmn1,'' as itmc2,'' as itmn2,'' as itmc3,'' as itmn3";
    private final String SQL_SALES_REPORT_4 = "SELECT 'HCM' as ofccd,'PF' as dept,'00###' as apl,'TT' as stype,'ODOO #00000003' as dtaid,'6' as btr,'725' as fcnty,'822' as tcnty,'FO006' as mcust,'' as tsect,'' as lino,'' as atno,'FBN006603' as cont,'FB-145009-SP' as ordr,'2019' as actyy,'03' as actmm,'08' as actdd,'D701012-03' as invno,'2019' as invyy,'03' as invmm,'08' as invdd,'5752' as cmdty,'' as whcd,'ZZ' as pytrm,'2019' as dueyy,'05' as duemm,'31' as duedd,'' as bank,'' as chqno,'' as draft,'#00000003' as dscrp,'' as gname,'' as atnby,'' as atnon,'05' as rtypb,'MDS' as trn,'UD' as fccd2,'1407' as fcam2,'23150' as fxrt2,'731' as drac,'32572050' as lamt,'' as crac,'300' as qty,'KG' as qtyun,'X' as gstmk,'' as gstcd,'' as taxmt,'' as gstrt,'' as orgac,'23150' as sfxrt,'32572050' as sivam,'0' as sgsam,'' as risk,'' as crrnt,'' as intcd,'' as intam,'' as intrt,'' as days,'' as ctyp,'' as stryy,'' as strmm,'' as strdd,'' as endyy,'' as endmm,'' as enddd,'' as bdept,'831007' as bcust,'' as bcont,'' as edpno,'' as itmc1,'' as itmn1,'' as itmc2,'' as itmn2,'' as itmc3,'' as itmn3";
    private final String SQL_SALES_REPORT_5 = "SELECT 'HCM' as ofccd,'PF' as dept,'00###' as apl,'TT' as stype,'ODOO #00000003' as dtaid,'6' as btr,'725' as fcnty,'822' as tcnty,'FO006' as mcust,'' as tsect,'' as lino,'' as atno,'FBN006603' as cont,'FB-145009-SP' as ordr,'2019' as actyy,'03' as actmm,'08' as actdd,'D701012-03' as invno,'2019' as invyy,'03' as invmm,'08' as invdd,'5752' as cmdty,'' as whcd,'ZZ' as pytrm,'2019' as dueyy,'05' as duemm,'31' as duedd,'' as bank,'' as chqno,'' as draft,'#00000003' as dscrp,'' as gname,'' as atnby,'' as atnon,'06' as rtypb,'MDS' as trn,'UD' as fccd2,'916' as fcam2,'23150' as fxrt2,'' as drac,'21205400' as lamt,'201' as crac,'200' as qty,'KG' as qtyun,'X' as gstmk,'' as gstcd,'' as taxmt,'' as gstrt,'' as orgac,'23150' as sfxrt,'21205400' as sivam,'0' as sgsam,'' as risk,'' as crrnt,'' as intcd,'' as intam,'' as intrt,'' as days,'' as ctyp,'' as stryy,'' as strmm,'' as strdd,'' as endyy,'' as endmm,'' as enddd,'' as bdept,'' as bcust,'SGW1810GW' as bcont,'' as edpno,'09' as itmc1,'SA001' as itmn1,'12' as itmc2,'010219' as itmn2,'' as itmc3,'' as itmn3";
    private final String SQL_SALES_REPORT_6 = "SELECT 'HCM' as ofccd,'PF' as dept,'00###' as apl,'TT' as stype,'ODOO #00000003' as dtaid,'6' as btr,'725' as fcnty,'822' as tcnty,'FO006' as mcust,'' as tsect,'' as lino,'' as atno,'FBN006603' as cont,'FB-145009-SP' as ordr,'2019' as actyy,'03' as actmm,'08' as actdd,'D701012-03' as invno,'2019' as invyy,'03' as invmm,'08' as invdd,'5752' as cmdty,'' as whcd,'ZZ' as pytrm,'2019' as dueyy,'05' as duemm,'31' as duedd,'' as bank,'' as chqno,'' as draft,'#00000003' as dscrp,'' as gname,'' as atnby,'' as atnon,'07' as rtypb,'MDS' as trn,'UD' as fccd2,'1407' as fcam2,'23150' as fxrt2,'' as drac,'32572050' as lamt,'201' as crac,'300' as qty,'KG' as qtyun,'X' as gstmk,'' as gstcd,'' as taxmt,'' as gstrt,'' as orgac,'23150' as sfxrt,'32572050' as sivam,'0' as sgsam,'' as risk,'' as crrnt,'' as intcd,'' as intam,'' as intrt,'' as days,'' as ctyp,'' as stryy,'' as strmm,'' as strdd,'' as endyy,'' as endmm,'' as enddd,'' as bdept,'' as bcust,'SGW1902GW' as bcont,'' as edpno,'09' as itmc1,'SA001' as itmn1,'12' as itmc2,'010219' as itmn2,'' as itmc3,'' as itmn3";

    private final List<String> sql_Sales = Arrays.asList(SQL_SALES_REPORT, SQL_SALES_REPORT_1, SQL_SALES_REPORT_2,
            SQL_SALES_REPORT_3, SQL_SALES_REPORT_4, SQL_SALES_REPORT_5, SQL_SALES_REPORT_6);


    @Override
    public List<TsRecord> createSalesReport() {
        ArrayList result = new ArrayList();

        for (String query : sql_Sales) {
            SqlParameterSource parameters = new MapSqlParameterSource();
//                .addValue("maxId", 50);

            List<TsRecord> lst = namedJdbcTemplate.queryForList(query, parameters)
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
            result.addAll(lst);
        }
        return result;
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
