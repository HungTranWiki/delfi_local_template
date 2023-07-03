package com.delfi.vn.template.ui.menu11.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.ui.customeadapter.search.MyAdapter;

public class ProductListAdapter extends MyAdapter<ProductMenu11, ProductListAdapter.PurchaseHolder> {

    public ProductListAdapter(OnItemClickListener itemClickListener) {
        super(itemClickListener);
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public void setValueSelected(ProductMenu11 warehouseSelected) {

    }

    @Override
    public ProductListAdapter.PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu11_product, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(32, 16, 32, 8);
        view.setLayoutParams(lp);
        return new ProductListAdapter.PurchaseHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.PurchaseHolder holder, int position) {
        holder.bind(mObjects.get(position), mItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public ProductMenu11 getValue(int position) {
        return mObjects.get(position);
    }

    public void setValue(int position, ProductMenu11 product){
        mObjects.set(position, product);
        notifyDataSetChanged();
    }

    protected class PurchaseHolder extends RecyclerView.ViewHolder {

        private Context context;

        public PurchaseHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
        }

        public void bind(ProductMenu11 data, final OnItemClickListener onItemClickListener, int position) {
            ImageView imgIcon = itemView.findViewById(R.id.imgIcon);
            if(data.soLuongDaQuet == 0){
                imgIcon.setImageResource( R.drawable.ic_product_0);
            }else if(data.soLuongDaQuet < data.soLuongYeuCau) {
                imgIcon.setImageResource(R.drawable.ic_product_working);
            }else{
                imgIcon.setImageResource(R.drawable.ic_product_enough);
            }
            ((TextView) itemView.findViewById(R.id.tvProductName)).setText(data.productId);
            ((TextView) itemView.findViewById(R.id.tvProductCode)).setText(data.maVT);
            ((TextView) itemView.findViewById(R.id.tvQtyPicked)).setText(String.valueOf(data.soLuongDaQuet));
            ((TextView) itemView.findViewById(R.id.tvQtyExpect)).setText(String.valueOf(data.soLuongYeuCau));
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(data, position));
        }
    }

}

