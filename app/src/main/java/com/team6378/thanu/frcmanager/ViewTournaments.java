package com.team6378.thanu.frcmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewTournaments extends AppCompatActivity {

    private ListView allTournaments;
    private TournamentCustomAdaptor customAdaptor;
    public static int selected = -1;
    private int counter = -1;
    public static ArrayList <Tournament> tournaments = new ArrayList<>();
    public static TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);
        tinydb = new TinyDB(getApplicationContext());
        customAdaptor = new TournamentCustomAdaptor();
        allTournaments = findViewById(R.id.robotImagesListView);
        allTournaments.setAdapter(customAdaptor);
        if (tinydb.getPreferences().contains("Tournaments"))
            tournaments = tinydb.getListObject("Tournaments", Tournament.class);
        customAdaptor.notifyDataSetChanged();
        Button addTournamentButton = findViewById(R.id.addTournamentButton);
        addTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTournaments.this);
                builder.setTitle("Add Tournament");

                // Set up the input
                final EditText input = new EditText(ViewTournaments.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tournament newTournament = new Tournament(input.getText().toString());
                        tournaments.add(newTournament);
                        counter++;
                        selected = counter;
                        customAdaptor.notifyDataSetChanged();
                        tinydb.clear();
                        tinydb.putListObject("Tournaments", tournaments);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        allTournaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                ConstraintLayout layout = findViewById(R.id.tournamentConstraintLayout);
                Snackbar setTournament = Snackbar.make(layout, "Set the tournament to " + tournaments.get(position).getTournamentName(), Snackbar.LENGTH_LONG);
                setTournament.show();
            }
        });

        allTournaments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTournaments.this);

                builder.setTitle("Confirm");
                builder.setMessage("What would you like to do?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    tournaments.remove(position);
                        customAdaptor.notifyDataSetChanged();
                        tinydb.clear();
                        tinydb.putListObject("Tournaments", tournaments);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTournaments.this);
                        builder.setTitle("Edit Tournament Name");

                        // Set up the input
                        final EditText input = new EditText(ViewTournaments.this);

                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        input.setText(tournaments.get(position).getTournamentName());
                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tournaments.get(position).changeTournamentName(input.getText().toString());
                                customAdaptor.notifyDataSetChanged();
                                tinydb.clear();
                                tinydb.putListObject("Tournaments", tournaments);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

    }

    public static void addTeam(Team newTeam){
        tournaments.get(selected).getTeams().add(newTeam);
    }

    private class TournamentCustomAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return tournaments.size();
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
           convertView = getLayoutInflater().inflate(R.layout.view_tournaments_custom_layout, null);
            TextView tournamentName = convertView.findViewById(R.id.viewRobotImageTeamNumberTextView);
            TextView entries = convertView.findViewById(R.id.entriesTextVIew);
            tournamentName.setText(tournaments.get(position).getTournamentName());
            entries.setText("Entries: " + tournaments.get(position).getTeams().size());
            return convertView;
        }
    }
}
