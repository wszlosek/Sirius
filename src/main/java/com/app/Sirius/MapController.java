package com.app.Sirius;

import com.app.Sirius.OpenWeatherReader.Weather__1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class MapController {

    @Value("55fTaan5Ak0lpkFRPHk8XzDVAyrU752A")
    private String tomTomApiKey;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("apikey", tomTomApiKey);
        model.addAttribute("coolLocations", coolLocations());
        return "home";
    }

    private List<Location> coolLocations() {

        Location l = new Location(new double[]{20.863889, 50.236389}, "Zalipie");
        try {
            l.includeWeather();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of(l);
    }

    private static class Location {
        private final double[] lnglat;
        private final String description;
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
