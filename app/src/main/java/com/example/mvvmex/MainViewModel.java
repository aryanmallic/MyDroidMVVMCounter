package com.example.mvvmex;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel implements LifecycleObserver {

    private static String TAG = MainViewModel.class.getSimpleName();
    private Thread counterThread;
    private int count = 0;
    private boolean isCounterProgress = false;

    private MutableLiveData<Integer> counter;

    public MainViewModel() {

        counter = new MutableLiveData<Integer>();

        counterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isCounterProgress) {
                    try {
                        Thread.sleep(1000);
                        count++;
                        Log.d("TAG", "counter: " + count);
                        counter.postValue(count);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public LiveData<Integer> getCounter() {
        return counter;
    }

    public void startCounter() {
        if (!isCounterProgress) {
            isCounterProgress = true;
            counterThread.start();
        }
    }

    public void stopCounter() {
        isCounterProgress = false;

    }

    //To perform action on lifecycle events
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void HitEventOnResume() {
        Log.d(TAG, "Lifecycle : Event called when onResume is called");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void HitEventOnStart() {
        Log.d(TAG, "Lifecycle : Event called when onStart is called");
    }

}
