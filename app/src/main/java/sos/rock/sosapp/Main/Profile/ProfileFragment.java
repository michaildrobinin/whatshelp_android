package sos.rock.sosapp.Main.Profile;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sos.rock.sosapp.Adapters.SpinerAdapter;
import sos.rock.sosapp.ForgotPassword.ForgotPassword;
import sos.rock.sosapp.Main.BaseFragment;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.StringCheckUtil;


public class ProfileFragment extends BaseFragment implements View.OnClickListener{

    AppCompatCheckBox chkMarried;
    EditText txtFName, txtLName, txtUserId, txtUserAge, txtEmail, txtCity, txtSport;
    TextView txtPhone;
    String fName, lName, userID, userAge, email, city, myPhone, sport;
    int selectedJob, selectedStudy;
    boolean isMarried;
    Button btGetPhone, btChangePassword, btChangeProfile;
    SpinerAdapter adapterJob, adapterStudy;
    Spinner spJob, spStudy;
    String[] job, study;
    ArrayList<String> dataProviderJob = new ArrayList<>();
    ArrayList<String> dataProviderStudy = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        txtFName = (EditText) rootView.findViewById(R.id.txtFName);
        txtLName = (EditText) rootView.findViewById(R.id.txtLName);
        txtUserId = (EditText) rootView.findViewById(R.id.txtUserId);
        txtUserAge = (EditText) rootView.findViewById(R.id.txtUserAge);
        chkMarried = (AppCompatCheckBox) rootView.findViewById(R.id.chkMarried);
        txtEmail = (EditText) rootView.findViewById(R.id.txtEmail);
        txtCity = (EditText) rootView.findViewById(R.id.txtCity);
        txtPhone = (TextView) rootView.findViewById(R.id.txtPhone);
        txtSport = (EditText) rootView.findViewById(R.id.txtSport);

        job = getResources().getStringArray(R.array.job);
        study = getResources().getStringArray(R.array.study);
        spJob = (Spinner) rootView.findViewById(R.id.spJob);
        spStudy = (Spinner) rootView.findViewById(R.id.spStudy);
        for (int i = 0; i < job.length; i++) {
            dataProviderJob.add(job[i]);
        }
        for (int i = 0; i < study.length; i++) {
            dataProviderStudy.add(study[i]);
        }
        adapterJob = new SpinerAdapter(dataProviderJob);
        adapterStudy = new SpinerAdapter(dataProviderStudy);
        spJob.setAdapter(adapterJob);
        spStudy.setAdapter(adapterStudy);

        btGetPhone = (Button) rootView.findViewById(R.id.btGetPhone);
        btGetPhone.setOnClickListener(this);
        btChangePassword = (Button) rootView.findViewById(R.id.btChangePassword);
        btChangePassword.setOnClickListener(this);
        btChangeProfile = (Button) rootView.findViewById(R.id.btChangeProfile);
        btChangeProfile.setOnClickListener(this);

        InitDisplay();

        return rootView;
    }

    private void InitDisplay() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btGetPhone:
                TelephonyManager tMgr =(TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                myPhone = tMgr.getLine1Number();
                txtPhone.setText(myPhone);
                break;
            case R.id.btChangePassword:
                GotoResetPassword();
                break;
            case R.id.btChangeProfile:
                SaveProfile();
                break;
        }
    }

    private void SaveProfile() {
        fName = txtFName.getText().toString();
        txtFName.setText(fName.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtFName)) {
            return;
        }

        lName = txtLName.getText().toString();
        txtLName.setText(lName.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtLName)) {
            return;
        }

        userID = txtUserId.getText().toString();
        txtUserId.setText(userID.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtUserId)) {
            return;
        }

        userAge = txtUserAge.getText().toString();
        txtUserAge.setText(userAge.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtUserAge)) {
            return;
        }

        email = txtEmail.getText().toString();
        txtEmail.setText(email.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtEmail)) {
            return;
        }
        if(!StringCheckUtil.validEmail(txtEmail))
            return;

        city = txtCity.getText().toString();
        txtCity.setText(city.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtCity)) {
            return;
        }

        myPhone = txtPhone.getText().toString();
        if (myPhone.isEmpty()) {
            Toast.makeText(getContext(), R.string.empty_error_phone, Toast.LENGTH_SHORT).show();
            return;
        }

        if (spJob.getSelectedItem().toString().equals(getString(R.string.select_option)) || spStudy.getSelectedItem().toString().equals(getString(R.string.select_option)) ) {
            Alert(getString(R.string.Error_Title), getString(R.string.empty_error_spiner));
            return;
        }

        ContentValues values = new ContentValues();
        values.put("FIRST_NAME", fName);
        values.put("LAST_NAME", lName);
        values.put("NICK_NAME", userID);
        values.put("AGE", userAge);
        values.put("MARRIED", isMarried);
        values.put("EMAIL", email);
        values.put("CITY", city);
        values.put("PHONE", myPhone);
        values.put("USER_OS", 0);
        values.put("JOB", selectedJob - 1);
        values.put("TITLE_STUDY", selectedStudy - 1);
        values.put("SPORT", sport);
    }

    private void GotoResetPassword() {
        Intent i = new Intent(getContext(), ForgotPassword.class);
        i.putExtra(ForgotPassword.CHANGE_PASSWORD_FLAG, true);
        startActivity(i);
    }

    @Override
    public void setUserLocation(Location location) {

    }

}
