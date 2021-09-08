package com.app.Sirius;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
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

        try {
            xd();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of(
                new Location(new double[]{-121.901481, 36.618253}, "Monterey Bay Aquarium"),
                new Location(new double[]{21.006010, 52.231606}, "Palace of Culture and Science"),
                new Location(new double[]{20.863889, 50.236389}, "Zalipie")
        );
    }

    private void xd() throws IOException {

        Weather w1 = new Weather();
        w1.setCoords(3.45, 1.2);
        w1.initiation();
    }

    private static class Location {
        private final double[] lnglat;
        private final String description;
        private final String xd;

        public Location(double[] lnglat, String description) {
            this.lnglat = lnglat;
            this.description = description;
            this.xd = "xdddd";
        }

        public double[] getLnglat() {
            return lnglat;
        }

        public String getDescription() {
            return description;
        }

        public String getXd() {
            return xd;
        }

    }

}
