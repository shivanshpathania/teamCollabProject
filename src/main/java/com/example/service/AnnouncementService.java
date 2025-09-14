package com.example.service;

import com.example.dao.AnnouncementDao;
import com.example.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementDao announcementDao;

    public void saveAnnouncement(Announcement announcement) {
        announcementDao.saveAnnouncement(announcement);
    }

    public List<Announcement> getAnnouncementsByTeamId(int teamId) {
        return announcementDao.getAnnouncementsByTeamId(teamId);
    }
}
