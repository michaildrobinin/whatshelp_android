package sos.rock.sosapp.Main.Contacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.Model.ContactList;
import sos.rock.sosapp.R;
import sos.rock.sosapp.UsersFromMe.ContactListAdapter;
import sos.rock.sosapp.ViewHolders.ContactListViewHolder;

public class ContactAddActivity extends BaseTitleActivity implements ContactListViewHolder.OnContactItemClickListener{

    EditText etSearch;
    ImageButton ibSearch;
    RecyclerView recyclerList;
    ArrayList<ContactList> dataProvider;
    ContactListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();

        etSearch = (EditText) findViewById(R.id.etSearch);
        ibSearch = (ImageButton) findViewById(R.id.ibSearch);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitListRecyclerView();
            }
        });

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        SetupRecyclerView();

    }

    private void SetupRecyclerView() {
        dataProvider = new ArrayList<ContactList>();
        adapter = new ContactListAdapter(dataProvider, this, this);
        recyclerList.setAdapter(adapter);
    }

    private void InitListRecyclerView() {

        ContactList model = new ContactList();
        model.setOwnerApprove(false);
        model.setOtherApprove(false);
        dataProvider.add(model);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void contactItem(int position, ContactList contactList) {
//        ContactRequest(contactList.getNickName(), contactList.getPhone());
    }

    private void ContactRequest(String nickName, String phone) {
        Alert("Request", "You have sent Contact Request to " + nickName);

        dataProvider.clear();

        ContactList model = new ContactList();
        model.setOwnerApprove(true);
        model.setOtherApprove(false);
        dataProvider.add(model);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void BackToParent() {
        super.BackToParent();
        finish();
    }
}
