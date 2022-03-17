package system;
import sql.components.cars.Car;
import sql.components.customers.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        while(exit==false){
            ui.showInfoBox("Welcome to Kailua Car Rental V0.1!\n\nSelect an option:\n" +
                    "1: Rentals\n" +
                    "2: Customers\n" +
                    "3: View cars\n" +
                    "4: Repairs\n" +
                    "5: Exit");
            switch (input.nextInt()) {
                case 1:
                    ui.showInfoBox("RENTAL MENU:\n" +
                            "1: Create rental\n" +
                            "2: View active rentals\n" +
                            "3: View archive\n" +
                            "4: Back");
                    int menuSelection = input.nextInt();
                    if(menuSelection==1){
                        createRental(input);
                    }else if(menuSelection==2){
                        viewActiveRentals(input);
                    }else if(menuSelection==3){
                        viewRentalArchive(input);
                    }
                    break;
                case 2:
                    ui.showInfoBox("CUSTOMER MENU:\n" +
                            "1: Create new customer\n" +
                            "2: Edit existing customer\n" +
                            "3: Back");
                    int menuSelection2 = input.nextInt();
                    if(menuSelection2==1){
                        createCustomer(input);
                    }else if(menuSelection2==2){
                        editCustomer(input);
                    }
                    break;
                case 3:
                    ui.showInfoBox("CAR MENU:\n" +

                            //Enter something here to print all cars

                            "1: Add car\n" +
                            "2: Remove car\n" +
                            "3: Back");
                    int menuSelection3 = input.nextInt();
                    if(menuSelection3==1){
                        addCar(input);
                    }else if(menuSelection3==2){
                        removeCar(input);
                    }
                    break;
                case 4:
                    ui.showInfoBox("REPAIR MENU:\n" +
                            "1: Schedule repair\n" +
                            "2: View active repairs\n" +
                            "3: View repair history\n" +
                            "4: Back");
                    int menuSelection4 = input.nextInt();
                    if(menuSelection4==1){
                        scheduleRepair(input);
                    }else if(menuSelection4==2){
                        viewActiveRepairs(input);
                    }else if(menuSelection4==3){
                        viewRepairHistory(input);
                    }
                    break;
                case 5:
                    System.out.println("Goodbye.");
                    exit=true;
                    break;
            }
        }
    }
    public void createRental(Scanner input) throws ParseException {
        boolean exit = false;
        Customer customer = null;
        Car car = null;
        Date startDate = null;
        Date endDate = null;
        int phoneNumber;
        int nextSwitch = 1;
        double finalPrice;
        System.out.println("You are about to create a new rental. Please enter the required information");
        while(exit==false) {
            switch (nextSwitch) {

                //First case to check if customer exists, and otherwise create new customer
                case 1:
                    System.out.println("What is the customers phone number? (Remember country code, eg. 45)");
                    System.out.print("ENTER PHONE: ");
                    phoneNumber = input.nextInt();
                    if (false) { //Placeholder. Replace false with boolean method to check if phone number exists in system
                        //customer = createCustomer(input); (pseudo)
                        //Customer.addCustomerToDatabase(customer); (pseudo)
                        nextSwitch = 2;
                    } else {
                        System.out.println("Customer found in database. Confirm info to proceed\n1: Confirm\n2: No");
                        if (input.nextInt() == 1) {
                            //customer == getCustomer (pseudo)
                            nextSwitch = 2;
                        } else {
                            System.out.println("Operation will cancel. Either create a new customer with another phone number, or edit the existing one.");
                            exit=true;
                        }
                    }
                    break;

                // Displaying cars
                case 2:
                    System.out.print("What type of car would the customer like?\n1: Family\n2: Luxury\n3: Sport\n\nEnter an option: ");
                    int carType = input.nextInt();
                    if(carType==1){
                        //print all family cars
                        nextSwitch=3;
                    }else if(carType==2){
                        //print all luxury cars
                        nextSwitch=3;
                    }else if(carType==3){
                        //print all sports cars
                        nextSwitch=3;
                    }
                    break;

                // Selecting a car
                case 3:
                    System.out.print("Enter registration plate to continue: ");
                    //input.nextLine(); (pseudo) to get rid of the fucking leftover return from nextInt();
                    //car = getCarByLicensePlate(input.nextLine()); (pseudo)
                    if(car==null){ //Replace with !=null when the above is fixed. The reason this is done, is otherwise the program goes fucking ham when testing the UI
                        //System.out.println(car.toString); (pseudo)
                        System.out.println("Confirm car selection?\n1: Confirm\n2: Try again");
                        if(input.nextInt()==1){nextSwitch=4;}
                    }else{System.out.println("Registration plate not found in database. Try again");}
                    break;

                //Entering dates
                case 4:
                    System.out.println("Enter the start date for the rental in the following format: DD-MM-YYYY");
                    input.nextLine(); //to get rid of the leftover return from nextInt();
                    startDate = new SimpleDateFormat("dd-MM-yyyy").parse(input.nextLine());
                    System.out.println("Enter the end date for the rental in the following format: DD-MM-YYYY");
                    endDate = new SimpleDateFormat("dd-MM-yyyy").parse(input.nextLine());
                    System.out.println("The following timespan has been selected:\n\n" +
                            "PICKUP DATE: " + startDate.toString() + "\n" +
                            "RETURN DATE: " + endDate.toString() + "\n\n" +
                            "Confirm?\n1: Yes\n2: No");
                    if(input.nextInt()==1){
                        nextSwitch=5;
                    }
                    break;

                //Confirm price with customer
                case 5:
                    ui.showInfoBox("RENTAL CONTRACT CONFIRMATION\n" +
                            "Customer: "+customer.toString()+"\n"+
                            "Car: " + car.toString() + "\n" +
                            "Pickup date: " + startDate.toString() + "\n" +
                            "Return date: " + endDate.toString() + "\n\n" +
                            "Confirm?\n1: Yes\n2: Cancel");
                    if(input.nextInt()==1){
                        //Something something add contract to database; (pseudo)
                        System.out.println("*** CONTRACT REGISTERED - THANK YOU ***");
                        exit=true;
                    }else if(input.nextInt()==2){
                        System.out.println("Operation cancelled.");
                        exit=true;
                    }
                    break;
            }
        }
    }

    public void viewActiveRentals(Scanner input){

    }
    public void viewRentalArchive(Scanner input){

    }
    public void createCustomer(Scanner input){

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
