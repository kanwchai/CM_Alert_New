package com.saikaew_rus.cm_alert;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;

import pl.droidsonroids.gif.GifImageView;

public class CM_6_Travel extends AppCompatActivity implements LocationListener {

    TextView workng;
    EditText regCar, mKilocarText;
    int car_id;
    Location locA, locB;
    Intent intent;
    Repo_6_RUN_DATA repo_6_run_data;
    TB_6_RUN_DATA tb_6_run_data;
    A_Toast_Time a_toast_time;
    GifImageView gifImageView;
    TB_1_CAR tb_1_car;
    Repo_1_CAR repo_1_car;
    DecimalFormat decimalFormat;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6_travel);
        this.setTitle(A_Word_App.title_travel[A_Word_App.language]);

        setLayout();
        setValue();
    }

    public void setLayout() {
        mKilocarText = (EditText) findViewById(R.id.kilo_car);
        regCar = (EditText) findViewById(R.id.reg_car_tra);
        workng = (TextView) findViewById(R.id.statusRun);
        gifImageView = (GifImageView) findViewById(R.id.gifImage_work);
    }

    public void setValue() {
        repo_6_run_data = new Repo_6_RUN_DATA(this);
        tb_6_run_data = new TB_6_RUN_DATA();
        a_toast_time = new A_Toast_Time();
        repo_1_car = new Repo_1_CAR(this);
        tb_1_car = new TB_1_CAR();
        decimalFormat = new DecimalFormat("#,###,###.##");


        intent = getIntent();
        car_id = intent.getIntExtra(TB_1_CAR.Car_Id, 0);
        tb_1_car = repo_1_car.getCarById(car_id);
        tb_6_run_data = repo_6_run_data.getLastRunByCar_Id(car_id);

        regCar.setText(tb_1_car.car_Register);
        Log.d("reg_car", tb_1_car.car_Register);
        mKilocarText.setText(decimalFormat.format(tb_6_run_data.run_Kilo_End));

        workng.setText("Not Working");
        workng.setTextColor(Color.RED);
        gifImageView.setBackgroundResource(0);
    }

    public void travel_control(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.starttra:
                if (checked) {
                    chkTravel();
                    workng.setText("Do Working");
                    workng.setTextColor(getResources().getColor(R.color.working));
                    gifImageView.setBackgroundResource(R.drawable.point);
                }
                break;
            case R.id.stoptra:
                if (checked) {
                    workng.setText("Not Working");
                    workng.setTextColor(Color.RED);
                    gifImageView.setBackgroundResource(0);
                    stopFusedLocation();
                    locA = null;
                    locB = null;
                }
                break;
        }
    }

    public void chkTravel() {
        if (checkGPS()) {
            if (checkPlayServices()) {
                startFusedLocation();
                registerRequestUpdate(this);
            }
        }
    }

    @Override
    protected void onStop() {
        stopFusedLocation();
        super.onStop();
    }

    // check if google play services is installed on the device
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(),
                        "This device is supported. Please download google play services", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    public boolean checkGPS() {
        LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        } else {
            return true;
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void startFusedLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {

                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {

                        }
                    }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                        @Override
                        public void onConnectionFailed(ConnectionResult result) {

                        }
                    }).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
    }

    public void stopFusedLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void registerRequestUpdate(final LocationListener listener) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // every second

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!isGoogleApiClientConnected()) {
                        mGoogleApiClient.connect();
                    }
                    registerRequestUpdate(listener);
                }
            }
        }, 1000);
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (locA == null) {
            locA = location;
            Log.e("testdataloc", locA.getLatitude() + " " + locA.getLongitude());
        } else {
            locB = location;
            if (locA.distanceTo(locB) >= 100) {
                tb_6_run_data.run_Kilo_End += locA.distanceTo(locB);
                locA = location;
                mKilocarText.setText(decimalFormat.format(tb_6_run_data.run_Kilo_End));
            }
            Log.e("testdataloc", locB.getLatitude() + " " + locB.getLongitude());
        }
        a_toast_time.Toast_Time(getApplicationContext(), "NEW LOCATION RECEIVED", 1000);
    }
}
