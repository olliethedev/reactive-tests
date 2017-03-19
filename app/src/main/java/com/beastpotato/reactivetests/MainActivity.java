package com.beastpotato.reactivetests;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.beastpotato.reactivetests.databinding.ActivityMainBinding;
import com.beastpotato.reactivetests.events.RXBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    ActivityMainBinding mBinding;
    private Disposable mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_bottom_sheet, new MainBottomSheetFragment())
                .commit();
        setListeners();
    }

    private void setListeners() {
        RXBus.getInstance().subscribe().filter(o -> o instanceof MainBottomSheetFragment.TouchEvent).subscribe(o -> BottomSheetBehavior.from(mBinding.mainBottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED));
        BottomSheetBehavior.from(mBinding.mainBottomSheet).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(getCurrentFocus(), 0);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.homeLoader.show();
        mSubscription = mApiService.getConfig(BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(configuration -> {
                    mBinding.homeTextView.setText(configuration.toString());
                    mBinding.homeLoader.hide();
                }, throwable -> {
                    Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    mBinding.homeLoader.hide();
                });
    }

    @Override
    protected void onPause() {
        mSubscription.dispose();
        super.onPause();
    }
}
