package com.example.service;

import com.example.dao.TeamDao;
import com.example.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamDao teamDao;

    public int createTeam(Team team) {
        return teamDao.saveTeam(team);
    }

    public List<Team> getTeamsByGroup(int groupNumber) {
        return teamDao.getTeamsByGroup(groupNumber);
    }

    public List<Team> getAllTeams() {
        return teamDao.getAllTeams();
    }

    public List<Team> getTeamsByCreator(int creatorUserId) {
        return teamDao.getTeamsByCreator(creatorUserId);
    }

    public Team getTeamById(int id) {
        return teamDao.getTeamById(id);
    }

    public void updateTeam(int id, Team team) {
        teamDao.updateTeam(id, team);
    }

    public void deleteTeam(int id) {
        teamDao.deleteTeam(id);
    }
}
