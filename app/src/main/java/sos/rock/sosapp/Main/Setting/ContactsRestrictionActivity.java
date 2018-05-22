package sos.rock.sosapp.Main.Setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sos.rock.sosapp.Adapters.ContactsRestrictionAdapter;
import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.Model.ContactsRestriction;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.MPreferenceManager;

public class ContactsRestrictionActivity extends BaseTitleActivity implements View.OnClickListener, ContactsRestrictionAdapter.ContactListItemCheckedChangeListener{

    TextView tvRestriction;
    int restriction = 0;
    Button btRestriction, btSave;
    ImageButton ibUp, ibDown;
    RecyclerView recyclerList;
    ArrayList<ContactsRestriction> dataProvider;
    ContactsRestrictionAdapter adapter;
    int totallyCheckedCount = 0;
    private static int UNIT_RESTRICTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_restriction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        SetupRecyclerView();
        InitListRecyclerView();

        tvRestriction = (TextView) findViewById(R.id.tvRestriction);
        restriction = MPreferenceManager.readIntInformation(this, MPreferenceManager.RESTRICTION);
        tvRestriction.setText(String.valueOf(restriction));

        ibUp = (ImageButton) findViewById(R.id.ibUp);
        ibUp.setOnClickListener(this);

        ibDown = (ImageButton) findViewById(R.id.ibDown);
        ibDown.setOnClickListener(this);

        btRestriction = (Button) findViewById(R.id.btRestriction);
        btRestriction.setOnClickListener(this);

        btSave = (Button) findViewById(R.id.btSave);
        btSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibUp:
                IncreaseDistance();
                break;
            case R.id.ibDown:
                DecreaseDistance();
                break;
            case R.id.btRestriction:
                SaveRestriction();
                break;
            case R.id.btSave:
                SaveContacts();
                break;
        }
    }

    private void SetupRecyclerView() {
        dataProvider = new ArrayList<ContactsRestriction>();
        adapter = new ContactsRestrictionAdapter(dataProvider, this);
        recyclerList.setAdapter(adapter);
    }

    private void InitListRecyclerView() {
        for (int i = 0; i < 30; i++) {
            ContactsRestriction model = new ContactsRestriction();
            model.setUserName("Michel Jodan" + i);
            model.setUserPhone("111-1111" + i);
            model.setChecked(false);
            dataProvider.add(model);
        }
        adapter.notifyDataSetChanged();
    }

    private void IncreaseDistance() {
        if (restriction < dataProvider.size()) {
            restriction = restriction + UNIT_RESTRICTION;
            tvRestriction.setText(String.valueOf(restriction));
        }
    }

    private void DecreaseDistance() {
        if (restriction > 0) {
            restriction = restriction - UNIT_RESTRICTION;
            tvRestriction.setText(String.valueOf(restriction));
        }
    }

    private void SaveRestriction() {
        MPreferenceManager.saveIntInformation(this, MPreferenceManager.RESTRICTION, restriction);
        Alert(R.string.restriction + ": "+restriction, getString(R.string.you_select) + restriction + getString(R.string.number_sos));
    }

    private void SaveContacts() {
        MPreferenceManager.saveBoolInformation(ContactsRestrictionActivity.this, MPreferenceManager.ACTIONWAY, true);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onCheckItem(int position, boolean isChecked) {

//        if (totallyCheckedCount < restriction) {
//            dataProvider.get(position).setChecked(isChecked);
//        } else {
//            dataProvider.get(position).setChecked(false);
//        }
//        adapter.notifyItemChanged(position);
//        totallyCheckedCount = 0;
//        for(int i = 0 ; i < dataProvider.size(); i++)
//        {
//            if(dataProvider.get(i).isChecked())
//                totallyCheckedCount++;
//        }
    }

    @Override
    protected void BackToParent() {
        super.BackToParent();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }


}
