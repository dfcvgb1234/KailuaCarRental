package system;

import sql.components.Mechanic;
import sql.components.RentalContract;
import sql.components.Repair;
import sql.components.cars.Car;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StringConverter {

    public static Date toDate(String value, String format) {

        return Date.valueOf(LocalDate.parse(value, DateTimeFormatter.ofPattern(format)));

    }

    public static Date toDateFromDMRDate(String value) {
        String datePart = value.split("T")[0];
        return toDate(datePart, "yyyy-MM-dd");
    }

    public static Timestamp dateToTimestamp(Date value) {

        return new Timestamp(value.getTime());

    }

    public static ArrayList<String> carListToStringList(List<Car> cars) {
        ArrayList<String> values = new ArrayList<>();
        for (Car car : cars) {
            values.add(car.toString());
        }

        return values;
    }

    public static ArrayList<String> contractListToStringList(List<RentalContract> contracts) {
        ArrayList<String> values = new ArrayList<>();
        for (RentalContract contract : contracts) {
            values.add(contract.toString());
        }

        return values;
    }

    public static ArrayList<String> repairListToStringList(List<Repair> repairs) {
        ArrayList<String> values = new ArrayList<>();
        for (Repair repair : repairs) {
            values.add(repairs.toString());
        }

        return values;
    }

    public static ArrayList<String> mechanicListToStringList(List<Mechanic> mechanics) {
        ArrayList<String> values = new ArrayList<>();
        for (Mechanic mechanic : mechanics) {
            values.add(mechanics.toString());
        }

        return values;
    }
}
