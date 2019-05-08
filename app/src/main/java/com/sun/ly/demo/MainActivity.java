package com.sun.ly.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.sun.lib.photo.select.EasyPhotos;
import com.sun.ly.baselib.image.GlideEngine;
import com.sun.ly.demo.anim.KickBackAnimator;
import com.sun.view.weight.weight.constant.NaAnim;
import com.sun.view.weight.weight.listener.OnTabClickListener;
import com.sun.view.weight.weight.utils.NaUtil;
import com.sun.view.weight.weight.view.BottomNavigationBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * 入口
 * @author cool
 */
public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragments;

    private BottomNavigationBar navigationBar;

    private String[] tabText = {"首页", "消息", "", "定位", "我的"};

    private int[] normalIcon = {R.drawable.home, R.drawable.message, R.drawable.add_hover,
            R.drawable.location, R.drawable.mine};

    private int[] selectIcon = {R.drawable.home_hover, R.drawable.message_hover,
            R.drawable.add_hover, R.drawable.location_hover, R.drawable.mine_hover};

    private int[] menuIconItems = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4};
    private String[] menuTextItems = {"文字", "拍摄", "相册", "直播"};

    private LinearLayout menuLayout;
    private View cancelImageView;
    private Handler mHandler = new Handler();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

    }

    private void initFragment(){
        fragments = new ArrayList<>();
        Fragment trackFragment = CC.obtainBuilder("TrackComponent")
                .build().call()
                .getDataItem("key");
        if (trackFragment != null) {
            fragments.add(trackFragment);
        }
        Fragment messageFragment = CC.obtainBuilder("MessageComponent")
                .build().call().getDataItem("key");
        if (messageFragment != null) {
            fragments.add(messageFragment);
        }
        Fragment locationFragment = CC.obtainBuilder("LocationComponent")
                .build().call().getDataItem("key");
        if (locationFragment != null) {
            fragments.add(locationFragment);
        }
        Fragment mineFragment = CC.obtainBuilder("MineComponent")
                .build().call().getDataItem("key");
        if (mineFragment != null) {
            fragments.add(mineFragment);
        }

        initView();
    }

    private IComponentCallback fragmentCallback = new IComponentCallback() {
        private int position;



        @Override
        public void onResult(CC cc, CCResult result) {

        }
    };

    private void initView(){
        navigationBar = findViewById(R.id.main_navigation);

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .addLayoutRule(BottomNavigationBar.RULE_CENTER)
                .addLayoutBottom(50)
                .onTabClickListener(new OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        if (position == 2){
                            showMenu();
                        }
                        return false;
                    }
                }).mode(BottomNavigationBar.MODE_ADD)
                .anim(NaAnim.Pulse)
                .build();

        navigationBar.setAddViewLayout(createWeiboView());
    }

    private void showMenu() {
        startAnimation();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //＋ 旋转动画
                cancelImageView.animate().rotation(90).setDuration(400);
            }
        });
        //菜单项弹出动画
        for (int i = 0; i < menuLayout.getChildCount(); i++) {
            final View child = menuLayout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    fadeAnim.setDuration(500);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(500);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            }, i * 50 + 100);
        }
    }

    private void startAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //圆形扩展的动画
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        int x = NaUtil.getScreenWidth(MainActivity.this) / 2;
                        int y = (NaUtil.getScreenHeith(MainActivity.this) - NaUtil.dip2px(MainActivity.this, 25));
                        Animator animator = ViewAnimationUtils.createCircularReveal(navigationBar.getAddViewLayout(), x,
                                y, 0, navigationBar.getAddViewLayout().getHeight());
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                navigationBar.getAddViewLayout().setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }
                        });
                        animator.setDuration(300);
                        animator.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private View createWeiboView() {
        ViewGroup view = (ViewGroup) View.inflate(this, R.layout.layout_add_view, null);
        menuLayout = view.findViewById(R.id.icon_group);
        cancelImageView = view.findViewById(R.id.cancel_iv);
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation();
            }
        });
        for (int i = 0; i < 4; i++) {
            View itemView = View.inflate(this, R.layout.item_icon, null);
            ImageView menuImage = itemView.findViewById(R.id.menu_icon_iv);
            TextView menuText = itemView.findViewById(R.id.menu_text_tv);

            menuImage.setImageResource(menuIconItems[i]);
            menuText.setText(menuTextItems[i]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            itemView.setLayoutParams(params);
            itemView.setVisibility(View.GONE);
            itemView.setOnClickListener(new OnAddTabItemClickListener(i));
            menuLayout.addView(itemView);
        }
        return view;
    }

    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cancelImageView.animate().rotation(0).setDuration(400);
            }
        });

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                int x = NaUtil.getScreenWidth(this) / 2;
                int y = (NaUtil.getScreenHeith(this) - NaUtil.dip2px(this, 25));
                Animator animator = ViewAnimationUtils.createCircularReveal(navigationBar.getAddViewLayout(), x,
                        y, navigationBar.getAddViewLayout().getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //							layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        navigationBar.getAddViewLayout().setVisibility(View.GONE);
                        //dismiss();
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        } catch (Exception e) {
        }
    }

    public class OnAddTabItemClickListener implements View.OnClickListener {
        private int position;

        public OnAddTabItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (position){
                case 0:
                    Toast.makeText(MainActivity.this, "文字", Toast.LENGTH_SHORT).show();
                    closeAnimation();
                    break;
                case 1:
                    EasyPhotos.createCamera(MainActivity.this)
                            .setFileProviderAuthority("com.sun.ly.demo.provider")
                            .start(102);
                    break;
                case 2:
                    EasyPhotos.createAlbum(MainActivity.this, true, GlideEngine.getInstance())
                            .setFileProviderAuthority("com.sun.ly.demo.provider")
                            .start(101);
                    break;
                case 3:
                    Toast.makeText(MainActivity.this, "直播", Toast.LENGTH_SHORT).show();
                    closeAnimation();
                    break;
                default:
                    break;
            }
        }
    }

    private void init(){
        CCResult result = CC.obtainBuilder("HomeComponent")
                .setActionName("Utils")
                .build()
                .call();

        String i = result.getDataItem("str", "-1");

        textView = findViewById(R.id.text);
        textView.setText(i);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC.obtainBuilder("HomeComponent")
                        .setActionName("openActivity")
                        .build()
                        .callAsyncCallbackOnMainThread(new IComponentCallback() {
                            @Override
                            public void onResult(CC cc, CCResult result) {
                                String str = result.getDataItem("back_str", "-100");
                                textView.setText(str);
                            }
                        });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                String imgUrl = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS).get(0);
                Toast.makeText(MainActivity.this,imgUrl, Toast.LENGTH_SHORT).show();
                closeAnimation();
            }
        }else if (requestCode == 102){
            String imgUrl = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS).get(0);
            Toast.makeText(MainActivity.this,imgUrl, Toast.LENGTH_SHORT).show();
            closeAnimation();
        }
    }
}
