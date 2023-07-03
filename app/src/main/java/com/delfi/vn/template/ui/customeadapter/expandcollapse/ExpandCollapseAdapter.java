package com.delfi.vn.template.ui.customeadapter.expandcollapse;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import com.delfi.vn.template.R;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpandCollapseAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements Filterable {
    private List<ExpandCollapseModel> mObjects;
    private ArrayList<ExpandCollapseModel> mOriginalValues;
    private ArrayFilter mFilter;
    private final Object mLock = new Object();
    private int headerLayoutRes;
    private Integer expandLayoutRes;
    private int style = 1;
    private SearchType searchType = SearchType.CONTAIN;
    protected OnItemClickListener mItemClickListener;


    public ExpandCollapseAdapter(@LayoutRes int headerLayoutRes,
                                 OnItemClickListener itemClickListener
    ) {
        this.mItemClickListener = itemClickListener;
        this.headerLayoutRes = headerLayoutRes;
        this.expandLayoutRes = null;
        mObjects = new ArrayList<>();
    }

    public ExpandCollapseAdapter(@LayoutRes int headerLayoutRes,
                                 @LayoutRes Integer expandLayoutRes,
                                 OnItemClickListener itemClickListener
    ) {
        this.mItemClickListener = itemClickListener;
        this.headerLayoutRes = headerLayoutRes;
        this.expandLayoutRes = expandLayoutRes;
        mObjects = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collapse_expand_cardview_top, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (this.style == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collapse_expand_view_middle, null);
            lp.setMargins(0, 0, 0, 8);
        } else {
            lp.setMargins(20, 0, 20, 8);
        }

        view.setLayoutParams(lp);
        LinearLayout headerView = view.findViewById(R.id.lnHeader);

        View headerLayout = LayoutInflater.from(parent.getContext()).inflate(headerLayoutRes, null);
        headerView.addView(headerLayout);

        if (expandLayoutRes != null) {
            LinearLayout expandView = view.findViewById(R.id.lnExpand);
            View expandLayout = LayoutInflater.from(parent.getContext()).inflate(expandLayoutRes, null);
            expandView.addView(expandLayout);
            expandView.setTag(false);
            return onCreateExpandCollapseViewHolder(view, headerView, expandView, parent.getContext());
        }
        return onCreateExpandCollapseViewHolder(view, headerView, null, parent.getContext());

    }

    public abstract void setValueSelected(T warehouseSelected);

    public abstract VH onCreateExpandCollapseViewHolder(View itemView, View headerView, @Nullable View expandView, Context context);

    public List<T> getDataList() {
        List<T> items = mOriginalValues != null ? convertToOriginalModelList(mOriginalValues) : convertToOriginalModelList(mObjects);
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setDataList(List<T> items) {
        if (items == null)
            items = new ArrayList<>();
        this.mObjects = convertFromOriginalModelList(items);
        setOriginalModelList(this.mObjects);
        mapperToExpandCollapseModel();
        notifyDataSetChanged();
        if (mItemClickListener != null) {
            mItemClickListener.onDataListChanged(items);
        }
    }

    public void clearData() {
        try {
            if (mObjects != null) mObjects.clear();
            else mObjects = new ArrayList<>();

            if (mOriginalValues != null) mOriginalValues.clear();
            else mOriginalValues = new ArrayList<>();
            notifyDataSetChanged();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            mObjects = new ArrayList<>();
            mOriginalValues = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null || mOriginalValues.size() == 0) {
                synchronized (mLock) {
                    setOriginalModelList(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<ExpandCollapseModel> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<ExpandCollapseModel> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<ExpandCollapseModel> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final ExpandCollapseModel item = values.get(i);
                    final T value = (T) item.data;
                    if (value.toString() != null) {
                        final String valueText = value.toString().toLowerCase();

                        // First match against the whole, non-splitted value
                        if (searchType == SearchType.CONTAIN) {
                            if (valueText.startsWith(prefixString)) {
                                newValues.add(values.get(i));
                            } else {
                                final String[] words = valueText.split(" ");
                                for (String word : words) {
                                    if (word.startsWith(prefixString) || word.contains(prefixString)) {
                                        newValues.add(item);
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (valueText.equals(prefixString)) {
                                newValues.add(values.get(i));
                            } else {
                                final String[] words = valueText.split(" ");
                                for (String word : words) {
                                    if (word.equals(prefixString)) {
                                        newValues.add(item);
                                        break;
                                    }
                                }
                            }
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
            mObjects = (List<ExpandCollapseModel>) results.values;
            notifyDataSetChanged();
            if (mItemClickListener != null)
                mItemClickListener.onDataListChanged(mObjects);
        }
    }


    public List<ExpandCollapseModel> mapperToExpandCollapseModel() {
        return (List<ExpandCollapseModel>) mOriginalValues;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);

        void onDataListChanged(List<T> items);
    }

    public List<ExpandCollapseModel> convertFromOriginalModelList(List<T> originalList) {
        List<ExpandCollapseModel> list = new ArrayList<>();
        for (T data : originalList) {
            list.add(new ExpandCollapseModel(data));
        }
        return list;
    }

    public void setOriginalModelList(List<ExpandCollapseModel> originalList) {
        mOriginalValues = new ArrayList<>();
        for (ExpandCollapseModel data : originalList) {
            mOriginalValues.add(data);
        }
    }

    public List<T> convertToOriginalModelList(List<ExpandCollapseModel> list) {
        List<T> originalList = new ArrayList<>();
        for (ExpandCollapseModel item : list) {
            originalList.add((T) item.data);
        }
        return originalList;
    }

    public boolean isExpanding(int position) {
        return mOriginalValues.get(position).isExpanding;
    }

    public T getItem(int position) {
        return (T) mObjects.get(position).data;
    }

    public void setExpanding(int position, boolean isExpanding) {
        mOriginalValues.get(position).isExpanding = isExpanding;
    }

    public int size() {
        if (mObjects != null)
            return mObjects.size();
        return 0;
    }

    public void setItem(int index, T newValue) {
        ExpandCollapseModel item = mOriginalValues.get(index);
        item.data = newValue;
        mOriginalValues.set(index, item);
        notifyItemChanged(index);
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
}
