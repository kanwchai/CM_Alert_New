package com.saikaew_rus.cm_alert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class CM_6_Travel_Map extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation, locA, locB;

    TextView workng, car_regis, car_kilo;
    EditText regCar, mKilocarText;
    int car_id, countKilo;
    RadioButton btnStop, btnStart;
    Intent intent;
    Repo_6_RUN_DATA repo_6_run_data;
    TB_6_RUN_DATA tb_6_run_data;
    A_Toast_Time a_toast_time;
    GifImageView gifImageView;
    TB_1_CAR tb_1_car;
    Repo_1_CAR repo_1_car;
    DecimalFormat decimalFormat;
    SimpleDateFormat sdf;
    A_Repo_Check a_repo_check;
    RadioButton radioStart, radioStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6_travel_map);
        this.setTitle(R.string.title_travel);

        setLayout();
        setValue();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void setLayout() {
        car_regis = (TextView) findViewById(R.id.textView5);
        car_kilo = (TextView) findViewById(R.id.textviewKilo);
        mKilocarText = (EditText) findViewById(R.id.kilo_car);
        regCar = (EditText) findViewById(R.id.reg_car_tra);
        workng = (TextView) findViewById(R.id.statusRun);
        gifImageView = (GifImageView) findViewById(R.id.gifImage_work);
        btnStop = (RadioButton) findViewById(R.id.stoptra);
        btnStart = (RadioButton) findViewById(R.id.starttra);
        radioStart = (RadioButton) findViewById(R.id.starttra);
        radioStop = (RadioButton) findViewById(R.id.stoptra);
    }

    public void setValue() {
        repo_6_run_data = new Repo_6_RUN_DATA(this);
        tb_6_run_data = new TB_6_RUN_DATA();
        a_toast_time = new A_Toast_Time();
        repo_1_car = new Repo_1_CAR(this);
        tb_1_car = new TB_1_CAR();
        decimalFormat = new DecimalFormat("#,###,###.##");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        a_repo_check = new A_Repo_Check(this);

        intent = getIntent();
        car_id = intent.getIntExtra(TB_1_CAR.Car_Id, 0);
        tb_1_car = repo_1_car.getCarById(car_id);
        tb_6_run_data = repo_6_run_data.getLastRunByCar_Id(car_id);

        regCar.setText(tb_1_car.car_Register);
        Log.d("reg_car", tb_1_car.car_Register);
        mKilocarText.setText(decimalFormat.format(tb_6_run_data.run_Kilo_End));

        workng.setText(R.string.travel_disconnecting);
        workng.setTextColor(Color.RED);
        gifImageView.setBackgroundResource(0);
    }

    public void travel_control(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.starttra:
                if (checked) {
                    if (mGoogleApiClient == null) {
                        chkTravel();
                        countKilo = 0;
                        locA = null;
                        locB = null;
                    }
                }
                break;
            case R.id.stoptra:
                if (checked) {
                    if (mGoogleApiClient != null) {
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                        mGoogleApiClient = null;
                        workng.setText(R.string.travel_disconnecting);
                        workng.setTextColor(Color.RED);
                        gifImageView.setBackgroundResource(0);
                        setStopTravel();
                        if (a_repo_check.chkDueKilo(car_id) >= 1) {
                            notifiChkkilo();
                        }
                    }
                }
                break;
        }
    }

    public void setStartTravel() {
        tb_6_run_data.run_Kilo_Start = tb_6_run_data.run_Kilo_End;
        tb_6_run_data.run_Date_Start = sdf.format(Calendar.getInstance().getTime());
        workng.setText(R.string.travel_connecting);
        workng.setTextColor(getResources().getColor(R.color.working));
        gifImageView.setBackgroundResource(R.drawable.point);
    }

    public void setStopTravel() {
        tb_6_run_data.run_Date_End = sdf.format(Calendar.getInstance().getTime());
        tb_6_run_data.run_Kilo_End = Double.parseDouble(String.format("%.2f", tb_6_run_data.run_Kilo_End));
        repo_6_run_data.insert(tb_6_run_data);
    }

    public void chkTravel() {
        if (checkGPS()) {
            if (checkPlayServices()) {
                //Initialize Google Play Services
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkLocationPermission();
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                        setStartTravel();
                    }
                } else {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                    setStartTravel();
                }
            }
        }
    }

    // check if google play services is installed on the device
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "This device is supported. Please download google play services", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    public boolean checkGPS() {
        LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            btnStop.setChecked(true);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Asking tb_9_user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Prompt the tb_9_user once explanation has been shown
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        if (locA == null) {
            locA = mLastLocation;
            a_toast_time.Toast_Time(this, "Update Location", 1000);
        } else {
            locB = mLastLocation;
            a_toast_time.Toast_Time(this, "Update Location", 1000);
            tb_6_run_data.run_Kilo_End += locA.distanceTo(locB) / 1000;
            if (String.valueOf(locA.distanceTo(locB)) != null) {
                countKilo += Integer.valueOf((int) locA.distanceTo(locB));
            }

            locA = mLastLocation;
            if (countKilo >= 100) {
                mKilocarText.setText(decimalFormat.format(tb_6_run_data.run_Kilo_End));
                countKilo %= 100;
            }
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location tb_9_user
                .zoom(17)                   // Sets the zoom
                .bearing(0)                 // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    private static final String TITLE = "Maintenance Your Car";
    private static final String MESSAGE = "You have parts breakdown!!!";

    public void notifiChkkilo() {
        Intent intent = new Intent(getApplicationContext(), CM_7_List_Parts_Recycle.class);
        intent.putExtra(TB_1_CAR.Car_Id, car_id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CM_7_List_Parts_Recycle.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_logo)
                .setLargeIcon(icon)
                .setContentTitle(TITLE)
                .setContentText(MESSAGE)
                .setAutoCancel(true)
                .setVibrate(new long[]{500, 1000, 500})
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }
}
