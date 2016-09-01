package com.fairytale110.demo.studio21application.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.fairytale110.demo.studio21application.R;
import com.fairytale110.demo.studio21application.Widget.HoneycombVView;

/**
 * Created by   fairytale110
 * Creat date   2016/8/22 16:14
 * Copy Right   www.xkyiliao.com
 * function
 */
public class HoneycombActivity extends Activity {
    HoneycombVView honeycombVView;
    String[] tittles = new String[]{"优惠活动", "技术文档", "供求信息", "新闻", "商品查询", "广告", "公司简介"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.honeycombactivity);

        honeycombVView = (HoneycombVView) findViewById(R.id.honeycombVView_main);
        honeycombVView.setOnActionListener(new HoneycombVView.ActionListener() {
            @Override
            public void ActionListener(int actionIndex) {
                String actionStr = actionIndex >= 0 ? tittles[actionIndex] : "空白区域";
                Toast.makeText(HoneycombActivity.this, "点击了 " + actionStr, Toast.LENGTH_SHORT).show();

                switch (actionIndex) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
