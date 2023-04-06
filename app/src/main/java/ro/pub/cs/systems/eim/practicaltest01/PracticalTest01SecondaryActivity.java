package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {
    private TextView numberOfClicksTextView;
    private Button okButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        numberOfClicksTextView = (TextView)findViewById(R.id.textView3);
        okButton = findViewById(R.id.button);
        cancelButton = findViewById(R.id.button2);
        okButton.setOnClickListener(v -> {
            setResult(RESULT_OK, null);
            finish();
        });
        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });
        Intent intent = getIntent();

        if (intent != null && intent.getExtras().containsKey(Constants.NUMBER_OF_CLICKS)) {
            int numberOfClicks = intent.getIntExtra(Constants.NUMBER_OF_CLICKS, -1);
            numberOfClicksTextView.setText(String.valueOf(numberOfClicks));
        }

    }
}
