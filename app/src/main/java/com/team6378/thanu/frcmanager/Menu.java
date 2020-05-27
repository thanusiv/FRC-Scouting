package com.team6378.thanu.frcmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button addTeam = findViewById(R.id.addATeamButton);
        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewTournaments.selected != -1) {
                    Intent startIntent = new Intent(getApplicationContext(), AddATeam.class);
                    startActivity(startIntent);
                }
                else{
                    ConstraintLayout layout = findViewById(R.id.menuConstraintLayout);
                    Snackbar error = Snackbar.make(layout, "Please select or create a tournament", Snackbar.LENGTH_LONG);
                    error.show();
                }
            }
        });

        Button bluetooth = findViewById(R.id.bluetoothButton);
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewTournaments.selected != -1) {
                    Intent startIntent = new Intent(getApplicationContext(), MainBluetoothActivity.class);
                    startActivity(startIntent);
                }
                else{
                    ConstraintLayout layout = findViewById(R.id.menuConstraintLayout);
                    Snackbar error = Snackbar.make(layout, "Please select or create a tournament", Snackbar.LENGTH_LONG);
                    error.show();
                }
            }
        });

        Button viewTeams = findViewById(R.id.viewTeamsButton);
        viewTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewTournaments.selected != -1) {
                    Intent startIntent = new Intent(getApplicationContext(), ViewTeams.class);
                    startActivity(startIntent);
                }
                else{
                    ConstraintLayout layout = findViewById(R.id.menuConstraintLayout);
                    Snackbar error = Snackbar.make(layout, "Please select or create a tournament", Snackbar.LENGTH_LONG);
                    error.show();
                }
            }
        });

        Button rankings = findViewById(R.id.rankingButton);
        rankings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewTournaments.selected != -1) {
                    Intent startIntent = new Intent(getApplicationContext(), ViewRankings.class);
                    startActivity(startIntent);
                }
                else{
                    ConstraintLayout layout = findViewById(R.id.menuConstraintLayout);
                    Snackbar error = Snackbar.make(layout, "Please select or create a tournament", Snackbar.LENGTH_LONG);
                    error.show();
                }
            }
        });

        Button images = findViewById(R.id.viewImagesButton);
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewTournaments.selected != -1) {
                    Intent startIntent = new Intent(getApplicationContext(), ViewRobotImages.class);
                    startActivity(startIntent);
                }
                else{
                    ConstraintLayout layout = findViewById(R.id.menuConstraintLayout);
                    Snackbar error = Snackbar.make(layout, "Please select or create a tournament", Snackbar.LENGTH_LONG);
                    error.show();
                }
            }
        });

        Button tournaments = findViewById(R.id.tournamentButton);
        tournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent startIntent = new Intent(getApplicationContext(), ViewTournaments.class);
                    startActivity(startIntent);
            }
        });

        Button exportExcel = findViewById(R.id.exportExcelFile);
        exportExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewTournaments.selected == -1) {
                    ConstraintLayout layout = findViewById(R.id.menuConstraintLayout);
                    Snackbar error = Snackbar.make(layout, "Please select or create a tournament", Snackbar.LENGTH_LONG);
                    error.show();
                } else {
                    int permissionCheck = checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
                    permissionCheck += checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
                    if (permissionCheck != 0) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
                    }

                    if (ContextCompat.checkSelfPermission(Menu.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        createExcelFile();
                        ConstraintLayout layout = findViewById(R.id.menuConstraintLayout);
                        Snackbar success = Snackbar.make(layout, "Success! Excel file is now located in Downloads", Snackbar.LENGTH_LONG);
                        success.show();
                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {}

    public void createExcelFile(){

        String excelFile="ScoutingData"  + ".xls";
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File directory = new File (downloads.getAbsolutePath());
        File file = new File(directory, excelFile);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;
        Collections.sort(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams(), new Team.TeamNumberComparator());

        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label0 = new Label(0,0,"Match Number");
            Label label1 = new Label(1,0,"Team Number");
            Label label2 = new Label(2,0,"Crossed Baseline");
            Label label3 = new Label(3,0,"Auto Cube in Switch");
            Label label4 = new Label(4,0,"Auto Cube in Scale");
            Label label5 = new Label(5,0,"Teleop Cubes in Switch");
            Label label6 = new Label(6,0,"Teleop Cubes in Scale");
            Label label7 = new Label(7,0,"Teleop Cubes in Exchange");
            Label label8 = new Label(8,0,"Climbed");
            Label label9 = new Label(9,0,"Malfunction");

            try {
                sheet.addCell(label0);
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
                sheet.addCell(label4);
                sheet.addCell(label5);
                sheet.addCell(label6);
                sheet.addCell(label7);
                sheet.addCell(label8);
                sheet.addCell(label9);

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(0,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getMatchNumber()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(1,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getTeamNumber()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(2,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getCrossedBaseline()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(3,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getAutoCubesSwitch()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(4,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getAutoCubesScale()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(5,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getTeleopCubesSwitch()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(6,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getTeleopCubesScale()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(7,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getTeleopCubesExchange()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(8,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getClimbed()));
                    sheet.addCell(label0);
                }

                for (int i = 1; i <= ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().size(); i++ ){
                    label0 = new Label(9,i,String.valueOf(ViewTournaments.tournaments.get(ViewTournaments.selected).getTeams().get(i-1).getMalfunction()));
                    sheet.addCell(label0);
                }

                for (int i = 0; i < 10; i++)
                    sheet.setColumnView(i,22);

            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();

            try {
                workbook.close();
            } catch (WriteException e) {

                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
