package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.arun.ampereplusapp.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.Charset;
import java.util.Arrays;

public class AppplianceRemote extends AppCompatActivity {

    private Button onbtn, offbtn, ontimer_btn, off_timer_btn ;
    private ImageView on_indicator, off_indicator;
    private TimePicker timePicker;
    CharSequence s_no ;
    private String appliance_name, device_name, device_id;
    private TextView eappliance_name;

    private SharedPreferences preferences;
    private String userid;



    MqttClient client; //Persistence

    {
        try {
            client = new MqttClient(
                    "tcp://broker.hivemq.com:1883", //URI
                        MqttClient.generateClientId(), //ClientId
                        new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apppliance_remote);

        Intent it = getIntent();
        device_id = it.getStringExtra("device_id");
        appliance_name = it.getStringExtra("appliance_name");
        s_no = it.getCharSequenceExtra("S_NO");
        eappliance_name = findViewById(R.id.appliance_name_remote);
        eappliance_name.setText(appliance_name);
        on_indicator = findViewById(R.id.appliance_on_indicator);
        on_indicator.setVisibility(View.INVISIBLE);
        off_indicator = findViewById(R.id.appliance_off_indicator);
        timePicker =findViewById(R.id.timePicker);
        onbtn = findViewById(R.id.appliance_on_btn);
        offbtn = findViewById(R.id.appliance_off_btn);
        ontimer_btn = findViewById(R.id.on_timer);
        off_timer_btn = findViewById(R.id.off_timer);
        preferences = getSharedPreferences("spuser",MODE_PRIVATE);
        userid = preferences.getString("keyid","");
        String device_no = "device"+s_no;
        String on_cmd = "{ \"cmd\": \"on\", \"device\":\""+device_no+"\""+"}";
        System.out.println(on_cmd);
        String off_cmd = "{ \"cmd\": \"off\" ,\"device\":\""+device_no+"\""+"}";
        System.out.println(off_cmd);
        final byte[] onByteArr = on_cmd.getBytes(Charset.forName("UTF-8"));
        final byte[] offByteArr = off_cmd.getBytes(Charset.forName("UTF-8"));





        final MqttConnectOptions options = new MqttConnectOptions();

        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        // options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        try {
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }



         final MqttClient finalClient = client;

        onbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                off_indicator.setVisibility(View.INVISIBLE);
                on_indicator.setVisibility(View.VISIBLE);

                try {
                    finalClient.publish(
                            "ampere/esp32_196394" , // topic
                            // "userid/"+ device_id ", // actual topic
                            onByteArr, // payload
                            2, // QoS
                            false); // retained?
                } catch (MqttException e) {
                    e.printStackTrace();
                }


                //client.publish( "topic", // topic
                // "payload".getBytes(UTF_8), // payload
                //   2, // QoS
                // true); // retained


                finalClient.setCallback(new MqttCallback() {

                    @Override
                    public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        System.out.println(topic + ": " + Arrays.toString(message.getPayload()));
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                    }
                });

                try {
                    finalClient.connect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                try {
                    finalClient.subscribe("#", 1);
                } catch (MqttException e) {
                    e.printStackTrace();
                }


            }

        });

        offbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_indicator.setVisibility(View.INVISIBLE);
                off_indicator.setVisibility(View.VISIBLE);

                try {
                    finalClient.publish(
                            "ampere/esp32_196394", // topic
                        //    "userid/"+ device_id +"/device"+ s_no, // topic
                            offByteArr, // payload
                            2, // QoS
                            false); // retained?
                } catch (MqttException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }
}
