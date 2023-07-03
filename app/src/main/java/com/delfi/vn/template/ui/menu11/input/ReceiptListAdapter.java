package com.delfi.vn.template.ui.menu11.input;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.customeadapter.search.MyAdapter;
import com.delfi.vn.template.utils.DateTime;

public class ReceiptListAdapter extends MyAdapter<Receipt11, ReceiptListAdapter.PurchaseHolder> {

    public ReceiptListAdapter(OnItemClickListener itemClickListener) {
        super(itemClickListener);
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public void setValueSelected(Receipt11 warehouseSelected) {

    }

    @Override
    public PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu01_receipt, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 6);
        view.setLayoutParams(lp);
        return new PurchaseHolder(view);
    }

    @Override
    public void onBindViewHolder(PurchaseHolder holder, int position) {
        holder.bind(mObjects.get(position), mItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public Receipt11 getValue(int position) {
        return mObjects.get(position);
    }

    protected class PurchaseHolder extends RecyclerView.ViewHolder {

        public PurchaseHolder(View itemView) {
            super(itemView);
        }

        public void bind(final Receipt11 data,
                         final OnItemClickListener onItemClickListener,
                         final int position) {
            ((TextView) itemView.findViewById(R.id.tvReceiptCodeItem)).setText(data.soCT);
            ((TextView) itemView.findViewById(R.id.tvDate)).setText(DateTime.parseDate(data.createDate));
            itemView.findViewById(R.id.cardProductItem).setOnClickListener(v -> onItemClickListener.onItemClick(data, position));
        }
    }
}