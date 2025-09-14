package com.example.dao;

import com.example.model.Announcement;
import java.util.List;

public interface AnnouncementDao {
    void saveAnnouncement(Announcement announcement);
    List<Announcement> getAnnouncementsByTeamId(int teamId);
}
