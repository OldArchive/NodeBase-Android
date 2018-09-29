package nodebase.org.nodebasewallet.ui.settings_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import chain.BlockchainState;
import global.NodeBaseModuleImp;
import nodebase.org.nodebasewallet.BuildConfig;
import nodebase.org.nodebasewallet.R;
import nodebase.org.nodebasewallet.module.NodeBaseContext;
import nodebase.org.nodebasewallet.ui.base.BaseDrawerActivity;
import nodebase.org.nodebasewallet.ui.base.dialogs.SimpleTwoButtonsDialog;
import nodebase.org.nodebasewallet.ui.export_account.ExportKeyActivity;
import nodebase.org.nodebasewallet.ui.import_watch_only.SettingsWatchOnly;
import nodebase.org.nodebasewallet.ui.restore_activity.RestoreActivity;
import nodebase.org.nodebasewallet.ui.settings_backup_activity.SettingsBackupActivity;
import nodebase.org.nodebasewallet.ui.settings_network_activity.SettingsNetworkActivity;
import nodebase.org.nodebasewallet.ui.settings_pincode_activity.SettingsPincodeActivity;
import nodebase.org.nodebasewallet.ui.settings_rates.SettingsRatesActivity;
import nodebase.org.nodebasewallet.ui.start_node_activity.StartNodeActivity;
import nodebase.org.nodebasewallet.ui.tutorial_activity.TutorialActivity;
import nodebase.org.nodebasewallet.utils.CrashReporter;
import nodebase.org.nodebasewallet.utils.DialogsUtil;
import nodebase.org.nodebasewallet.utils.IntentsUtils;
import nodebase.org.nodebasewallet.utils.NavigationUtils;
import nodebase.org.nodebasewallet.utils.ReportIssueDialogBuilder;

import static nodebase.org.nodebasewallet.service.IntentsConstants.INTENT_BROADCAST_DATA_BLOCKCHAIN_STATE;
import static nodebase.org.nodebasewallet.service.IntentsConstants.INTENT_BROADCAST_DATA_PEER_CONNECTED;
import static nodebase.org.nodebasewallet.service.IntentsConstants.INTENT_BROADCAST_DATA_TYPE;
import static nodebase.org.nodebasewallet.service.IntentsConstants.INTENT_EXTRA_BLOCKCHAIN_STATE;
import static nodebase.org.nodebasewallet.ui.tutorial_activity.TutorialActivity.INTENT_EXTRA_INFO_TUTORIAL;

/**
 * Created by Neoperol on 5/11/17.
 */

public class SettingsActivity extends BaseDrawerActivity implements View.OnClickListener {
    private Switch videoSwitch;
    private Button buttonBackup;
    private Button buttonRestore;
    private Button btn_export_pub_key;
    private Button btn_import_xpub;
    private Button buttonChange;
    private Button btn_change_node;
    private Button btn_reset_blockchain;
    private Button btn_report;
    private Button btn_support;
    private Button buttonTutorial;
    private TextView textAbout, text_rates;
    private TextView txt_network_info;

    @Override
    protected void onCreateView(Bundle savedInstanceState, ViewGroup container) {
        getLayoutInflater().inflate(R.layout.fragment_settings, container);
        setTitle("Settings");

        TextView app_version = (TextView) findViewById(R.id.app_version);
        app_version.setText(BuildConfig.VERSION_NAME);

        txt_network_info = (TextView) findViewById(R.id.txt_network_info);

        textAbout = (TextView)findViewById(R.id.text_about);
        String text = "Made by<br> <font color=#016ACD>akshaynexus</font> <br>(c) NDB Community (c) PIVX Devs";
        textAbout.setText(Html.fromHtml(text));
        // Open Backup Wallet
        buttonBackup = (Button) findViewById(R.id.btn_backup_wallet);
        buttonBackup.setOnClickListener(this);

        // Open Restore Wallet
        buttonRestore = (Button) findViewById(R.id.btn_restore_wallet);
        buttonRestore.setOnClickListener(this);

        btn_export_pub_key = (Button) findViewById(R.id.btn_export_pub_key);
        btn_export_pub_key.setOnClickListener(this);

        btn_import_xpub = (Button) findViewById(R.id.btn_import_xpub);
        btn_import_xpub.setOnClickListener(this);

        // Open Change Pincode
        buttonChange = (Button) findViewById(R.id.btn_change_pincode);
        buttonChange.setOnClickListener(this);

        btn_change_node = (Button) findViewById(R.id.btn_change_node);
        btn_change_node.setOnClickListener(this);

        btn_reset_blockchain = (Button) findViewById(R.id.btn_reset_blockchain);
        btn_reset_blockchain.setOnClickListener(this);

        // rates
        findViewById(R.id.btn_rates).setOnClickListener(this);
        text_rates = (TextView) findViewById(R.id.text_rates);
        text_rates.setText(nodebaseApplication.getAppConf().getSelectedRateCoin());

        // Open Network Monitor
        buttonChange = (Button) findViewById(R.id.btn_network);
        buttonChange.setOnClickListener(this);

        btn_report = (Button) findViewById(R.id.btn_report);
        btn_report.setOnClickListener(this);

        btn_support = (Button) findViewById(R.id.btn_support);
        btn_support.setOnClickListener(this);

        // Open Tutorial
        buttonTutorial = (Button) findViewById(R.id.btn_tutorial);
        buttonTutorial.setOnClickListener(this);

        // Video Switch
        videoSwitch = (Switch) findViewById(R.id.videoSwitch);
        videoSwitch.setChecked(nodebaseApplication.getAppConf().isSplashSoundEnabled());
        videoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                nodebaseApplication.getAppConf().setSplashSound(checked);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        setNavigationMenuItemChecked(2);
        updateNetworkStatus();
        text_rates.setText(nodebaseApplication.getAppConf().getSelectedRateCoin());
    }

    private void updateNetworkStatus() {
        // Check if the activity is on foreground
        if (!isOnForeground)return;
        txt_network_info.setText(
                Html.fromHtml(
                        "Network<br><font color=#016ACD>"+nodebaseModule.getConf().getNetworkParams().getId()+
                                "</font><br>" +
                                "Height<br><font color=#016ACD>"+nodebaseModule.getChainHeight()+"</font><br>" +
                                "Protocol Version<br><font color=#016ACD>"+
                                nodebaseModule.getProtocolVersion()+"</font>"

                )
        );
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_backup_wallet){
            Intent myIntent = new Intent(v.getContext(), SettingsBackupActivity.class);
            startActivity(myIntent);
        }else if (id == R.id.btn_tutorial){
            Intent myIntent = new Intent(v.getContext(), TutorialActivity.class);
            myIntent.putExtra(INTENT_EXTRA_INFO_TUTORIAL,true);
            startActivity(myIntent);
        }else if (id == R.id.btn_restore_wallet){
            Intent myIntent = new Intent(v.getContext(), RestoreActivity.class);
            startActivity(myIntent);
        }else if (id == R.id.btn_change_pincode){
            Intent myIntent = new Intent(v.getContext(), SettingsPincodeActivity.class);
            startActivity(myIntent);
        }else if (id == R.id.btn_network){
            startActivity(new Intent(v.getContext(),SettingsNetworkActivity.class));
        }else if(id == R.id.btn_change_node) {
            startActivity(new Intent(v.getContext(), StartNodeActivity.class));
        }else if(id == R.id.btn_reset_blockchain){
            launchResetBlockchainDialog();
        }else if (id == R.id.btn_report){
            launchReportDialog();
        }else if(id == R.id.btn_support){
            IntentsUtils.startSend(
                    this,
                    getString(R.string.support_subject),
                    getString(R.string.report_issue_dialog_message_issue),
                    new ArrayList<Uri>()
            );
        }else if (id == R.id.btn_export_pub_key){
            startActivity(new Intent(v.getContext(), ExportKeyActivity.class));
        }else if (id == R.id.btn_import_xpub){
            startActivity(new Intent(v.getContext(), SettingsWatchOnly.class));
        }else if (id == R.id.btn_rates){
            startActivity(new Intent(v.getContext(), SettingsRatesActivity.class));
        }
    }

    private void launchResetBlockchainDialog() {
        SimpleTwoButtonsDialog dialog = DialogsUtil.buildSimpleTwoBtnsDialog(
                this,
                getString(R.string.dialog_reset_blockchain_title),
                getString(R.string.dialog_reset_blockchain_body),
                new SimpleTwoButtonsDialog.SimpleTwoBtnsDialogListener() {
                    @Override
                    public void onRightBtnClicked(SimpleTwoButtonsDialog dialog) {
                        nodebaseApplication.stopBlockchain();
                        Toast.makeText(SettingsActivity.this,R.string.reseting_blockchain,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onLeftBtnClicked(SimpleTwoButtonsDialog dialog) {
                        dialog.dismiss();
                    }
                }
        );
        dialog.setLeftBtnText(R.string.button_cancel)
                .setRightBtnText(R.string.button_ok);
        dialog.show();
    }

    private void launchReportDialog() {
        ReportIssueDialogBuilder dialog = new ReportIssueDialogBuilder(
                this,
                "nodebase.org.nodebasewallet.myfileprovider",
                R.string.report_issuea_dialog_title,
                R.string.report_issue_dialog_message_issue)
        {
            @Nullable
            @Override
            protected CharSequence subject() {
                return NodeBaseContext.REPORT_SUBJECT_ISSUE+" "+nodebaseApplication.getVersionName();
            }

            @Nullable
            @Override
            protected CharSequence collectApplicationInfo() throws IOException {
                final StringBuilder applicationInfo = new StringBuilder();
                CrashReporter.appendApplicationInfo(applicationInfo, nodebaseApplication);
                return applicationInfo;
            }

            @Nullable
            @Override
            protected CharSequence collectStackTrace() throws IOException {
                return null;
            }

            @Nullable
            @Override
            protected CharSequence collectDeviceInfo() throws IOException {
                final StringBuilder deviceInfo = new StringBuilder();
                CrashReporter.appendDeviceInfo(deviceInfo, SettingsActivity.this);
                return deviceInfo;
            }

            @Nullable
            @Override
            protected CharSequence collectWalletDump() throws IOException {
                return ((NodeBaseModuleImp)nodebaseModule).getWallet().toString(false,true,true,null);
            }
        };
        dialog.show();
    }
    @Override
    protected void onBlockchainStateChange() {
        updateNetworkStatus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavigationUtils.goBackToHome(this);
    }
}
