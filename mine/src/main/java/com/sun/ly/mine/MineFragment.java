package com.sun.ly.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.sun.ly.baselib.component.ActionName;
import com.sun.ly.baselib.component.ComponentKey;
import com.sun.ly.baselib.component.ComponentUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author cool
 */
public class MineFragment extends Fragment {
    private Button track;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        Button login = view.findViewById(R.id.f_mine_btn);
        track = view.findViewById(R.id.f_mine_track);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        CCResult result = CC.obtainBuilder(ComponentUtil.HOME_COMPONENT)
                .setActionName(ActionName.HOME_COMPONENT_GET_NAME)
                .build()
                .call();
        String i = result.getDataItem(ComponentKey.KEY_HOME_GET_NAME, "-1");
        track.setText(i);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC.obtainBuilder(ComponentUtil.HOME_COMPONENT)
                        .setActionName(ActionName.HOME_COMPONENT_OPEN_ACTIVITY)
                        .build()
                        .callAsyncCallbackOnMainThread(new IComponentCallback() {
                            @Override
                            public void onResult(CC cc, CCResult result) {
                                String str = result.getDataItem(ComponentKey.KEY_HOME_GET_BACK_NAME,
                                        "-100");
                                track.setText(str);
                            }
                        });
            }
        });
        return view;
    }
}
