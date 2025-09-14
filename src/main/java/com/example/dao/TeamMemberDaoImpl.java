    // Removed misplaced duplicate updateTeamMembers method
package com.example.dao;

import com.example.model.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamMemberDaoImpl implements TeamMemberDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveTeamMembers(List<TeamMember> members, int teamId) {
        String sql = "INSERT INTO team_members (team_id, name, roll_number, email) VALUES (?, ?, ?, ?)";
        for (TeamMember member : members) {
            jdbcTemplate.update(sql, teamId, member.getName(), member.getRollNumber(), member.getEmail());
        }
    }

    @Override
    public List<TeamMember> getMembersByTeamId(int teamId) {
        String sql = "SELECT * FROM team_members WHERE team_id = ?";
        return jdbcTemplate.query(sql, new Object[]{teamId}, (rs, rowNum) -> {
            TeamMember m = new TeamMember();
            m.setId(rs.getInt("id"));
            m.setTeamId(rs.getInt("team_id"));
            m.setName(rs.getString("name"));
            m.setRollNumber(rs.getString("roll_number"));
            m.setEmail(rs.getString("email"));
            return m;
        });
    }
    @Override
    public void updateTeamMembers(List<TeamMember> members, int teamId) {
        // For simplicity: delete all old members and re-insert current list
        String deleteSql = "DELETE FROM team_members WHERE team_id = ?";
        jdbcTemplate.update(deleteSql, teamId);
        saveTeamMembers(members, teamId);
    }
}
