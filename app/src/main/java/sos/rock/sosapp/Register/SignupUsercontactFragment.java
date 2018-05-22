package sos.rock.sosapp.Register;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.StringCheckUtil;


public class SignupUsercontactFragment extends BaseSignupFragment implements View.OnClickListener {

    EditText txtEmail, txtCity;
    TextView txtPhone;
    Button btGetPhone, btBack, btContinue;
    String myPhone, email, city;

    public SignupUsercontactFragment() {
        // Required empty public constructor
    }

    public static SignupUsercontactFragment newInstance() {
        SignupUsercontactFragment fragment = new SignupUsercontactFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_signup_usercontact, container, false);
        txtEmail = (EditText) rootView.findViewById(R.id.txtEmail);
        txtCity = (EditText) rootView.findViewById(R.id.txtCity);
        txtPhone = (TextView) rootView.findViewById(R.id.txtPhone);

        ContentValues value = parent.SendDataToUserContact();
        txtEmail.setText(value.getAsString("EMAIL"));
        txtCity.setText(value.getAsString("CITY"));
        txtPhone.setText(value.getAsString("PHONE"));

        btGetPhone = (Button) rootView.findViewById(R.id.btGetPhone);
        btGetPhone.setOnClickListener(this);
        btBack = (Button) rootView.findViewById(R.id.btBack);
        btBack.setOnClickListener(this);
        btContinue = (Button) rootView.findViewById(R.id.btContinue);
        btContinue.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btGetPhone:
                TelephonyManager tMgr =(TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                myPhone = tMgr.getLine1Number();
                txtPhone.setText(myPhone);

                break;
            case R.id.btBack:
                BackStep();
                break;
            case R.id.btContinue:
                NextStep();
                break;
        }
    }

    private void BackStep() {
        email = txtEmail.getText().toString();
        city = txtCity.getText().toString();
        myPhone = txtPhone.getText().toString();

        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("CITY", city);
        values.put("PHONE", myPhone);
        parent.ReceiveDataFromUserContact(values);
        parent.addFragment(SignupActivity.FRAGMENTS.SIGNUPUSERINFO);
    }

    private void NextStep() {
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
            Toast.makeText(getContext(), getString(R.string.empty_error_phone), Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("CITY", city);
        values.put("PHONE", myPhone);
        parent.ReceiveDataFromUserContact(values);
        parent.addFragment(SignupActivity.FRAGMENTS.SIGNUPOTHER);
    }


}
