package com.spring.stepcounter.simplestepcounter.calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spring.stepcounter.simplestepcounter.R;
import com.spring.stepcounter.simplestepcounter.utils.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 查看当前日期前七天或后七天日历
 */

public class BeforeOrAfterCalendarView extends RelativeLayout implements OnScrollViewListener{

    private List<Integer> dayList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();

    protected List<RecordsCalenderItemView> itemViewList = new ArrayList<>();
    protected Context mContext;
    protected LinearLayout calenderViewLl;
    private TextView calenderText;
    protected int curPosition=3;

    public BeforeOrAfterCalendarView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.before_or_after_calendar_layout, this);

        calenderViewLl = view.findViewById(R.id.boa_calender_view_ll);
        calenderText = view.findViewById(R.id.boa_calender_text);

        setBeforeDateViews(TimeUtil.getCurrentDate());

        initItemViews();
        switchPositionView(curPosition);
    }

    /**
     * 设置之前的日期显示
     */
    private void setBeforeDateViews(String time) {
        //获取日期列表
        dateList.clear();
        dayList.clear();
        dateList.addAll(TimeUtil.getBeforeDateListByNow(time));
        dayList.addAll(TimeUtil.dateListToDayList(dateList));
    }

    private void initItemViews() {
        calenderViewLl.removeAllViews();
        itemViewList.clear();
        for (int i = 0; i < dateList.size(); i++) {
            int day = dayList.get(i);
            String curItemDate = dateList.get(i);
            final RecordsCalenderItemView itemView;
            if(curItemDate.equals(TimeUtil.getCurrentDate())){
                itemView = new RecordsCalenderItemView(mContext, "今天", String.valueOf(day), i, curItemDate,this);
            }else{
                itemView = new RecordsCalenderItemView(mContext, TimeUtil.getCurWeekDay(curItemDate), String.valueOf(day), i, curItemDate,this);
            }

            itemViewList.add(itemView);
            calenderViewLl.addView(itemView);

            itemView.setOnCalenderItemClick(new RecordsCalenderItemView.OnCalenderItemClick() {
                @Override
                public void onCalenderItemClick() {
                    curPosition = itemView.getPosition();
                    switchPositionView(curPosition);

                    //点击事件
                    if (calenderClickListener != null) {
                        calenderClickListener.onClickToRefresh(curPosition,dateList.get(curPosition));
                    }
                }
            });
        }
    }

    private void switchPositionView(int position) {
        for (int i = 0; i < itemViewList.size(); i++) {
            if (position == i) {
                itemViewList.get(i).setChecked(true);
                calenderText.setText(itemViewList.get(i).curItemDate);
            } else {
                itemViewList.get(i).setChecked(false);
                if (i==0||i==itemViewList.size()-1) {
                    itemViewList.get(i).setDateColor(getResources().getColor(R.color.gray_default_dark));
                }
            }
        }
    }

    private BoaCalenderClickListener calenderClickListener;

    @Override
    public void onScrollLeftViewListener() {
        setBeforeDateViews(TimeUtil.getPastTime(dateList.get(dateList.size() / 2),-7));
        initItemViews();
        switchPositionView(curPosition);
        if (calenderClickListener != null) {
            calenderClickListener.onClickToRefresh(curPosition,dateList.get(curPosition));
        }

    }

    @Override
    public void onScrollRightViewListener() {
        setBeforeDateViews(TimeUtil.getPastTime(dateList.get(dateList.size() / 2),7));
        initItemViews();
        switchPositionView(curPosition);
        if (calenderClickListener != null) {
            calenderClickListener.onClickToRefresh(curPosition,dateList.get(curPosition));
        }
    }

    public interface BoaCalenderClickListener {
        void onClickToRefresh(int position, String curDate);
    }

    public void setOnBoaCalenderClickListener(BoaCalenderClickListener calenderClickListener) {
        this.calenderClickListener = calenderClickListener;
    }
}
