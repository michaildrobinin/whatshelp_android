package sos.rock.sosapp.UsersFromMe;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.Main.HomeActivity;
import sos.rock.sosapp.Model.ContactList;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.MPreferenceManager;
import sos.rock.sosapp.ViewHolders.ContactListViewHolder;

public class UsersFromMeActivity extends BaseTitleActivity implements ContactListViewHolder.OnContactItemClickListener {

    RecyclerView recyclerList;
    ArrayList<ContactList> dataProvider;
    ContactListAdapter adapter;
    TextView tvFromMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_from_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();

        tvFromMe = (TextView) findViewById(R.id.tvFromMe);

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        SetupRecyclerView();
        InitListRecyclerView();

    }

    private void SetupRecyclerView() {
        dataProvider = new ArrayList<ContactList>();
        adapter = new ContactListAdapter(dataProvider, this, this);
        recyclerList.setAdapter(adapter);
    }

    private void InitListRecyclerView() {
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(true);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(false);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(false);
            model.setOtherApprove(true);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(false);
            model.setOtherApprove(false);
            dataProvider.add(model);
        }
        adapter.notifyDataSetChanged();

        if (MPreferenceManager.readIntInformation(this, MPreferenceManager.DISTANCE) > 0) {
            AlertUsersFromMe("" + MPreferenceManager.readIntInformation(this, MPreferenceManager.DISTANCE), dataProvider.size());
            tvFromMe.setText(dataProvider.size() + getString(R.string.substring_users_around) + MPreferenceManager.readIntInformation(this, MPreferenceManager.DISTANCE) + "km");
        }
        else {
            tvFromMe.setText(dataProvider.size() + getString(R.string.substring_users_around) + " 20km");
            AlertUsersFromMe("20", dataProvider.size());
        }
    }

    private void AlertUsersFromMe(String distance, int users){
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert)
                .setMessage(getString(R.string.substring1_fromme) + users + getString(R.string.substring2_fromme) +
                        distance + getString(R.string.substring3_fromme))
                .setPositiveButton(R.string.detail_view, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(getString(R.string.button_no), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(UsersFromMeActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void contactItem(int position, ContactList contactList) {
//        if (contactList.isOtherApprove() && contactList.isOwnerApprove()) VoiceCall(contactList.getPhone());
//        else if (!contactList.isOwnerApprove() && !contactList.isOtherApprove()) ContactRequest(contactList.getNickName(), contactList.getPhone());
//        else if (!contactList.isOwnerApprove() && contactList.isOtherApprove()) RequestApprove(contactList.getNickName(), contactList.getPhone());
    }

    private void RequestApprove(String nickName, String phone) {
        Alert("Approved", "You have approved Contact Request of " + nickName);

        dataProvider.clear();
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(true);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(false);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(true);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(false);
            model.setOtherApprove(false);
            dataProvider.add(model);
        }
        adapter.notifyDataSetChanged();
    }

    private void ContactRequest(String nickName, String phone) {
        Alert("Request", "You have sent Contact Request to " + nickName);
        dataProvider.clear();
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(true);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(false);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(true);
            dataProvider.add(model);
        }
        for (int i = 0; i < 3; i++) {
            ContactList model = new ContactList();
            model.setOwnerApprove(true);
            model.setOtherApprove(false);
            dataProvider.add(model);
        }
        adapter.notifyDataSetChanged();
    }

    private void VoiceCall(String phone) {
        try
        {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                startActivity(callIntent);
            }
        }
        catch (ActivityNotFoundException activityException)
        {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }
    }

    @Override
    protected void BackToParent() {
        super.BackToParent();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

}
