package vn.com.nev.odoo.reporting.common;

public enum ColumnNames {

  CITY("city"),
  COMPANY_ID("company_id"),
  DISPLAY_NAME("display_name"),
  EMAIL("email"),
  ID("id"),
  NAME("name"),
  PHONE("phone"),
  STREET("street"),
  ZIP("zip");

  private String propertyName;

  ColumnNames(String name) {
    this.propertyName = name;
  }

  @Override
  public String toString() {
    return propertyName;
  }
}
