package com.electropeyk.squenda.activities;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.electropeyk.squenda.R;
import com.electropeyk.squenda.mqttdemo.PahoMqttClient;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;
    private String clientid = "";
    private Timer myTimer;
    TextView tvMessage ;
    EditText etSubTopic;
    EditText etPubTopic ;
    EditText etPubMsg ;
    EditText etBroker ;
    EditText etUName ;
    EditText etPWord ;
    String msg_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                                                 // Main Activity layout file
        TextView tvMessage = (TextView) findViewById(R.id.subscribedMsg);
        tvMessage.setMovementMethod(new ScrollingMovementMethod());                             // Scroller for feedback TextView object

        //Generate unique client id for MQTT broker connection
        Random r = new Random();
        int i1 = r.nextInt(5000 - 1) + 1;
        clientid = "mqtt" + i1;

        //Get Edit field values from layout GUI

        etSubTopic = (EditText) findViewById(R.id.subTopic);
        etPubTopic = (EditText) findViewById(R.id.pubTopic);
        etPubMsg = (EditText) findViewById(R.id.pubMsg);
        etBroker = (EditText) findViewById(R.id.urlBroker);
        etUName = (EditText) findViewById(R.id.clientUn);
        etPWord = (EditText) findViewById(R.id.clientPw);

        String urlBroker    = etBroker.getText().toString().trim();
        String username     = etUName.getText().toString().trim();
        String password     = etPWord.getText().toString().trim();
        tvMessage = (TextView) findViewById(R.id.subscribedMsg);


        pahoMqttClient = new PahoMqttClient();
        client = pahoMqttClient.getMqttClient(  getApplicationContext(),                        // Connect to MQTT Broker
                urlBroker,
                clientid,
                username,
                password
        );
        //Register Bottom Navigation Callback


        //Create listener for MQTT messages.
        mqttCallback();

        //Create Timer to report MQTT connection status every 1 second
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ScheduleTasks();
            }

        }, 0, 1000);
    }
    private void ScheduleTasks()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(RunScheduledTasks);
    }


    private Runnable RunScheduledTasks = new Runnable() {
        public void run() {
            //This method runs in the same thread as the UI.

            //Check MQTT Connection Status
            TextView tvMessage  = (TextView) findViewById(R.id.cnxStatus);
            String msg_new="";

            if(pahoMqttClient.mqttAndroidClient.isConnected() ) {
                msg_new = "Connected\r\n";
                tvMessage.setTextColor(0xFF00FF00); //Green if connected
                tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            else {
                msg_new = "Disconnected\r\n";
                tvMessage.setTextColor(0xFFFF0000); //Red if not connected
                tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
            tvMessage.setText(msg_new);
        }
    };


    // Called when a subscribed message is received
    protected void mqttCallback() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                //msg("Connection lost...");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                TextView tvMessage = (TextView) findViewById(R.id.subscribedMsg);
                if(topic.equals("mycustomtopic1")) {
                    //Add custom message handling here (if topic = "mycustomtopic1")
                }
                else if(topic.equals("mycustomtopic2")) {
                    //Add custom message handling here (if topic = "mycustomtopic2")
                }
                else {
                    String msg = "topic: " + topic + "\r\nMessage: " + message.toString() + "\r\n";
                    tvMessage.append( msg);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void connect(View view) {
        //--- Set Connection Parameters ---
        String urlBroker = etBroker.getText().toString().trim();
        String username  = etUName.getText().toString().trim();
        String password  = etPWord.getText().toString().trim();

        Random r = new Random();        //Unique Client ID for connection
        int i1 = r.nextInt(5000 - 1) + 1;
        clientid = "mqtt" + i1;

        if(pahoMqttClient.mqttAndroidClient.isConnected() ) {
            //Disconnect and Reconnect to  Broker
            try {
                //Disconnect from Broker
                pahoMqttClient.disconnect(client);
                //Connect to Broker
                client = pahoMqttClient.getMqttClient(getApplicationContext(), urlBroker, clientid, username, password);
                //Set Mqtt Message Callback
                mqttCallback();
            }
            catch (MqttException e) {
            }
        }
        else {
            //Connect to Broker
            client = pahoMqttClient.getMqttClient(getApplicationContext(), urlBroker, clientid, username, password);
            //Set Mqtt Message Callback
            mqttCallback();
        }
    }

    public void subscribe(View view) {
        if(!pahoMqttClient.mqttAndroidClient.isConnected() ) {
            msg_new = "Currently not connected to MQTT broker: Must be connected to subscribe to a topic\r\n";
            tvMessage.append(msg_new);

        }
        String topic = etSubTopic.getText().toString().trim();
        if (!topic.isEmpty()) {
            try {
                pahoMqttClient.subscribe(client, topic, 1);
                msg_new = "Added subscription topic: " + etSubTopic.getText() + "\r\n";
                tvMessage.append(msg_new);

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void publish(View view) {
        //Check if connected to broker
        if(!pahoMqttClient.mqttAndroidClient.isConnected() ) {
            msg_new = "Currently not connected to MQTT broker: Must be connected to publish message to a topic\r\n";
            tvMessage.append(msg_new);

        }
        //Publish non-blank message
        String pubtopic = etPubTopic.getText().toString().trim();
        String msg      = etPubMsg.getText().toString().trim();
        if (!msg.isEmpty()) {
            try {
                pahoMqttClient.publishMessage(client, msg, 1, pubtopic);
                msg_new = "Message sent to pub topic: " + etPubTopic.getText() + "\r\n";
                tvMessage.append(msg_new);
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void clear(View view) {
        //Clear message field
        tvMessage.setText("");
    }

    public void exit(View view) {
        System.exit(0);
    }
}

