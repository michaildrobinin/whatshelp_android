package sos.rock.sosapp.ForgotPassword;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import sos.rock.sosapp.ApiUtils.ApiCallWrapper;
import sos.rock.sosapp.ApiUtils.AsyncTaskCallback;
import sos.rock.sosapp.ApiUtils.ServerURLs;
import sos.rock.sosapp.Login.LoginActivity;
import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.GlobalConstants;
import sos.rock.sosapp.Utils.MPreferenceManager;
import sos.rock.sosapp.Utils.StringCheckUtil;

public class ForgotPassword extends BaseTitleActivity {

    public final static String CHANGE_PASSWORD_FLAG = "ChangeFlag";
    private boolean FLAG;
    EditText txtEmail, txtNewPassword, txtConfirmNewPassword;
    String email;
    TextInputLayout txtLayoutEmail;
    Button btForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FLAG = getIntent().getBooleanExtra(CHANGE_PASSWORD_FLAG, false);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();

        txtLayoutEmail = (TextInputLayout) findViewById(R.id.txtLayoutEmail);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtConfirmNewPassword = (EditText)findViewById(R.id.txtConfirmNewPassword);
        txtNewPassword = (EditText)findViewById(R.id.txtPassword);
        btForgotPassword = (Button)findViewById(R.id.btForgotPassword);


        btForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickResetPassword();
            }
        });
        if(FLAG){
            txtLayoutEmail.setVisibility(View.GONE);
//            email = getMainApp().getMe().getEmail();
        }
    }

    private void onClickResetPassword() {
        if (!FLAG) {
            if(StringCheckUtil.isEmpty(this, txtEmail))
                return;
        }

        if(StringCheckUtil.isEmpty(this, txtNewPassword))
            return;

        if(StringCheckUtil.isEmpty(this, txtConfirmNewPassword))
            return;

        if(!StringCheckUtil.isLength(txtNewPassword, 6))
            return;

        if(!StringCheckUtil.isEqual(txtNewPassword, txtConfirmNewPassword)){
            txtConfirmNewPassword.setError(getString(R.string.password_doesnot_match));
            return;
        }

        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("PASSWORD", txtNewPassword.getText().toString());
        ApiCallWrapper servicePwdChange = new ApiCallWrapper(this, true, getString(R.string.progress_title), ServerURLs.CHANGEPASSWORD_URL, values, new AsyncTaskCallback() {
            @Override
            public void onResultService(Object result) {
                try
                {
                    JSONObject resultJson = new JSONObject(result.toString());
                    if(resultJson.getBoolean(GlobalConstants.API_RESULT_FIELD))
                    {
                        Toast.makeText(ForgotPassword.this, getString(R.string.email_sent_alert), Toast.LENGTH_SHORT).show();
                        MPreferenceManager.saveStringInformation(ForgotPassword.this, MPreferenceManager.TOKEN, "");
                        MPreferenceManager.saveBoolInformation(ForgotPassword.this, MPreferenceManager.REMEMBER_ME, false);
                        getMainApp().setToken("");
                        getMainApp().setMe(null);
                        BackToParent();
                    }
                    else
                    {
                        Toast.makeText(ForgotPassword.this, getString(R.string.error_on_server), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(ForgotPassword.this, getString(R.string.error_on_server), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        servicePwdChange.execute();
    }

    @Override
    protected void BackToParent() {
        super.BackToParent();
        if(!FLAG) {
            Intent i = new Intent(ForgotPassword.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        else{
            finish();
        }
    }
}
