package com.example.mvvmex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private TextView tvCounter;
    private Button btStart, btStop;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class); // .AndroidViewModelFactory(getApplication()).create(MainViewModel.class);

        getLifecycle().addObserver(viewModel);
        //this trigger the life cycle event is called in ViewModel
        //this class is the LifecycleOwner and the ViewModel is LifecycleObserver
        //LifecycleOwner - those class which uses viewModel objects


        initiateComponent();

        viewModel.getCounter().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvCounter.setText(String.valueOf(integer));
            }
        });
    }

    private void initiateComponent() {
        btStart.setOnClickListener((View view) -> {
            viewModel.startCounter();
        });

        btStop.setOnClickListener((View view) -> {
            viewModel.stopCounter();
        });
    }

    private void setView() {
        tvCounter = findViewById(R.id.tvCounter);
        btStart = findViewById(R.id.btStart);
        btStop = findViewById(R.id.btStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
