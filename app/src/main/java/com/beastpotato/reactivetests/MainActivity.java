package com.beastpotato.reactivetests;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.beastpotato.reactivetests.databinding.ActivityMainBinding;

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
