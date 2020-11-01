package com.blured.coronavirustracker.controllers;

import com.blured.coronavirustracker.models.LocationStats;
import com.blured.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CountryGraphController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/graph")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        List<Integer> statsFrance = new ArrayList<>();
        // Find stats for France
        for (LocationStats locationStats : allStats) {
            if (locationStats.getCountry().equals("France") && locationStats.getState().equals("")) {
                statsFrance = locationStats.getDeltaCasesDayBefore();
                break;
            }
        }


        model.addAttribute("locationStats", statsFrance);

        return "france";
    }

    @GetMapping(value = "/{state}/{country}")
    public String getGraphForCountryState(Model model, @PathVariable String state, @PathVariable String country) {
        System.out.println(country + "/" + state);
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        List<Integer> stats = new ArrayList<>();
        // Find stats for country/state
        for (LocationStats locationStats : allStats) {
            if (locationStats.getCountry().equals(country) && locationStats.getState().equals(state)) {
                stats = locationStats.getDeltaCasesDayBefore();
                break;
            }
        }

        model.addAttribute("country", country);
        model.addAttribute("state", state);
        model.addAttribute("locationStats", stats);

        return "ute";
    }

    @GetMapping(value = "/{country}")
    public String getGraphForCountry(Model model, @PathVariable String country) {
        System.out.println(country);
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        List<Integer> stats = new ArrayList<>();
        // Find stats for country/state
        for (LocationStats locationStats : allStats) {
            if (locationStats.getCountry().equals(country) && locationStats.getState().equals("")) {
                stats = locationStats.getDeltaCasesDayBefore();
                break;
            }
        }

        model.addAttribute("country", country);
        model.addAttribute("locationStats", stats);

        return "ute";
    }
}
