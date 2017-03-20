package com.beastpotato.reactivetests;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beastpotato.reactivetests.databinding.FragmentBottomsheetMainBinding;
import com.beastpotato.reactivetests.endpoints.Api;
import com.beastpotato.reactivetests.events.RXBus;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleksiy on 3/18/2017.
 */

public class MainBottomSheetFragment extends Fragment {
    private FragmentBottomsheetMainBinding mBinding;
    private Api mApiService = Api.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottomsheet_main, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    private void setListeners() {
        mBinding.searchText.setOnTouchListener((view, motionEvent) -> {
            RXBus.getInstance().post(new TouchEvent());
            return false;
        });
        RxTextView.textChangeEvents(mBinding.searchText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(text -> !TextUtils.isEmpty(text.text()))
                .switchMap(in -> {
                    mBinding.searchLoader.setVisibility(View.VISIBLE);
                    return mApiService.getService().search(in.text().toString());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        searchData -> {
                            mBinding.searchOut.setText(searchData.toString());
                            mBinding.searchLoader.setVisibility(View.GONE);
                        },
                        error -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public class TouchEvent {
    }
}
