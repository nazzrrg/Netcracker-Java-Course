package com.github.nazzrrg.simplespringangularapp.utils;

import com.github.nazzrrg.simplespringangularapp.model.Cafe;
import com.github.nazzrrg.simplespringangularapp.model.Day;
import com.github.nazzrrg.simplespringangularapp.model.Hours;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JSONMapper {
    private static boolean checkEveryday(JSONObject availability) {
        if (availability.get("Everyday") != null) return true;
        else return false;
    }
    private static boolean checkTwentyFourHours(JSONObject availability) {
        if (availability.get("TwentyFourHours") != null) return true;
        else return false;
    }
    private static List<Hours> parseWorkingHours(JSONObject jo) {
        List<Hours> workingHours = new ArrayList<>();

        JSONArray availabilities = (JSONArray) jo.get("Availabilities");
        for (int i = 0; i< availabilities.size(); i++) {
            JSONObject availability = (JSONObject) availabilities.get(i);
            String from;
            String to;

            if (checkTwentyFourHours(availability)) {
                from = "00:00";
                to = "23:59";
            }
            else {
                JSONArray intervals = (JSONArray) availability.get("Intervals");
                from = ((JSONObject) intervals.get(0)).get("from").toString();
                to = ((JSONObject) intervals.get(0)).get("to").toString();
            }
            if (checkEveryday(availability)) {
                for (Day day: Day.values()) {
                    workingHours.add(new Hours(day.name(),
                            LocalTime.parse(from),
                            LocalTime.parse(to)));
                }
            }
            else {
                for (String day: (Set<String>)availability.keySet()) {
                    try {
                        String checkDay = Day.valueOf(day).name();
                        workingHours.add(new Hours(checkDay,
                                LocalTime.parse(from),
                                LocalTime.parse(to)));
                    } catch (IllegalArgumentException e) {}
                }
            }
        }

        return workingHours;
    }
    public static Cafe toCafe(JSONObject jo) {
        Cafe cafe = new Cafe();

        JSONObject geometry = (JSONObject) jo.get("geometry");
        JSONArray coordinates = (JSONArray) geometry.get("coordinates");
        String location = coordinates.toString();
        cafe.setLocation(location.substring(1,location.length()-1));
        JSONObject properties = (JSONObject) jo.get("properties");
        cafe.setName(properties.get("name").toString());
        cafe.setDescription(properties.get("description").toString());
        JSONObject companyMetaData = (JSONObject) properties.get("CompanyMetaData");
        cafe.setId(Long.parseLong(companyMetaData.get("id").toString()));
        cafe.setAddress(companyMetaData.get("address").toString());
        cafe.setUrl(companyMetaData.get("url").toString());
        JSONArray phones = (JSONArray) companyMetaData.get("Phones");
        String phonesOnString = "";
        for (int i=0; i<phones.size(); i++) {
            phonesOnString += ((JSONObject) phones.get(i)).get("formatted").toString() + ";";
        }
        cafe.setPhone(phonesOnString);
        JSONObject workingHours = (JSONObject) companyMetaData.get("Hours");
        cafe.setWorkingHours(parseWorkingHours(workingHours));

        return cafe;
    }
}
