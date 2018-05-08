package net.lvtushiguang.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import net.lvtushiguang.widget.dialog.BottomDialog;
import net.lvtushiguang.widget.dialog.LoadingDailog;
import net.lvtushiguang.widget.intro.SlipPageIntroView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LoadingDailog mDialog;

    private SlipPageIntroView mIntro;
    private int[] intros = new int[]{R.drawable.guide_control_equ, R.drawable.guide_diningroom_3fuc, R.drawable.guide_diningroom_longpress};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mIntro = findViewById(R.id.intro);
//        mIntro.setDate(intros);
//        mIntro.setOnClickNextListener(new SlipPageIntroView.OnClickNextListener() {
//            @Override
//            public void onClick(View view) {
//                mIntro.setVisibility(View.GONE);
//            }
//        });

        List<String> lists = new ArrayList<>(2);
        lists.add(0, "标题一");
        lists.add(1, "标题二");
        BottomDialog dialog = new BottomDialog(this);
        dialog.setTitle("标题");
        dialog.setData(lists);
        dialog.show();
        dialog.setOnItemClickListener(new BottomDialog.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Log.i(TAG, "onItemClick: " + view.getTag());
            }
        });
    }


}
