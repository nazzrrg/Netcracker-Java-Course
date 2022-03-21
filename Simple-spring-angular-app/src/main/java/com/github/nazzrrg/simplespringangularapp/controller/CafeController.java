package com.github.nazzrrg.simplespringangularapp.controller;

import com.github.nazzrrg.simplespringangularapp.model.Cafe;
import com.github.nazzrrg.simplespringangularapp.model.Grade;
import com.github.nazzrrg.simplespringangularapp.model.Hours;
import com.github.nazzrrg.simplespringangularapp.model.User;
import com.github.nazzrrg.simplespringangularapp.service.CafeService;
import com.github.nazzrrg.simplespringangularapp.service.UserService;
import com.github.nazzrrg.simplespringangularapp.utils.JSONMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/cafeterias")
public class CafeController {
    private final CafeService service;
    private final UserService userService;
    public CafeController(CafeService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }
    @PostMapping
    //@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void create(@RequestBody(required = false) Cafe cafe) {
        // тестовая загрузка кафе, убрать потом
        userService.create(new User("name", "email", "password"));
        Cafe cafe1 = new Cafe(
                123l,
                "Test Cafe",
                "For test",
                "3.5;45.34",
                "Проспект Испытатателей",
                "url",
                "+791231234545"
        );
        User user = userService.getById(1);
        List<Grade> grades = Arrays.asList(new Grade("very cool", 5, user));
        cafe1.setGrades(grades);
        cafe1.setManager(user);
        List<Hours> hours = Arrays.asList(new Hours("MON", LocalTime.of(8,0), LocalTime.of(20,0)),
                new Hours("THU", LocalTime.of(9,0), LocalTime.of(20,0)));
        cafe1.setWorkingHours(hours);
        service.create(cafe1);
    }

    @GetMapping("/{id}")
    public Cafe getCafe(@PathVariable long id) {
        return service.getById(id);
    }
    @PostMapping("/update")
    public String updateCafeterias(@RequestParam("geo") String geo,
                                   @RequestParam("res") Integer res,
                                   @Value("${netcracker.app.mapAPI}") String apikey) throws Exception {
        String request = String.format(
                "https://search-maps.yandex.ru/v1/?text=кофе,Санкт-Петербург,%s&results=%d&type=biz&lang=ru_RU&apikey=%s",
                geo, res, apikey);
        final RestTemplate restTemplate = new RestTemplate();
        final String str = restTemplate.getForObject(request, String.class);
        JSONObject jo = (JSONObject) new JSONParser().parse(str);
        JSONArray cafeterias = (JSONArray) jo.get("features");
        for (int i =0; i< cafeterias.size(); i++) {
            JSONObject joCafe = (JSONObject) cafeterias.get(i);
            Cafe cafe = JSONMapper.toCafe(joCafe);
            System.out.println(cafe);
        }

        return cafeterias.toJSONString();
    }
}
