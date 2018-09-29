package nodebase.org.nodebasewallet.ui.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import nodebase.org.nodebasewallet.NodeBaseApplication;
import global.NodeBaseModule;

/**
 * Created by akshaynexus on 6/29/17.
 */

public class BaseFragment extends Fragment {

    protected NodeBaseApplication nodebaseApplication;
    protected NodeBaseModule nodebaseModule;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodebaseApplication = NodeBaseApplication.getInstance();
        nodebaseModule = nodebaseApplication.getModule();
    }

    protected boolean checkPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(getActivity(),permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
