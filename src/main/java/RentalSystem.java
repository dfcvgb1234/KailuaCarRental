import java.util.Scanner;

public class RentalSystem {
    private UI ui;

    public RentalSystem() {
        this.ui = new UI();
        mainMenu();
    }

    public void mainMenu(){
        Scanner input = new Scanner(System.in);
        boolean exit=false;
        ui.showInfoBox("Welcome to Kailua Car Rental V0.1!\n\nSelect an option:\n" +
                "1: Rentals\n" +
                "2: Customers\n" +
                "3: View cars\n" +
                "4: Repairs\n" +
                "5: Exit");
        while(exit==false){
            switch (input.nextInt()) {
                case 1:
                    ui.showInfoBox("RENTAL MENU:\n" +
                            "1: Create rental\n" +
                            "2: View active rentals\n" +
                            "3: View archive\n" +
                            "4: Back");
                    break;
                case 2:
                    ui.showInfoBox("CUSTOMER MENU:\n" +
                            "1: Create new customer\n" +
                            "2: Edit existing customer\n" +
                            "3: Back");
                    break;
                case 3:
                    ui.showInfoBox("CAR MENU:\n" +

                            //Enter something here to print all cars

                            "1: Add car\n" +
                            "2: Remove car\n" +
                            "3: Back");
                    break;
                case 4:
                    ui.showInfoBox("REPAIR MENU:\n" +
                            "1: Schedule repair\n" +
                            "2: View active repairs\n" +
                            "3: View repair history\n" +
                            "4: Back");
                    break;
                case 5:
                    System.out.println("Goodbye.");
                    exit=true;
                    break;
            }
        }
    }
}
