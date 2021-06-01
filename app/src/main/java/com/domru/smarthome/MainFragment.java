package com.domru.smarthome;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

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


    private RecyclerView recyclerView;
    private Button addButton;
    private Button removeButton;
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

        this.removeButton = view.findViewById(R.id.remove_button);
        this.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                removeDevice();
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

    private void removeDevice(){
        int index = this.adapter.dataList.size() - 1;
        this.adapter.dataList.remove(index);
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

        item = new DeviceItem();
        item.deviceType = DeviceTypes.LIGHTBULB_STRING;
        item.deviceName = "Лампочка 1";
        item.deviceInfo = "Информация об устройстве " + item.deviceName;
        list.add(item);

        this.adapter.setDataList(list);
    }

    public final static class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnFocusChangeListener,
    View.OnClickListener, CompoundButton.OnCheckedChangeListener{
        private ArrayList<DeviceItem> dataList = new ArrayList<>();

        public static class ViewHolder extends RecyclerView.ViewHolder{
            private TextView deviceType;
            private TextView deviceName;
            private LinearLayout cardExpansion;

            public ViewHolder(View itemView){
                super(itemView);
                this.deviceType = itemView.findViewById(R.id.device_type);
                this.deviceName = itemView.findViewById(R.id.device_name);
                this.cardExpansion = itemView.findViewById(R.id.card_expansion);
                itemView.setTag(this);
                this.cardExpansion.setTag(this);
            }

            public void bind(final DeviceItem data){
                this.deviceType.setText(data.deviceType);
                this.deviceName.setText(data.deviceName);
                this.cardExpansion.setVisibility(data.expanded ? View.VISIBLE : View.GONE);
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
            contactView.setOnFocusChangeListener(this);
            contactView.setOnClickListener(this);

            LinearLayout cardExpansion = contactView.findViewById(R.id.card_expansion);
            switch(viewType){
                case (DeviceTypes.SOCKET_INT):
                    ToggleButton onOffButtonSocket = CardToggleButton(context, R.string.toggle_on, R.string.toggle_off);
                    cardExpansion.addView(onOffButtonSocket);
                    break;
                case (DeviceTypes.SMOKE_DETECTOR_INT):
                    break;
                case (DeviceTypes.LIGHTBULB_INT):
                    ToggleButton onOffButtonLight = CardToggleButton(context, R.string.toggle_on, R.string.toggle_off);
                    cardExpansion.addView(onOffButtonLight);
                    break;
            }
            return new ViewHolder(contactView);
        }

        private Button CardButton(Context context, int textId){
            Button button = new Button(context);
            button.setText(textId);
            button.setFocusable(true);
            button.setNextFocusUpId(R.id.device_card_root);
            return button;
        }

        private ToggleButton CardToggleButton(Context context, int textOnId, int textOffId){
            ToggleButton toggle = new ToggleButton(context);
            toggle.setOnCheckedChangeListener(this);
            toggle.setTextOff(context.getString(textOffId));
            toggle.setTextOn(context.getString(textOnId));
            toggle.setFocusable(true);
            toggle.setNextFocusUpId(R.id.device_card_root);
            return toggle;
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
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                ViewHolder vh = (ViewHolder)v.getTag();
                int position = vh.getAdapterPosition();
                DeviceItem item = this.dataList.get(position);
                TextView deviceInfo = v.getRootView().findViewById(R.id.device_info_title);
                deviceInfo.setText(item.deviceInfo);
            }
        }

        @Override
        public void onClick(View v){
            LinearLayout expansion = v.findViewById(R.id.card_expansion);
            if(expansion.getChildCount() > 0){
                ViewHolder vh = (ViewHolder)v.getTag();
                int position = vh.getAdapterPosition();
                DeviceItem item = this.dataList.get(position);
                item.expanded = !item.expanded;
                notifyItemChanged(position);
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ViewHolder vh = (ViewHolder)((View)buttonView.getParent()).getTag();
            int position = vh.getAdapterPosition();
            DeviceItem item = this.dataList.get(position);
            String text = "Устройство " + item.deviceName;
            if(isChecked) {
                text += " включено";
            }
            else{
                text += " выключено";
            }
            Toast toast = Toast.makeText(buttonView.getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

