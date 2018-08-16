package com.example.arun.ampereplusapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.arun.ampereplusapp.activity.ApplianceEdit;
import com.example.arun.ampereplusapp.R;
import com.example.arun.ampereplusapp.activity.AppplianceRemote;
import com.example.arun.ampereplusapp.bean.ApplianceBean;
import com.example.arun.ampereplusapp.bean.DeviceBean;

import java.util.List;


public class ApplianceAdapter extends BaseAdapter {

    private Context context;
    private List<ApplianceBean> list;
    private List<DeviceBean> list1;
    private String appliance_name;
    private ApplianceBean applianceBean;
    private TextView eappliance_name;
    private String eappliance_id;
    TextView snotxt;
    Button edit_app_btn;


    public ApplianceAdapter(Context context, List<ApplianceBean> list) {
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
        View colView = inflater.inflate(R.layout.cols,null);
        applianceBean = list.get(i);
        snotxt = colView.findViewById(R.id.sno_text);
        snotxt.setText((i+1)+"");
        eappliance_name = colView.findViewById(R.id.appliance_name);
        eappliance_name.setText(applianceBean.getAppliance_name());
        eappliance_id = applianceBean.getDevice_id();
        final CharSequence s_no = snotxt.getText();
        edit_app_btn = colView.findViewById(R.id.edit_app);
        edit_app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(context, ApplianceEdit.class);
                it1.putExtra("device_id_edit",list.get(i).getDevice_id());
                it1.putExtra("appliance_name_edit",list.get(i).getAppliance_name());
                it1.putExtra("appliance_edit_id", list.get(i).getId());
                context.startActivity(it1);

            }
        });

        colView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, AppplianceRemote.class);
                it.putExtra("device_id",list.get(i).getDevice_id());
                it.putExtra("appliance_name",list.get(i).getAppliance_name());
                it.putExtra("S_NO",s_no);
                context.startActivity(it);
            }
        });
        return colView;
    }
}
