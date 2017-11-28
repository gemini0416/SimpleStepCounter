package com.spring.stepcounter.simplestepcounter.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spring.stepcounter.simplestepcounter.view.MainActivity;
import com.spring.stepcounter.simplestepcounter.R;


/**
 * 查看一周内每天的历史纪录日历Item布局
 */

public class RecordsCalenderItemView extends RelativeLayout {
    private static final String TAG = "RecordsCalenderItemView";

    private Context mContext;

    private LinearLayout itemLl;
    private View lineView;
    private TextView weekTv;
    private RelativeLayout dateRl;
    private TextView dateTv;
    //日期时间
    private String weekStr, dateStr;
    private int position;
    private int lastX;

    //当前item 的时间,如2017年02月07日,用以判断当前item是否可以被点击
    protected String curItemDate;


    OnCalenderItemClick itemClick = null;

    OnScrollViewListener scrollViewListener = null;
    private int offsetX;

    public interface OnCalenderItemClick {
        public void onCalenderItemClick();
    }

    public void setOnCalenderItemClick(OnCalenderItemClick itemClick) {
        this.itemClick = itemClick;
    }


    public RecordsCalenderItemView(Context context, String week, String date, int position, String curItemDate,OnScrollViewListener scrollViewListener) {
        super(context);
        this.mContext = context;
        this.weekStr = week;
        this.dateStr = date;
        this.position = position;
        this.curItemDate = curItemDate;
        this.scrollViewListener = scrollViewListener;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.records_calender_item_view, this);
        itemLl = itemView.findViewById(R.id.records_calender_item_ll);
        weekTv = itemView.findViewById(R.id.records_calender_item_week_tv);
        lineView = itemView.findViewById(R.id.calendar_item_line_view);
        dateRl = itemView.findViewById(R.id.records_calender_item_date_rl);
        dateTv = itemView.findViewById(R.id.records_calender_item_date_tv);

        weekTv.setTextSize(15);
        lineView.setVisibility(GONE);

        weekTv.setText(weekStr);
        dateTv.setText(dateStr);

        itemView.setLayoutParams(new LayoutParams((MainActivity.screenWidth) / 7,
                ViewGroup.LayoutParams.MATCH_PARENT));

        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onCalenderItemClick();
            }
        });

    }

    //返回当前项的id
    public int getPosition() {
        return position;
    }

    public void setChecked(boolean checkedFlag) {

        if (checkedFlag) {
            //当前item被选中后样式
            weekTv.setTextColor(getResources().getColor(R.color.main_text_color));
            setDateColor(getResources().getColor(R.color.white));
            dateRl.setBackgroundResource(R.mipmap.ic_blue_round_bg);
        } else {
            //当前item未被选中样式
            weekTv.setTextColor(getResources().getColor(R.color.black));
            setDateColor(getResources().getColor(R.color.black));
            //设置背景透明
            dateRl.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setDateColor(int color){
        dateTv.setTextColor(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                break;

            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                offsetX = x - lastX;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("test1","offsetX:"+ offsetX);
                if (offsetX >200&&scrollViewListener!=null){
                    scrollViewListener.onScrollLeftViewListener();
                }else if (offsetX <-200&&scrollViewListener!= null){
                    scrollViewListener.onScrollRightViewListener();
                }

        }
        return super.onTouchEvent(event);
    }
}
