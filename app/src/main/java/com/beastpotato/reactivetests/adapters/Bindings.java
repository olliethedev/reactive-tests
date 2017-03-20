package com.beastpotato.reactivetests.adapters;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Oleksiy on 3/19/2017.
 */

public class Bindings {
    @BindingAdapter({"bind:itemBindings", "bind:layoutManager"})
    public static void showItemsList(RecyclerView recyclerView, List itemBindings, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new BindingRecyclerViewAdapter(itemBindings));
    }
}
