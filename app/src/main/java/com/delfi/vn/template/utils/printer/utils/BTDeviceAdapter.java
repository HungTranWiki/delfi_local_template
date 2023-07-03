package com.delfi.vn.template.utils.printer.utils;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delfi.vn.template.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by DTO-BTAD on 8/31/2021.
 */
public class BTDeviceAdapter extends RecyclerView.Adapter<BTDeviceAdapter.ViewHolder> {

    private List<BluetoothDevice> dataList;
    private OnItemClickListener listener;

    public BTDeviceAdapter() {
        dataList = new ArrayList<>();
    }

    public List<BluetoothDevice> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<BluetoothDevice> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_device_item, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        view.setLayoutParams(lp);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataList.get(position), listener);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(BluetoothDevice item);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDeviceName;
        //private ImageView checkbox;
        private Context context;
        private LinearLayout layout;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            layout = itemView.findViewById(R.id.layout);
            //checkbox = itemView.findViewById(R.id.checkbox);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
        }

        public void bind(final BluetoothDevice data, OnItemClickListener listener) {

            tvDeviceName.setText(data.getName());

                /*if(data.isSelected){
                    checkbox.setImageResource(R.mipmap.ic_check_circle_black_24dp);
                    checkbox.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                else {
                    checkbox.setImageResource(R.mipmap.ic_radio_button_unchecked_black_24dp);
                    checkbox.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey), android.graphics.PorterDuff.Mode.SRC_IN);
                }*/


            layout.setOnClickListener(v -> {
                listener.onItemClick(data);
            });
        }
    }
}
