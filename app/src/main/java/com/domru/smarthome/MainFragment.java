package com.domru.smarthome;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Locale;

public class MainFragment extends Fragment{
    public static class DeviceTypes{
        public static final Pair<Integer, String> SOCKET = 1;
        public static final int LIGHTBULB = 2;
        public static final int SMOKE_DETECTOR = 3;
    }

    private RecyclerView recyclerView;
    private Button addButton;
    private ItemAdapter adapter;
    private TextView deviceInfoText;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.deviceInfoText = view.findViewById(R.id.device_info_text);

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
        this.adapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onClick(DeviceItem data) {

            }
        });
        this.recyclerView.setAdapter(adapter);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.main_fragment, null);
    }

    private void showDeviceInfo(DeviceItem deviceItem) {
        switch(deviceItem.deviceType){

        }
    }

    private void  addDevice(){
        DeviceItem deviceItem = new DeviceItem();
        deviceItem.deviceName = "Розетка3";
        deviceItem.deviceType = "Розетка";
        this.adapter.dataList.add(deviceItem);
        this.adapter.notifyDataSetChanged();
    }

    private void initData(){
        ArrayList<DeviceItem> list = new ArrayList<>();
        DeviceItem item = new DeviceItem();
        item.deviceType = "Розетка";
        item.deviceName = "Розетка 1";
        list.add(item);

        item = new DeviceItem();
        item.deviceType = "Датчик задымления";
        item.deviceName = "Детектор дыма 1";
        list.add(item);

        this.adapter.setDataList(list);
    }

    public final static class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<DeviceItem> dataList = new ArrayList<>();
        private OnItemClick onItemClick;

        public static class ViewHolder extends RecyclerView.ViewHolder{
            private Context context;
            private TextView deviceType;
            private TextView deviceName;
            private OnItemClick onItemClick;

            public ViewHolder(View itemView, Context context, OnItemClick onItemClick){
                super(itemView);
                this.context = context;
                this.onItemClick = onItemClick;
                this.deviceType = itemView.findViewById(R.id.device_type);
                this.deviceName = itemView.findViewById(R.id.device_name);
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

        public void setOnItemClick(OnItemClick onItemClick){
            this.onItemClick = onItemClick;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View contactView = inflater.inflate(R.layout.device_card, parent, false);
            LinearLayout linLayout = (LinearLayout)contactView.findViewById(R.id.card_layout);
            View miniature = null;
            switch(viewType){
                case (DeviceTypes.SOCKET):
                    miniature = new TextView(context);
                    ((TextView) miniature).setText("Работает");
                    break;
                case (DeviceTypes.SMOKE_DETECTOR):
                    miniature = new TextView(context);
                    ((TextView) miniature).setText("ЗАДЫМЛЕНИЕ!");
                    break;
                case (DeviceTypes.LIGHTBULB):
                    miniature = new TextView(context);
                    ((TextView) miniature).setText("Горит");
                    break;
            }
            linLayout.addView(miniature);
            return new ViewHolder(contactView, context, onItemClick);
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
                case "Лампочка":
                    return DeviceTypes.LIGHTBULB;
                case "Датчик задымления":
                    return DeviceTypes.SMOKE_DETECTOR;
                default:
                    return DeviceTypes.SOCKET;
            }
        }
    }

    interface OnItemClick{
        void onClick(DeviceItem data);
    }
}

