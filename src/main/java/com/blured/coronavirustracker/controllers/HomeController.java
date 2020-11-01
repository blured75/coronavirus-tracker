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
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDeltaCasesDayBefore().get(0)).sum();

        List<String> headersTab = new ArrayList<>();
        int j = 0;
        for (Integer delta : allStats.get(0).getDeltaCasesDayBefore()) {
            headersTab.add("Delta day(" + j + ") / day (" + (j-1) + ")");
            j--;
        }

        model.addAttribute("headersTab", headersTab);
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}
