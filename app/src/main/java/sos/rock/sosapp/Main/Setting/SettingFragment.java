package sos.rock.sosapp.Main.Setting;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sos.rock.sosapp.Main.BaseFragment;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.MPreferenceManager;


public class SettingFragment extends BaseFragment implements View.OnClickListener{

    private static int BACK_DISTANCE = 100;
    private static int BACK_CONTACT = 101;
    Button btDistanceWay, btContactLists, btExtraNumbers;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        btDistanceWay = (Button)rootView.findViewById(R.id.btDistanceWay);
        btDistanceWay.setOnClickListener(this);
        btContactLists = (Button)rootView.findViewById(R.id.btContactLists);
        btContactLists.setOnClickListener(this);
        if (MPreferenceManager.readBoolInformation(getContext(), MPreferenceManager.ACTIONWAY)) {
            btDistanceWay.setText(R.string.distance);
            btContactLists.setText(R.string.contacts_restriction + "   " +" (SOS)");
        } else {
            btDistanceWay.setText(R.string.distance + "   " +" (SOS)");
            btContactLists.setText(R.string.contacts_restriction);
        }

        btExtraNumbers = (Button)rootView.findViewById(R.id.btExtraNumbers);
        btExtraNumbers.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btDistanceWay:
                GoToDistanceWay();
                break;
            case R.id.btContactLists:
                GoToContactList();
                break;
            case R.id.btExtraNumbers:
                GoToExtraNumber();
                break;
        }
    }

    private void GoToDistanceWay() {
        Intent i = new Intent(getContext(), DistanceActivity.class);
        startActivityForResult(i, 1);

    }

    private void GoToContactList() {
        Intent i = new Intent(getContext(), ContactsRestrictionActivity.class);
        startActivityForResult(i, 1);

    }

    private void GoToExtraNumber() {
        Intent i = new Intent(getContext(), ExtraNumbersActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK){
                boolean result = data.getBooleanExtra("result", false);
                if (result) {
                    btDistanceWay.setText(R.string.distance);
                    btContactLists.setText(R.string.contacts_restriction + "   " +" (SOS)");
                } else {
                    btDistanceWay.setText(R.string.distance + "   " +" (SOS)");
                    btContactLists.setText(R.string.contacts_restriction);
                }
            }
        }
    }

    @Override
    public void setUserLocation(Location location) {

    }
}
