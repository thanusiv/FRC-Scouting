package com.team6378.thanu.frcmanager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static com.team6378.thanu.frcmanager.ViewTournaments.tournaments;


public class ViewRobotImages extends AppCompatActivity {

    private ListView allRobotImages;
    private RobotImagesCustomAdaptor customAdaptor;
    private static final int CAMERA_IMAGE_REQUEST=1;
    private int teamNumber = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_robot_images);

        customAdaptor = new RobotImagesCustomAdaptor();
        allRobotImages = findViewById(R.id.robotImagesListView);
        allRobotImages.setAdapter(customAdaptor);
        customAdaptor.notifyDataSetChanged();


        Button addRobotImagesButton = findViewById(R.id.addRobotImagesButton);
        addRobotImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewRobotImages.this);
                builder.setTitle("Add Robot");

                // Set up the input
                final EditText input = new EditText(ViewRobotImages.this);

                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (checkSelfPermission(Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    CAMERA_IMAGE_REQUEST);
                        }
                        if(ContextCompat.checkSelfPermission(ViewRobotImages.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            captureImage();
                            teamNumber = Integer.parseInt(input.getText().toString());
                        }
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_IMAGE_REQUEST) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_LONG).show();

            }
        }
    }

    public void captureImage() {
        Log.d("Check", "captureImage is called");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) != null)
        startActivityForResult(takePictureIntent,
                CAMERA_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        Log.d("Check", "onActivityResult");

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        if (imageBitmap != null) {
            Log.d("Check", "adding image");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String encoded = Base64.encodeToString(b, Base64.DEFAULT);
            RobotImage temp = new RobotImage(teamNumber, encoded);
            tournaments.get(ViewTournaments.selected).getRobotImages().add(temp);
            teamNumber = -1;
            ViewTournaments.tinydb.clear();
            ViewTournaments.tinydb.putListObject("Tournaments", ViewTournaments.tournaments);
        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


    private class RobotImagesCustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return tournaments.get(ViewTournaments.selected).getRobotImages().size();
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
            convertView = getLayoutInflater().inflate(R.layout.view_robot_images_custom_layout, null);
            TextView viewRobotImagesTextView = convertView.findViewById(R.id.viewRobotImageTeamNumberTextView);
            viewRobotImagesTextView.setText(String.valueOf(tournaments.get(ViewTournaments.selected).getRobotImages().get(position).getTeamNumber()));
            ImageView robotImageView = convertView.findViewById(R.id.robotImageView);
            Bitmap c = StringToBitMap(tournaments.get(ViewTournaments.selected).getRobotImages().get(position).getRobotImage());
            robotImageView.setImageBitmap(c);
            return convertView;
        }
    }
}
