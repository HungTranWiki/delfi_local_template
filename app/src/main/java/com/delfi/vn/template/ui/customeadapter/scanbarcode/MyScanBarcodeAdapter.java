package com.delfi.vn.template.ui.customeadapter.scanbarcode;

import android.support.v7.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public abstract class MyScanBarcodeAdapter<T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> implements Filterable {
    protected List<T> mObjects;

    protected ArrayList<T> mOriginalValues;
    private ArrayFilter mFilter;
    private final Object mLock = new Object();

    protected OnItemClickListener mItemClickListener;

    public MyScanBarcodeAdapter(List<T> items) {
        this.mObjects = items;
    }

    public MyScanBarcodeAdapter(OnItemClickListener itemClickListener) {
        this.mObjects = new ArrayList<>();
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    public abstract void setValueSelected(T warehouseSelected);

    public abstract void setValueSelected(T warehouseSelected, int position);

    public List<T> getDataList() {
        List<T> items = mOriginalValues != null ? mOriginalValues : mObjects;
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setDataList(List<T> items) {
        if (items == null)
            items = new ArrayList<>();
        this.mObjects = items;
        mOriginalValues = new ArrayList<>(items);
        notifyDataSetChanged();
        if (mItemClickListener != null) {
            mItemClickListener.onDataListChanged(items);
        }
    }

    public void clearData() {
        try {
            if (mObjects != null)
                mObjects.clear();
            if (mOriginalValues != null)
                mOriginalValues.clear();
            notifyDataSetChanged();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            mObjects = new ArrayList<>();
            mOriginalValues = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    public List<T> getOriginalValues() {
        return mOriginalValues != null ? mOriginalValues : new ArrayList<>();
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null || mOriginalValues.size() == 0) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<T> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toUpperCase();

                final ArrayList<T> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<T> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final T value = values.get(i);
                    if (value.toString() != null) {
                        final String valueText = value.toString().toUpperCase();
                        if (valueText.equals(prefixString)) {
                            newValues.add(value);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mObjects = (List<T>) results.values;
            notifyDataSetChanged();
            if (mItemClickListener != null)
                mItemClickListener.onDataListChanged(mObjects);
        }

    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);

        void onDataListChanged(List<T> items);
    }
}
