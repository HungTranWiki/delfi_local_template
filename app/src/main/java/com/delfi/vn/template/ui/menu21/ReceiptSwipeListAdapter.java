package com.delfi.vn.template.ui.menu21;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.customeadapter.swipeandfilter.RecycleSwipeFilterAdapter;
import com.delfi.vn.template.utils.DateTime;

import butterknife.ButterKnife;

public class ReceiptSwipeListAdapter extends RecycleSwipeFilterAdapter<Receipt11, ReceiptSwipeListAdapter.PurchaseHolder> {
    private OnItemClickListener clickListener;

    public ReceiptSwipeListAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void setValueSelected(Receipt11 warehouseSelected) {

    }


    public void deleteItem(int position) {
        if (position < mObjects.size()) {
            mObjects.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Receipt11 getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu21_receipt, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 20);
        view.setLayoutParams(lp);
        return new PurchaseHolder(view);
    }

    @Override
    public void onBindViewHolder(PurchaseHolder holder, int position) {
        Receipt11 model = mObjects.get(position);
        if (model != null)
            holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.containerSwipe;
    }

    class PurchaseHolder extends RecyclerView.ViewHolder {

        public PurchaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Receipt11 data) {
            ((TextView) itemView.findViewById(R.id.tvReceiptCodeItem)).setText(data.soCT);
            ((TextView) itemView.findViewById(R.id.tvDate)).setText(DateTime.parseDate(data.createDate));
            itemView.findViewById(R.id.cardProductItem).setOnClickListener(view -> {
                mItemManger.closeAllItems();
                if (clickListener != null)
                    clickListener.onItemClick(getItem(getAdapterPosition()), getAdapterPosition());
            });


            itemView.findViewById(R.id.lnPrinter).setOnClickListener(view -> {
                mItemManger.closeAllItems();
                if (clickListener != null)
                    clickListener.onPrintLabel(getItem(getAdapterPosition()), getAdapterPosition());
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Receipt11 w, int position);

        void onViewOrderDetail(Receipt11 w, int position);

        void onPrintLabel(Receipt11 w, int position);
    }
}








//        extends MyAdapter<Receipt11, ReceiptListAdapter.PurchaseHolder> {
//
//    public ReceiptListAdapter(OnItemClickListener itemClickListener) {
//        super(itemClickListener);
//        this.mItemClickListener = itemClickListener;
//    }
//
//    @Override
//    public void setValueSelected(Receipt11 warehouseSelected) {
//
//    }
//
//    @Override
//    public PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu1_receipt_item, null);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 0, 6);
//        view.setLayoutParams(lp);
//        return new PurchaseHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(PurchaseHolder holder, int position) {
//        holder.bind(mObjects.get(position), mItemClickListener, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mObjects.size();
//    }
//
//    public Receipt11 getValue(int position) {
//        return mObjects.get(position);
//    }
//
//    protected class PurchaseHolder extends RecyclerView.ViewHolder {
//
//        public PurchaseHolder(View itemView) {
//            super(itemView);
//        }
//
//        public void bind(final Receipt11 data,
//                         final OnItemClickListener onItemClickListener,
//                         final int position) {
//            ((TextView) itemView.findViewById(R.id.tvReceiptCodeItem)).setText(data.soCT);
//            ((TextView) itemView.findViewById(R.id.tvDate)).setText(DateTime.parseDate(data.createDate));
//            itemView.findViewById(R.id.cardProductItem).setOnClickListener(v -> onItemClickListener.onItemClick(data, position));
//        }
//    }
//}