package net.lvtushiguang.widget.intro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.lvtushiguang.widget.R;
import net.lvtushiguang.widget.bitmap.DecodeBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称 widget
 * 创建人 梧桐树
 * 创建时间  2018/5/4 10:37
 * 功能描述：滑页式介绍
 */

public class SlipPageIntroView extends RelativeLayout {

    private ViewPager mViewPager;
    private Button mBtnNext;
    private RelativeLayout mRlDots;
    private LinearLayout mLLIn;
    private ImageView mRedDot;

    private List<View> mList = new ArrayList<>();
    private ViewPagerAdapter mAdapter;

    private Context mContext;
    private boolean isShowDot = true;

    private int mDistance;

    public interface OnClickNextListener {
        void onClick(View view);
    }

    private OnClickNextListener onClickNextListener;

    public SlipPageIntroView(Context context) {
        this(context, null);
    }

    public SlipPageIntroView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_slip_page_intro, this, true);
        mViewPager = findViewById(R.id.viewpager);
        mBtnNext = findViewById(R.id.btn_next);
        mRlDots = findViewById(R.id.rl_dots);
        mLLIn = findViewById(R.id.ll_in);
        mRedDot = findViewById(R.id.img_red_dot);

        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);

        mBtnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onClickNextListener) {
                    onClickNextListener.onClick(view);
                }
            }
        });
    }

    public void setOnClickNextListener(OnClickNextListener listener) {
        onClickNextListener = listener;
    }

    private void moveDots() {
        mRedDot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = mLLIn.getChildAt(1).getLeft() - mLLIn.getChildAt(0).getLeft();
                mRedDot.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                float leftMargin = mDistance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedDot.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mRedDot.setLayoutParams(params);
                if (position == mList.size() - 1) {
                    mBtnNext.setVisibility(View.VISIBLE);
                }
                if (position != mList.size() - 1 && mBtnNext.getVisibility() == View.VISIBLE) {
                    mBtnNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                float leftMargin = mDistance * position;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedDot.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mRedDot.setLayoutParams(params);
                if (position == mList.size() - 1) {
                    mBtnNext.setVisibility(View.VISIBLE);
                }
                if (position != mList.size() - 1 && mBtnNext.getVisibility() == View.VISIBLE) {
                    mBtnNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setDate(@NonNull List<Integer> resources) {
        for (int resource : resources) {
            LayoutInflater lf = LayoutInflater.from(mContext);
            View view = lf.inflate(R.layout.item_slippage_intro, null);
            ImageView img = view.findViewById(R.id.img_src);
            img.setImageResource(resource);
            img.setImageBitmap(DecodeBitmap.decodeSampledBitmapFromResource(getResources(),
                    resource, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels));

            mList.add(view);
            addDots();
        }
        mAdapter.notifyDataSetChanged();

        if (resources.size() >= 2)
            moveDots();
        else {
            mBtnNext.setVisibility(View.VISIBLE);
        }
    }

    public void setDate(@NonNull int[] resources) {
        for (int resource : resources) {
            LayoutInflater lf = LayoutInflater.from(mContext);
            View view = lf.inflate(R.layout.item_slippage_intro, null);
            ImageView img = view.findViewById(R.id.img_src);
            img.setImageResource(resource);
            img.setImageBitmap(DecodeBitmap.decodeSampledBitmapFromResource(getResources(),
                    resource, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels));

            mList.add(view);
            addDots();
        }
        mAdapter.notifyDataSetChanged();
        if (resources.length >= 2)
            moveDots();
        else {
            mBtnNext.setVisibility(View.VISIBLE);
        }
    }

    public void setShowDot(boolean type) {
        isShowDot = type;
        if (isShowDot) {
            mRlDots.setVisibility(View.VISIBLE);
        } else {
            mRlDots.setVisibility(View.GONE);
        }
    }

    private void addDots() {
        ImageView img_dot = new ImageView(mContext);
        img_dot.setImageResource(R.drawable.shape_white_dot);
        int size = (int) getResources().getDimension(R.dimen.space_8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(0, 0, 40, 0);
        mLLIn.addView(img_dot, params);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
}
