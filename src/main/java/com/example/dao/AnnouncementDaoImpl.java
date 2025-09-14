package com.example.dao;

import com.example.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveAnnouncement(Announcement announcement) {
        String sql = "INSERT INTO announcements (team_id, message, author, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, announcement.getTeamId(), announcement.getMessage(), announcement.getAuthor(), announcement.getTimestamp());
    }

    @Override
    public List<Announcement> getAnnouncementsByTeamId(int teamId) {
        String sql = "SELECT * FROM announcements WHERE team_id = ? ORDER BY timestamp DESC";
        return jdbcTemplate.query(sql, new Object[]{teamId}, (rs, rowNum) -> {
            Announcement a = new Announcement();
            a.setId(rs.getInt("id"));
            a.setTeamId(rs.getInt("team_id"));
            a.setMessage(rs.getString("message"));
            a.setAuthor(rs.getString("author"));
            a.setTimestamp(rs.getString("timestamp"));
            return a;
        });
    }
}
