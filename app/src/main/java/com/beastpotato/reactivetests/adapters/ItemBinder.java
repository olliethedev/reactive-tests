package com.beastpotato.reactivetests.adapters;

import android.support.annotation.LayoutRes;

/**
 * Created by Oleksiy on 3/19/2017.
 */

public class ItemBinder {
    private int layoutItemId, boundVariableId;
    private Object boundObject;

    public ItemBinder(@LayoutRes int layoutItemId, int bindId, Object boundObject) {
        this.layoutItemId = layoutItemId;
        this.boundVariableId = bindId;
        this.boundObject = boundObject;
    }

    public int getLayoutItemId() {
        return layoutItemId;
    }

    public int getBoundVariableId() {
        return boundVariableId;
    }

    public Object getBoundObject() {
        return boundObject;
    }
}
