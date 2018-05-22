package sos.rock.sosapp.UsersFromMe;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sos.rock.sosapp.Model.ContactList;
import sos.rock.sosapp.R;
import sos.rock.sosapp.ViewHolders.ContactListViewHolder;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListViewHolder> {

    ArrayList<ContactList> dataProvider;
    Context context;
    LayoutInflater inflater;
    ContactListViewHolder.OnContactItemClickListener listener;

    public ContactListAdapter(ArrayList<ContactList> dataProvider, Context context, ContactListViewHolder.OnContactItemClickListener lst) {
        this.dataProvider = dataProvider;
        this.context = context;
        this.listener = lst;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.contact_list_viewholder, null);
        return new ContactListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ContactListViewHolder holder, int position) {
        holder.init(dataProvider.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dataProvider.size();
    }
}
