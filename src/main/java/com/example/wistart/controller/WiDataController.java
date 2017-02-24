package com.example.wistart.controller;

import com.example.wistart.model.WiData;
import com.example.wistart.service.WiDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by pdomokos on 2/16/17.
 */

@Controller
public class WiDataController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WiDataService wiDataService;

    @GetMapping("/widata")
    public ModelAndView wiDataForm() {
        ModelAndView mav = new ModelAndView("site.homepage", "command", new WiData());
        mav.addObject("selectList", new int[]{15, 30, 45, 60});
        Map<Integer, String> users = new LinkedHashMap<Integer, String>();
        users.put(1, "User1");
        users.put(2, "User2");
        mav.addObject("userList", users);
        mav.addObject("dateInterest", new Date());
        return mav;
    }

    @PostMapping("/widata")
    public String wiDataSubmit(@ModelAttribute WiData wiData, ModelMap model) {
        model.addAttribute("interval", wiData.getInterval());
        model.addAttribute("userIds", wiData.getUserIds());
        Date d = wiData.getDateInterest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(d);
        model.addAttribute("dateInterest", s);
        List<int[]> days = wiDataService.extractCSV(wiData.getInterval(), wiData.getUserIds(), wiData.getDateInterest());
        List<List<Object>> oneDayWiData = wiDataService.writeData(days, wiData.getDateInterest());
        wiData.setOneDayWiData(oneDayWiData);
        model.addAttribute("oneDayWiData", oneDayWiData);
        return "site.result";
    }

}