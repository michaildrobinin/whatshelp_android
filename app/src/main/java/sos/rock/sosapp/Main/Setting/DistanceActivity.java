package sos.rock.sosapp.Main.Setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import sos.rock.sosapp.Main.BaseTitleActivity;
import sos.rock.sosapp.R;
import sos.rock.sosapp.Utils.MPreferenceManager;

public class DistanceActivity extends BaseTitleActivity implements View.OnClickListener{

    SeekBar sbDistance;
    TextView tvDistance;
    ImageButton ibUp, ibDown;
    int valueProgress, distance;
    Button btSave;
    private static int MAX_DISTANCE = 200;
    private static int UNIT_DISTANCE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitToolbar();

        sbDistance = (SeekBar) findViewById(R.id.sbDistance);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        valueProgress = MPreferenceManager.readIntInformation(this, MPreferenceManager.DISTANCE_PROGRESS);
        sbDistance.setProgress(valueProgress);
        distance = MAX_DISTANCE /100 * valueProgress;
        tvDistance.setText(String.valueOf(distance));

        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueProgress = progress;
                distance = MAX_DISTANCE /100 * valueProgress;
                tvDistance.setText(String.valueOf(distance));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        ibUp = (ImageButton) findViewById(R.id.ibUp);
        ibUp.setOnClickListener(this);

        ibDown = (ImageButton) findViewById(R.id.ibDown);
        ibDown.setOnClickListener(this);

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
            case R.id.btSave:
                saveDistance();
                break;
        }
    }

    private void IncreaseDistance() {
        if (distance < MAX_DISTANCE) {
            distance = distance + UNIT_DISTANCE;
            tvDistance.setText(String.valueOf(distance));
            valueProgress = distance * 100 / MAX_DISTANCE;
            sbDistance.setProgress(valueProgress);
        }
    }

    private void DecreaseDistance() {
        if (distance > 0) {
            distance = distance - UNIT_DISTANCE;
            tvDistance.setText(String.valueOf(distance));
            valueProgress = distance * 100 / MAX_DISTANCE;
            sbDistance.setProgress(valueProgress);
        }
    }

    private void saveDistance() {
        MPreferenceManager.saveBoolInformation(this, MPreferenceManager.ACTIONWAY, false);
        MPreferenceManager.saveIntInformation(this, MPreferenceManager.DISTANCE_PROGRESS, valueProgress);
        MPreferenceManager.saveIntInformation(this, MPreferenceManager.DISTANCE, distance);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", false);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void BackToParent() {
        super.BackToParent();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}