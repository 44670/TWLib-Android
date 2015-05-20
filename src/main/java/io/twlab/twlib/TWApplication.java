package io.twlab.twlib;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

public class TWApplication {
	public static final String TWLIB_VERSION = "1.0";
	
    Service handlerService;
    String internalName;
    String serviceName;
    String displayName = "UNTITLED";

    public static final int SDK_CMD_ONACTIVATE = 1;
    public static final int SDK_CMD_ONDEACTIVATE = 2;
    public static final int SDK_CMD_UPDATE_LAYOUT = 3;
    public static final int SDK_CMD_ONBUTTONCLICK = 4;
    public static final int SDK_CMD_REGISTER_APP = 5;

	/* 设置应用的显示名称 */
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

	/* 向 TWL 管家注册当前应用 */
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

	/* 手表端应用激活事件 */
    public void onActivate() {

    }

	/* 手表端按钮按下事件 */
    public void onButtonClick(int id) {

    }

	/* 手表端应用退出事件 */
    public void onDeactivate() {

    }

	/* 更新应用布局 */
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

	/* 处理来自 TWL 管家的 intent */
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
