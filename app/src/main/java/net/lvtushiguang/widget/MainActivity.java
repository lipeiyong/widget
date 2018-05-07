package net.lvtushiguang.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.lvtushiguang.widget.dialog.LoadingDailog;
import net.lvtushiguang.widget.intro.SlipPageIntroView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LoadingDailog mDialog;

    private SlipPageIntroView mIntro;
    private int[] intros = new int[]{R.drawable.guide_control_equ, R.drawable.guide_diningroom_3fuc, R.drawable.guide_diningroom_longpress};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntro = findViewById(R.id.intro);
        mIntro.setDate(intros);
        mIntro.setOnClickNextListener(view -> mIntro.setVisibility(View.GONE));
        //
    }


}
