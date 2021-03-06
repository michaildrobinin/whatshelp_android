package sos.rock.sosapp.ViewHolders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import sos.rock.sosapp.Model.ContactList;
import sos.rock.sosapp.R;


public class ContactListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView txtNickName, txtPhone;
    ImageView imageUser;
    Button btContact;
    OnContactItemClickListener listener;
    ContactList contactList;

    public ContactListViewHolder(View itemView) {
        super(itemView);

        txtNickName = (TextView) itemView.findViewById(R.id.txtNickName);
        txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
        imageUser = (ImageView) itemView.findViewById(R.id.imageUser);
        btContact = (Button) itemView.findViewById(R.id.btContact);
        btContact.setOnClickListener(this);
    }

    public void init(ContactList list, OnContactItemClickListener listener) {
        this.listener = listener;
        this.contactList = list;
//        txtNickName.setText(contactList.getNickName());
//        txtPhone.setText(contactList.getPhone());
        imageUser.setImageResource(contactList.isMale()? R.drawable.male_placeholder: R.drawable.female_placeholder);

        if (contactList.isOwnerApprove() && contactList.isOtherApprove()) btContact.setText(R.string.button_chat);
        if (!contactList.isOwnerApprove() && !contactList.isOtherApprove()) btContact.setText(R.string.button_chat);
        if (!contactList.isOwnerApprove() && contactList.isOtherApprove()) btContact.setText(R.string.button_chat);
        if (contactList.isOwnerApprove() && !contactList.isOtherApprove())
        {
            btContact.setText(R.string.button_chat);
            btContact.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        listener.contactItem(getAdapterPosition(), contactList);
    }


    public interface OnContactItemClickListener
    {
        void contactItem(int position, ContactList contactList);
    }
}
