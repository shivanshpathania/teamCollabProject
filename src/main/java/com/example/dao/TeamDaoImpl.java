package com.example.dao;

import com.example.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeamDaoImpl implements TeamDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Override
    public int saveTeam(Team team) {
        String sql = "INSERT INTO teams (subject, group_number, progress, creator_user_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, team.getSubject());
            ps.setInt(2, team.getGroupNumber());
            ps.setInt(3, team.getProgress());
            ps.setInt(4, team.getCreatorUserId());
            return ps;
        }, keyHolder);
        int teamId = keyHolder.getKey().intValue();
        teamMemberDao.saveTeamMembers(team.getMembers(), teamId);
        return teamId;
    }

    @Override
    public Team getTeamById(int id) {
        String sql = "SELECT * FROM teams WHERE id = ?";
        Team team = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Team t = new Team();
            t.setId(rs.getInt("id"));
            t.setSubject(rs.getString("subject"));
            t.setGroupNumber(rs.getInt("group_number"));
            t.setProgress(rs.getInt("progress"));
            t.setCreatorUserId(rs.getInt("creator_user_id"));
            return t;
        });
        team.setMembers(teamMemberDao.getMembersByTeamId(id));
        return team;
    }

    @Override
    public List<Team> getTeamsByGroup(int groupNumber) {
        String sql = "SELECT * FROM teams WHERE group_number = ?";
        List<Team> teams = jdbcTemplate.query(sql, new Object[]{groupNumber}, (rs, rowNum) -> {
            Team t = new Team();
            t.setId(rs.getInt("id"));
            t.setSubject(rs.getString("subject"));
            t.setGroupNumber(rs.getInt("group_number"));
            t.setProgress(rs.getInt("progress"));
            t.setCreatorUserId(rs.getInt("creator_user_id"));
            return t;
        });
        for (Team team : teams) {
            team.setMembers(teamMemberDao.getMembersByTeamId(team.getId()));
        }
        return teams;
    }

    @Override
    public List<Team> getAllTeams() {
        String sql = "SELECT * FROM teams";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Team t = new Team();
            t.setId(rs.getInt("id"));
            t.setSubject(rs.getString("subject"));
            t.setGroupNumber(rs.getInt("group_number"));
            t.setProgress(rs.getInt("progress"));
            t.setCreatorUserId(rs.getInt("creator_user_id"));
            return t;
        });
    }

    @Override
    public List<Team> getTeamsByCreator(int creatorUserId) {
        String sql = "SELECT * FROM teams WHERE creator_user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{creatorUserId}, (rs, rowNum) -> {
            Team t = new Team();
            t.setId(rs.getInt("id"));
            t.setSubject(rs.getString("subject"));
            t.setGroupNumber(rs.getInt("group_number"));
            t.setProgress(rs.getInt("progress"));
            t.setCreatorUserId(rs.getInt("creator_user_id"));
            return t;
        });
    }

    @Override
    public void updateTeam(int id, Team team) {
        String sql = "UPDATE teams SET subject=?, group_number=?, progress=? WHERE id=?";
        jdbcTemplate.update(sql, team.getSubject(), team.getGroupNumber(), team.getProgress(), id);
        teamMemberDao.updateTeamMembers(team.getMembers(), id);
    }

    @Override
    public void deleteTeam(int id) {
        String sql = "DELETE FROM teams WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
