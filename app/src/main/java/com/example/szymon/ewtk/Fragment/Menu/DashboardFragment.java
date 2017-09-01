package com.example.szymon.ewtk.Fragment.Menu;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.User;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.SessionManagement;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements SensorEventListener{

    private User user;
    private TextView userName;
    private TextView userEmail;
    private ImageView userImage;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;
    private SensorManager mSensorManager;
    private Sensor sensor;
    private TextView accelerometerX;
    private TextView accelerometerY;
    private TextView accelerometerZ;
    private TextView gyroscopeX;
    private TextView gyroscopeY;
    private TextView gyroscopeZ;
    private TextView temperatureAmbient;
    private TextView heartRate;
    private TextView atmospheric;
    private TextView light;

    public static DashboardFragment newInstance(User user) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.USER_INSTANCE,user);
        fragment.setArguments(args);
        return  fragment;
    }

    public User getUser(){
        return getArguments().getParcelable(Constants.USER_INSTANCE);
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (savedInstanceState != null)
            user = savedInstanceState.getParcelable(Constants.USER_STATE);
         else
            user = getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.home));
        initSensorText(view);
        userName = (TextView) view.findViewById(R.id.dashboard_user_name);
        userEmail = (TextView) view.findViewById(R.id.dashboard_user_email);
        userImage = (ImageView) view.findViewById(R.id.dashboard_user_image);
        userName.setText(user.getName() + " " + user.getSurname());
        userEmail.setText(user.getEmail());
        String firstLetter = String.valueOf(getResources().getString(R.string.welcome).charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(user.getID());
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        userImage.setImageDrawable(drawable);
        registerListener();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.USER_STATE, user);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onDestroyView(){
        super.onDestroyView();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            DecimalFormat df = new DecimalFormat("0.0000");
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;

            accelerometerX.setText("X: "+df.format(event.values[0]));
            accelerometerY.setText("Y: "+df.format(event.values[1]));
            accelerometerZ.setText("Z: "+df.format(event.values[2]));

        }if(sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            temperatureAmbient.setText("Celsius: " + event.values[0]);
        }if(sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            DecimalFormat df = new DecimalFormat("0.00000");
            gyroscopeX.setText("X: " + df.format(event.values[0]));
            gyroscopeY.setText("Y: " + df.format(event.values[1]));
            gyroscopeZ.setText("Z: " + df.format(event.values[2]));
        }if(sensor.getType() == Sensor.TYPE_HEART_RATE){
            heartRate.setText(String.valueOf(event.values[0])+" /min");
        }if(sensor.getType() == Sensor.TYPE_LIGHT){
            light.setText(event.values[0] + " lx");
        }if(sensor.getType() == Sensor.TYPE_PRESSURE){
            DecimalFormat df = new DecimalFormat("0.0");
            atmospheric.setText(df.format(event.values[0]) + " hPa");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void initSensorText(View view){
        accelerometerX = (TextView) view.findViewById(R.id.accelerometerX);
        accelerometerY = (TextView) view.findViewById(R.id.accelerometerY);
        accelerometerZ = (TextView) view.findViewById(R.id.accelerometerZ);
        light = (TextView) view.findViewById(R.id.light);
        atmospheric = (TextView) view.findViewById(R.id.atmospheric);
        gyroscopeX = (TextView) view.findViewById(R.id.gyroscopeX);
        gyroscopeY = (TextView) view.findViewById(R.id.gyroscopeY);
        gyroscopeZ = (TextView) view.findViewById(R.id.gyroscopeZ);
        heartRate = (TextView) view.findViewById(R.id.heartRATE);
    }

    public void registerListener(){
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_GAME);
    }
}
