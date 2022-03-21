package system;
import rest.DMRCar;
import rest.HttpHelper;
import sql.components.RentalContract;
import sql.components.cars.Car;
import sql.components.cars.FamilyCar;
import sql.components.cars.LuxuryCar;
import sql.components.cars.SportsCar;
import sql.components.customers.Customer;
import sql.repositories.ActiveRentalContractsRepository;
import sql.repositories.ActiveRepairsRepository;
import sql.repositories.CarRepository;
import sql.repositories.CustomerRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RentalSystem {
    private UI ui;

    public RentalSystem() throws ParseException {
        this.ui = new UI();
        mainMenu();
    }

    public void mainMenu() throws ParseException {

        Scanner input = new Scanner(System.in);

        boolean exit=false;
        while(!exit){

            ui.showInfoBox("Welcome to Kailua Car Rental V0.1!\n\nSelect an option:\n" +
                    "1: Rentals\n" +
                    "2: Customers\n" +
                    "3: View cars\n" +
                    "4: Repairs\n" +
                    "5: Exit");

            switch (input.nextInt()) {
                case 1 -> showRentalMenu(input);
                case 2 -> showCustomerMenu(input);
                case 3 -> showCarMenu(input);
                case 4 -> showRepairMenu(input);
                case 5 -> {
                    System.out.println("Goodbye.");
                    exit = true;
                }
            }
        }
    }

    private void showRentalMenu(Scanner input) {
        ui.showInfoBox("RENTAL MENU:\n" +
                "1: Create rental\n" +
                "2: View active rentals\n" +
                "3: View archive\n" +
                "4: Back");

        switch (input.nextInt()) {
            case 1 -> createRental(input);
            case 2 -> viewActiveRentals(input);
            case 3 -> viewRentalArchive(input);
        }
    }

    private void showCustomerMenu(Scanner input) {
        ui.showInfoBox("CUSTOMER MENU:\n" +
                "1: Create new customer\n" +
                "2: Edit existing customer\n" +
                "3: Back");

        switch (input.nextInt()) {
            case 1 -> createCustomer(input);
            case 2 -> editCustomer(input);
        }
    }

    private void showCarMenu(Scanner input) {
        ui.showInfoBox("CAR MENU:\n" +
                "1: Add car\n" +
                "2: Remove car\n" +
                "3: Back");
        switch (input.nextInt()) {
            case 1 -> addCar(input);
            case 2 -> removeCar(input);
        }
    }

    private void showRepairMenu(Scanner input) {
        ui.showInfoBox("REPAIR MENU:\n" +
                "1: Schedule repair\n" +
                "2: View active repairs\n" +
                "3: View repair history\n" +
                "4: Back");
        switch (input.nextInt()) {
            case 1 -> scheduleRepair(input);
            case 2 -> viewActiveRepairs(input);
            case 3 -> viewRepairHistory(input);
        }
    }

    public void createRental(Scanner input) {
        boolean exit = false;

        while (!exit) {

            ui.showInfoBox("You are about to create a new rental. Please enter the required information");
            Customer customer = getCustomer(input);
            if (customer == null) {
                ui.showInfoBox("You have to specify a customer for a rental contract, exiting...");
                return;
            }

            viewAllAvailableCars(input);
            Car car = getCar(input);
            if (car == null) {
                ui.showInfoBox("You have to specify a car for a rental contract, exiting...");
                return;
            }

            // Get startdate timestamp, you can skip this value by pressing enter and not entering a value
            // Default value will be current timestamp
            Timestamp startDate = null;
            System.out.println("(Optional) Enter the start date for the rental in the following format: DD-MM-YYYY");
            String strStartDate = input.nextLine();
            if (strStartDate != "") {
                startDate = StringConverter.toTimestamp(strStartDate, "dd-mm-yyyy");
                if (startDate == null) {
                    startDate = Timestamp.from(Instant.now());
                }
            }
            else {
                startDate = Timestamp.from(Instant.now());
            }

            // Get enddate timestamp, you can skip this value by pressing enter and not entering a value
            // Default value will be null
            Timestamp endDate = null;
            System.out.println("(Optional) Enter the end date for the rental in the following format: DD-MM-YYYY");
            String strEndDate = input.nextLine();
            if (strEndDate != "") {
                endDate = StringConverter.toTimestamp(strEndDate, "dd-mm-yyyy");
                if (endDate == null) {
                    strEndDate = "N/A";
                }
            }
            else {
                strEndDate = "N/A";
            }

            System.out.println("Enter the max amount of kilometers the customer is allowed to drive");
            System.out.print("Enter kilometers: ");
            int kilometers = input.nextInt();

            double calculatedPrice = kilometers * car.getPricePerKilometer();

            ui.showInfoBox("RENTAL CONTRACT CONFIRMATION\n" +
                    "Customer: "+customer.getName() + "\n"+
                    "Car: " + car.getRegNumber() + "\n" +
                    "Pickup date: " + strStartDate + "\n" +
                    "Return date: " + strEndDate + "\n" +
                    "Max kilometers: " + kilometers + "\n" +
                    "Calculated price: " + calculatedPrice + "\n\n" +
                    "Confirm?\n1: Yes\n2: Cancel");

            switch (input.nextInt()) {
                case 1:
                    RentalContract contract = new RentalContract(car, customer, startDate, endDate, kilometers, calculatedPrice);
                    new ActiveRentalContractsRepository().insert(contract);
                    ui.showInfoBox("*** CONTRACT REGISTERED - THANK YOU ***");
                    exit = true;
                    break;
            }
        }
    }

    private Customer getCustomer(Scanner input) {
        System.out.println("What is the customers phone number? (Remember country code, eg. +45)");
        System.out.print("ENTER Phone: ");
        String phoneNumber = input.nextLine();

        Customer customer = new CustomerRepository().findFirstByPhonenumber(phoneNumber);

        if (customer == null) {
            if (ui.showYesNoDialogBox("Customer not found\nDo you wish to create them?", input)) {
                customer = createCustomer(input);
                new CustomerRepository().insert(customer);
            }
        }

        return customer;
    }

    private Car getCar(Scanner input) {
        Car car = null;
        while (car == null) {
            System.out.println("What is the cars Registration number:");
            System.out.print("ENTER RegistrationNumber: ");
            String registrationNumber = input.nextLine();

            car = new CarRepository().findFirstAvailableCarById(registrationNumber);

            if (car == null) {
                System.out.println("Car not found or available, please try again..");
            }
        }

        return car;
    }

    private void viewAllAvailableCars(Scanner input) {
        System.out.print("What type of car would the customer like?\n1: Family\n2: Luxury\n3: Sport\n\nEnter an option: ");
        switch (input.nextInt()) {
            case 1:
                List<Car> familyCars = new CarRepository().getAllCarsOfType("Family Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(familyCars));
                break;

            case 2:
                List<Car> luxuryCars = new CarRepository().getAllCarsOfType("Luxury Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(luxuryCars));
                break;

            case 3:
                List<Car> sportsCars = new CarRepository().getAllCarsOfType("Sports Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(sportsCars));
                break;
        }
    }

    private void createCar(Scanner input) {
        Car newCar = null;

        while (newCar == null) {
            ui.showInfoBox("Creating an entry for a new car");

            System.out.println("Please enter the registration number for the new car");
            System.out.print("Enter registration number: ");
            String regNumber = input.nextLine();

            System.out.println("Looking up license plate on DMR...");
            DMRCar car = new HttpHelper().getDMRCar(regNumber);

            if (car != null) {
                if (ui.showYesNoDialogBox("Car found!\n\n+" +
                        "Car Brand: " + car.getMaerkeTypeNavn() + "\n" +
                        "Car Model: " + car.getModelTypeNavn() + "\n" +
                        "Car Variant: " + car.getVariantTypeNavn() + "\n\n" +
                        "Is this correct?", input)) {

                    System.out.println("Please enter car type: ");
                    System.out.print("Car Type: ");
                    String carType = input.nextLine();

                    System.out.println("Please enter the price per kilometer for the car");
                    System.out.print("Price per kilometer: ");
                    int pricePerKilometer = input.nextInt();

                    switch (carType.toLowerCase()) {
                        case "luxury car" -> newCar = new LuxuryCar(car, pricePerKilometer);
                        case "family car" -> newCar = new FamilyCar(car, pricePerKilometer);
                        case "sports car" -> newCar = new SportsCar(car, pricePerKilometer);
                    }
                }
            }
            else {
                System.out.println("Car not found in DMR, please try again");
            }
        }
        new CarRepository().insert(newCar);
        ui.showInfoBox("Car with regnumber: " + newCar.getRegNumber() + "\n" +
                "has been created");
    }

    public void viewActiveRentals(Scanner input){

    }
    public void viewRentalArchive(Scanner input){

    }
    public Customer createCustomer(Scanner input){

    }
    public void editCustomer(Scanner input){

    }
    public void addCar(Scanner input){

    }
    public void removeCar(Scanner input){

    }
    public void scheduleRepair(Scanner input){

    }
    public void viewActiveRepairs(Scanner input){

    }
    public void viewRepairHistory(Scanner input){

    }
}
