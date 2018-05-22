package sos.rock.sosapp.Main.Setting;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONObject;

import sos.rock.sosapp.ApiUtils.ApiCallWrapper;
import sos.rock.sosapp.ApiUtils.AsyncTaskCallback;
import sos.rock.sosapp.ApiUtils.ServerURLs;
import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.GlobalConstants;
import sos.rock.sosapp.Utils.MPreferenceManager;

public class PrivacyTermsActivity extends BaseTitleActivity {
    AppCompatCheckBox chkPrivacy;
    WebView webView;
    boolean initialValue;

    /***
     * For login view values
     */
    boolean fromLogin;
    String fName, lName, email, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_terms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();
        chkPrivacy = (AppCompatCheckBox)findViewById(R.id.chkAgree);
        webView = (WebView)findViewById(R.id.webView);
        fromLogin = getIntent().getBooleanExtra("FROM_LOGIN", false);
        if(fromLogin) {
            fName = getIntent().getStringExtra("FNAME");
            lName = getIntent().getStringExtra("LNAME");
            email = getIntent().getStringExtra("EMAIL");
            token = getIntent().getStringExtra("TOKEN");
        }
        InitViews();
    }

    private void InitCheckValue()
    {
        ContentValues values = new ContentValues();
        values.put(GlobalConstants.API_TOKEN, getMainApp().getToken());

        ApiCallWrapper service = new ApiCallWrapper(this, true, "Loading", ServerURLs.PRIVACY_STATUS, values, new AsyncTaskCallback() {
            @Override
            public void onResultService(Object result) {
                try
                {
                    JSONObject jsonResult = new JSONObject(result.toString());
                    if(jsonResult.getBoolean(GlobalConstants.API_RESULT_FIELD))
                    {
                        initialValue = jsonResult.getInt(GlobalConstants.API_RESULT_CONTENT)==1;
                        chkPrivacy.setChecked(initialValue);
                        chkPrivacy.setEnabled(false);
                    }
                    else
                    {
                        MPreferenceManager.saveBoolInformation(PrivacyTermsActivity.this, MPreferenceManager.REMEMBER_ME, true);
                        MPreferenceManager.saveStringInformation(PrivacyTermsActivity.this, MPreferenceManager.TOKEN, "");
                        getMainApp().setMe(null);
                        getMainApp().setToken("");

                        finish();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(PrivacyTermsActivity.this, getString(R.string.error_on_server), Toast.LENGTH_SHORT).show();
                }
            }
        });
        service.execute();
    }

    private void InitViews()
    {
        webView.loadUrl(ServerURLs.PRIVACY_URL);
        webView.setWebViewClient(new WebViewClient());
        if(!fromLogin)
            InitCheckValue();
    }

    @Override
    protected void BackToParent() {
        super.BackToParent();
        if(fromLogin)
        {
            if(!chkPrivacy.isChecked())
            {
                new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage("Agree to privacy and terms to use BookMapp")
                        .create()
                        .show();
            }
            else
            {
                Intent returnIntent = getIntent();
                returnIntent.putExtra("FNAME", fName);
                returnIntent.putExtra("LNAME", lName);
                returnIntent.putExtra("EMAIL", email);
                returnIntent.putExtra("TOKEN", token);
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        }
        else {
            finish();
        }
    }
}
