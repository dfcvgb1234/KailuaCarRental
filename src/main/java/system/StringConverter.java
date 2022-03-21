package system;

import sql.components.cars.Car;

import java.sql.Date;
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

    public static Timestamp toTimestamp(String value, String format) {

        return Timestamp.valueOf(LocalDateTime.parse(value, DateTimeFormatter.ofPattern(format)));

    }

    public static ArrayList<String> carListToStringList(List<Car> cars) {
        ArrayList<String> values = new ArrayList<>();
        for (Car car : cars) {
            values.add(car.toString());
        }

        return values;
    }
}
