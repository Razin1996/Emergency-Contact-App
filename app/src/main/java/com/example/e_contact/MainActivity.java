package com.example.e_contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int HOSPITAL = 1;
    private static final int POLICE = 2;
    private static final int FIRESTATION = 3;
    private static final int AMBULANCE = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if(Preferences.readString(this, Preferences.NAME) == null){
            startActivity(new Intent(this, Registration.class));
            if(Preferences.readString(this, Preferences.NAME) == null){
                this.finish();
            }
        } else {
            setContentView(R.layout.main_activity);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }


    /**
     * This is the main screen of our application. This is the first screen shown after a user has registered.
     * The registration screen only shows if it is the users first time using the application.
     * @param v
     */
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sos_button) {
            startActivity(new Intent(this, Sos.class));
        } else if (id == R.id.firestation_button) {
            Intent firestation = new Intent(this, Maps_new.class);
            firestation.putExtra("key", FIRESTATION);
            startActivity(firestation);
        } else if (id == R.id.hospital_button) {
            Intent hospital = new Intent(this, Maps_new.class);
            hospital.putExtra("key", HOSPITAL);
            startActivity(hospital);
        } else if (id == R.id.ambulance_button) {
            Intent ambulance = new Intent(this, Maps_new.class);
            ambulance.putExtra("key", AMBULANCE);
            startActivity(ambulance);
        } else if (id == R.id.police_button) {
            Intent police = new Intent(this, Maps_new.class);
            police.putExtra("key", POLICE);
            startActivity(police);
        } else if (id == R.id.bloodbank_button) {
            startActivity(new Intent(this,BloodBank.class));
        } else if (id == R.id.emergencycontact_button) {
            try{
                StringBuilder number = new StringBuilder();
                number.append("tel:");
                number.append(Preferences.readString(this, Preferences.EM_NUMBER));
                Intent callIntent = new Intent (Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(number.toString()));
                startActivity(callIntent);
            }catch (ActivityNotFoundException activityException) {
                Log.e("First Response", "Call failed");
            }
        } else if (id == R.id.home_button) {
            try{
                StringBuilder number = new StringBuilder();
                number.append("tel:");
                number.append(Preferences.readString(this, Preferences.PHONE_NUMBER));
                Intent callIntent = new Intent (Intent.ACTION_CALL);
                //callIntent.setData(Uri.parse("tel:5136523144"));
                callIntent.setData(Uri.parse(number.toString()));
                startActivity(callIntent);
            }catch (ActivityNotFoundException activityException) {
                Log.e("First Response", "Call failed");
            }
        } else if (id == R.id.settings_button) {
            startActivity(new Intent(this,Settings.class));
        }
    }

    /** used upon resuming the application.
     *
     */
    public void onResume(){
        super.onResume();
        Log.v(null, "MainActivity's onResume Method !!!");
    }

    /**
     * an empty onPause method
     */
    public void onPause(){
        super.onPause();
        Log.v(null, "MainActivity's onPause Method !!!");
    }
    /**
     * Called before interrupt to save data.
     */
    protected void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        // nothing needs to be saved here since nothing is changed or being used.
    }
    /**
     * Called during onResume() to restore data.
     */
    protected void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        // Restore session score
        // nothing needs to be restored here since nothing is used previously.
    }
}