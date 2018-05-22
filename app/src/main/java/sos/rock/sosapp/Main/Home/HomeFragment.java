package sos.rock.sosapp.Main.Home;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import sos.rock.sosapp.Main.BaseFragment;
import sos.rock.sosapp.Model.ContactListHome;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.MPreferenceManager;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    Button btSos;
    FrameLayout flTopAds, flBottomAds;
    ImageView ivTopAds, ivBottomAds;
    ImageButton ibTopClose, ibBottomClose, btSpeak;
    String topAdsURL, bottomAdsURL;

    RecyclerView recyclerList;
    ArrayList<ContactListHome> dataProvider;
    HomeContactListAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        flTopAds = (FrameLayout) rootView.findViewById(R.id.flTopAds);
        ivTopAds = (ImageView) rootView.findViewById(R.id.ivTopAds);
        ibTopClose = (ImageButton) rootView.findViewById(R.id.ibTopClose);
        flTopAds.setVisibility(MPreferenceManager.readBoolInformation(getContext(), MPreferenceManager.TOPADS_SHOW)? View.VISIBLE: View.INVISIBLE);
        ivTopAds.setOnClickListener(this);
        ibTopClose.setOnClickListener(this);

        flBottomAds = (FrameLayout) rootView.findViewById(R.id.flBottomAds);
        ivBottomAds = (ImageView) rootView.findViewById(R.id.ivBottomAds);
        ibBottomClose = (ImageButton) rootView.findViewById(R.id.ibBottomClose);
        flBottomAds.setVisibility(MPreferenceManager.readBoolInformation(getContext(), MPreferenceManager.BOTTOMADS_SHOW)? View.VISIBLE: View.INVISIBLE);
        ivBottomAds.setOnClickListener(this);
        ibBottomClose.setOnClickListener(this);

        btSos = (Button) rootView.findViewById(R.id.btSos);
        btSos.setOnClickListener(this);

        btSpeak = (ImageButton) rootView.findViewById(R.id.btSpeak);
        btSpeak.setOnClickListener(this);

        recyclerList = (RecyclerView) rootView.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        SetupRecyclerView();
        InitListRecyclerView();

        return rootView;
    }

    private void SetupRecyclerView() {
        topAdsURL = "https://thebestteamadv4930prj2.wordpress.com/sample-ads/";
        bottomAdsURL = "https://marketplace.southernminn.com/places/view/13524/olivia_s_family_restaurant.html";

        dataProvider = new ArrayList<ContactListHome>();
        adapter = new HomeContactListAdapter(dataProvider);
        recyclerList.setAdapter(adapter);
    }

    private void InitListRecyclerView() {
        for (int i = 0; i < 5; i++) {
            ContactListHome model = new ContactListHome();
            model.setId(i);
            model.setUserName("UserName" + i);
            model.setPhone("222-2222-2222" + i);
            model.setCallResult(0);
            dataProvider.add(model);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTopAds:
                WebpageLoading(topAdsURL);
                break;
            case R.id.ivBottomAds:
                WebpageLoading(bottomAdsURL);
                break;
            case R.id.ibTopClose:
                MPreferenceManager.saveBoolInformation(getContext(), MPreferenceManager.TOPADS_SHOW, false);
                flTopAds.setVisibility(View.GONE);
                break;
            case  R.id.ibBottomClose:
                MPreferenceManager.saveBoolInformation(getContext(), MPreferenceManager.BOTTOMADS_SHOW, false);
                flBottomAds.setVisibility(View.GONE);
                break;
            case R.id.btSos:
                SosCall();
                break;
            case R.id.btSpeak:
                startVoiceInput();
                break;
        }
    }

    private void WebpageLoading(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void SosCall() {
        MPreferenceManager.saveBoolInformation(getContext(), MPreferenceManager.IS_SOS, true);
        for (int i = 0; i < dataProvider.size(); i++) {
            try
            {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + dataProvider.get(i).getPhone()));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {
                    startActivity(callIntent);
                }
                dataProvider.get(i).setCallResult(1);
                adapter.notifyItemChanged(i);
            }
            catch (ActivityNotFoundException activityException)
            {
                Log.e("Calling a Phone Number", "Call failed", activityException);
            }
        }
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0).equals("help")) SosCall();
                }
                break;
            }
        }
    }

    @Override
    public void setUserLocation(Location location) {

    }
}
