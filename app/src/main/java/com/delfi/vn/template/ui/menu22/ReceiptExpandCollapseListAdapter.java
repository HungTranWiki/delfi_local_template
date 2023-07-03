package com.delfi.vn.template.ui.menu22;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.customeadapter.expandcollapse.ExpandCollapseAdapter;
import com.delfi.vn.template.ui.customeadapter.expandcollapse.ExpandCollapseViewHolder;

public class ReceiptExpandCollapseListAdapter extends ExpandCollapseAdapter<Receipt11, ReceiptExpandCollapseListAdapter.PurchaseHolder> {

    @Override
    public void setValueSelected(Receipt11 item) {
    }

    public ReceiptExpandCollapseListAdapter(ExpandCollapseAdapter.OnItemClickListener itemClickListener) {
        super(R.layout.item_menu22_receipt, R.layout.item_menu22_receipt_expand, itemClickListener);
    }

    @Override
    public PurchaseHolder onCreateExpandCollapseViewHolder(View itemView, View headerView, @Nullable View expandView, Context context) {
        return new PurchaseHolder(itemView, headerView, expandView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(PurchaseHolder holder, int position) {
        holder.onBindData(this, getItem(position), position, isExpanding(position));
    }

    @Override
    public int getItemCount() {
        return size();
    }

    public class PurchaseHolder extends ExpandCollapseViewHolder<Receipt11> {

        public PurchaseHolder(View itemView, View headerView, @Nullable View expandView, ExpandCollapseAdapter.OnItemClickListener itemClickListener) {
            super(itemView, headerView, expandView, itemClickListener);
        }

        @Override
        protected void bind(Receipt11 receipt, int position) {
            ((TextView) itemView.findViewById(R.id.tvReceiptCodeItem)).setText(receipt.soCT);
            expandView.findViewById(R.id.constrainExpandReceipt).setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (PurchaseHolder.this.itemClickListener != null)
                                PurchaseHolder.this.itemClickListener.onItemClick(receipt, position);
                        }
                    }
            );
            headerView.findViewById(R.id.constCollapse).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PurchaseHolder.this.itemClickListener != null)
                        PurchaseHolder.this.itemClickListener.onItemClick(receipt, position);
                }
            });
        }

        @Override
        protected void onExpanded(View expandView, Receipt11 receipt, int position) {
            ((TextView) itemView.findViewById(R.id.tvToWarehouse)).setText(receipt.fromWH);
            ((TextView) itemView.findViewById(R.id.tvFromWarehouse)).setText(receipt.toWH);
        }

        @Override
        protected void onCollapsed(View expandView, Receipt11 receipt, int position) {
        }

    }

}