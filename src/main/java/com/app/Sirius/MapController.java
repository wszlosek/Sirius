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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        var file = readFileAsString(fileName);
        var json = new Gson().fromJson(file, JsonFile.class);

        l1 = json.latitude();
        l2 = json.longitude();
        l3 = json.location();
    }

    private List<Location> locations() {
        try {
            // your file path
            readJSON("/Users/wojciechszlosek/Desktop/Java/Sirius/loc.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        var location = new Location(new double[]{l2, l1}, l3);
        try {
            location.includeWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of(location);
    }

    private String getCode() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        var stringBuilder = new StringBuilder()
                .append(year).append(month).append(day).append(hours).append(min);

        return stringBuilder.toString();
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

            if (this.description.equals("")) {
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
            var w1 = w.getWeather().get(0);
            var dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            var now = LocalDateTime.now();

            this.printer = "Date: " + dtf.format(now) + "<br> <br>";
            this.printer += "Location: " + description + "<br> temp.: "
                    + w.kelvinToCelsius(w.getMain().getTemp()) + "°C <br>"
                    + w1.getDescription() + "<br> <br> Time zone: UTC";

            if (w.getTimezone() > 0) {
                this.printer += "+";
            }

            this.printer += w.getTimezone() / 3600 + "h";
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
