package sos.rock.sosapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import sos.rock.sosapp.Login.LoginActivity;
import sos.rock.sosapp.Utils.MPreferenceManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public MainApplication getMainApp() {
        return (MainApplication) getApplication();
    }

    public void LogOut(){
        MPreferenceManager.saveBoolInformation(this, MPreferenceManager.REMEMBER_ME, false);
        MPreferenceManager.saveStringInformation(this, MPreferenceManager.TOKEN, "");

        getMainApp().setToken("");

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void Alert(String title, String content) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(content)
                .setPositiveButton(getString(R.string.button_ok),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
