package com.example.controller;

import com.example.model.Announcement;
import com.example.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/add")
    public String addAnnouncement(@ModelAttribute Announcement announcement) {
        // Set timestamp
        announcement.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        announcementService.saveAnnouncement(announcement);
        return "redirect:/mentor/dashboard?group=" + announcement.getTeamId();
    }
}
