/*
 * Copyright 2016. Alejandro Sánchez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.delfi.vn.template.ui.main.submenu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MainMenu> dataList;
    private OnItemClickListener listener;

    public MenuAdapter() {
        dataList = new ArrayList<>();
    }

    public void setDataList(List<MainMenu> dataList) {
        this.dataList = dataList;
    }

    public List<MainMenu> getDataList() {
        return this.dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_menu, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(16, 8, 16, 8);
        view.setLayoutParams(lp);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataList.get(position), listener, position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView, Context context) {
            super(itemView);
        }

        public void bind(final MainMenu data, OnItemClickListener listener, int position) {
            CardView cardSubMenu = itemView.findViewById(R.id.cardSubMenu);
            TextView tvName = itemView.findViewById(R.id.tvSubMenu);

            tvName.setText(data.getNameResource());
            cardSubMenu.setOnClickListener(v -> {
                listener.onMenuClick(data);
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onMenuClick(MainMenu item);
    }

}
