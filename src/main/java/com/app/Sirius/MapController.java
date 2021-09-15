package com.app.Sirius;

import com.app.Sirius.OpenWeatherReader.Weather__1;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Controller
public class MapController {

    public double l1;
    public double l2;
    public String l3;

    @Value("55fTaan5Ak0lpkFRPHk8XzDVAyrU752A")
    private String tomTomApiKey;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("apikey", tomTomApiKey);
        model.addAttribute("locations", locations());
        return "home";
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private void readJSON(String fileName) throws Exception {
        Gson g = new Gson();
        JSONObject j = new JSONObject();

        String file = readFileAsString(fileName);
        jsonFile json = g.fromJson(file, jsonFile.class);

        l1 = json.latitude;
        l2 = json.longitude;
        l3 = json.location;
    }

    private List<Location> locations() {

        try {
            // your file path
            readJSON("/Users/wojciechszlosek/Desktop/Java/Sirius/loc.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Location l = new Location(new double[]{l2, l1}, l3);

        try {
            l.includeWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of(l);
    }

    private String getCode() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        return String.valueOf(year) +  String.valueOf(month) +
                String.valueOf(day) +  String.valueOf(hours) +
                String.valueOf(min);
    }

    private static class Location {
        private final double[] lnglat;
        private String description;
        private Weather weather;
        private com.app.Sirius.OpenWeatherReader.Weather w;
        public String printer;

        public Location(double[] lnglat) {
            this.lnglat = lnglat;
            this.description = "";
            this.weather = new Weather();
            this.printer = "";
        }

        public Location(double[] lnglat, String description) {
            this.lnglat = lnglat;
            this.description = description;
            this.weather = new Weather();
            this.printer = "";
        }

        public void includeWeather() throws IOException {
            this.weather.setCoords(this.lnglat[1], this.lnglat[0]);
            w = this.weather.initiation();

            if(this.description.equals("")) {
                this.description = w.getName();
            }

            initPrinter();
        }

        @Override
        public String toString() {
            return "Location{" +
                    "lnglat=" + Arrays.toString(lnglat) +
                    ", description='" + description + '\'' +
                    ", weather=" + weather +
                    '}';
        }

        private void initPrinter() {
            Weather__1 w1 = w.getWeather().get(0);

            this.printer = "Location: " + description + "<br> temp.: "
                    + w.kelvinToCelsius(w.getMain().getTemp()) + "°C <br>"
                    + w1.getDescription();
        }

        public String getPrinter() {
            initPrinter();
            return printer;
        }

        public double[] getLnglat() {
            return lnglat;
        }

        public String getDescription() {
            return description;
        }

    }

}