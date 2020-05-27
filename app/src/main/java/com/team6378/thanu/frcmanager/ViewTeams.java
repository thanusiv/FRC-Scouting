package com.team6378.thanu.frcmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ViewTeams extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText searchEditText;
    private String searchValue;
    public static ArrayList<Team> search = new ArrayList<>();
    private CustomAdaptor customAdaptor;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);

        ListView listView = findViewById(R.id.viewTeams);
        searchEditText = findViewById(R.id.searchEditText);

        customAdaptor = new CustomAdaptor();
        listView.setAdapter(customAdaptor);

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startIntent = new Intent(getApplicationContext(), ViewSpecificTeam.class);
                startIntent.putExtra("Position", position);
                if (searchValue.equals(""))
                    startIntent.putExtra("Arraylist","teams");
                else
                    startIntent.putExtra("Arraylist","search");
                startActivity(startIntent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTeams.this);

                builder.setTitle("Confirm");
                builder.setMessage("What would you like to do?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                       if (search.size() == 0)
                           ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().remove(position);
                        else{
                           for (int i = 0; i < ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++) {
                                   if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i) == search.get(position))
                                       ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().remove(i);
                           }
                           search.remove(position);
                       }

                        customAdaptor.notifyDataSetChanged();
                        ViewTournaments.tinydb.clear();
                        ViewTournaments.tinydb.putListObject("Tournaments", ViewTournaments.tournaments);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent startIntent = new Intent(getApplicationContext(), AddATeam.class);
                        if (search.size() == 0)
                            startIntent.putExtra("TeamEdit", ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position));
                        else
                            startIntent.putExtra("TeamEditSearch", search.get(position));
                        startIntent.putExtra("Position", position);
                        startActivity(startIntent);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

                searchEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        search.clear();
                        searchValue = searchEditText.getText().toString();
                        Log.d("T", "something in edit text");
                        if (!searchValue.equals("")) {
                            for (int i = 0; i < ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++) {

                                if (text.equals("Team Number")) {
                                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeamNumber() == Integer.parseInt(searchValue))
                                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                                } else if (text.equals("Match Number")) {
                                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getMatchNumber() == Integer.parseInt(searchValue))
                                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                                } else if (text.equals("Teleop Cubes in Switch")) {
                                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeleopCubesSwitch() == Integer.parseInt(searchValue))
                                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                                } else if (text.equals("Teleop Cubes in Scale")) {
                                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeleopCubesScale() == Integer.parseInt(searchValue))
                                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                                } else if (text.equals("Teleop Cubes in Exchange")) {
                                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeleopCubesExchange() == Integer.parseInt(searchValue))
                                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                                }

                            }
                        }
                        Collections.sort(search, new Team.MatchNumberComparator());
                        customAdaptor.notifyDataSetChanged();
                        ViewTournaments.tinydb.clear();
                        ViewTournaments.tinydb.putListObject("Tournaments", ViewTournaments.tournaments);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

        spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choices_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        text = spinner.getSelectedItem().toString();

        search.clear();
        searchValue = searchEditText.getText().toString();
        Log.d("T", "something in edit test");
        if (!searchValue.equals("")) {
            for (int i = 0; i < ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++) {

                if (text.equals("Team Number")) {
                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeamNumber() == Integer.parseInt(searchValue))
                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                } else if (text.equals("Match Number")) {
                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getMatchNumber() == Integer.parseInt(searchValue))
                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                } else if (text.equals("Teleop Cubes in Switch")) {
                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeleopCubesSwitch() == Integer.parseInt(searchValue))
                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                } else if (text.equals("Teleop Cubes in Scale")) {
                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeleopCubesScale() == Integer.parseInt(searchValue))
                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                } else if (text.equals("Teleop Cubes in Exchange")) {
                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i).getTeleopCubesExchange() == Integer.parseInt(searchValue))
                        search.add(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i));
                }

            }
        }

        if (text.equals("Auto Cube in Switch")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.AutoCubeInSwitchComparator());
            Collections.sort(search, new Team.AutoCubeInSwitchComparator());
        }

        else if (text.equals("Auto Cube in Scale")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.AutoCubeInScaleComparator());
            Collections.sort(search, new Team.AutoCubeInScaleComparator());
        }

        else if (text.equals("Crossed Baseline")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.CrossedBaselineComparator());
            Collections.sort(search, new Team.CrossedBaselineComparator());
        }

        else if (text.equals("Teleop Cubes in Switch")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.TeleopCubesSwitchComparator());
            Collections.sort(search, new Team.TeleopCubesSwitchComparator());
        }

        else if (text.equals("Teleop Cubes in Scale")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.TeleopCubesScaleComparator());
            Collections.sort(search, new Team.TeleopCubesScaleComparator());
        }

        else if (text.equals("Teleop Cubes in Exchange")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.TeleopCubesExchangeComparator());
            Collections.sort(search, new Team.TeleopCubesExchangeComparator());
        }

        else if (text.equals("Climbed")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.ClimbedComparator());
            Collections.sort(search, new Team.ClimbedComparator());
        }

        else if (text.equals("Malfunction")) {
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.MalfunctionComparator());
            Collections.sort(search, new Team.MalfunctionComparator());
        }

        else if (text.equals("Team Number")){
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.TeamNumberComparator());
            Collections.sort(search, new Team.TeamNumberComparator());
        }

        else{
            Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.MatchNumberComparator());
            Collections.sort(search, new Team.MatchNumberComparator());
        }

        customAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResume(){
        super.onResume();
        customAdaptor.notifyDataSetChanged();
        ViewTournaments.tinydb.clear();
        ViewTournaments.tinydb.putListObject("Tournaments", ViewTournaments.tournaments);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ViewTeams.this, Menu.class));
        finish();
    }


    private class CustomAdaptor extends BaseAdapter{

        @Override
        public int getCount() {

            searchValue = searchEditText.getText().toString();

            if (!searchValue.equals(""))
                return search.size();
            else
                return ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size();
        }

        @Override
        public Object getItem(int position) {
            return getItemId(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.view_teams_custom_layout,null);
            TextView viewTeamNumber = convertView.findViewById(R.id.viewTeamNumber);
            TextView viewMatchNumber = convertView.findViewById(R.id.viewMatchNumber);
            TextView viewSpecific = convertView.findViewById(R.id.viewSpecific);
            TextView viewSpecificText = convertView.findViewById(R.id.viewSpecificText);
            searchValue = searchEditText.getText().toString();
            Log.d("T", "check");
            text = spinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(searchValue)) {
                viewTeamNumber.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeamNumber()));
                viewMatchNumber.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getMatchNumber()));

                if (text.equals("Auto Cube in Switch")) {

                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getAutoCubesSwitch())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Auto Cube in Scale")) {

                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getAutoCubesScale())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Crossed Baseline")) {

                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getCrossedBaseline())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Teleop Cubes in Switch")) {

                    viewSpecific.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeleopCubesSwitch()));
                    viewSpecificText.setText(text);

                } else if (text.equals("Teleop Cubes in Scale")) {

                    viewSpecific.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeleopCubesScale()));
                    viewSpecificText.setText(text);

                } else if (text.equals("Teleop Cubes in Exchange")) {

                    viewSpecific.setText(String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getTeleopCubesExchange()));
                    viewSpecificText.setText("Teleop Exchange");

                } else if (text.equals("Climbed")) {

                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getClimbed())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Malfunction")) {

                    if (ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(position).getMalfunction())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Team Number")) {

                    viewSpecificText.setText("More");
                    viewSpecific.setText("Information");

                } else {
                    viewSpecificText.setText("More");
                    viewSpecific.setText("Information");
                }
                customAdaptor.notifyDataSetChanged();
            }
            else{

                viewTeamNumber.setText(String.valueOf(search.get(position).getTeamNumber()));
                viewMatchNumber.setText(String.valueOf(search.get(position).getMatchNumber()));

                if (text.equals("Auto Cube in Switch")) {

                    if (search.get(position).getAutoCubesSwitch())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Auto Cube in Scale")) {

                    if (search.get(position).getAutoCubesScale())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Crossed Baseline")) {

                    if (search.get(position).getCrossedBaseline())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Teleop Cubes in Switch")) {

                    viewSpecific.setText(String.valueOf(search.get(position).getTeleopCubesSwitch()));
                    viewSpecificText.setText(text);

                } else if (text.equals("Teleop Cubes in Scale")) {

                    viewSpecific.setText(String.valueOf(search.get(position).getTeleopCubesScale()));
                    viewSpecificText.setText(text);

                } else if (text.equals("Teleop Cubes in Exchange")) {

                    viewSpecific.setText(String.valueOf(search.get(position).getTeleopCubesExchange()));
                    viewSpecificText.setText("Teleop Exchange");

                } else if (text.equals("Climbed")) {

                    if (search.get(position).getClimbed())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Malfunction")) {

                    if (search.get(position).getMalfunction())
                        viewSpecific.setText("Yes");
                    else
                        viewSpecific.setText("No");

                    viewSpecificText.setText(text);

                } else if (text.equals("Team Number")) {

                    viewSpecificText.setText("More");
                    viewSpecific.setText("Information");

                } else {
                    viewSpecificText.setText("More");
                    viewSpecific.setText("Information");
                }
                customAdaptor.notifyDataSetChanged();
            }


            return convertView;
        }
    }

}