package com.example.controller;

import com.example.model.Team;
import com.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import com.example.dao.UserDao;
import com.example.model.User;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private UserDao userDao;

    @GetMapping("/dashboard")
    public String studentDashboard(Model model, Authentication authentication) {
        int currentUserId = getCurrentUserId(authentication);
        List<Team> teams = teamService.getTeamsByCreator(currentUserId);
        model.addAttribute("teams", teams);
        model.addAttribute("currentUserId", currentUserId);
        return "student_dashboard";
    }

    @PostMapping("/createTeam")
    public String createTeam(@ModelAttribute Team team, Authentication authentication) {
        team.setCreatorUserId(getCurrentUserId(authentication));
        if (team.getProgress() < 0) team.setProgress(0);
        if (team.getProgress() > 100) team.setProgress(100);
        teamService.createTeam(team);
        return "redirect:/student/dashboard?created=true";
    }

    // ====== UPDATE TEAM ======
    @GetMapping("/updateTeam/{id}")
    public String showUpdateTeamForm(@PathVariable("id") int id, Model model, Authentication authentication) {
        Team team = teamService.getTeamById(id);
        if (!canModifyTeam(authentication, team)) {
            return "redirect:/student/dashboard?forbidden=true";
        }
        model.addAttribute("team", team);
        model.addAttribute("updateActionUrl", "/student/updateTeam/" + team.getId());
        return "team_update"; // new template
    }

    @PostMapping("/updateTeam/{id}")
    public String updateTeam(@PathVariable("id") int id, @ModelAttribute Team team, Authentication authentication) {
        Team existing = teamService.getTeamById(id);
        if (!canModifyTeam(authentication, existing)) {
            return "redirect:/student/dashboard?forbidden=true";
        }
        // preserve immutable fields
        team.setCreatorUserId(existing.getCreatorUserId());
        if (team.getProgress() < 0) team.setProgress(0);
        if (team.getProgress() > 100) team.setProgress(100);
        teamService.updateTeam(id, team);
        return "redirect:/student/dashboard?updated=true";
    }

    // ====== DELETE TEAM ======
    @GetMapping("/deleteTeam/{id}")
    public String deleteTeam(@PathVariable("id") int id, Authentication authentication) {
        Team team = teamService.getTeamById(id);
        if (!canModifyTeam(authentication, team)) {
            return "redirect:/student/dashboard?forbidden=true";
        }
        teamService.deleteTeam(id);
        return "redirect:/student/dashboard?deleted=true";
    }

    private boolean canModifyTeam(Authentication authentication, Team team) {
        if (authentication == null || team == null) return false;
        int currentUserId = getCurrentUserId(authentication);
        boolean isOwner = team.getCreatorUserId() == currentUserId;
        boolean isMentor = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_MENTOR"));
        return isOwner || isMentor;
    }

    private int getCurrentUserId(Authentication authentication) {
        String email = authentication.getName();
        return userDao.findByEmail(email).map(User::getId).orElse(0);
    }
}
