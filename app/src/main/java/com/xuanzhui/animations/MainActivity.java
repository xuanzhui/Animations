package com.xuanzhui.animations;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xuanzhui.animations.fragment.FlipCardAnimView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void flipAnim(View v) {
        FlipCardAnimView flipCardAnimView = new FlipCardAnimView();
        flipCardAnimView.showNow(getSupportFragmentManager(), "flipCardAnimView");
    }
}
