package com.example.model;

import java.util.List;

public class Team {
    private int id;
    private String subject;
    private int groupNumber;
    private List<TeamMember> members;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public int getGroupNumber() { return groupNumber; }
    public void setGroupNumber(int groupNumber) { this.groupNumber = groupNumber; }
    public List<TeamMember> getMembers() { return members; }
    public void setMembers(List<TeamMember> members) { this.members = members; }
}
