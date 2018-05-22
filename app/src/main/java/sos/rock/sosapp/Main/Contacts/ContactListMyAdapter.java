package sos.rock.sosapp.Main.Contacts;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sos.rock.sosapp.Model.ContactList;
import sos.rock.sosapp.R;
import sos.rock.sosapp.ViewHolders.ContactListMyViewHolder;
import sos.rock.sosapp.ViewHolders.ContactListViewHolder;


public class ContactListMyAdapter extends RecyclerView.Adapter<ContactListMyViewHolder> {

    ArrayList<ContactList> dataProvider;
    Context context;
    LayoutInflater inflater;
    ContactListMyViewHolder.OnContactItemClickListener listener;

    public ContactListMyAdapter(ArrayList<ContactList> dataProvider, Context context, ContactListMyViewHolder.OnContactItemClickListener lst) {
        this.dataProvider = dataProvider;
        this.context = context;
        this.listener = lst;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ContactListMyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.contact_list_my_viewholder, null);
        return new ContactListMyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ContactListMyViewHolder holder, int position) {
        holder.init(dataProvider.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dataProvider.size();
    }
}
