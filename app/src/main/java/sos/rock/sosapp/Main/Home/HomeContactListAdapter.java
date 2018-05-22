package sos.rock.sosapp.Main.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sos.rock.sosapp.Model.ContactListHome;
import sos.rock.sosapp.R;


public class HomeContactListAdapter extends RecyclerView.Adapter<HomeContactListAdapter.HomeContactViewHolder> {

    private ArrayList<ContactListHome> dataProvider;

    public HomeContactListAdapter(ArrayList<ContactListHome> dataProvider)
    {
        this.dataProvider = dataProvider;
    }

    @Override
    public HomeContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_home_viewholder, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeContactViewHolder holder, int position) {
        holder.InitData(dataProvider.get(position));
    }

    @Override
    public int getItemCount() {
        return dataProvider.size();
    }

    public class HomeContactViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvContactInfo;
        ImageView ivCallCheck;

        public HomeContactViewHolder(View itemView) {
            super(itemView);
            tvContactInfo = (TextView) itemView.findViewById(R.id.tvContactInfo);
            ivCallCheck = (ImageView) itemView.findViewById(R.id.ivCallCheck);
        }

        public void InitData(ContactListHome model)
        {
            tvContactInfo.setText(model.getUserName() + ":  " + model.getPhone());
            switch (model.getCallResult()) {
                case 0:
                    ivCallCheck.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    ivCallCheck.setVisibility(View.VISIBLE);
                    ivCallCheck.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    break;
                case 2:
                    ivCallCheck.setVisibility(View.VISIBLE);
                    ivCallCheck.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                    break;
            }
        }
    }

}
