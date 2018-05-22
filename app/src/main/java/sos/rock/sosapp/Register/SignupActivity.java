package sos.rock.sosapp.Register;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import sos.rock.sosapp.ApiUtils.ApiCallWrapper;
import sos.rock.sosapp.ApiUtils.AsyncTaskCallback;
import sos.rock.sosapp.ApiUtils.ServerURLs;
import sos.rock.sosapp.Login.LoginActivity;
import sos.rock.sosapp.Main.BaseFragment;
import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.GlobalConstants;
import sos.rock.sosapp.Utils.StringCheckUtil;

public class SignupActivity extends BaseTitleActivity {

    public enum FRAGMENTS{
        SIGNUPUSERINFO, SIGNUPUSERCONTACT, SIGNUPOTHER
    }

    TextView txtAgree;
    String FName, LName, UserID, Age, Email, City, Phone, SportName, Password, ConfirmPassword;
    int SelectedJob, SelectedStudy;
    boolean isMarried, CheckedPrivacy, isSignup;
    private String TAG = getClass().getSimpleName();

    public void setCurrentFragment(FRAGMENTS currentFragment) {
        this.currentFragment = currentFragment;
    }

    private FRAGMENTS currentFragment;

    public void addFragment(FRAGMENTS otherfragment) {
        if(currentFragment != otherfragment){
            setCurrentFragment(otherfragment);
            BaseSignupFragment f = null;
            switch (otherfragment){
                case SIGNUPUSERINFO:
                    f = SignupUserinfoFragment.newInstance();

                    break;
                case SIGNUPUSERCONTACT:
                    f = SignupUsercontactFragment.newInstance();
                    break;
                case SIGNUPOTHER:
                    f = SignupOtherFragment.newInstance();
                    break;
            }
            attachFragment(f);
        }
    }

    private void attachFragment(BaseSignupFragment f) {
        if (f != null) {
            getSupportFragmentManager().popBackStackImmediate();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContent, f).addToBackStack(null)
                    .commit();
        }
    }

    public void ReceiveDataFromUserinfo (ContentValues values) {
        FName = values.getAsString("FNAME");
        LName = values.getAsString("LNAME");
        UserID = values.getAsString("USERID");
        Age = values.getAsString("AGE");
        isMarried = values.getAsBoolean("ISMARRIED");
    }
    public ContentValues SendDataToUserInfo() {
        ContentValues values = new ContentValues();
        values.put("FNAME", FName);
        values.put("LNAME", LName);
        values.put("USERID", UserID);
        values.put("AGE", Age);
        values.put("ISMARRIED", isMarried);
        return values;
    }

    public void ReceiveDataFromUserContact (ContentValues values) {
        Email = values.getAsString("EMAIL");
        City = values.getAsString("CITY");
        Phone = values.getAsString("PHONE");
    }
    public ContentValues SendDataToUserContact() {
        ContentValues values = new ContentValues();
        values.put("EMAIL", Email);
        values.put("CITY", City);
        values.put("PHONE", Phone);
        return values;
    }

    public void ReceiveDataFromOther (ContentValues values) {
        SelectedJob = values.getAsInteger("JOB");
        SelectedStudy = values.getAsInteger("STUDY");
        SportName = values.getAsString("SPORT");
        Password = values.getAsString("PASSWORD");
        ConfirmPassword = values.getAsString("CONFIRM_PASSWORD");
        CheckedPrivacy = values.getAsBoolean("TERMS_AGREE");
        isSignup = values.getAsBoolean("IS_SIGNUP");

        if (isSignup) sendRegisterService();
    }
    public ContentValues SendDataToOther() {
        ContentValues values = new ContentValues();
        values.put("JOB", SelectedJob);
        values.put("STUDY", SelectedStudy);
        values.put("SPORT", SportName);
        values.put("PASSWORD", Password);
        values.put("CONFIRM_PASSWORD", ConfirmPassword);
        values.put("TERMS_AGREE", CheckedPrivacy);
        return values;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();

        addFragment(FRAGMENTS.SIGNUPUSERINFO);
    }

    private void sendRegisterService() {
        ContentValues values = new ContentValues();
        values.put("FIRST_NAME", FName);
        values.put("LAST_NAME", LName);
        values.put("NICK_NAME", UserID);
        values.put("AGE", Age);
        values.put("MARRIED", isMarried);
        values.put("EMAIL", Email);
        values.put("CITY", City);
        values.put("PHONE", Phone);
        values.put("USER_OS", 0);
        values.put("JOB", SelectedJob - 1);
        values.put("TITLE_STUDY", SelectedStudy - 1);
        values.put("SPORT", SportName);
        values.put("PASSWORD", Password);
        values.put("TERMS_ALLOW", CheckedPrivacy);

        ApiCallWrapper service = new ApiCallWrapper(this,true, getString(R.string.progress_title), ServerURLs.REGISTER_URL, values, new AsyncTaskCallback() {
            @Override
            public void onResultService(Object result) {
                try
                {
                    JSONObject returnValue = new JSONObject(result.toString());
                    if(returnValue.getInt(GlobalConstants.API_RESULT_FIELD) == 0)
                    {
                        //duplicate email
                        Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    }
                    else if(returnValue.getInt(GlobalConstants.API_RESULT_FIELD) == 1)
                    {
                        //db error
                        Toast.makeText(SignupActivity.this, getString(R.string.error_on_server), Toast.LENGTH_SHORT).show();
                    }
                    else if(returnValue.getInt(GlobalConstants.API_RESULT_FIELD) == 2)
                    {
                        // success
                        Toast.makeText(SignupActivity.this, getString(R.string.email_sent_alert), Toast.LENGTH_SHORT).show();
//                        BackToParent();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(SignupActivity.this, getString(R.string.error_on_server), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        service.execute();

        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void BackToParent(){
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}
