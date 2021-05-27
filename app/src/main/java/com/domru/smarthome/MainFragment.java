package com.domru.smarthome;

import android.util.Pair;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.ArrayList;


public class MainFragment extends Fragment{
    public static class DeviceTypes{
        public static final String SOCKET_STRING = "Розетка";
        public static final int SOCKET_INT = 1;

        public static final String LIGHTBULB_STRING = "Лампочка";
        public static final int LIGHTBULB_INT = 2;

        public static final String SMOKE_DETECTOR_STRING = "Датчик задымления";
        public static final int SMOKE_DETECTOR_INT = 3;
    }

    private RecyclerView recyclerView;
    private Button addButton;
    private ItemAdapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.addButton = view.findViewById(R.id.add_button);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDevice();
            }
        });

        this.recyclerView = view.findViewById(R.id.devices);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        this.adapter = new ItemAdapter();
        this.recyclerView.setAdapter(adapter);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.main_fragment, null);
    }

    private void addDevice(){
        DeviceItem deviceItem = new DeviceItem();
        deviceItem.deviceType = DeviceTypes.SOCKET_STRING;
        deviceItem.deviceName = "Розетка3";
        deviceItem.deviceInfo = "Информация об устройстве " + deviceItem.deviceName;
        this.adapter.dataList.add(deviceItem);
        this.adapter.notifyDataSetChanged();
    }

    private void initData(){
        ArrayList<DeviceItem> list = new ArrayList<>();
        DeviceItem item = new DeviceItem();
        item.deviceType = DeviceTypes.SOCKET_STRING;
        item.deviceName = "Розетка 1";
        item.deviceInfo = "Информация об устройстве " + item.deviceName;
        list.add(item);

        item = new DeviceItem();
        item.deviceType = DeviceTypes.SMOKE_DETECTOR_STRING;
        item.deviceName = "Датчик задымления 1";
        item.deviceInfo = "Информация об устройстве " + item.deviceName;
        list.add(item);

        this.adapter.setDataList(list);
    }

    public final static class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
        private ArrayList<DeviceItem> dataList = new ArrayList<>();

        public static class ViewHolder extends RecyclerView.ViewHolder{
            private TextView deviceType;
            private TextView deviceName;

            public ViewHolder(View itemView){
                super(itemView);
                this.deviceType = itemView.findViewById(R.id.device_type);
                this.deviceName = itemView.findViewById(R.id.device_name);
                itemView.setTag(this);
            }

            public void bind(final DeviceItem data){
                this.deviceType.setText(data.deviceType);
                this.deviceName.setText(data.deviceName);
            }
        }

        public void setDataList(ArrayList<DeviceItem> dataList) {
            this.dataList = new ArrayList<>();
            if (dataList != null) {
                this.dataList.addAll(dataList);
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View contactView = inflater.inflate(R.layout.device_card, parent, false);
            contactView.setOnClickListener(this);

            LinearLayout linLayout = (LinearLayout)contactView.findViewById(R.id.card_layout);
            View miniature = null;
            switch(viewType){
                case (DeviceTypes.SOCKET_INT):
                    miniature = new TextView(context);
                    ((TextView) miniature).setText("Работает");
                    break;
                case (DeviceTypes.SMOKE_DETECTOR_INT):
                    miniature = new TextView(context);
                    ((TextView) miniature).setText("ЗАДЫМЛЕНИЕ!");
                    break;
                case (DeviceTypes.LIGHTBULB_INT):
                    miniature = new TextView(context);
                    ((TextView) miniature).setText("Горит");
                    break;
            }
            linLayout.addView(miniature);
            return new ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            DeviceItem data = dataList.get(position);
            ((ViewHolder) viewHolder).bind(data);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            DeviceItem data = dataList.get(position);
            switch(data.deviceType) {
                case DeviceTypes.LIGHTBULB_STRING:
                    return DeviceTypes.LIGHTBULB_INT;
                case DeviceTypes.SMOKE_DETECTOR_STRING:
                    return DeviceTypes.SMOKE_DETECTOR_INT;
                default:
                    return DeviceTypes.SOCKET_INT;
            }
        }

        @Override
        public void onClick(View v){
            ViewHolder vh = (ViewHolder)v.getTag();
            int position = vh.getAdapterPosition();
            DeviceItem item = this.dataList.get(position);
            TextView deviceInfo = v.getRootView().findViewById(R.id.device_info_text);
            deviceInfo.setText(item.deviceInfo);
        }
    }
}

