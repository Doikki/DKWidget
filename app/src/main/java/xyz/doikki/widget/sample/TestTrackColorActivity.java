package xyz.doikki.widget.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.widget.tab.TabView;

public class TestTrackColorActivity extends AppCompatActivity {

    private List<String> strs = new ArrayList<>();

    private LinearLayout mContainer;

    private HorizontalScrollView mScroll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_text_color);

        mScroll = findViewById(R.id.scroll);
        mContainer = findViewById(R.id.container);
        TabView colorTrackTextView = (TabView) mContainer.getChildAt(0);
        colorTrackTextView.startTrack(1f, true);
        ViewPager viewPager = findViewById(R.id.vp);

        strs.add("天气");
        strs.add("深圳深圳");
        strs.add("天气");
        strs.add("深圳");
        strs.add("天气天气");
        strs.add("深圳");
        strs.add("天气");
        strs.add("深圳");
        strs.add("天气");
        strs.add("深圳");

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return strs.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView textView = new TextView(container.getContext());
                textView.setText(strs.get(position));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                container.addView(textView);
                return textView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0) return;
                TabView selected = (TabView) mContainer.getChildAt(position);
                TabView next = (TabView) mContainer.getChildAt(position + 1);
                selected.startTrack(positionOffset, false);
                next.startTrack(positionOffset, true);

                int selectedWidth = selected.getWidth();
                int nextWidth = next.getWidth();
                int scrollBase = selected.getLeft() + (selectedWidth / 2) - (mScroll.getWidth() / 2);
                int scrollOffset = (int) ((selectedWidth + nextWidth) * 0.5f * positionOffset);
                mScroll.scrollTo(scrollOffset + scrollBase, 0);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
