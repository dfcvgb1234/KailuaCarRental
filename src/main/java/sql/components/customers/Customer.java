package sql.components.customers;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String mobilePhone;
    private String phone;
    private String email;
    private DriverLicense license;

    public Customer(int id, String name, String address, String mobilePhone, String phone, String email, DriverLicense license) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.email = email;
        this.license = license;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DriverLicense getLicense() {
        return license;
    }

    public void setLicense(DriverLicense license) {
        this.license = license;
    }
}
