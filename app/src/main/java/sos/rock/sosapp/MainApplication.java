package sos.rock.sosapp;

import android.app.Application;

import java.util.ArrayList;

import sos.rock.sosapp.Model.Shelve;
import sos.rock.sosapp.Model.User;

/**
 * Created by rock on 4/8/2017.
 */

public class MainApplication extends Application {
    User me;
    String token;
    public ArrayList<Shelve> myShelves = new ArrayList<>();
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public ArrayList<Shelve> getMyShelves() {
        return myShelves;
    }

    public void setMyShelves(ArrayList<Shelve> myShelves) {
        this.myShelves = myShelves;
    }
}
