package com.beastpotato.reactivetests.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Oleksiy on 3/19/2017.
 */

public class BindingRecyclerViewAdapter extends RecyclerView.Adapter<BindingRecyclerViewAdapter.BindingViewHolder> {
    private List<ItemBinder> bindings;

    public BindingRecyclerViewAdapter(List<ItemBinder> bindings) {
        this.bindings = bindings;
    }

    public void setData(List<ItemBinder> bindings) {
        this.bindings = bindings;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), bindings.get(viewType).getLayoutItemId(), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.getBinding().setVariable(bindings.get(position).getBoundVariableId(), bindings.get(position).getBoundObject());
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return bindings != null ? bindings.size() : 0;
    }

    public static class BindingViewHolder extends RecyclerView.ViewHolder {
        public BindingViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return DataBindingUtil.getBinding(itemView);
        }
    }
}
