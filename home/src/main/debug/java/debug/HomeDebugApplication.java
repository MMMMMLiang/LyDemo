package debug;

import android.app.Application;

import com.billy.cc.core.component.CC;
import com.sun.ly.home.BuildConfig;

/**
 * debug模式下 单包运行
 * @author cool
 */
public class HomeDebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(BuildConfig.DEBUG);
    }
}
