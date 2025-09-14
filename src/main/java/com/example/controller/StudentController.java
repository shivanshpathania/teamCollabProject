package com.example.controller;

import com.example.model.Team;
import com.example.model.TeamMember;
// ...existing code...
import com.example.service.TeamService;
// ...existing code...
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private TeamService teamService;

// ...existing code...

    @GetMapping("/dashboard")
    public String studentDashboard(Model model) {
        model.addAttribute("teams", teamService.getAllTeams()); // show list of teams
        return "student_dashboard";
    }

    @PostMapping("/createTeam")
    public String createTeam(@ModelAttribute Team team) {
        teamService.createTeam(team);
        return "redirect:/student/dashboard?created=true";
    }

    // ====== UPDATE TEAM ======
    @GetMapping("/updateTeam/{id}")
    public String showUpdateTeamForm(@PathVariable("id") int id, Model model) {
        Team team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        return "team_update"; // new template
    }

    @PostMapping("/updateTeam/{id}")
    public String updateTeam(@PathVariable("id") int id, @ModelAttribute Team team) {
        teamService.updateTeam(id, team);
        return "redirect:/student/dashboard?updated=true";
    }

    // ====== DELETE TEAM ======
    @GetMapping("/deleteTeam/{id}")
    public String deleteTeam(@PathVariable("id") int id) {
        teamService.deleteTeam(id);
        return "redirect:/student/dashboard?deleted=true";
    }
}
