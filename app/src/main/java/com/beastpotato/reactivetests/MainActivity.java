package com.beastpotato.reactivetests;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.beastpotato.reactivetests.databinding.ActivityMainBinding;
import com.beastpotato.reactivetests.endpoints.Api;
import com.beastpotato.reactivetests.events.RXBus;
import com.beastpotato.reactivetests.models.MoviesData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private Api mApiService = Api.getInstance();
    private Disposable mSubscription;
    public ObservableField<GridLayoutManager> movieLayoutManger = new ObservableField<>();
    public ObservableField<List> movieListBindings = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_bottom_sheet, new MainBottomSheetFragment())
                .commit();
        setListeners();
        loadConfig();
    }

    private void loadConfig() {
        mBinding.homeLoader.setVisibility(View.VISIBLE);
        mSubscription = mApiService.getService().getConfig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(configuration -> {
                    //mBinding.homeTextView.setText(configuration.toString());
                    mBinding.homeLoader.setVisibility(View.GONE);
                    mBinding.homeBottomNav.setSelectedItemId(R.id.action_popular);
                }, throwable -> {
                    Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    mBinding.homeLoader.setVisibility(View.GONE);
                });
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
        mBinding.homeBottomNav.setOnNavigationItemSelectedListener(item -> {
            mBinding.homeLoader.setVisibility(View.VISIBLE);
            switch (item.getItemId()) {
                case R.id.action_popular:
                    mSubscription = attachLogic(mApiService.getService().getPopular());
                case R.id.action_now_playing:
                    mSubscription = attachLogic(mApiService.getService().getCurrent());

                case R.id.action_coming_soon:
                    mSubscription = attachLogic(mApiService.getService().getUpcoming());

            }
            return true;
        });
    }

    private Disposable attachLogic(Observable<MoviesData> data) {
        return data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moviesData -> {
                    //mBinding.homeTextView.setText(moviesData.getResults().toString());
                    mBinding.setActivity(this);
                    mBinding.homeLoader.setVisibility(View.GONE);
                }, throwable -> {
                    Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    mBinding.homeLoader.setVisibility(View.GONE);
                });
    }

    @Override
    public void onBackPressed() {
        if (BottomSheetBehavior.from(mBinding.mainBottomSheet).getState() == BottomSheetBehavior.STATE_EXPANDED) {
            BottomSheetBehavior.from(mBinding.mainBottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.dispose();
    }
}
