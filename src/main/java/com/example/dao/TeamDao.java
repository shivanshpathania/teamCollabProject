package com.example.dao;

import com.example.model.Team;
import java.util.List;

public interface TeamDao {
    int saveTeam(Team team);
    Team getTeamById(int id);
    List<Team> getTeamsByGroup(int groupNumber);

    // 🔹 New methods for update/delete
    List<Team> getAllTeams();
    void updateTeam(int id, Team team);
    void deleteTeam(int id);
}
