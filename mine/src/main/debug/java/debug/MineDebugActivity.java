package debug;

import android.os.Bundle;

import com.sun.ly.mine.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * debug模式下入口类
 * @author cool
 */
public class MineDebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.debug_activity_main);


    }
}
