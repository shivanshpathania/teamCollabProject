package com.example.dao;

import com.example.model.TeamMember;
import java.util.List;

public interface TeamMemberDao {
    void saveTeamMembers(List<TeamMember> members, int teamId);
    List<TeamMember> getMembersByTeamId(int teamId);
    void updateTeamMembers(List<TeamMember> members, int teamId);
}
