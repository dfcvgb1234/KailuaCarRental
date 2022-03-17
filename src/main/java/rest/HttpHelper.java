package rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;


public class HttpHelper {

    public void getDMRCar(String regNumber) {
        try {

            var uri = URI.create("https://www.tjekbil.dk/api/v3/dmr/regnrquery/" + regNumber);
            var client = HttpClient.newHttpClient();
            var request = HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .header("accept", "application/json")
                    .GET()
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());

            Gson gson = new GsonBuilder().create();

            List<DMRCar> car = gson.fromJson(response.body(), new TypeToken<List<DMRCar>>(){}.getType());
            System.out.println(car.get(0).maerkeTypeNavn + " " + car.get(0).modelTypeNavn + " " + car.get(0).variantTypeNavn);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
