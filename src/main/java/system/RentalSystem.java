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
import sql.repositories.CarRepository;
import sql.repositories.CompletedRentalContractsRepository;
import sql.repositories.CustomerRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
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

            ui.showActionBox("Welcome to Kailua Car Rental V0.1!\n\nSelect an option:\n" +
                    "1: Rentals\n" +
                    "2: Customers\n" +
                    "3: View cars\n" +
                    "4: Repairs\n" +
                    "5: Exit");

            switch (Integer.parseInt(input.nextLine())) {
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
        ui.showActionBox("RENTAL MENU:\n" +
                "1: Create rental\n" +
                "2: View active rentals\n" +
                "3: View archive\n" +
                "4: Back");

        switch (Integer.parseInt(input.nextLine())) {
            case 1 -> createRental(input);
            case 2 -> viewActiveRentals(input);
            case 3 -> viewRentalArchive(input);
        }
    }

    private void showCustomerMenu(Scanner input) {
        ui.showActionBox("CUSTOMER MENU:\n" +
                "1: Create new customer\n" +
                "2: Edit existing customer\n" +
                "3: Back");

        switch (Integer.parseInt(input.nextLine())) {
            case 1 -> createCustomer(input);
            case 2 -> editCustomer(input);
        }
    }

    private void showCarMenu(Scanner input) {
        ui.showActionBox("CAR MENU:\n" +
                "1: Add/Change car\n" +
                "2: Remove car\n" +
                "3: View car overview\n" +
                "4: Back");
        switch (Integer.parseInt(input.nextLine())) {
            case 1 -> addCar(input);
            case 2 -> removeCar(input);
            case 3 -> viewCars(input);
        }
    }

    private void showRepairMenu(Scanner input) {
        ui.showActionBox("REPAIR MENU:\n" +
                "1: Schedule repair\n" +
                "2: View active repairs\n" +
                "3: View repair history\n" +
                "4: Back");

        switch (Integer.parseInt(input.nextLine())) {
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
                startDate = StringConverter.dateToTimestamp(StringConverter.toDate(strStartDate, "dd-MM-yyyy"));
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
                endDate = StringConverter.dateToTimestamp(StringConverter.toDate(strEndDate, "dd-MM-yyyy"));
                if (endDate == null) {
                    endDate = null;
                    strEndDate = "N/A";
                }
            }
            else {
                endDate = null;
                strEndDate = "N/A";
            }

            System.out.println("Enter the max amount of kilometers the customer is allowed to drive");
            System.out.print("Enter kilometers: ");
            int kilometers = Integer.parseInt(input.nextLine());

            double calculatedPrice = kilometers * car.getPricePerKilometer();

            if (ui.showYesNoDialogBox("RENTAL CONTRACT CONFIRMATION\n" +
                    "Customer: "+customer.getName() + "\n"+
                    "Car: " + car.getRegNumber() + "\n" +
                    "Pickup date: " + startDate + "\n" +
                    "Return date: " + strEndDate + "\n" +
                    "Max kilometers: " + kilometers + "\n" +
                    "Calculated price: " + calculatedPrice + "\n\n" +
                    "Confirm?", input)) {

                RentalContract contract = new RentalContract(car, customer, startDate, endDate, kilometers, calculatedPrice);
                new ActiveRentalContractsRepository().insert(contract);
                ui.showInfoBox("*** CONTRACT REGISTERED - THANK YOU ***");
                exit = true;

            }
        }
    }

    private Customer getCustomer(Scanner input) {
        Customer customer = null;
        System.out.println("What is the customers phone number? (Remember country code, eg. +45)");
        System.out.print("Enter Phone: ");
        String phoneNumber = input.nextLine();

        customer = new CustomerRepository().findFirstByPhonenumber(phoneNumber);

        if (customer == null) {
            if (ui.showYesNoDialogBox("Customer not found\nDo you wish to create them?", input)) {
                customer = createCustomer(input);

                if (customer != null) {
                    new CustomerRepository().insert(customer);
                }
            }
        }
        else {
            if (!ui.showYesNoDialogBox("Found this customer: \n\n" +
                    "Name: " + customer.getName() + "\n" +
                    "Address: " + customer.getAddress() + "\n" +
                    "Email: " + customer.getEmail() + "\n\n" +
                    "Is this correct?", input)) {
                customer = null;
            }
        }

        return customer;
    }

    private Car getCar(Scanner input) {

        Car car = null;
        boolean exit = false;
        while (!exit) {
            System.out.println("What is the cars Registration number:");
            System.out.print("Enter RegistrationNumber: ");
            String registrationNumber = input.nextLine();

            car = new CarRepository().findFirstAvailableById(registrationNumber);

            if (car == null) {
                exit = !ui.showYesNoDialogBox("Car not found or available\n\nDo you wish to make another search?", input);
            }
            else {
                exit = true;
            }
        }

        return car;
    }

    private void viewCars(Scanner input) {
        ui.showActionBox("Get an overview of all cars\n\n" +
                "1: View available cars\n" +
                "2: View all cars");

        switch (Integer.parseInt(input.nextLine())) {
            case 1 -> viewAllAvailableCars(input);
            case 2 -> viewAllCars(input);
        }
    }

    private void viewAllCars(Scanner input) {
        ui.showActionBox("What type of car would you like?\n1: Family\n2: Luxury\n3: Sport\n4: All");
        switch (input.nextLine().toLowerCase()) {
            case "1", "family":
                List<Car> familyCars = new CarRepository().getAllCarsOfType("Family Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(familyCars));
                break;

            case "2", "luxury":
                List<Car> luxuryCars = new CarRepository().getAllCarsOfType("Luxury Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(luxuryCars));
                break;

            case "3", "sport":
                List<Car> sportsCars = new CarRepository().getAllCarsOfType("Sports Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(sportsCars));
                break;

            case "4", "All":
                List<Car> allCars = new CarRepository().getAll();
                ui.showMultipleInfoBox(StringConverter.carListToStringList(allCars));
        }
    }

    private void viewAllAvailableCars(Scanner input) {
        ui.showActionBox("What type of car would you like?\n1: Family\n2: Luxury\n3: Sport\n4: All");
        switch (input.nextLine().toLowerCase()) {
            case "1", "family":
                List<Car> familyCars = new CarRepository().getAllAvailableCarsOfType("Family Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(familyCars));
                break;

            case "2", "luxury":
                List<Car> luxuryCars = new CarRepository().getAllAvailableCarsOfType("Luxury Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(luxuryCars));
                break;

            case "3", "sport":
                List<Car> sportsCars = new CarRepository().getAllAvailableCarsOfType("Sports Car");
                ui.showMultipleInfoBox(StringConverter.carListToStringList(sportsCars));
                break;

            case "4", "All":
                List<Car> allCars = new CarRepository().getAllAvailableCars();
                ui.showMultipleInfoBox(StringConverter.carListToStringList(allCars));
                break;
        }
    }

    private void addCar(Scanner input) {
        Car newCar = null;

        while (newCar == null) {
            ui.showInfoBox("Creating/Changing an entry for a car");

            System.out.println("Please enter the registration number for the car");
            System.out.print("Enter registration number: ");
            String regNumber = input.nextLine();

            System.out.println("Looking up license plate on DMR...");
            DMRCar car = new HttpHelper().getDMRCar(regNumber);

            if (car != null) {
                if (ui.showYesNoDialogBox("Car found!\n\n" +
                        "Car Brand: " + car.getMaerkeTypeNavn() + "\n" +
                        "Car Model: " + car.getModelTypeNavn() + "\n" +
                        "Car Variant: " + car.getVariantTypeNavn() + "\n\n" +
                        "Is this correct?", input)) {

                    System.out.println("Please enter the price per kilometer for the car");
                    System.out.print("Price per kilometer: ");
                    double pricePerKilometer = Double.parseDouble(input.nextLine());

                    System.out.println("Please enter car type (Luxury Car, Familiy Car, Sports Car)");
                    System.out.print("Car Type: ");
                    String carType = input.nextLine();

                    switch (carType.toLowerCase()) {
                        case "luxury car" -> newCar = new LuxuryCar(car, pricePerKilometer);
                        case "family car" -> newCar = new FamilyCar(car, pricePerKilometer);
                        case "sports car" -> newCar = new SportsCar(car, pricePerKilometer);
                        default -> System.out.println("Not a valid car type, please try again");
                    }
                }
            }
            else {
                if (!ui.showYesNoDialogBox("Car not found in DMR, would you like to make another search", input)) {
                    return;
                }
            }
        }
        System.out.println();
        System.out.println("Creating car, please wait...");
        new CarRepository().insert(newCar);
        ui.showInfoBox("Car with regnumber: " + newCar.getRegNumber() + "\n" +
                "has been created");
    }

    public void viewActiveRentals(Scanner input){
        ui.showInfoBox("Here is a list of active rentals: ");
        List<RentalContract> contracts = new ActiveRentalContractsRepository().getAll();
        ui.showMultipleInfoBox(StringConverter.contractListToStringList(contracts));

        ui.showActionBox("What action do you wish to perform?\n\n" +
                "1: Complete Rental\n" +
                "2: Create Rental\n" +
                "3: Back");

        switch (Integer.parseInt(input.nextLine())) {
            case 1 -> completeRental(input);
            case 2 -> createRental(input);
        }
    }

    private void completeRental(Scanner input) {
        ui.showInfoBox("Complete rental");
        System.out.println("Please enter the registration number for the car that is connected to the desired rental");
        System.out.print("Enter Registration number: ");
        String regNumber = input.nextLine();

        System.out.println("Please enter the amount of kilometers driven");
        System.out.print("Enter kilometers driven: ");
        int drivenKilometers = Integer.parseInt(input.nextLine());

        System.out.println("(Optional) Please enter the final price for the contract");
        System.out.print("Final price: ");
        String price = input.nextLine();
        double finalPrice;
        if (price == "") {
            System.out.println("Calculating price, please wait...");
            // Find car to get the price per kilometer
            finalPrice = new CarRepository().findFirstById(regNumber).getPricePerKilometer() * drivenKilometers;
            System.out.println("Calculated price: " + finalPrice);
        }
        else {
            finalPrice = Double.parseDouble(price);
        }

        System.out.println("Completing rental contract, please wait...");
        RentalContract contract = new ActiveRentalContractsRepository().findFirstById(regNumber);
        contract.setKilometers(drivenKilometers);
        contract.setPrice(finalPrice);
        new CompletedRentalContractsRepository().insert(contract);
        ui.showInfoBox("Contract successfully completed");
    }

    public void viewRentalArchive(Scanner input){
        ui.showActionBox("Rental Archive\n\n" +
                "1: Show the last amount completed rentals\n" +
                "2: Show all completed rentals with a specific car\n" +
                "3: Show all rentals from a specific start date\n" +
                "4: Show all completed rentals");

        switch (Integer.parseInt(input.nextLine())) {
            case 1 -> viewRentalArchiveShowTop(input);
            case 2 -> viewRentalArchiveFromCars(input);
            case 3 -> viewRentalArchiveFromStartDate(input);
            case 4 -> viewAllArchivedRentals(input);
        }
    }

    private void viewAllArchivedRentals(Scanner input) {
        ui.showInfoBox("View all archived rentals\n\n" +
                "Warning this may take a long time to load!");

        if (ui.showYesNoDialogBox("Are you sure you wish to continue?", input)) {

            List<RentalContract> contracts = new CompletedRentalContractsRepository().getAll();
            ui.showMultipleInfoBox(StringConverter.contractListToStringList(contracts));
            showRentalContractSummary(contracts);

        }
    }

    private void viewRentalArchiveFromStartDate(Scanner input) {
        ui.showInfoBox("Show all rentals started on a specific date");

        System.out.println("Please enter the start date in the following format: DD-MM-YYYY");
        System.out.print("Date: ");
        Timestamp startDate = StringConverter.dateToTimestamp(StringConverter.toDate(input.nextLine(), "dd-MM-yyyy"));

        List<RentalContract> contracts = new CompletedRentalContractsRepository().getAllOnSpecificDate(startDate);
        ui.showMultipleInfoBox(StringConverter.contractListToStringList(contracts));
        showRentalContractSummary(contracts);
    }

    private void viewRentalArchiveShowTop(Scanner input) {
        ui.showInfoBox("Show a selected amount of completed rentals");

        System.out.println("Please specify how many contracts you wish to see: ");
        System.out.print("Amount: ");
        int amount = Integer.parseInt(input.nextLine());

        List<RentalContract> contracts = new CompletedRentalContractsRepository().getAmount(amount);
        ui.showMultipleInfoBox(StringConverter.contractListToStringList(contracts));
        showRentalContractSummary(contracts);
    }

    private void viewRentalArchiveFromCars(Scanner input) {
        ui.showInfoBox("Show all completed rentals with a specific vehicle");

        System.out.println("Please enter registration number of vehicle");
        System.out.print("Enter registration number: ");
        String regNumber = input.nextLine();

        List<RentalContract> contracts = new CompletedRentalContractsRepository().getAllWithCarId(regNumber);
        ui.showMultipleInfoBox(StringConverter.contractListToStringList(contracts));
        showRentalContractSummary(contracts);
    }

    private void showRentalContractSummary(List<RentalContract> contracts) {

        double totalPrice = 0;
        int totalKilometersDriven = 0;

        for (RentalContract contract : contracts) {
            totalPrice += contract.getPrice();
            totalKilometersDriven += contract.getKilometers();
        }

        ui.showInfoBox("Summary\n\n" +
                "Total income: " + totalPrice + "\n" +
                "Total kilometers driven: " + totalKilometersDriven);

    }

    public Customer createCustomer(Scanner input){
        ui.showInfoBox("Create a customer");

        return null;
    }

    public void editCustomer(Scanner input){

    }
    public void removeCar(Scanner input){
        ui.showInfoBox("Remove a car from the database");

        String regNumber = "";
        Car car = null;
        while (car == null) {
            System.out.println("Please enter the registration number for the car to be removed");
            System.out.print("Registration number: ");
            regNumber = input.nextLine();

            car = new CarRepository().findFirstAvailableById(regNumber);
            if (car == null) {
                if (!ui.showYesNoDialogBox("Car not found in database or is not available\nWould you like to make another search?", input)) {
                    return;
                }
            }
        }

        if (ui.showYesNoDialogBox("Car found!\n\n" +
                "Car Brand: " + car.getBrand() + "\n" +
                "Car Model: " + car.getModel() + "\n" +
                "Car Variant: " + car.getVariant() + "\n\n" +
                "Is this correct?", input)) {

            if (ui.showYesNoDialogBox("Are you sure you wish to remove this car from the database?", input)) {
                new CarRepository().deleteOnId(regNumber);
            }
        }
    }
    public void scheduleRepair(Scanner input){

    }
    public void viewActiveRepairs(Scanner input){

    }
    public void viewRepairHistory(Scanner input){

    }
}
