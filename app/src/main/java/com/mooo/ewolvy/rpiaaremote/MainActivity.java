package com.mooo.ewolvy.rpiaaremote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    AAState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String address = sharedPrefs.getString(getString(R.string.settings_address_key), "");
        String port_str = sharedPrefs.getString(getString(R.string.settings_port_key), "0");
        String username = sharedPrefs.getString(getString(R.string.settings_username_key), "");
        String password = sharedPrefs.getString(getString(R.string.settings_password_key), "");
        int port;
        try {
            port = Integer.parseInt(port_str);
        } catch(NumberFormatException nfe) {
            port = 0;
        }

        // Constructor sin datos del servidor: hay que avisar al usuario que los rellene
        state = new AAState(AAState.AUTO_MODE,          // Modo automático
                AAState.AUTO_FAN,                       // Ventilador automático
                27);                                    // 27 grados

        if (Objects.equals(address, "") || port == 0 || Objects.equals(username, "") || Objects.equals(password, "")) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.server_data_missing), Toast.LENGTH_LONG);
            toast.show();
        }else {
            // TODO: Preparar el objeto Servidor
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void offClick(View view) {
        ImageView onOffSign = (ImageView) findViewById(R.id.onOffSign);
        if (onOffSign != null){
            onOffSign.setVisibility(View.INVISIBLE);
        }
        state.setOn(false);
        Toast toast = Toast.makeText(getApplicationContext(), state.getPowerOff(), Toast.LENGTH_LONG);
        toast.show();
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
        if (!state.isActiveFan()){return;}
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
        if (state.getCurrentTemp() > AAState.TEMP_MIN && state.isActiveTemp()){
            state.setMinusTemp();
            TextView tempView = (TextView) findViewById(R.id.tempView);
            String temperature = Integer.toString(state.getCurrentTemp());
            if (tempView != null) tempView.setText(temperature);
        }
    }

    public void tempPlusClick(View view) {
        if (state.getCurrentTemp() < AAState.TEMP_MAX && state.isActiveTemp()){
            state.setPlusTemp();
            TextView tempView = (TextView) findViewById(R.id.tempView);
            String temperature = Integer.toString(state.getCurrentTemp());
            if (tempView != null) tempView.setText(temperature);
        }
    }

    public void sendClick(View view) {
        ImageView onOffSign = (ImageView) findViewById(R.id.onOffSign);
        if (onOffSign != null){
            onOffSign.setVisibility(View.VISIBLE);
        }
        state.setOn (true);
        Toast toast = Toast.makeText(getApplicationContext(), state.getCommand(), Toast.LENGTH_LONG);
        toast.show();
    }

    public void swingClick(View view) {
        if (state.getIsOn()) {
            Toast toast = Toast.makeText(getApplicationContext(), state.getSwing(), Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.is_off_message), Toast.LENGTH_LONG);
            toast.show();
        }
    }
}