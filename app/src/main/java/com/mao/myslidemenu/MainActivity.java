package com.mao.myslidemenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mao.myslidemenu.utils.Constant;
import com.mao.myslidemenu.view.MyLinerLayout;
import com.mao.myslidemenu.view.MySlideMenu;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class MainActivity extends AppCompatActivity {


    private ListView menu_listview;
    private ListView main_listview;

    private MySlideMenu mySlideMenu;

    private MyLinerLayout myLinerLayout;

    private ImageView iv_head;//主界面左上角图标对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLinerLayout= (MyLinerLayout) findViewById(R.id.my_layout);
        menu_listview= (ListView) findViewById(R.id.menu_listview);
        main_listview= (ListView) findViewById(R.id.main_listview);
        mySlideMenu= (MySlideMenu) findViewById(R.id.myslidemenu);
        iv_head= (ImageView) findViewById(R.id.iv_head);
        myLinerLayout.setSlideMenu(mySlideMenu);


        menu_listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Constant.sCheeseStrings){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView= (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });
        main_listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Constant.NAMES){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView= (TextView) super.getView(position, convertView, parent);
                return textView;
            }
        });

        mySlideMenu.setOnDragStateChangeListener(new MySlideMenu.onDragStateChangeListener() {
            @Override
            public void onOpen() {
                Log.w("毛麒添","侧滑面板打开了");
            }
            @Override
            public void onClose() {
                Log.w("毛麒添","侧滑面板关闭了");
                //侧换面板关闭让左上角的小图片抖动
                ViewPropertyAnimator.animate(iv_head).translationX(15)
                        .setInterpolator(new CycleInterpolator(4))//左右抖动四次
                        .setDuration(300)
                        .start();
            }
            @Override
            public void Draging(float fraction) {
                Log.w("毛麒添","侧滑正在滑动"+fraction);
                //拖动过程中让图标渐变消失
                ViewHelper.setAlpha(iv_head,1-fraction);
            }
        });
        //点击头部图标事件处理
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mySlideMenu.getDragState()== MySlideMenu.DragState.STATE_OPEN){
                    mySlideMenu.close();
                }else if(mySlideMenu.getDragState()== MySlideMenu.DragState.STATE_CLOSE){
                    mySlideMenu.open();
                }
            }
        });
    }
}
