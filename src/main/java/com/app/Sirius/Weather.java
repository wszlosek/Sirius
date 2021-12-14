package com.app.Sirius;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Weather {
    private String apiKey;
    private String latitude;
    private String longitude;
    private String urlAddress;

    Weather() {
        this.apiKey = "55fTaan5Ak0lpkFRPHk8XzDVAyrU752A";
        this.urlAddress = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}";
    }

    Weather(double lat, double longi) {
        setCoords(lat, longi);
    }

    public void setCoords(double lat, double longi) {
        this.latitude = String.valueOf(lat);
        this.longitude = String.valueOf(longi);
    }

    public com.app.Sirius.OpenWeatherReader.Weather initiation() throws IOException {
        this.urlAddress = this.urlAddress.replace("{lat}", this.latitude)
                .replace("{lon}", this.longitude).replace("{API key}", this.apiKey);

        var url = new URL(this.urlAddress);
        var reader = new InputStreamReader(url.openStream());
        return new Gson().fromJson(reader, com.app.Sirius.OpenWeatherReader.Weather.class);
    }
}
