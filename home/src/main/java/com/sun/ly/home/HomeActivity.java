package com.sun.ly.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.sun.ly.baselib.component.ComponentKey;

import androidx.appcompat.app.AppCompatActivity;


/**
 * 组件化 分包1
 * @author cool
 */
public class HomeActivity extends AppCompatActivity {
    private TextView textView;
    private String idStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            idStr = bundle.getString(CCUtil.EXTRA_KEY_CALL_ID, "");
        }

        textView = findViewById(R.id.home_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CCResult result = new CCResult();
                result.addData(ComponentKey.KEY_HOME_GET_BACK_NAME, "Hello CC");
                CC.sendCCResult(idStr, result);
                finish();
            }
        });
    }
}
