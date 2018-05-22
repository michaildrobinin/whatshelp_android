package sos.rock.sosapp.Main.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import sos.rock.sosapp.Model.MapMarker;
import sos.rock.sosapp.R;


public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    private final View myContentsView;
    ArrayList<MapMarker> mapMarkers;
    GoogleMap.OnInfoWindowClickListener listener;

    public CustomInfoWindow(Context ctx, ArrayList<MapMarker> markers, GoogleMap.OnInfoWindowClickListener listener){
        myContentsView = LayoutInflater.from(ctx).inflate(R.layout.item_custom_marker, null);
        mapMarkers = markers;
        this.listener = listener;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        int tagValue = (int) marker.getTag();
        if(tagValue != -1) {
            TextView txtNumber = (TextView) myContentsView.findViewById(R.id.txtNumber);
            CircleImageView imgView = (CircleImageView) myContentsView.findViewById(R.id.circleImageView);
            imgView.setImageResource(tagValue == 0? R.drawable.marker_icon: R.drawable.shop_marker);
            txtNumber.setText(mapMarkers.get(tagValue).getCount() + "");
            Button btShowDetail = (Button) myContentsView.findViewById(R.id.btDetailinfo);
            btShowDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    listener.onInfoWindowClick(marker);
                }
            });
            return myContentsView;
        }
        else
            return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}