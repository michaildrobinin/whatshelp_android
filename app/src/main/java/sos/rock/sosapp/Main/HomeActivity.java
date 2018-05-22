package sos.rock.sosapp.Main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import sos.rock.sosapp.BaseActivity;
import sos.rock.sosapp.Main.Contacts.ContactsFragment;
import sos.rock.sosapp.Main.Home.HomeFragment;
import sos.rock.sosapp.Main.Map.MapFragment;
import sos.rock.sosapp.Main.Profile.ProfileFragment;
import sos.rock.sosapp.Main.Setting.SettingFragment;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.GpsUtils;
import sos.rock.sosapp.Utils.LocationServiceHelper;

public class HomeActivity extends BaseActivity {

    Handler gpsTrackerListener = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == LocationServiceHelper.MESSAGE_NOTIFITY) {
                try {
                    //update user location
                    Bundle bundle = msg.getData();
                    Location location = bundle.getParcelable("lastLocation");
                    updateUserLocation(location);
                    Log.d("Location", "Location");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public enum FRAGMENTS{
        HOMEFRAGMENT, CONTACTSFRAGMENT, PROFILEFRAGMENT, SETTINGFRAGEMT, MAPFRAGMENT
    }

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigation;
    ImageButton imgLogout;

    private int PARAM_INT_VALUE = -1;
    public void setParentIntParameter(int value)
    {
        PARAM_INT_VALUE = value;
    }

    public void updateUserLocation(Location location){
        BaseFragment mapFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        mapFragment.setUserLocation(location);
    }

    public void setCurrentFragment(FRAGMENTS currentFragment) {
        this.currentFragment = currentFragment;
    }

    private FRAGMENTS currentFragment;

    public void addFragment(FRAGMENTS donationfragment) {
        if(currentFragment != donationfragment){
            setCurrentFragment(donationfragment);
            BaseFragment f = null;
            switch (donationfragment){
                case HOMEFRAGMENT:
                    f = HomeFragment.newInstance();
                    break;
                case CONTACTSFRAGMENT:
                    f = ContactsFragment.newInstance();
                    break;
                case PROFILEFRAGMENT:
                    f = ProfileFragment.newInstance();
                    break;
                case SETTINGFRAGEMT:
                    f = SettingFragment.newInstance();
                    break;
                case MAPFRAGMENT:
                    f = MapFragment.newInstance();
                    break;
            }
            attachFragment(f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        navigation = (NavigationView) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

        addFragment(FRAGMENTS.HOMEFRAGMENT);

        TurnOnGps();
    }

    private void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                addFragment(FRAGMENTS.HOMEFRAGMENT);
                break;
            case R.id.navigation_contacts:
                addFragment(FRAGMENTS.CONTACTSFRAGMENT);
                break;
            case R.id.navigation_profile:
                addFragment(FRAGMENTS.PROFILEFRAGMENT);
                break;
            case R.id.navigation_setting:
                addFragment(FRAGMENTS.SETTINGFRAGEMT);
                break;
            case R.id.navigation_map:
                addFragment(FRAGMENTS.MAPFRAGMENT);
                break;
            case R.id.navigation_logout:
                LogOut();
                break;
        }
        item.setChecked(true);
//        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    private void TurnOnGps(){
        LocationManager location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = GpsUtils.checkGpsAvailable(location_manager);
        if(!gps_enabled){
            showGPSDisabledAlertToUser();
        }
        else{
            GpsTrackerInit();
        }
    }

    private  void GpsTrackerInit(){
        Intent service_intent = new Intent(this, LocationServiceHelper.class);
        service_intent.putExtra(LocationServiceHelper.EXTRA_MESSENGER, new Messenger(gpsTrackerListener));
        startService(service_intent);
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.gps_error)
                .setCancelable(false)
                .setPositiveButton(R.string.button_gps_setting, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                        GpsTrackerInit();
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(HomeActivity.this, R.string.error_without_gps, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void ExitApp(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm))
                .setMessage(R.string.exit_app_alert)
                .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FinishApp();
                    }
                })
                .setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void FinishApp(){
        finish();
    }

    @Override
    public void onBackPressed() {
        ExitApp();
    }

    private void attachFragment(BaseFragment f) {
        if (f != null) {
            while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, f).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, LocationServiceHelper.class));

    }



}
