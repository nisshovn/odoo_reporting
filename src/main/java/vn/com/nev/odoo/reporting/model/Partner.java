package vn.com.nev.odoo.reporting.model;

public class Partner {

  private int id;
  private int companyId;
  private String name;
  private String displayName;


  private String street;
  private String zip;
  private String city;

  private String email;
  private String phone;

  public Partner() {
  }

  @Override
  public String toString() {
    return "Partner{" +
        "id=" + id +
        ", companyId=" + companyId +
        ", name=" + name +
        ", displayName=" + displayName +
        ", street=" + street +
        ", zip=" + zip +
        ", city=" + city +
        ", email=" + email +
        ", phone=" + phone +
        "}";
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
