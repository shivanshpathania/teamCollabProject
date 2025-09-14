// File: src/main/java/com/example/controller/MentorController.java
package com.example.controller;

import com.example.model.Team;
import com.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mentor")
public class MentorController {

    @Autowired
    private TeamService teamService;

    // This is the updated method with the correct logic and syntax
    @GetMapping("/dashboard")
    public String mentorDashboard(@RequestParam(value = "group", required = false) Integer group, Model model) {
        List<Team> teams;
        if (group != null) {
            // If a group is selected, show teams from that group
            teams = teamService.getTeamsByGroup(group);
        } else {
            // If no group is selected (like after login), show all teams
            teams = teamService.getAllTeams();
        }
        model.addAttribute("teams", teams);
        model.addAttribute("selectedGroup", group);
        return "mentor_dashboard";
    }
}