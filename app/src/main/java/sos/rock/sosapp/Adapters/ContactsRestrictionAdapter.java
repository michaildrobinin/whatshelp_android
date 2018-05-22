package sos.rock.sosapp.Adapters;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;

import sos.rock.sosapp.Model.ContactsRestriction;
import sos.rock.sosapp.R;


public class ContactsRestrictionAdapter extends RecyclerView.Adapter<ContactsRestrictionAdapter.ContactsRestrictionViewHolder> {
    private ArrayList<ContactsRestriction> dataProvider;
    ContactListItemCheckedChangeListener parentListener;
    public ContactsRestrictionAdapter(ArrayList<ContactsRestriction> dataProvider, ContactListItemCheckedChangeListener ParentListener)
    {
        this.dataProvider = dataProvider;
        this.parentListener = ParentListener;
    }

    @Override
    public ContactsRestrictionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactsRestrictionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_check, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactsRestrictionViewHolder holder, int position) {
        holder.InitData(dataProvider.get(position), parentListener);
    }

    @Override
    public int getItemCount() {
        return dataProvider.size();
    }

    public class ContactsRestrictionViewHolder extends RecyclerView.ViewHolder
    {
        public AppCompatCheckBox chkCheckedBox;

        public ContactsRestrictionViewHolder(View itemView) {
            super(itemView);
            chkCheckedBox = (AppCompatCheckBox) itemView.findViewById(R.id.chkContact);
        }

        public void InitData(ContactsRestriction model, final ContactListItemCheckedChangeListener parentListener)
        {
            chkCheckedBox.setText(model.getUserName() + "  " + model.getUserPhone());
            chkCheckedBox.setChecked(model.isChecked());
            chkCheckedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    parentListener.onCheckItem(getAdapterPosition(), isChecked);
                }
            });
        }
    }


    public interface ContactListItemCheckedChangeListener
    {
        void onCheckItem(int position, boolean isChecked);
    }
}
