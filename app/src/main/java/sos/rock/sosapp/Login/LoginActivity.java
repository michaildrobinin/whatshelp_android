package sos.rock.sosapp.Login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sos.rock.sosapp.ApiUtils.*;
import sos.rock.sosapp.BaseActivity;
import sos.rock.sosapp.ForgotPassword.ForgotPassword;
import sos.rock.sosapp.Main.HomeActivity;
import sos.rock.sosapp.Main.Setting.PrivacyTermsActivity;
import sos.rock.sosapp.Model.LoginRequestInfo;
import sos.rock.sosapp.Model.User;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Register.SignupActivity;
import sos.rock.sosapp.UsersFromMe.UsersFromMeActivity;
import sos.rock.sosapp.Utils.GlobalConstants;
import sos.rock.sosapp.Utils.MPreferenceManager;
import sos.rock.sosapp.Utils.StringCheckUtil;
import sos.rock.sosapp.Utils.ShowProgressDialog;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static sos.rock.sosapp.Utils.ShowProgressDialog.hideProgressDialog;
import static sos.rock.sosapp.Utils.ShowProgressDialog.showProgressDialog;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    FrameLayout flMiddleAds;
    ImageView ivMiddleAds;
    ImageButton ibMiddleClose;
    String middleAdsURL;
    Typeface custom_font;
    FloatingActionButton fabLogin;
    Button btSignUp, btForgotPassword;
    EditText txtEmail, txtPassword;
    TextView tvHeader;
    AppCompatCheckBox chkRememberMe;
    SosAPIService apiService;

    public static final int REQUEST_PERMISSION = 101;
    public static final int PRIVACY_AGREE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        custom_font = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");

        tvHeader = (TextView) findViewById(R.id.tvHeader);
        fabLogin = (FloatingActionButton) findViewById(R.id.fabLogin);
        btSignUp = (Button) findViewById(R.id.btSignUp);
        btForgotPassword = (Button) findViewById(R.id.btForgotPassword);
        fabLogin.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
        btForgotPassword.setOnClickListener(this);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        flMiddleAds = (FrameLayout) findViewById(R.id.flMiddleAds);
        ivMiddleAds = (ImageView) findViewById(R.id.ivMiddleAds);
        ibMiddleClose = (ImageButton) findViewById(R.id.ibMiddleClose);
        ivMiddleAds.setOnClickListener(this);
        ibMiddleClose.setOnClickListener(this);
        middleAdsURL = "http://savingthefamilymoney.com/whole-foods-10-off-printable-coupon/";

        chkRememberMe = (AppCompatCheckBox) findViewById(R.id.chkRemember);
        boolean isRemember = MPreferenceManager.readBoolInformation(this, MPreferenceManager.REMEMBER_ME);
        chkRememberMe.setChecked(isRemember);

        CheckPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (txtPassword != null)
            txtPassword.setText("");
    }

    private void CheckPermission() {
        String[] perms = {Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkAllGrantedOrNot(perms)) {
                requestPermissions(perms, REQUEST_PERMISSION);
            } else {
                LoginWithToken();
            }
        } else {
            LoginWithToken();
        }
    }

    public void LoginWithToken() {
        if (chkRememberMe.isChecked()) {
            MPreferenceManager.saveBoolInformation(this, MPreferenceManager.REMEMBER_ME, true);
            MPreferenceManager.saveBoolInformation(this, MPreferenceManager.TOPADS_SHOW, true);
            MPreferenceManager.saveBoolInformation(this, MPreferenceManager.BOTTOMADS_SHOW, true);
            Intent i = new Intent(LoginActivity.this, UsersFromMeActivity.class);
            startActivity(i);
            finish();
        }
//
// String token = MPreferenceManager.readStringInformation(this, MPreferenceManager.TOKEN);
//        boolean isRemember =  MPreferenceManager.readBoolInformation(this, MPreferenceManager.REMEMBER_ME);
//        if(isRemember&&!token.isEmpty())
//        {
//            ContentValues values = new ContentValues();
//            values.put("TOKEN", token);
//            ApiCallWrapper loginService = new ApiCallWrapper(this, true, "Log in", ServerURLs.VERIFYTOKEN_URL, values, new AsyncTaskCallback() {
//                @Override
//                public void onResultService(Object result) {
//                    try{
//                        JSONObject resultService = new JSONObject(result.toString());
//                        if(resultService.getBoolean(GlobalConstants.API_RESULT_FIELD))
//                        {
//                            String token = resultService.getString(GlobalConstants.API_RESULT_CONTENT);
//                            MPreferenceManager.saveBoolInformation(LoginActivity.this, MPreferenceManager.REMEMBER_ME, chkRememberMe.isChecked());
//                            MPreferenceManager.saveStringInformation(LoginActivity.this, MPreferenceManager.TOKEN, token);
//                            User me = new User(resultService.getJSONObject("me"));
//
//                            getMainApp().setMe(me);
//                            getMainApp().setToken(token);
//
//                            Intent  i = new Intent(LoginActivity.this, HomeActivity.class);
//                            startActivity(i);
//                            finish();
//                        }
//                        else
//                        {
//                            String reason = resultService.getString(GlobalConstants.API_RESULT_CONTENT);
//                            if(reason.equals("NoUser"))
//                            {
//                                Toast.makeText(LoginActivity.this, getString(R.string.token_invalid), Toast.LENGTH_SHORT).show();
//                            }
//                            else if(reason.equals("Inactive"))
//                            {
//                                Toast.makeText(LoginActivity.this, "User is inactive. Verify email, please.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }catch (Exception e){
//                        Toast.makeText(LoginActivity.this, "Error. Try again.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            loginService.execute();
//
//        }
    }

    private boolean checkAllGrantedOrNot(String[] perms) {
        boolean ret_val = true;
        for (String permission : perms) {
            if (!hasPermission(this, permission)) {
                ret_val = false;
            }
        }
        return ret_val;
    }

    public boolean hasPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.needs_permission)
                            .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CheckPermission();
                                }
                            })
                            .create()
                            .show();
                    return;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fabLogin:
                Login();
                break;
            case R.id.btSignUp:
                Signup();
                break;
            case R.id.btForgotPassword:
                ForgotPassword();
                break;
            case R.id.ivMiddleAds:
                WebpageLoading(middleAdsURL);
                break;
            case R.id.ibMiddleClose:
                flMiddleAds.setVisibility(View.GONE);
                break;
        }
    }

    private void WebpageLoading(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void ForgotPassword() {
        Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
        i.putExtra("changeflag", false);
        startActivity(i);
        finish();
    }

    private void Signup() {
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
        finish();
    }

    private void Login(){
        Intent i = new Intent(LoginActivity.this, UsersFromMeActivity.class);
        startActivity(i);
        finish();
        txtEmail.setText(txtEmail.getText().toString().trim());
        if (StringCheckUtil.isEmpty(this, txtEmail)) {
            return;
        }

        txtPassword.setText(txtPassword.getText().toString().trim());
        if (StringCheckUtil.isEmpty(this, txtPassword)) {
            return;
        }

        if (chkRememberMe.isChecked()) {
            MPreferenceManager.saveBoolInformation(this, MPreferenceManager.REMEMBER_ME, true);
        } else {
            MPreferenceManager.saveBoolInformation(this, MPreferenceManager.REMEMBER_ME, false);
        }
        MPreferenceManager.saveBoolInformation(this, MPreferenceManager.TOPADS_SHOW, true);
        MPreferenceManager.saveBoolInformation(this, MPreferenceManager.BOTTOMADS_SHOW, true);

        final LoginRequestInfo logininfo = new LoginRequestInfo();
        logininfo.setEmail(txtEmail.getText().toString().trim());
        logininfo.setPassword(txtPassword.getText().toString().trim());
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerURLs.BASE_URL)
                .addConverterFactory(new APIManager<LoginResponse>().createGsonConverter(LoginResponse.class))
                .addCallAdapterFactory(rxAdapter)
                .build();

        apiService = retrofit.create(SosAPIService.class);

        try {
            URL url = new URL("http://192.168.1.82");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        showProgressDialog(this, "");
        Observable<LoginResponse> call = apiService.login(logininfo);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Response response = exception.response();
                            //       errorMsg = response.message();

                        }
                        hideProgressDialog();
//                        LogInActivity.this.showErrorMessage("Failed to Login" , errorMsg);
                    }

                    @Override
                    public void onNext(LoginResponse response) {
                        if (response.getStatusCode() == 200) {
                            hideProgressDialog();

                        } else {
                            hideProgressDialog();
                            return;
                        }
                    }
                });

//
//        ContentValues values = new ContentValues();
//        values.put("AUTH", txtEmail.getText().toString());
//        values.put("PASSWORD", txtPassword.getText().toString());
//        ApiCallWrapper loginService = new ApiCallWrapper(this, true, "Log in", ServerURLs.LOGIN_URL, values, new AsyncTaskCallback() {
//            @Override
//            public void onResultService(Object result) {
//                try{
//                    JSONObject resultService = new JSONObject(result.toString());
//                    if(resultService.getBoolean(GlobalConstants.API_RESULT_FIELD)){
//                        String token = resultService.getString(GlobalConstants.API_RESULT_CONTENT);
//                        MPreferenceManager.saveBoolInformation(LoginActivity.this, MPreferenceManager.REMEMBER_ME, chkRememberMe.isChecked());
//                        MPreferenceManager.saveStringInformation(LoginActivity.this, MPreferenceManager.TOKEN, token);
//                        User me = new User(resultService.getJSONObject("me"));
//                        getMainApp().setMe(me);
//                        getMainApp().setToken(token);


//                    }
//                    else
//                    {
//                        String reason = resultService.getString(GlobalConstants.API_RESULT_CONTENT);
//                        if(reason.equals("NoUser"))
//                        {
//                            Toast.makeText(LoginActivity.this, getString(R.string.token_invalid), Toast.LENGTH_SHORT).show();
//                        }
//                        else if(reason.equals("Inactive"))
//                        {
//                            Toast.makeText(LoginActivity.this, "User is inactive. Verify email, please.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }catch (Exception e) {
//                    Toast.makeText(LoginActivity.this, "Error. Try again.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        loginService.execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRIVACY_AGREE) {
            if (resultCode == Activity.RESULT_OK) {
                String fName = data.getStringExtra("FNAME");
                String lName = data.getStringExtra("LNAME");
                String email = data.getStringExtra("EMAIL");
                String tokenValue = data.getStringExtra("TOKEN");
            }
        }
    }
}
