package com.mooo.ewolvy.rpiaaremote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    AAState state = new AAState(false,                      // Está apagado
                                AAState.AUTO_MODE,          // Modo automático
                                AAState.AUTO_FAN, 27);      // Ventilador automático

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOffClick(View view) {
        ImageView button = (ImageView) findViewById(R.id.onOffButton);
        ImageView onOffSign = (ImageView) findViewById(R.id.onOffSign);
        if (state.isOn() && button != null && onOffSign != null) {
            if (state.setOn (false)){
                button.setImageResource(R.drawable.onoff_off);
                onOffSign.setVisibility(View.INVISIBLE);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.connection_error), Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (button != null && onOffSign != null) {
            if (state.setOn (true)) {
                button.setImageResource(R.drawable.onoff_on);
                onOffSign.setVisibility(View.VISIBLE);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.connection_error), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void modeClick(View view) {
        TextView modeView;
        switch (state.getMode()){
            case AAState.AUTO_MODE:
                modeView = (TextView) findViewById(R.id.autoMode);
                if (modeView != null) {
                    modeView.setVisibility(View.INVISIBLE);
                }
                modeView = (TextView) findViewById(R.id.coolMode);
                if (modeView != null) {
                    modeView.setVisibility(View.VISIBLE);
                    state.setMode(AAState.COOL_MODE);
                }
                break;

            case AAState.COOL_MODE:
                modeView = (TextView) findViewById(R.id.coolMode);
                if (modeView != null) {
                    modeView.setVisibility(View.INVISIBLE);
                }
                modeView = (TextView) findViewById(R.id.dryMode);
                if (modeView != null) {
                    modeView.setVisibility(View.VISIBLE);
                    state.setMode(AAState.DRY_MODE);
                }
                break;

            case AAState.DRY_MODE:
                modeView = (TextView) findViewById(R.id.dryMode);
                if (modeView != null) {
                    modeView.setVisibility(View.INVISIBLE);
                }
                modeView = (TextView) findViewById(R.id.heatMode);
                if (modeView != null) {
                    modeView.setVisibility(View.VISIBLE);
                    state.setMode(AAState.HEAT_MODE);
                }
                break;

            case AAState.HEAT_MODE:
                modeView = (TextView) findViewById(R.id.heatMode);
                if (modeView != null) {
                    modeView.setVisibility(View.INVISIBLE);
                }
                modeView = (TextView) findViewById(R.id.fanMode);
                if (modeView != null) {
                    modeView.setVisibility(View.VISIBLE);
                    state.setMode(AAState.FAN_MODE);
                }
                break;

            case AAState.FAN_MODE:
                modeView = (TextView) findViewById(R.id.fanMode);
                if (modeView != null) {
                    modeView.setVisibility(View.INVISIBLE);
                }
                modeView = (TextView) findViewById(R.id.autoMode);
                if (modeView != null) {
                    modeView.setVisibility(View.VISIBLE);
                    state.setMode(AAState.AUTO_MODE);
                }
                break;
        }
    }

    public void fanClick(View view) {
        TextView fanView;
        switch (state.getFan()) {
            case AAState.AUTO_FAN:
                fanView = (TextView) findViewById(R.id.fanLevelAuto);
                if (fanView != null) {
                    fanView.setVisibility(View.INVISIBLE);
                }
                fanView = (TextView) findViewById(R.id.fanLevel1);
                if (fanView != null) {
                    fanView.setVisibility(View.VISIBLE);
                    state.setFan(AAState.LEVEL1_FAN);
                }
                break;

            case AAState.LEVEL1_FAN:
                fanView = (TextView) findViewById(R.id.fanLevel2);
                if (fanView != null) {
                    fanView.setVisibility(View.VISIBLE);
                    state.setFan(AAState.LEVEL2_FAN);
                }
                break;

            case AAState.LEVEL2_FAN:
                fanView = (TextView) findViewById(R.id.fanLevel3);
                if (fanView != null) {
                    fanView.setVisibility(View.VISIBLE);
                    state.setFan(AAState.LEVEL3_FAN);
                }
                break;

            case AAState.LEVEL3_FAN:
                fanView = (TextView) findViewById(R.id.fanLevel1);
                if (fanView != null) {
                    fanView.setVisibility(View.INVISIBLE);
                }
                fanView = (TextView) findViewById(R.id.fanLevel2);
                if (fanView != null) {
                    fanView.setVisibility(View.INVISIBLE);
                }
                fanView = (TextView) findViewById(R.id.fanLevel3);
                if (fanView != null) {
                    fanView.setVisibility(View.INVISIBLE);
                }
                fanView = (TextView) findViewById(R.id.fanLevelAuto);
                if (fanView != null) {
                    fanView.setVisibility(View.VISIBLE);
                    state.setFan(AAState.AUTO_FAN);
                }
                break;
        }
    }

    public void tempMinusClick(View view) {
        if (state.getCurrentTemp() > AAState.TEMP_MIN){
            state.setMinusTemp();
            TextView tempView = (TextView) findViewById(R.id.tempView);
            String temperature = Integer.toString(state.getCurrentTemp());
            if (tempView != null) tempView.setText(temperature);
        }
    }

    public void tempPlusClick(View view) {
        if (state.getCurrentTemp() < AAState.TEMP_MAX){
            state.setPlusTemp();
            TextView tempView = (TextView) findViewById(R.id.tempView);
            String temperature = Integer.toString(state.getCurrentTemp());
            if (tempView != null) tempView.setText(temperature);
        }
    }
}
