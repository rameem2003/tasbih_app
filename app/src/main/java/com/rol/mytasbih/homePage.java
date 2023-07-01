
package com.rol.mytasbih;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;


public class homePage extends AppCompatActivity {
    ImageView aboutBtn, updateBtn;
    Button btnQub,btn33,btn100,btn500,btnInf;
    TextView counter;

    CardView countableBtn,refresh;

    Vibrator vibrate;
    AppUpdateManager appUpdateManager;

    private static final int MY_REQUEST_CODE = 100;

    AlertDialog.Builder alertdialog;
    int count = 0;
    int limit = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        checkAppUpdate();
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
                        Uri uri = Uri.parse("https://republic-of-legends.netlify.app/appgallery/tasbih");
                        Intent u = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(u);

                        checkAppUpdate();

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


    private void checkAppUpdate (){
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo
                            // an activity result launcher registered via registerForActivityResult
                            ,AppUpdateType.FLEXIBLE, homePage.this,
                            // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                            // flexible updates.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Before starting an update, register a listener for updates.
        appUpdateManager.registerListener(listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.d("msg","Update flow failed! Result code: " + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // When status updates are no longer needed, unregister the listener.
        appUpdateManager.unregisterListener(listener);
    }

    InstallStateUpdatedListener listener = state -> {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            popupSnackbarForCompleteUpdate();
        }
    };

    // Displays the snackbar notification and call to action.
    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("RESTART", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(android.R.color.holo_blue_bright));
        snackbar.show();
    }
}