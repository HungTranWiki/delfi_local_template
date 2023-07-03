package com.delfi.vn.template.ui.customeadapter.mainmenu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.delfi.vn.template.R;
import com.delfi.vn.template.models.appmodels.MainMenu;

import java.util.ArrayList;
import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MenuViewHolder>
        implements MainMenuView, MainMenuModel {

    private final Context context;
    private List<MainMenu> mainMenuList;
    private OnItemClickListener onItemClickListener;
    private boolean isActive;

    public MainMenuAdapter(Context context, boolean isActive) {
        this.context = context;
        this.mainMenuList = new ArrayList();
        this.isActive = isActive;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_main_menu, parent, false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        MainMenu mainMenu = get(position);
        int countDocument = mainMenu.getCountDocument();

        ImageView imgMainMenu = holder.itemView.findViewById(R.id.imgMainMenu);
        imgMainMenu.setImageResource(mainMenu.getIcon());
        TextView tvName = holder.itemView.findViewById(R.id.tvName);
        tvName.setText(mainMenu.getNameResource());
        TextView tvMenuCount = holder.itemView.findViewById(R.id.menuCountView);

        if (countDocument > 0) {
            tvMenuCount.setVisibility(View.VISIBLE);
            tvMenuCount.setText(String.valueOf(countDocument));
        } else {
            tvMenuCount.setVisibility(View.GONE);
        }
        if(isActive){
            holder.itemView.setEnabled(true);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(mainMenu);
                    }
                }
            });
        }else {
            holder.itemView.setEnabled(false);
            holder.itemView.setOnClickListener(null);
        }
        ColorStateList color = getTextColorStateList();
        tvName.setTextColor(color);
    }

    private ColorStateList getTextColorStateList() {
        int[][]  states = new int[][]{
                new int[]{-android.R.attr.state_pressed}, // unpressed
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                context.getResources().getColor(R.color.black),
                Color.WHITE
        };
        return new ColorStateList(states, colors);
    }

    @Override
    public void refresh(List<MainMenu> mainMenuList) {
        this.mainMenuList = mainMenuList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mainMenuList.size();
    }

    @Override
    public MainMenu get(int position) {
        return this.mainMenuList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    protected class MenuViewHolder extends RecyclerView.ViewHolder {
        public MenuViewHolder(View itemView) {
            super(itemView);
        }
    }

}
