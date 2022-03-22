package sql.components;

public class Mechanic {

    private String name;
    private String address;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Mechanic(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nAddress: " + address + "\nPhonenumber: " + phoneNumber;
    }
}
