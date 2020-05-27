package com.team6378.thanu.frcmanager;


import java.util.Comparator;

public class Ranking {

    private int teamNumber;
    private double averageSwitch;
    private double averageScale;
    private double averageExchange;
    private int numberOfGames;
    private int crossedBaseline;
    private int autoSwitch;
    private int autoScale;
    private int climbs;
    private int malfunctions;

    public Ranking(int teamNumber, double averageSwitch, double averageScale, double averageExchange, int crossedBaseline, int autoSwitch, int autoScale, int games, int climbs, int malfunctions){
        this.teamNumber = teamNumber;
        this.averageSwitch = averageSwitch;
        this.averageScale = averageScale;
        this.averageExchange = averageExchange;
        this.crossedBaseline = crossedBaseline;
        this.autoSwitch = autoSwitch;
        this.autoScale = autoScale;
        numberOfGames = games;
        this.climbs = climbs;
        this.malfunctions = malfunctions;
    }

    public double getAverageExchange() {
        return averageExchange;
    }

    public double getAverageScale() {
        return averageScale;
    }

    public double getAverageSwitch() {
        return averageSwitch;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public int getNumberOfGames() {return numberOfGames;}

    public int getCrossedBaseline(){return crossedBaseline;}

    public int getAutoScale() {
        return autoScale;
    }

    public int getAutoSwitch() {
        return autoSwitch;
    }

    public int getClimbs(){ return climbs; }

    public int getMalfunctions() {return malfunctions; }

    static class TeleopCubesSwitchComparator implements Comparator<Ranking> {
        @Override
        public int compare(Ranking o1, Ranking o2) {
            if (o2.getAverageSwitch() > o1.getAverageSwitch())
                return 1;
            else if (o2.getAverageSwitch() < o1.getAverageSwitch())
                return -1;
            else
                return 0;
        }
    }

    static class TeleopCubesScaleComparator implements Comparator<Ranking> {
        @Override
        public int compare(Ranking o1, Ranking o2) {
            if (o2.getAverageScale() > o1.getAverageScale())
                return 1;
            else if (o2.getAverageScale() < o1.getAverageScale())
                return -1;
            else
                return 0;
        }
    }

    static class TeleopCubesExchangeComparator implements Comparator<Ranking> {
        @Override
        public int compare(Ranking o1, Ranking o2) {
            if (o2.getAverageExchange() > o1.getAverageExchange())
                return 1;
            else if (o2.getAverageExchange() < o1.getAverageExchange())
                return -1;
            else
                return 0;
        }
    }
}
