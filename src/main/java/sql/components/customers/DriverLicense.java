package sql.components.customers;

import java.sql.Date;

public class DriverLicense {

    private int licenseNumber;
    private String licenseName;
    private Date startDate;
    private Date expirationDate;
    private Date birthday;
    private String countryOfOrigin;

    public DriverLicense(int licenseNumber, String licenseName, Date startDate, Date expirationDate, Date birthday, String countryOfOrigin) {
        this.licenseNumber = licenseNumber;
        this.licenseName = licenseName;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.birthday = birthday;
        this.countryOfOrigin = countryOfOrigin;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }
}
