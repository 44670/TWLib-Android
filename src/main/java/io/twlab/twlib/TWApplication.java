package io.twlab.twlib;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.blue.rmcclient.PadActivity;

import org.json.JSONException;

/**
 * Created by CTC on 2015/5/18.
 */
public class TWApplication {
    Service handlerService;
    String internalName;
    String serviceName;
    String displayName = "UNTITLED";

    public static final int SDK_CMD_ONACTIVATE = 1;
    public static final int SDK_CMD_ONDEACTIVATE = 2;
    public static final int SDK_CMD_UPDATE_LAYOUT = 3;
    public static final int SDK_CMD_ONBUTTONCLICK = 4;
    public static final int SDK_CMD_REGISTER_APP = 5;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }



    Intent buildIntent(int cmd) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("io.twlab.twlclient",
                "io.twlab.twlclient.TWLService"));
        intent.putExtra("cmd", cmd);
        intent.putExtra("internalName", internalName);

        return intent;
    }

    public void registerCurrentApplication() {
        Intent intent = buildIntent(SDK_CMD_REGISTER_APP);
        intent.putExtra("displayName", displayName);
        intent.putExtra("serviceName", serviceName);
        trySendIntent(intent);
    }

    boolean trySendIntent(Intent intent) {
        try {
            handlerService.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onActivate() {

    }

    public void onButtonClick(int id) {

    }

    public void onDeactivate() {

    }

    public void updateLayout(TWAutoLayout layout, int focusOnId) {
        Intent intent = buildIntent(SDK_CMD_UPDATE_LAYOUT);
        try {
            intent.putExtra("json", layout.toJSON());
            Log.e("json", layout.toJSON());
            intent.putExtra("focusOnId", focusOnId);
            trySendIntent(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void handleIntent(Intent intent) {
        int cmd = intent.getIntExtra("cmd", 0);
        if (cmd == SDK_CMD_ONACTIVATE) {
            onActivate();
        }
        if (cmd == SDK_CMD_ONBUTTONCLICK) {
            onButtonClick(intent.getIntExtra("arg0", 0));
        }
        if (cmd == SDK_CMD_ONDEACTIVATE) {
            onDeactivate();
        }
    }

    public TWApplication(Service s) {
        handlerService = s;
        internalName = s.getPackageName();
        serviceName = s.getClass().getName();
    }

}
