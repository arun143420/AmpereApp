package com.example.arun.ampereplusapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.arun.ampereplusapp.R;
import com.example.arun.ampereplusapp.activity.AddAppliance;
import com.example.arun.ampereplusapp.activity.Appliances;
import com.example.arun.ampereplusapp.bean.DeviceBean;

import java.util.List;

public class DeviceAdapter extends BaseAdapter {

    private Context context;
    private List<DeviceBean> list;
    private DeviceBean deviceBean;
    private TextView nametxt, idtext;
    private Button onbtn, offbtn;
    private ProgressBar spinner;
    private Button app_btn;
    String device_id;

    public DeviceAdapter(Context context, List<DeviceBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rows,null);
        deviceBean = list.get(i);
        nametxt = rowView.findViewById(R.id.id_device_name);
        nametxt.setText(deviceBean.getDevice_name());
        idtext = rowView.findViewById(R.id.id_device_id);
        idtext.setText(deviceBean.getDevice_id());
        idtext.setVisibility(view.INVISIBLE);
        device_id = idtext.getText().toString();
        app_btn = rowView.findViewById(R.id.add_app_btn);



        nametxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, Appliances.class);
                it.putExtra("device_id",list.get(i).getDevice_id());
                it.putExtra("device_name",list.get(i).getDevice_name());
                context.startActivity(it);

            }
        });


        app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(context, AddAppliance.class);
                it.putExtra("device_id",list.get(i).getDevice_id());
                it.putExtra("device_name",list.get(i).getDevice_name());
                context.startActivity(it);

            }
        });




        //onbtn = rowView.findViewById(R.id.device_switchon);
        //offbtn = rowView.findViewById(R.id.device_switchoff);

       /* MqttClient client = null;


        try {
            client = new MqttClient(
                    "tcp://broker.hivemq.com:1883", //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence()); //Persistence
        } catch (MqttException e) {
            e.printStackTrace();
        }



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

                try {
                    finalClient.publish(
                            "/ampereplus/cmd", // topic
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

        final MqttClient finalClient1 = client;
        offbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    finalClient1.publish(
                            "/ampereplus/cmd", // topic
                            offByteArr, // payload
                            2, // QoS
                            false); // retained?
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
        });*/

        return rowView;
    }
}
