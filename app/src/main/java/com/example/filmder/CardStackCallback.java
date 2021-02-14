package com.example.filmder;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class CardStackCallback extends DiffUtil.Callback{

    private List<ItemModel> old_list, new_list;

    public CardStackCallback(List<ItemModel> old_list, List<ItemModel> new_list) {
        this.old_list = old_list;
        this.new_list = new_list;
    }

    @Override
    public int getOldListSize() {
        return old_list.size();
    }

    @Override
    public int getNewListSize() {
        return new_list.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old_list.get(oldItemPosition).getImage() == new_list.get(newItemPosition).getImage();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old_list.get(oldItemPosition) == new_list.get(newItemPosition);
    }
}
