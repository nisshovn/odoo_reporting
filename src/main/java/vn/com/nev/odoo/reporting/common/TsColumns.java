package vn.com.nev.odoo.reporting.common;

public enum TsColumns {

    OFCCD("ofccd"),
    DEPT("dept"),
    APL("apl"),
    STYPE("stype"),
    DTAID("dtaid"),
    BTR("btr"),
    FCNTY("fcnty"),
    TCNTY("tcnty"),
    MCUST("mcust"),
    TSECT("tsect"),
    LINO("lino"),
    ATNO("atno"),
    CONT("cont"),
    ORDR("ordr"),
    ACTYY("actyy"),
    ACTMM("actmm"),
    ACTDD("actdd"),
    INVNO("invno"),
    INVYY("invyy"),
    INVMM("invmm"),
    INVDD("invdd"),
    CMDTY("cmdty"),
    WHCD("whcd"),
    PYTRM("pytrm"),
    DUEYY("dueyy"),
    DUEMM("duemm"),
    DUEDD("duedd"),
    BANK("bank"),
    CHQNO("chqno"),
    DRAFT("draft"),
    DSCRP("dscrp"),
    GNAME("gname"),
    ATNBY("atnby"),
    ATNON("atnon"),
    RTYPB("rtypb"),
    TRN("trn"),
    FCCD2("fccd2"),
    FCAM2("fcam2"),
    FXRT2("fxrt2"),
    DRAC("drac"),
    LAMT("lamt"),
    CRAC("crac"),
    QTY("qty"),
    QTYUN("qtyun"),
    GSTMK("gstmk"),
    GSTCD("gstcd"),
    TAXMT("taxmt"),
    GSTRT("gstrt"),
    ORGAC("orgac"),
    SFXRT("sfxrt"),
    SIVAM("sivam"),
    SGSAM("sgsam"),
    RISK("risk"),
    CRRNT("crrnt"),
    INTCD("intcd"),
    INTAM("intam"),
    INTRT("intrt"),
    DAYS("days"),
    CTYP("ctyp"),
    STRYY("stryy"),
    STRMM("strmm"),
    STRDD("strdd"),
    ENDYY("endyy"),
    ENDMM("endmm"),
    ENDDD("enddd"),
    BDEPT("bdept"),
    BCUST("bcust"),
    BCONT("bcont"),
    EDPNO("edpno"),
    ITMC1("itmc1"),
    ITMN1("itmn1"),
    ITMC2("itmc2"),
    ITMN2("itmn2"),
    ITMC3("itmc3"),
    ITMN3("itmn3");

    private String propertyName;

    TsColumns(String name) {
        this.propertyName = name;
    }

    @Override
    public String toString() {
        return propertyName;
    }

}
