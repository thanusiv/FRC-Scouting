package com.team6378.thanu.frcmanager;

import java.util.ArrayList;


public class Tournament {

    private ArrayList<Team> teams;
    private String name;
    private ArrayList<RobotImage> robotImages;

    public Tournament(String name){
        teams = new ArrayList<>();
        robotImages = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<Team> getTeams(){
        return teams;
    }

    public ArrayList<RobotImage> getRobotImages() {
        return robotImages;
    }

    public String getTournamentName(){
        return name;
    }

    public void changeTournamentName(String s){
        name = s;
    }

}
