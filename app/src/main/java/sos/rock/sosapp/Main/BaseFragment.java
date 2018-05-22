package sos.rock.sosapp.Main;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import sos.rock.sosapp.R;


public abstract class BaseFragment extends Fragment {
    public abstract void setUserLocation(Location location);
    protected  HomeActivity parent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof HomeActivity)
            parent = (HomeActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    public void Alert(String title, String content) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
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
