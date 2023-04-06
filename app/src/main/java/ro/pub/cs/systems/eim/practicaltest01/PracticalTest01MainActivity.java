package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {
    private Button button1, button2, button3;
    public TextView textView1, textView2;
    public boolean serviceStatus = true;
    private IntentFilter intentFilter = new IntentFilter();


    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

        button1  = (Button)findViewById(R.id.button4);
        button2  = (Button)findViewById(R.id.button5);
        button3 = (Button)findViewById(R.id.buttonactiv2);
        textView1 = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);

        button3.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            int numberOfClicks = Integer.parseInt(textView1.getText().toString()) +
                    Integer.parseInt(textView2.getText().toString());
            intent.putExtra(Constants.NUMBER_OF_CLICKS, numberOfClicks);
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
        });

        button1.setOnClickListener(v -> {
            int leftNumberOfClicks = Integer.valueOf(textView1.getText().toString());
            leftNumberOfClicks++;
            textView1.setText(String.valueOf(leftNumberOfClicks));


            leftNumberOfClicks = Integer.parseInt(textView1.getText().toString());
            int rightNumberOfClicks = Integer.parseInt(textView2.getText().toString());

            // ...

            if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
                   ) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRST_NUMBER, leftNumberOfClicks);
                intent.putExtra(Constants.SECOND_NUMBER, rightNumberOfClicks);
                getApplicationContext().startService(intent);
//                serviceStatus = Constants.SERVICE_STARTED;
            }
//            textView1.setText("maria");
        });

        button2.setOnClickListener(v -> {
            int rightNumberOfClicks = Integer.valueOf(textView2.getText().toString());
            rightNumberOfClicks++;
            textView2.setText(String.valueOf(rightNumberOfClicks));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}