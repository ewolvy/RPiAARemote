package com.mooo.ewolvy.rpiaaremote;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

class AAState {
    // Constants
    static final int AUTO_MODE = 0;
    static final int COOL_MODE = 1;
    static final int DRY_MODE = 2;
    static final int HEAT_MODE = 3;
    static final int FAN_MODE = 4;

    static final int AUTO_FAN = 0;
    static final int LEVEL1_FAN = 1;
    static final int LEVEL2_FAN = 2;
    static final int LEVEL3_FAN = 3;

    static final int TEMP_MAX = 32;
    static final int TEMP_MIN = 16;

    private static final String LOG_TAG = "AAState";

    private static final String BASE_URL = "http://ewolvy.mooo.com:1207/";
    private static final String URL_PLUSTEMP = "plustemp";
    private static final String URL_MINUSTEMP = "minustemp";
    private static final String URL_SWINGON = "swingOn";
    private static final String URL_SWINGOFF = "swingOff";
    private static final String URL_TURNON = "turnOn";
    private static final String URL_TURNOFF = "turnOff";
    private static final String URL_MODE = "mode";
    private static final String URL_FAN = "fan";
    private static final String URL_GETSTATUS = "getStatus";

    // Variables
    private boolean isOn;
    private boolean isSwingOn;
    private int currentMode;
    private int currentFan;
    private int currentTemp;

    // Constructor
    AAState (boolean stateOn,
                    boolean stateSwing,
                    int stateMode,
                    int stateFan,
                    int stateTemp){
        isOn = stateOn;
        isSwingOn = stateSwing;
        currentMode = stateMode;
        currentFan = stateFan;
        currentTemp = stateTemp;
    }

    // Setters and getters methods for variables //
    public boolean isOn() {
        return isOn;
    }
    boolean setOn(boolean on) {
        isOn = on;
        return true;
    }
    public boolean isSwingOn() {
        return isSwingOn;
    }
    public void setSwingOn(boolean isSwingOn) {
        this.isSwingOn = isSwingOn;
    }
    int getMode() {
        return currentMode;
    }
    boolean setMode(int mode) {
        if (mode < AUTO_MODE || mode > FAN_MODE){
            return false;
        }else{
            this.currentMode = mode;
            return true;
        }
    }
    int getFan() {
        return currentFan;
    }
    boolean setFan(int fan) {
        if (fan < AUTO_FAN || fan > LEVEL3_FAN){
            return false;
        }else {
            this.currentFan = fan;
            return true;
        }
    }
    int getCurrentTemp() {
        return currentTemp;
    }
    public boolean setCurrentTemp(int currentTemp) {
        if (currentTemp < TEMP_MIN || currentTemp > TEMP_MAX){
            return false;
        }else{
            this.currentTemp = currentTemp;
            return true;
        }
    }
    // End of setters and getters //

    // Plus 1 and minus 1 degrees
    boolean setPlusTemp(){
        if (currentTemp != TEMP_MAX){
            currentTemp++;
            return true;
        }else{
            return false;
        }
    }
    boolean setMinusTemp(){
        if (currentTemp != TEMP_MIN){
            currentTemp--;
            return true;
        }else{
            return false;
        }
    }

}
