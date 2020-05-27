package com.team6378.thanu.frcmanager;

import java.io.Serializable;
import java.util.Comparator;

public class Team implements Serializable{

    private int matchNumber;
    private int teamNumber;
    private boolean crossedBaseLine;
    private boolean autoCubeInSwitch;
    private boolean autoCubeInScale;
    private int cubesSwitch;
    private int cubesScale;
    private int cubesExchange;
    private boolean climbed;
    private boolean malfunction;
    private String additionalInformation;

    Team(int matchNumber, int teamNumber, boolean crossedBaseLine, boolean autoCubeInSwitch, boolean autoCubeInScale, int cubesSwitch, int cubesScale, int cubesExchange, boolean climbed,boolean malfunction, String additionalInformation ){
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
        this.crossedBaseLine = crossedBaseLine;
        this.autoCubeInSwitch = autoCubeInSwitch;
        this.autoCubeInScale = autoCubeInScale;
        this.cubesSwitch = cubesSwitch;
        this.cubesScale = cubesScale;
        this.cubesExchange = cubesExchange;
        this.climbed = climbed;
        this.malfunction = malfunction;
        this.additionalInformation = additionalInformation;
    }

    public int getTeamNumber(){
        return teamNumber;
    }

    public int getMatchNumber(){
        return matchNumber;
    }

    public int getTeleopCubesSwitch(){
        return cubesSwitch;
    }

    public boolean getAutoCubesSwitch(){
        return autoCubeInSwitch;
    }

    public boolean getAutoCubesScale(){
        return autoCubeInScale;
    }

    public boolean getCrossedBaseline(){
        return crossedBaseLine;
    }

    public int getTeleopCubesScale(){
        return cubesScale;
    }

    public int getTeleopCubesExchange(){
        return cubesExchange;
    }

    public boolean getClimbed(){
        return climbed;
    }

    public boolean getMalfunction(){
        return malfunction;
    }

    public String getAdditionalInformation(){return additionalInformation; }

    static class TeamNumberComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            return o1.getTeamNumber() - o2.getTeamNumber();
        }
    }

    static class MatchNumberComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            return o1.getMatchNumber() - o2.getMatchNumber();
        }
    }

    static class TeleopCubesSwitchComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            return o2.getTeleopCubesSwitch() - o1.getTeleopCubesSwitch();
        }
    }

    static class TeleopCubesScaleComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            return o2.getTeleopCubesScale() - o1.getTeleopCubesScale();
        }
    }

    static class TeleopCubesExchangeComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            return o2.getTeleopCubesExchange() - o1.getTeleopCubesExchange();
        }
    }

    static class CrossedBaselineComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            if (o2.getCrossedBaseline())
                return 1;
            else
                return -1;
        }
    }

    static class AutoCubeInSwitchComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            if (o2.getAutoCubesSwitch())
                return 1;
            else
                return -1;
        }
    }

    static class AutoCubeInScaleComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            if (o2.getAutoCubesScale())
                return 1;
            else
                return -1;
        }
    }

    static class ClimbedComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            if (o2.getClimbed())
                return 1;
            else
                return -1;
        }
    }

    static class MalfunctionComparator implements Comparator<Team> {
        @Override
        public int compare(Team o1, Team o2) {
            if (o2.getMalfunction())
                return 1;
            else
                return -1;
        }
    }

}
