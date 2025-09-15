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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/updateTeam/{id}")
    public String showUpdateTeamForm(@PathVariable("id") int id, Model model) {
        Team team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        model.addAttribute("updateActionUrl", "/mentor/updateTeam/" + team.getId());
        return "team_update";
    }

    @PostMapping("/updateTeam/{id}")
    public String updateTeam(@PathVariable("id") int id, Team team) {
        Team existing = teamService.getTeamById(id);
        team.setCreatorUserId(existing.getCreatorUserId());
        if (team.getProgress() < 0) team.setProgress(0);
        if (team.getProgress() > 100) team.setProgress(100);
        teamService.updateTeam(id, team);
        return "redirect:/mentor/dashboard?updated=true";
    }

    @GetMapping("/deleteTeam/{id}")
    public String deleteTeam(@PathVariable("id") int id) {
        teamService.deleteTeam(id);
        return "redirect:/mentor/dashboard?deleted=true";
    }
}