package com.team6378.thanu.frcmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

public class ViewSpecificTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_specific_team);

        Intent i = getIntent();
        int position = i.getExtras().getInt("Position");
        String whichArraylist = i.getExtras().getString("Arraylist");

        final TextView matchNumber = findViewById(R.id.matchNumberTextView);
        final TextView teamNumber = findViewById(R.id.teamNumberTextView);
        final CheckBox crossedBaseline = findViewById(R.id.crossedBaselineCheckBoxFixed);
        final CheckBox autoCubeInSwitch = findViewById(R.id.cubeInSwitchCheckBoxFixed);
        final CheckBox autoCubeInScale = findViewById(R.id.cubeInScaleCheckBoxFixed);
        final TextView cubesSwitch = findViewById(R.id.cubesSwitchTextViewFixed);
        final TextView cubesScale = findViewById(R.id.cubesScaleTextViewFixed);
        final TextView cubesExchange = findViewById(R.id.cubesExchangeTextViewFixed);
        final CheckBox climbed = findViewById(R.id.climbedCheckBoxFixed);
        final CheckBox malfunction = findViewById(R.id.malfunctionCheckBoxFixed);
        final TextView additionalComments = findViewById(R.id.additionalCommentsTextView);

        if (whichArraylist.equals("teams")) {
            matchNumber.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getMatchNumber()));
            teamNumber.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeamNumber()));
            crossedBaseline.setChecked(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getCrossedBaseline());
            autoCubeInSwitch.setChecked(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getAutoCubesSwitch());
            autoCubeInScale.setChecked(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getAutoCubesScale());
            cubesSwitch.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeleopCubesSwitch()));
            cubesScale.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeleopCubesScale()));
            cubesExchange.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeleopCubesExchange()));
            climbed.setChecked(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getClimbed());
            malfunction.setChecked(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getMalfunction());

            if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getAdditionalInformation().equals("Type any additional comments here"))
                additionalComments.setText("No additional comments were made");
            else
                additionalComments.setText(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getAdditionalInformation());
        }
        else if (whichArraylist.equals("search")){
            matchNumber.setText(String.valueOf(ViewTeams.search.get(position).getMatchNumber()));
            teamNumber.setText(String.valueOf(ViewTeams.search.get(position).getTeamNumber()));
            crossedBaseline.setChecked(ViewTeams.search.get(position).getCrossedBaseline());
            autoCubeInSwitch.setChecked(ViewTeams.search.get(position).getAutoCubesSwitch());
            autoCubeInScale.setChecked(ViewTeams.search.get(position).getAutoCubesScale());
            cubesSwitch.setText(String.valueOf(ViewTeams.search.get(position).getTeleopCubesSwitch()));
            cubesScale.setText(String.valueOf(ViewTeams.search.get(position).getTeleopCubesScale()));
            cubesExchange.setText(String.valueOf(ViewTeams.search.get(position).getTeleopCubesExchange()));
            climbed.setChecked(ViewTeams.search.get(position).getClimbed());
            malfunction.setChecked(ViewTeams.search.get(position).getMalfunction());

            if (ViewTeams.search.get(position).getAdditionalInformation().equals("Type any additional comments here"))
                additionalComments.setText("No additional comments were made");
            else
                additionalComments.setText(ViewTeams.search.get(position).getAdditionalInformation());
        }


    }

}
