package debug;

import android.os.Bundle;
import android.widget.TextView;

import com.sun.ly.home.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * debug模式下入口类
 * @author cool
 */
public class HomeDebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.debug_activity_main);

        TextView textView = findViewById(R.id.home_text);
        textView.setText("DEBUG_HOME");
    }
}
