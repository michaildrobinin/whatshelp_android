package sos.rock.sosapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sos.rock.sosapp.R;

import static android.view.View.GONE;


public class SpinerAdapter extends BaseAdapter {
    ArrayList<String> dataProvider;

    public SpinerAdapter(ArrayList<String> dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public int getCount() {
        return dataProvider.size();
    }

    @Override
    public Object getItem(int position) {
        return dataProvider.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.spiner_layout, null, false);
        LinearLayout llSpiner = (LinearLayout) rootView.findViewById(R.id.llSpiner);
        llSpiner.setBackgroundColor(0x00000000);
        TextView txtContent = (TextView) rootView.findViewById(R.id.txtSpiner);
        ImageView imgArrow = (ImageView)rootView.findViewById(R.id.imgArrow);
        txtContent.setText(dataProvider.get(position));
        return rootView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.spiner_layout, null, false);
        LinearLayout llSpiner = (LinearLayout) rootView.findViewById(R.id.llSpiner);
        llSpiner.setBackgroundColor(0x55000000);
        TextView txtContent = (TextView) rootView.findViewById(R.id.txtSpiner);
        ImageView imgArrow = (ImageView)rootView.findViewById(R.id.imgArrow);
        imgArrow.setVisibility(GONE);
        txtContent.setText(dataProvider.get(position));
        return rootView;
    }
}
