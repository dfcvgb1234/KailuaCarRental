import rest.DMRCar;
import rest.HttpHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new HttpHelper().getDMRCar("AK49576");
        UI ui = new UI();
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> test = new ArrayList<String>();
        test.add("test 123");
        test.add("test test 456");
        test.add("Bil er fundet med følgende informationer:\nAUDI BAUDI\n200 mil");

        ui.showMultipleInfoBox(test);
        ui.showInfoBox("Bil er fundet med følgende informationer:\nAUDI BAUDI\n200 mil");
    }
}
