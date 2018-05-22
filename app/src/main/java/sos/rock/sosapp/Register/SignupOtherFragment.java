package sos.rock.sosapp.Register;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.StringCheckUtil;


public class SignupOtherFragment extends BaseSignupFragment implements View.OnClickListener {

    EditText txtSport, txtPassword, txtConfirmPassword;
    String sport, password, confirmPassword;
    TextView txtAgreeContent;
    SpinerAdapter adapterJob, adapterStudy;
    Spinner spJob, spStudy;
    String[] job, study;
    ArrayList<String> dataProviderJob = new ArrayList<>();
    ArrayList<String> dataProviderStudy = new ArrayList<>();
    AppCompatCheckBox chkAgree;
    Button btRegister, btBack;
    private static String TERMS_LINK = "https://www.freeprivacypolicy.com/free-privacy-policy.html";
    Uri termsLink;

    public SignupOtherFragment() {
        // Required empty public constructor
    }

    public static SignupOtherFragment newInstance() {
        SignupOtherFragment fragment = new SignupOtherFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_signup_other, container, false);
        txtAgreeContent = (TextView) rootView.findViewById(R.id.txtAgreeContent);
        txtAgreeContent.setClickable(true);
        String policy = getString(R.string.html_agree_terms);
        txtAgreeContent.setText(Html.fromHtml(policy));
        txtAgreeContent.setOnClickListener(this);
        termsLink = Uri.parse(TERMS_LINK);

        txtSport = (EditText) rootView.findViewById(R.id.txtSport);
        txtPassword = (EditText) rootView.findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) rootView.findViewById(R.id.txtConfirmPassword);

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

        chkAgree = (AppCompatCheckBox) rootView.findViewById(R.id.chkAgree);

        ContentValues value = parent.SendDataToOther();
        spJob.setSelection(value.getAsInteger("JOB"));
        spStudy.setSelection(value.getAsInteger("STUDY"));
        txtSport.setText(value.getAsString("SPORT"));
        txtPassword.setText(value.getAsString("PASSWORD"));
        txtConfirmPassword.setText(value.getAsString("CONFIRM_PASSWORD"));
        chkAgree.setChecked(value.getAsBoolean("TERMS_AGREE"));

        btBack = (Button) rootView.findViewById(R.id.btBack);
        btBack.setOnClickListener(this);
        btRegister = (Button) rootView.findViewById(R.id.btRegister);
        btRegister.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBack:
                BackStep();
                break;
            case R.id.btRegister:
                SignUp();
                break;
            case R.id.txtAgreeContent:
                GotoTerms(termsLink);
                break;
        }
    }

    private void GotoTerms(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void BackStep() {
        sport = txtSport.getText().toString();
        password = txtPassword.getText().toString();
        confirmPassword = txtConfirmPassword.getText().toString();

        ContentValues values = new ContentValues();
        values.put("JOB", spJob.getSelectedItemPosition());
        values.put("STUDY", spStudy.getSelectedItemPosition());
        values.put("SPORT", sport);
        values.put("PASSWORD", password);
        values.put("CONFIRM_PASSWORD", confirmPassword);
        values.put("TERMS_AGREE", chkAgree.isChecked());
        values.put("IS_SIGNUP", false);
        parent.ReceiveDataFromOther(values);
        parent.addFragment(SignupActivity.FRAGMENTS.SIGNUPUSERCONTACT);
    }

    private void SignUp() {

        password = txtPassword.getText().toString();
        txtPassword.setText(password.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtPassword)) {
            return;
        }
        if(!StringCheckUtil.isLength(txtPassword, 6))
            return;

        confirmPassword = txtConfirmPassword.getText().toString();
        txtConfirmPassword.setText(confirmPassword.trim());
        if(StringCheckUtil.isEmpty(getContext(), txtConfirmPassword)) {
            return;
        }
        if(!StringCheckUtil.isEqual(txtPassword, txtConfirmPassword)){
            txtConfirmPassword.setError(getString(R.string.password_doesnot_match));
            return;
        }

        if (spJob.getSelectedItem().toString().equals(getString(R.string.select_option)) ||
                spStudy.getSelectedItem().toString().equals(getString(R.string.select_option)) ) {
            errorAlert(getString(R.string.Error_Title), getString(R.string.empty_error_spiner));
            return;
        }
        if(!chkAgree.isChecked()) {
            Toast.makeText(getContext(), R.string.agree_check_alert, Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("JOB", spJob.getSelectedItemPosition());
        values.put("STUDY", spStudy.getSelectedItemPosition());
        values.put("SPORT", sport);
        values.put("PASSWORD", password);
        values.put("CONFIRM_PASSWORD", confirmPassword);
        values.put("TERMS_AGREE", chkAgree.isChecked());
        values.put("IS_SIGNUP", true);
        parent.ReceiveDataFromOther(values);
    }
}
