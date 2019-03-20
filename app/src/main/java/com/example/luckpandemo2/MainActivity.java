package com.example.luckpandemo2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import tech.oom.luckpan.LuckBean;
import tech.oom.luckpan.LuckItemInfo;
import tech.oom.luckpan.NewLuckView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NewLuckView luckView;
    private String[] strs;
    private Integer[] images;
    private Button bt_winning_record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        luckView = (NewLuckView) findViewById(R.id.luck_view);
        bt_winning_record = findViewById(R.id.bt_winning_record);
        bt_winning_record.setOnClickListener(this);

        luckView.setIndicatorResourceId(R.mipmap.node);
        ArrayList<LuckItemInfo> items = new ArrayList<>();
        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        strs = new String[]{"1", "2","3","4","5","6","7","8"};
        images = new Integer[]{R.mipmap.other,R.mipmap.sports,R.mipmap.other,R.mipmap.sports,R.mipmap.other,R.mipmap.sports,R.mipmap.other,R.mipmap.sports};
        for (int i = 0; i < 8; i++) {
            LuckItemInfo luckItem = new LuckItemInfo();
            luckItem.prize_name = strs[i];
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images[i]);
            bitmaps.add(bitmap);
            items.add(luckItem);
        }

        LuckBean luck = new LuckBean();
        luck.details = items;
//        load数据
        luckView.loadData(luck, bitmaps);
//        luckView.setEnable(false); 设置是否可用 默认为true
        //添加监听 当luckview检测到indicator所在位置被点击时，会自动开始旋转
        luckView.setLuckViewListener(new NewLuckView.LuckViewListener() {
            @Override
            public void onStart() {

                //模拟网络请求获取抽奖结果，然后设置选中项的index值
                luckView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        int i = random.nextInt(6);
                        luckView.setStop(i);
                    }
                }, 3000);
            }

            @Override
            public void onStop(int index) {

                Toast.makeText(MainActivity.this, "敬请期待！", Toast.LENGTH_SHORT).show();

            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_winning_record:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);
                View view = View.inflate(getApplication(),R.layout.dialog_winning_record,null);
                builder.setView(view);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.create();
                dialog.show();
                ImageView close = view.findViewById(R.id.close_view);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



                break;
        }
    }
}
