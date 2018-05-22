package sos.rock.sosapp.Main.Contacts;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import sos.rock.sosapp.Main.BaseFragment;
import sos.rock.sosapp.Model.ContactList;
import sos.rock.sosapp.R;
import sos.rock.sosapp.UsersFromMe.ContactListAdapter;
import sos.rock.sosapp.ViewHolders.ContactListMyViewHolder;
import sos.rock.sosapp.ViewHolders.ContactListViewHolder;


public class ContactsFragment extends BaseFragment implements ContactListMyViewHolder.OnContactItemClickListener{

    EditText etSearch;
    ImageButton ibSearch;
    FloatingActionButton fabAdd, fabRefresh;
    RecyclerView recyclerList;
    ArrayList<ContactList> dataProvider;
    ContactListMyAdapter adapter;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        fabAdd = (FloatingActionButton)rootView.findViewById(R.id.fabAdd);
        fabRefresh = (FloatingActionButton) rootView.findViewById(R.id.fabRefresh);
        etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        ibSearch = (ImageButton) rootView.findViewById(R.id.ibSearch);

        recyclerList = (RecyclerView) rootView.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        SetupRecyclerView();
        InitListRecyclerView();

        recyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    fabAdd.show();
                    fabRefresh.show();
                }
                else if (dy > 0) {
                    fabAdd.hide();
                    fabRefresh.hide();
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ContactAddActivity.class);
                startActivity(i);
            }
        });

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataProvider.clear();
                InitListRecyclerView();
            }
        });

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResult();
            }
        });
        return rootView;
    }

    private void SetupRecyclerView() {
        dataProvider = new ArrayList<ContactList>();
        adapter = new ContactListMyAdapter(dataProvider, getContext(), this);
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void contactItem(int position, ContactList contactList) {
//        if (contactList.isOtherApprove() && contactList.isOwnerApprove()) VoiceCall(contactList.getPhone());
//        else if (!contactList.isOwnerApprove() && contactList.isOtherApprove()) RequestApprove(contactList.getNickName(), contactList.getPhone());
    }

    @Override
    public void removeItem(int position) {
        successAlert(getString(R.string.confirm), getString(R.string.remove_contact), position);
    }

    private void RemoveContactItem(int position) {
        dataProvider.clear();
        for (int i = 0; i < 2; i++) {
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
        adapter.notifyDataSetChanged();
    }

    private void VoiceCall(String phone) {
        try
        {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                startActivity(callIntent);
            }
        }
        catch (ActivityNotFoundException activityException)
        {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }
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
        adapter.notifyDataSetChanged();
    }

    private void SearchResult() {

        dataProvider.clear();
        ContactList model = new ContactList();
        model.setOwnerApprove(true);
        model.setOtherApprove(true);
        dataProvider.add(model);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUserLocation(Location location) {

    }

    private void successAlert(String title, String content, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        alertDialogBuilder
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(getString(R.string.button_ok),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        RemoveContactItem(position);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}