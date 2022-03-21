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

    public DMRCar getDMRCar(String regNumber) {

        try {
            String response = performGetRequest("https://www.tjekbil.dk/api/v3/dmr/regnrquery/" + regNumber);

            Gson gson = new GsonBuilder().create();
            List<DMRCar> cars = gson.fromJson(response, new TypeToken<List<DMRCar>>(){}.getType());

            return cars.get(0);
        }
        catch (Exception ex) {

        }

        return null;

    }

    public String performGetRequest(String url) {

        String body = "";
        try
        {
            var uri = URI.create(url);
            var client = HttpClient.newHttpClient();
            var request = HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .header("accept", "application/json")
                    .GET()
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            body = response.body();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            return body;
        }
    }
}
