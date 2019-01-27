package co.poynt.samples.codesamples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CompleteActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complete);

        String firstName = getIntent().getStringExtra("FIRST_NAME");
        TextView tv0 = (TextView)findViewById(R.id.nameTextView);
        tv0.setText(firstName);

        String temperature = getIntent().getStringExtra("ROOM_TEMPERATURE");
        TextView tv1 = (TextView)findViewById(R.id.temperatureValueTextView);
        tv1.setText(temperature);

        String light = getIntent().getStringExtra("ROOM_LIGHT");
        TextView tv2 = (TextView)findViewById(R.id.lightValueTextView);
        tv2.setText(light);
    }
}
