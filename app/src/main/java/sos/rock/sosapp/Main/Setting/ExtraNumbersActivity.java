package sos.rock.sosapp.Main.Setting;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import sos.rock.sosapp.ApiUtils.ApiCallWrapper;
import sos.rock.sosapp.ApiUtils.AsyncTaskCallback;
import sos.rock.sosapp.ApiUtils.ServerURLs;
import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.GlobalConstants;
import sos.rock.sosapp.Utils.MPreferenceManager;
import sos.rock.sosapp.Utils.StringCheckUtil;

public class ExtraNumbersActivity extends BaseTitleActivity {
    EditText txtFName, txtLName, etExtraPhone;
    Button btSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_numbers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();

        txtFName = (EditText)findViewById(R.id.txtFName);
        txtLName = (EditText)findViewById(R.id.txtLName);
        etExtraPhone = (EditText)findViewById(R.id.etExtraPhone);

        btSave = (Button)findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveExtraNumber();
            }
        });
    }

    private void onSaveExtraNumber() {

        BackToParent();

//        ContentValues values = new ContentValues();
//        values.put("TOKEN", getMainApp().getToken());
//        values.put("FIRST_EXTRA", etExtraPhone.getText().toString());
//
//        ApiCallWrapper service = new ApiCallWrapper(this, true, "Changing Email", ServerURLs.CHANGEEMAIL_URL, values, new AsyncTaskCallback() {
//            @Override
//            public void onResultService(Object result) {
//                try{
//                    JSONObject resultJson = new JSONObject(result.toString());
//                    if(resultJson.getBoolean(GlobalConstants.API_RESULT_FIELD)){
//                        Toast.makeText(ExtraNumbersActivity.this, "We've sent you E-mail to you.Log in again, please", Toast.LENGTH_SHORT).show();
//                        MPreferenceManager.saveStringInformation(ExtraNumbersActivity.this, MPreferenceManager.TOKEN, "");
//                        MPreferenceManager.saveBoolInformation(ExtraNumbersActivity.this, MPreferenceManager.REMEMBER_ME, false);
//                        getMainApp().setToken("");
//                        getMainApp().setMe(null);
//                        BackToParent();
//                    }
//                    else{
//                        Toast.makeText(ExtraNumbersActivity.this, "Error occured on server. Try again.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch (Exception e){
//                    Toast.makeText(ExtraNumbersActivity.this, "Error occured on server. Try again.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        service.execute();
    }
    @Override
    protected void BackToParent() {
        super.BackToParent();
        finish();
    }


}
