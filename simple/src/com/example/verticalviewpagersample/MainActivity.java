package com.example.verticalviewpagersample;

import java.util.ArrayList;
import java.util.List;

import com.view.vertical.VerticalViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * 
 * 
 * @author landsnail---hongyun.fang
 * @email fanghongyun@gmail.com
 * 
 */

public class MainActivity extends Activity {

	VerticalViewPager verticalViewPager;
	List<View> viewPageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		// /
		verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalViewPager);

		viewPageList = new ArrayList<View>();
		View cell_top = getLayoutInflater().inflate(R.layout.cell_top, null);
		viewPageList.add(cell_top);

		View cell_bottom = getLayoutInflater().inflate(R.layout.cell_bottom,
				null);
		viewPageList.add(cell_bottom);

		WebView web = (WebView) cell_bottom.findViewById(R.id.web_content);
		//设置WebView属性，能够执行Javascript脚本 
		web.getSettings().setJavaScriptEnabled(true); 
		web.loadUrl("http://www.baidu.com");

		// //初始化控件数据
		verticalViewPager.setAdapter(new com.view.vertical.PagerAdapter() {
			@Override
			public int getCount() {
				return viewPageList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				super.destroyItem(container, position, object);
				container.removeView(viewPageList.get(position
						% viewPageList.size()));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewPageList.get(position
						% viewPageList.size()));
				return viewPageList.get(position);
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}
		});
		verticalViewPager.setCurrentItem(0);
	}
}
