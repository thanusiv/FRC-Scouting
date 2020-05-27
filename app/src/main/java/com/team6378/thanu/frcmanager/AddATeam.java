package com.team6378.thanu.frcmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class AddATeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ateam);

        final EditText matchNumber = findViewById(R.id.matchNumberEditText);
        final EditText teamNumber = findViewById(R.id.editText);
        final CheckBox crossedBaseline = findViewById(R.id.crossedBaselineCheckBox);
        final CheckBox autoCubeInSwitch = findViewById(R.id.cubeInSwitchCheckBox);
        final CheckBox autoCubeInScale = findViewById(R.id.cubeInScaleCheckBox);
        final TextView cubesSwitch = findViewById(R.id.cubesSwitchTextView);
        final TextView cubesScale = findViewById(R.id.cubesScaleTextView);
        final TextView cubesExchange = findViewById(R.id.cubesExchangeTextView);
        final CheckBox climbed = findViewById(R.id.climbedCheckBox);
        final CheckBox malfunction = findViewById(R.id.malfunctionCheckBox);
        final EditText additionalComments = findViewById(R.id.additionalCommentsEditText);

        if (getIntent().hasExtra("TeamEdit")){
            Team temp = (Team)getIntent().getExtras().get("TeamEdit");
            matchNumber.setText(String.valueOf(temp.getMatchNumber()));
            teamNumber.setText(String.valueOf(temp.getTeamNumber()));
            crossedBaseline.setChecked(temp.getCrossedBaseline());
            autoCubeInScale.setChecked(temp.getAutoCubesScale());
            autoCubeInSwitch.setChecked(temp.getAutoCubesSwitch());
            cubesSwitch.setText(String.valueOf(temp.getTeleopCubesSwitch()));
            cubesScale.setText(String.valueOf(temp.getTeleopCubesScale()));
            cubesExchange.setText(String.valueOf(temp.getTeleopCubesExchange()));
            climbed.setChecked(temp.getClimbed());
            malfunction.setChecked(temp.getMalfunction());
            additionalComments.setText(temp.getAdditionalInformation());
        }
        else if (getIntent().hasExtra("TeamEditSearch")){
            Team temp = (Team)getIntent().getExtras().get("TeamEditSearch");
            matchNumber.setText(String.valueOf(temp.getMatchNumber()));
            teamNumber.setText(String.valueOf(temp.getTeamNumber()));
            crossedBaseline.setChecked(temp.getCrossedBaseline());
            autoCubeInScale.setChecked(temp.getAutoCubesScale());
            autoCubeInSwitch.setChecked(temp.getAutoCubesSwitch());
            cubesSwitch.setText(String.valueOf(temp.getTeleopCubesSwitch()));
            cubesScale.setText(String.valueOf(temp.getTeleopCubesScale()));
            cubesExchange.setText(String.valueOf(temp.getTeleopCubesExchange()));
            climbed.setChecked(temp.getClimbed());
            malfunction.setChecked(temp.getMalfunction());
            additionalComments.setText(temp.getAdditionalInformation());
        }

        Button cubesSwitchPlusButton = (Button)findViewById(R.id.cubesSwitchPlusButton);
        cubesSwitchPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cubesSwitch = (TextView) findViewById(R.id.cubesSwitchTextView);
                int num = Integer.parseInt(cubesSwitch.getText().toString());
                num++;
                cubesSwitch.setText(Integer.toString(num));
            }
        });

        Button cubesSwitchMinusButton = (Button)findViewById(R.id.cubesSwitchMinusButton);
        cubesSwitchMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cubesSwitch = (TextView) findViewById(R.id.cubesSwitchTextView);
                int num = Integer.parseInt(cubesSwitch.getText().toString());
                if (num > 0)
                num--;
                cubesSwitch.setText(Integer.toString(num));
            }
        });

        Button cubesScalePlusButton = (Button)findViewById(R.id.cubesScalePlusButton);
        cubesScalePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cubesScale = (TextView) findViewById(R.id.cubesScaleTextView);
                int num = Integer.parseInt(cubesScale.getText().toString());
                num++;
                cubesScale.setText(Integer.toString(num));
            }
        });

        Button cubesScaleMinusButton = (Button)findViewById(R.id.cubesScaleMinusButton);
        cubesScaleMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cubesScale = (TextView) findViewById(R.id.cubesScaleTextView);
                int num = Integer.parseInt(cubesScale.getText().toString());
                if (num > 0)
                num--;
                cubesScale.setText(Integer.toString(num));
            }
        });

        Button cubesExchangePlusButton = (Button)findViewById(R.id.cubesExchangePlusButton);
        cubesExchangePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cubesExchange = (TextView) findViewById(R.id.cubesExchangeTextView);
                int num = Integer.parseInt(cubesExchange.getText().toString());
                num++;
                cubesExchange.setText(Integer.toString(num));
            }
        });

        Button cubesExchangeMinusButton = (Button)findViewById(R.id.cubesExchangeMinusButton);
        cubesExchangeMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cubesExchange = (TextView) findViewById(R.id.cubesExchangeTextView);
                int num = Integer.parseInt(cubesExchange.getText().toString());
                if (num > 0)
                num--;
                cubesExchange.setText(Integer.toString(num));
            }
        });

        Button submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText teamNumber = (EditText)findViewById(R.id.editText);
                EditText matchNumber = (EditText)findViewById(R.id.matchNumberEditText);
                int teamNumberInteger = teamNumber.getText().length();
                int matchNumberInteger = matchNumber.getText().length();

                if (teamNumberInteger == 0 || matchNumberInteger == 0) {
                    ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.constraintLayout);
                    Snackbar notFilledIn = Snackbar.make(layout, "Team Number or Match Number is not filled in", Snackbar.LENGTH_LONG);
                    notFilledIn.show();
                }
                else {
                    boolean check = false;
                    int matchNumberValue = Integer.parseInt(matchNumber.getText().toString());
                    int teamNumberValue = Integer.parseInt(teamNumber.getText().toString());
                    Team temp2 = null;
                    int position = -1;
                    if (getIntent().hasExtra("TeamEdit")) {
                        temp2 = ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(getIntent().getExtras().getInt("Position"));
                        ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().remove(getIntent().getExtras().getInt("Position"));
                    }
                    else if (getIntent().hasExtra("TeamEditSearch")) {
                        temp2 = ViewTeams.search.get(getIntent().getExtras().getInt("Position"));
                        for (int i = 0; i < ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++){
                            if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i) == temp2) {
                                ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().remove(i);
                                position = i;
                                break;
                            }
                        }
                    }

                    for (int i = 0; i < ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ) {
                            if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getMatchNumber() == matchNumberValue && teamNumberValue == ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeamNumber()) {
                                check = true;
                                break;
                            }
                        }
                    if (getIntent().hasExtra("TeamEdit"))
                        ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().add(getIntent().getExtras().getInt("Position"), temp2);

                    else if (getIntent().hasExtra("TeamEditSearch"))
                        ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().add(position, temp2);

                        if (!check) {
                            Intent startIntent = new Intent(getApplicationContext(), Menu.class);
                            Team aTeam = new Team(Integer.parseInt(matchNumber.getText().toString()), Integer.parseInt(teamNumber.getText().toString()), crossedBaseline.isChecked(), autoCubeInSwitch.isChecked(), autoCubeInScale.isChecked(), Integer.parseInt(cubesSwitch.getText().toString()), Integer.parseInt(cubesScale.getText().toString()), Integer.parseInt(cubesExchange.getText().toString()), climbed.isChecked(), malfunction.isChecked(), additionalComments.getText().toString());

                            if(getIntent().hasExtra("TeamEdit")) {
                                ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().remove(getIntent().getExtras().getInt("Position"));
                                getIntent().removeExtra("TeamEdit");
                                getIntent().removeExtra("Position");
                                ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().add(aTeam);
                                ViewTournaments.tinydb.clear();
                                ViewTournaments.tinydb.putListObject("Tournaments", ViewTournaments.tournaments);
                                Intent viewTeamsAgain = new Intent(getApplicationContext(), ViewTeams.class);
                                startActivity(viewTeamsAgain);
                            }
                            else if(getIntent().hasExtra("TeamEditSearch")) {
                                ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().remove(position);
                                getIntent().removeExtra("TeamEditSearch");
                                getIntent().removeExtra("Position");
                                position = -1;
                                ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().add(aTeam);
                                ViewTournaments.tinydb.clear();
                                ViewTournaments.tinydb.putListObject("Tournaments", ViewTournaments.tournaments);
                                Intent viewTeamsAgain = new Intent(getApplicationContext(), ViewTeams.class);
                                startActivity(viewTeamsAgain);
                            }
                            else {
                                ViewTournaments.addTeam(aTeam);
                                ViewTournaments.tinydb.clear();
                                ViewTournaments.tinydb.putListObject("Tournaments", ViewTournaments.tournaments);
                                startActivity(startIntent);
                            }
                        }
                        else{
                            ConstraintLayout layout = findViewById(R.id.constraintLayout);
                            Snackbar notFilledIn = Snackbar.make(layout, "This specific team and match number already exists ", Snackbar.LENGTH_LONG);
                            notFilledIn.show();
                        }
                }
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if(getIntent().hasExtra("TeamEdit") || getIntent().hasExtra("TeamEditSearch")) {
            startActivity(new Intent(AddATeam.this, ViewTeams.class));
            finish();
        }
        else {
            startActivity(new Intent(AddATeam.this, Menu.class));
            finish();
        }

    }

}
