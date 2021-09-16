# Sirius - current weather for any coordinates on the globe

A website that allows you to check the location on a map of any place using geographic coordinates along with information about the current weather and time zone.

This application is a Spring Boot project.

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Presentation of the application](#presentation-of-the-application)
* [Status](#status)

## General info

"Sirius" application gives you the opportunity to obtain information about the current weather and time zone for any coordinates on the globe. In the current version of the application, the values of any geographic coordinates are in a separate JSON file. Coordinates must be in decimal degrees (DD). 
By editing this file, the user can change the coordinates and the location searched.


After entering the values of geographic coordinates, the location on the website is marked on the map along with the following information:
* current date
* location name
* current temperature (in degrees Celsius)
* brief description of the weather
* time zone information

## Technologies

#### Programming languages and technologies:
* Java (framework: Spring Boot): Maps API, Weather API, operations on JSON file
* JavaScript (Node.js): Maps API (only for the frontend)
* HTML, CSS: website

<br />

The TomTom Maps API was used to generate the map on the website. The weather information comes from the OpenWeatherMap API. Both of these APIs require a key that can be obtained from their websites (after signing up):
>https://developer.tomtom.com <br />
>https://openweathermap.org/api

<br />

#### Technologies, libraries, packages:  
* Spring Boot, version: 2.5.4  <br />
* Thymeleaf, version: 3.0.12  <br />
* Google Gson, version: 2.8.8  <br />
* json-simple, version: 1.1.1  <br />
* packages: java.nio, java.time, java.util, java.net, javax.annotation



## Presentation of the application

Your custome name of location...

https://user-images.githubusercontent.com/53795852/133678212-c7c15b4b-c816-4a17-bbfd-9ef89109e184.mp4


... or name determined automatically:

https://user-images.githubusercontent.com/53795852/133678347-a73fba0f-465d-4d7f-8831-25da1a7725ad.mp4



## Status

### Current version: 1.0 (2021)
>* Location using geographic coordinates from a JSON file.

#### Planned updates:
>* Ability to search for locations using city names.
>* Entering data on the website using a form directly on the website (no need to use a JSON file).
