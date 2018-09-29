package nodebase.org.nodebasewallet.ui.initial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import nodebase.org.nodebasewallet.NodeBaseApplication;
import nodebase.org.nodebasewallet.ui.splash_activity.SplashActivity;
import nodebase.org.nodebasewallet.ui.wallet_activity.WalletActivity;
import nodebase.org.nodebasewallet.utils.AppConf;

/**
 * Created by akshaynexus on 8/19/17.
 */

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NodeBaseApplication nodebaseApplication = NodeBaseApplication.getInstance();
        AppConf appConf = nodebaseApplication.getAppConf();
        // show report dialog if something happen with the previous process
        Intent intent;
        if (!appConf.isAppInit() || appConf.isSplashSoundEnabled()){
            intent = new Intent(this, SplashActivity.class);
        }else {
            intent = new Intent(this, WalletActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
