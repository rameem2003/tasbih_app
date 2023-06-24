
package com.example.testapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;


public class homePage extends AppCompatActivity {
    ImageView aboutBtn, updateBtn;
    Button btnQub,btn33,btn100,btn500,btnInf;
    TextView counter;

    CardView countableBtn,refresh;

    Vibrator vibrate;

    AlertDialog.Builder alertdialog;
    int count = 0;
    int limit = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        aboutBtn = findViewById(R.id.about);
        updateBtn = findViewById(R.id.update);
        btnQub = findViewById(R.id. btnQub);

        counter = findViewById( R.id.counter);
        countableBtn = findViewById(R.id.countableBtn);
        refresh = findViewById(R.id.refresh);

        btn33 = findViewById(R.id.btn33);
        btn100 = findViewById(R.id.btn100);
        btn500 = findViewById(R.id.btn500);
        btnInf = findViewById(R.id.btnInf);


        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);



        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e =new Intent(homePage.this, About_Page.class);
                startActivity(e);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog = new AlertDialog.Builder(homePage.this);
                alertdialog.setIcon(R.drawable.update);
                alertdialog.setTitle("Update");
                alertdialog.setMessage("Do you want to update Tasbih App?");

                alertdialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("https://republic-of-legends.netlify.app/projects/android/tasbih.html");
                        Intent u = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(u);

                    }
                });

                alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = alertdialog.create();
                dialog.show();
            }
        });
        btnQub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f =new Intent(homePage.this, Compass.class);
                startActivity(f);
            }
        });


        countableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //count++;
                if (limit==33 && count < 33){
                    count++;
                } else if (limit==100 && count < 100) {
                    count++;
                } else if (limit==500 && count < 500) {
                    count++;
                } else if (limit == 0) {
                    count++;
                } else {
                    vibrate.vibrate(150);
                    Toast.makeText(homePage.this, "Limit Fullfill", Toast.LENGTH_LONG).show();
                }

                counter.setText(String.valueOf(count));
            }
        });


       refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                counter.setText(String.valueOf(count));

                vibrate.vibrate(150);
            }
        });

       btn33.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               limit = 33;
           }
       });

        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limit = 100;
            }
        });
        btn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limit = 500;
            }
        });
        btnInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limit = 0;
            }
        });


    }
}