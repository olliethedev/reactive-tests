package com.beastpotato.reactivetests;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.beastpotato.reactivetests.databinding.FragmentBottomsheetMainBinding;
import com.beastpotato.reactivetests.events.RXBus;

/**
 * Created by Oleksiy on 3/18/2017.
 */

public class MainBottomSheetFragment extends Fragment {
    private FragmentBottomsheetMainBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomsheet_main, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    private void setListeners() {
        mBinding.searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                RXBus.getInstance().post(new TouchEvent());
                return false;
            }
        });
    }

    public class TouchEvent {
    }
}
