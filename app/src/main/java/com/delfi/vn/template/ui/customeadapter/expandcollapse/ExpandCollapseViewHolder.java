package com.delfi.vn.template.ui.customeadapter.expandcollapse;

import android.animation.ObjectAnimator;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.delfi.vn.template.R;

public abstract class ExpandCollapseViewHolder<T> extends RecyclerView.ViewHolder {

    public ExpandCollapseAdapter.OnItemClickListener itemClickListener;
    public View headerView;
    public View expandView;
    private int rotationAngle = 0;

    public ExpandCollapseViewHolder(View itemView, View headerView, @Nullable View expandView,
                                    @Nullable ExpandCollapseAdapter.OnItemClickListener itemClickListener) {
        super(itemView);
        this.headerView = headerView;
        this.expandView = expandView;
        this.itemClickListener = itemClickListener;
    }

    public void onBindData(ExpandCollapseAdapter adapter, final T data,
                           final int position, boolean isExpanding) {
        if (expandView != null) {
            expandView.setVisibility(isExpanding ? View.VISIBLE : View.GONE);
            ImageView icArrow = itemView.findViewById(R.id.imgArrow);
            icArrow.setEnabled(true);
            icArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(icArrow, "rotation",
                            rotationAngle, (rotationAngle + 180) % 360);
                    anim.setDuration(300);
                    anim.start();
                    if (rotationAngle == 0) {
                        ExpandCollapseViewHolder.this.onExpanded(expandView, data, position);
                        rotationAngle = 180;
                        expandView.setVisibility(View.VISIBLE);
                        adapter.setExpanding(position, true);
                    } else {
                        ExpandCollapseViewHolder.this.onCollapsed(expandView, data, position);
                        rotationAngle = 0;
                        expandView.setVisibility(View.GONE);
                        adapter.setExpanding(position, false);
                    }
                }
            });
            if (isExpanding)
                onExpanded(expandView, data, position);
            else
                onCollapsed(expandView, data, position);
        } else {
            ImageView icArrow = itemView.findViewById(R.id.imgArrow);
            icArrow.setEnabled(false);
        }
        if (itemClickListener != null) {
            headerView.setOnClickListener(v -> itemClickListener.onItemClick(data, position));
        }
        bind(data, position);
    }

    protected abstract void bind(final T data,
                                 final int position);

    protected abstract void onExpanded(final View expandView, final T data, final int position);

    protected abstract void onCollapsed(final View expandView, final T data, final int position);
}
