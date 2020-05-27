package com.team6378.thanu.frcmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ViewRankings extends AppCompatActivity {

    private ListView rankingsListView;
    private RankingCustomAdaptor rankingCustomAdaptor;
    private Spinner rankingSpinner;
    private ArrayList<Ranking> rankings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);
        rankingCustomAdaptor = new RankingCustomAdaptor();
        rankingsListView = findViewById(R.id.rankingsListView);
        rankingsListView.setAdapter(rankingCustomAdaptor);

        ArrayList<Team> temp = ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams();
        Collections.sort(temp, new Team.TeamNumberComparator());

        int current = 0;

        while (current < temp.size()){
            int x = temp.get(current).getTeamNumber();
            int t = current;
            int games = 0;
            while (t != temp.size() && x == temp.get(t).getTeamNumber()) {
                games++;
                t++;
            }
            int teamNumber = temp.get(current).getTeamNumber();
            double averageSwitch = 0;
            double averageScale = 0;
            double averageExchange = 0;
            int autoSwitch = 0;
            int crossedBaseline = 0;
            int autoScale = 0;
            int climbs = 0;
            int malfunctions = 0;

            for (int i = current; i < current + games; i++){
                averageSwitch += temp.get(i).getTeleopCubesSwitch();
                averageScale += temp.get(i).getTeleopCubesScale();
                averageExchange += temp.get(i).getTeleopCubesExchange();
                if (temp.get(i).getCrossedBaseline())
                    crossedBaseline++;
                if (temp.get(i).getAutoCubesSwitch())
                    autoSwitch++;
                if (temp.get(i).getAutoCubesScale())
                    autoScale++;
                if (temp.get(i).getMalfunction())
                    malfunctions++;
                if (temp.get(i).getClimbed())
                    climbs++;
            }
            current+= games;
            averageSwitch /= games;
            averageScale /= games;
            averageExchange /= games;

            Ranking r = new Ranking(teamNumber, averageSwitch, averageScale, averageExchange, crossedBaseline, autoSwitch, autoScale, games, climbs, malfunctions);
            rankings.add(r);
        }

        rankingCustomAdaptor.notifyDataSetChanged();
        Log.d("Check", " " + rankings.size());

        rankingSpinner = findViewById(R.id.rankingSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rankings_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rankingSpinner.setAdapter(adapter);
        rankingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = rankingSpinner.getSelectedItem().toString();

                if (s.equals("Best Exchange Bot"))
                    Collections.sort(rankings, new Ranking.TeleopCubesExchangeComparator());
                else if (s.equals("Best Scale Bot"))
                    Collections.sort(rankings, new Ranking.TeleopCubesScaleComparator());
                else
                    Collections.sort(rankings, new Ranking.TeleopCubesSwitchComparator());

                rankingCustomAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ViewRankings.this, Menu.class));
        finish();

    }


    private class RankingCustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return rankings.size();
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
            convertView = getLayoutInflater().inflate(R.layout.rankings_custom_layout, null);

            TextView teamNumber = convertView.findViewById(R.id.rankingTeamNumberTextVIew);
            TextView gameElementTextView = convertView.findViewById(R.id.gameElementTextView);
            TextView averageGameElementTextView = convertView.findViewById(R.id.averageGameElementTextVIew);
            TextView numberOfGamesTextView = convertView.findViewById(R.id.numberOfGamesTextView);
            TextView crossedBaseline = convertView.findViewById(R.id.crossedBaselineNumberTextView);
            TextView autoSwitch = convertView.findViewById(R.id.autoSwitchNumberTextView);
            TextView autoScale = convertView.findViewById(R.id.autoScaleNumberTextView);
            TextView rank = convertView.findViewById(R.id.viewRobotImageTeamNumberTextView);
            TextView climbs = convertView.findViewById(R.id.climbsNumberTextView);
            TextView malfunctions = convertView.findViewById(R.id.malfunctionsNumberTextView);
            String textSpinner = rankingSpinner.getSelectedItem().toString();

            rank.setText("#" + (position + 1));
            teamNumber.setText(String.valueOf(rankings.get(position).getTeamNumber()));
            if (textSpinner.equals("Best Switch Bot")) {
                gameElementTextView.setText("Average Switch");
                averageGameElementTextView.setText(String.valueOf(rankings.get(position).getAverageSwitch()));
            }
            else if (textSpinner.equals("Best Scale Bot")) {
                gameElementTextView.setText("Average Scale");
                averageGameElementTextView.setText(String.valueOf(rankings.get(position).getAverageScale()));
            }
            else {
                gameElementTextView.setText("Average Exchange");
                averageGameElementTextView.setText(String.valueOf(rankings.get(position).getAverageExchange()));
            }
            numberOfGamesTextView.setText("/" + String.valueOf(rankings.get(position).getNumberOfGames()) + " games");
            crossedBaseline.setText(String.valueOf(rankings.get(position).getCrossedBaseline()));
            autoSwitch.setText(String.valueOf(rankings.get(position).getAutoSwitch()));
            autoScale.setText(String.valueOf(rankings.get(position).getAutoScale()));
            climbs.setText(String.valueOf(rankings.get(position).getClimbs()));
            malfunctions.setText(String.valueOf(rankings.get(position).getMalfunctions()));

            return convertView;
        }
    }
}
