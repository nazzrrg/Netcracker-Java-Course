package com.github.nazzrrg.simplespringangularapp.utils;

import com.github.nazzrrg.simplespringangularapp.model.Cafe;
import com.github.nazzrrg.simplespringangularapp.model.Day;
import com.github.nazzrrg.simplespringangularapp.model.Hours;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JSONMapper {
    private static List<Hours> parseWorkingHours(JSONObject jo) {
        List<Hours> workingHours = new ArrayList<>();

        JSONArray availabilities = (JSONArray) jo.get("Availabilities");
        try {
            JSONObject availability = (JSONObject) availabilities.get(0);
            availability.get("Everyday"); // check everyday flag
            JSONArray intervals = (JSONArray) availability.get("Intervals");
            String from = ((JSONObject) intervals.get(0)).get("from").toString();
            String to = ((JSONObject) intervals.get(0)).get("to").toString();
            for (Day day: Day.values()) {
                workingHours.add(new Hours(day.name(),
                        LocalTime.parse(from),
                        LocalTime.parse(to)));
            }
        }
        catch (Exception e) {
            for (int i=0; i<availabilities.size(); i++) {
                // учесть отсутствие дней в списке (выходные)
            }
        }

        return workingHours;
    }
    public static Cafe toCafe(JSONObject jo) {
        Cafe cafe = new Cafe();

        JSONObject geometry = (JSONObject) jo.get("geometry");
        JSONArray coordinates = (JSONArray) geometry.get("coordinates");
        cafe.setLocation(coordinates.toString());
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
