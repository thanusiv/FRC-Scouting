package com.team6378.thanu.frcmanager;


public class RobotImage {

    String robotImage;
    int teamNumber;

    RobotImage(int teamNumber, String robotImage){
        this.teamNumber = teamNumber;
        this.robotImage = robotImage;
    }

    public String getRobotImage(){ return robotImage; }

    public int getTeamNumber(){return teamNumber; }

}
