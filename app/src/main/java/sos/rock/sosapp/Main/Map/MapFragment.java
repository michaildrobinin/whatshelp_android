package sos.rock.sosapp.Main.Map;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import sos.rock.sosapp.ApiUtils.ApiCallWrapper;
import sos.rock.sosapp.ApiUtils.AsyncTaskCallback;
import sos.rock.sosapp.ApiUtils.ServerURLs;
import sos.rock.sosapp.Login.LoginActivity;
import sos.rock.sosapp.Main.BaseFragment;
import sos.rock.sosapp.Model.MapMarker;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.GlobalConstants;

public class MapFragment extends BaseFragment {

    MapView mapView;
    GoogleMap map;
    Location userLocation;
    Marker userPosition;
    ArrayList<MapMarker> bookMarkerArray = new ArrayList<>();
//    FloatingActionButton btSearchBook;

    GoogleMap.OnInfoWindowClickListener infoWindowClickListener  = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Integer index = (Integer) marker.getTag();

            Intent i = new Intent(parent, LoginActivity.class);
            i.putExtra(GlobalConstants.BUNDLE_INT, bookMarkerArray.get(index).getGroupId());
            startActivity(i);
        }
    };


    public MapFragment() {
        // Required empty public constructor
    }
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView)rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                InitMap();
            }
        });

//        btSearchBook = (FloatingActionButton)rootView.findViewById(R.id.btSearchBook);
        return rootView;
    }

    private void InitMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), R.string.turnon_gps, Toast.LENGTH_SHORT).show();
            return;
        }
        if(userLocation != null)
        {
            LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            userPosition = map.addMarker(new MarkerOptions().position(latLng).title("Me"));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            map.animateCamera(cameraUpdate);
        }
        map.setMyLocationEnabled(true);

        /*
        ContentValues values = new ContentValues();
        Log.d("Token", parent.getMainApp().getToken());
        values.put("TOKEN", parent.getMainApp().getToken());
        ApiCallWrapper getBookService  = new ApiCallWrapper(getContext(), true, "Initializing map", ServerURLs.BOOKMAP_URL, values, new AsyncTaskCallback() {
            @Override
            public void onResultService(Object result) {
                try{
                    JSONObject resultJson = new JSONObject(result.toString());
                    if(resultJson.getBoolean(GlobalConstants.API_RESULT_FIELD)){
                        for(int i = 0 ; i < bookMarkerArray.size(); i++)
                        {
                            bookMarkerArray.get(i).getMarker().remove();
                        }

                        bookMarkerArray = new ArrayList<>();
                        JSONArray bookArray = resultJson.getJSONArray(GlobalConstants.API_RESULT_CONTENT);

                        for(int i = 0 ; i < bookArray.length(); i++)
                        {
                            LatLng site_point = new LatLng(bookArray.getJSONObject(i).getDouble("CENTER_LAT"), bookArray.getJSONObject(i).getDouble("CENTER_LOT"));
                            FrameLayout tv = (FrameLayout) parent.getLayoutInflater().inflate(R.layout.item_map_marker, null, false);
                            tv.measure(View.MeasureSpec.makeMeasureSpec(1, View.MeasureSpec.UNSPECIFIED),
                                    View.MeasureSpec.makeMeasureSpec(1, View.MeasureSpec.UNSPECIFIED));
                            tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
                            tv.setDrawingCacheEnabled(true);
                            tv.buildDrawingCache();
                            Bitmap bm = tv.getDrawingCache();
                            Marker tmp_marker = map.addMarker(new MarkerOptions().position(site_point).icon(BitmapDescriptorFactory.fromBitmap(bm)));
                            tmp_marker.setTag(i);
                            MapMarker mapMarker = new MapMarker();
                            mapMarker.setMarker(tmp_marker);
                            mapMarker.setGroupId(bookArray.getJSONObject(i).getInt("GEO_ID"));
                            mapMarker.setCount(bookArray.getJSONObject(i).getInt("COUNT"));
                            bookMarkerArray.add(mapMarker);
                        }
                        map.setInfoWindowAdapter(new CustomInfoWindow(getContext(), bookMarkerArray, infoWindowClickListener));
                    }
                    else{
                        Toast.makeText(parent, getString(R.string.token_invalid), Toast.LENGTH_SHORT).show();
                        parent.LogOut();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        getBookService.execute();
        */
        bookMarkerArray = new ArrayList<>();

        for (int i=0; i<2; i++) {
            LatLng site_point = new LatLng(0.0, 0.0);

            View tv = parent.getLayoutInflater().inflate(R.layout.item_map_marker, null, false);
            if (i==0) {
                site_point = new LatLng(37.422490625, -122.047221875);
                ImageView ivMapType = (ImageView) tv.findViewById(R.id.ivMapType);
                ivMapType.setImageResource(R.drawable.marker_icon);
            } else {
                site_point = new LatLng(37.06, 35.27);
                ImageView ivMapType = (ImageView) tv.findViewById(R.id.ivMapType);
                ivMapType.setImageResource(R.drawable.shop_marker);
            }

            tv.measure(View.MeasureSpec.makeMeasureSpec(1, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(1, View.MeasureSpec.UNSPECIFIED));
            tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
            tv.setDrawingCacheEnabled(true);
            tv.buildDrawingCache();
            Bitmap bm = tv.getDrawingCache();
            Marker tmp_marker = map.addMarker(new MarkerOptions().position(site_point).icon(BitmapDescriptorFactory.fromBitmap(bm)));
            tmp_marker.setTag(i);
            MapMarker mapMarker = new MapMarker();
            mapMarker.setMarker(tmp_marker);
            mapMarker.setGroupId(1);
            mapMarker.setCount(1);
            mapMarker.setMarkerType(i);
            bookMarkerArray.add(mapMarker);
        }

        map.setInfoWindowAdapter(new CustomInfoWindow(getContext(), bookMarkerArray, infoWindowClickListener));

        map.setOnInfoWindowClickListener(infoWindowClickListener);
    }

    @Override
    public void onResume() {
        if(mapView != null)
            mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy()
    {

        if(mapView != null)
            mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void setUserLocation(Location location)
    {
        userLocation = location;
        LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        //update user position map here.
        if(userPosition == null)
        {
            if(map == null)
                return;
            userPosition = map.addMarker(new MarkerOptions().position(latLng).title("Me"));
            userPosition.setTag(-1);
        }
        else
        {
            userPosition.setPosition(latLng);
        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .bearing(0)
                .tilt(45)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
