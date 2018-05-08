package net.lvtushiguang.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lvtushiguang.widget.R;
import net.lvtushiguang.widget.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称 widget
 * 创建人 梧桐树
 * 创建时间  2018/5/8 15:20
 * 功能描述：底部dialog
 */

public class BottomDialog extends Dialog {
    private Context mContext;
    private List<String> mDatas = new ArrayList<>();
    private BottomAdapter mAdapter;

    private RelativeLayout mRlHeader;
    private TextView mTitle;
    private boolean isShowHeader = true;
    private RecyclerView mRecyclerView;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    private OnItemClickListener onItemClickListener;

    public BottomDialog(@NonNull Context context) {
        this(context, R.style.LoadingDialogStyle);
    }

    public BottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.bottom_dialog, null);

        mRlHeader = view.findViewById(R.id.rl_header);
        mTitle = view.findViewById(R.id.tv_title);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        Button btnCancle = view.findViewById(R.id.btn_cancle);

        mAdapter = new BottomAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, mContext.getResources().getColor(R.color.line)));

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //点击屏幕空白处dialog消失
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        //设置在底部显示
        getWindow().setGravity(Gravity.BOTTOM);

        setContentView(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setShowHeader(boolean isShowHeader) {
        this.isShowHeader = isShowHeader;
        if (isShowHeader) {
            mRlHeader.setVisibility(View.VISIBLE);
            mRecyclerView.setBackgroundResource(R.drawable.btm_dialog_under_border);
        } else {
            mRlHeader.setVisibility(View.GONE);
            mRecyclerView.setBackgroundResource(R.drawable.btm_dialog_border);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setData(List<String> datas) {
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    public void setShowTitle(boolean isShowTitle) {
        if (!isShowTitle) {
            mRlHeader.setVisibility(View.GONE);
        }
    }

    public class BottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(v);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_dialog, null);
            RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(view) {
            };
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position == 0 && !isShowHeader) {//头部
                holder.itemView.setBackgroundResource(R.drawable.btm_dialog_on_selector);
            } else if (position == mDatas.size() - 1) {//尾部
                holder.itemView.setBackgroundResource(R.drawable.btm_dialog_under_selector);
            } else {//中间
                holder.itemView.setBackgroundResource(R.drawable.btm_dialog_selector);
            }
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);

            TextView mTvName = holder.itemView.findViewById(R.id.tv_name);
            mTvName.setText(mDatas.get(position));
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }
}
