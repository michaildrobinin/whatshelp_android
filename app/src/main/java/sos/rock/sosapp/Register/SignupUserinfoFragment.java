package sos.rock.sosapp.Register;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.StringCheckUtil;


public class SignupUserinfoFragment extends BaseSignupFragment {

    EditText txtFName, txtLName, txtUserId, txtUserAge;
    String fName, lName, userID, userAge;
    AppCompatCheckBox chkMarried;
    Button btContinue;

    public SignupUserinfoFragment() {
        // Required empty public constructor
    }

    public static SignupUserinfoFragment newInstance() {
        SignupUserinfoFragment fragment = new SignupUserinfoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_signup_userinfo, container, false);
        txtFName = (EditText) rootView.findViewById(R.id.txtFName);
        txtLName = (EditText) rootView.findViewById(R.id.txtLName);
        txtUserId = (EditText) rootView.findViewById(R.id.txtUserId);
        txtUserAge = (EditText) rootView.findViewById(R.id.txtUserAge);
        chkMarried = (AppCompatCheckBox) rootView.findViewById(R.id.chkMarried);

        ContentValues value = parent.SendDataToUserInfo();
        txtFName.setText(value.getAsString("FNAME"));
        txtLName.setText(value.getAsString("LNAME"));
        txtUserId.setText(value.getAsString("USERID"));
        txtUserAge.setText(value.getAsString("AGE"));
        chkMarried.setChecked(value.getAsBoolean("ISMARRIED"));

        btContinue = (Button) rootView.findViewById(R.id.btContinue);
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextStep();
            }
        });

        return rootView;
    }

    private void NextStep() {
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

        ContentValues values = new ContentValues();
        values.put("FNAME", fName);
        values.put("LNAME", lName);
        values.put("USERID", userID);
        values.put("AGE", userAge);
        values.put("ISMARRIED", chkMarried.isChecked());
        parent.ReceiveDataFromUserinfo(values);
        parent.addFragment(SignupActivity.FRAGMENTS.SIGNUPUSERCONTACT);
    }
}
