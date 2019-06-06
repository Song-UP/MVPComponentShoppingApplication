package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.lib.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LotteryRowItem extends LinearLayout{
    private Context context;
    private View view;
    private TextView mTvquan, mTvda, mTvxiao, mTvdan, mTvshuang, mTvqing;
    private TextView mTvUnit;
    private ArrayList<Boolean> chosenItems;
    private List<String> contents;
    private int rowNumber = 6;
    private int mRow;//第几行
    private ArrayList<String> max_odds;
    private int total;
    private int selectTab;

    public LotteryRowItem(Context context) {
        super(context);
    }

    Change change;

    public void setChange(Change change) {
        this.change = change;
    }

    public interface Change{
        void change(int row, int column);
    }

    public void setTotal(int selectTab, List<String> t, ArrayList<Boolean> a, int row, String layout_code, LinkedHashMap max_odds){
        this.selectTab = selectTab;
        total = t.size();
        chosenItems = a;
        contents = t;
        mRow = row;
        if(layout_code != null && layout_code.equals("hezhi_hezhi_hezhi")){
            this.max_odds = new ArrayList();
            for(Object key: max_odds.keySet()){
                this.max_odds.add(max_odds.get(key).toString());
            }
        } else {
            this.max_odds = null;
        }

        addItem();
    }

    public LotteryRowItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        view = LayoutInflater.from(context).inflate(R.layout.lottery_row_item, (ViewGroup) getRootView(), false);
        mTvquan = view.findViewById(R.id.quan);
        mTvda = view.findViewById(R.id.da);
        mTvxiao = view.findViewById(R.id.xiao);
        mTvdan = view.findViewById(R.id.dan);
        mTvshuang = view.findViewById(R.id.shuang);
        mTvqing = view.findViewById(R.id.qing);

        mTvUnit = view.findViewById(R.id.unit);
        addView(view);
    }

    private void resetBg(){
        mTvquan.setBackgroundColor(0);
        mTvquan.setTextColor(context.getResources().getColor(R.color.text_gray));
        mTvda.setBackgroundColor(0);
        mTvda.setTextColor(context.getResources().getColor(R.color.text_gray));
        mTvxiao.setBackgroundColor(0);
        mTvxiao.setTextColor(context.getResources().getColor(R.color.text_gray));
        mTvdan.setBackgroundColor(0);
        mTvdan.setTextColor(context.getResources().getColor(R.color.text_gray));
        mTvshuang.setBackgroundColor(0);
        mTvshuang.setTextColor(context.getResources().getColor(R.color.text_gray));
        mTvqing.setBackgroundColor(0);
        mTvqing.setTextColor(context.getResources().getColor(R.color.text_gray));
    }

    private void setSelectedTab(){
        switch (selectTab){
            case 1:
                mTvquan.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                break;
            case 2:
                mTvda.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                break;
            case 3:
                mTvxiao.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                break;
            case 4:
                mTvdan.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                break;
            case 5:
                mTvshuang.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                break;
            case 6:
                mTvqing.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                break;
        }
    }


    public void hasTools(boolean b){
        LinearLayout wholeRow = view.findViewById(R.id.tools);
        if(b){
           // wholeRow.setVisibility(VISIBLE);
          //  mTvUnit.setBackgroundColor(context.getResources().getColor(R.color.bg_grey));
            mTvquan.setVisibility(VISIBLE);
            mTvda.setVisibility(VISIBLE);
            mTvxiao.setVisibility(VISIBLE);
            mTvdan.setVisibility(VISIBLE);
            mTvshuang.setVisibility(VISIBLE);
            mTvqing.setVisibility(VISIBLE);
        } else {
          //  wholeRow.setVisibility(INVISIBLE);
           /// mTvUnit.setBackgroundColor(0);
            mTvquan.setVisibility(GONE);
            mTvda.setVisibility(GONE);
            mTvxiao.setVisibility(GONE);
            mTvdan.setVisibility(GONE);
            mTvshuang.setVisibility(GONE);
            mTvqing.setVisibility(GONE);
        }
    }

    public void setUnit(String i){
        mTvUnit.setText(i);
    }

    public void resetTabState(){
        resetBg();
        setSelectedTab();
    }

    public void clearContent(){
        LinearLayout wholeRow = view.findViewById(R.id.row_layout);

        wholeRow.removeAllViews();
    }

    private void addItem(){
        try {
            int full = total / rowNumber;
            int extra = total % rowNumber;
            int extraline = (extra == 0 ? 0 : 1);
            int line = full + extraline;
            final ArrayList<ArrayList<LotteryBall>> ballArrayLists = new ArrayList<>();
            ArrayList<ArrayList<String>> oddsArrayLists = null;
            if(max_odds != null){
                oddsArrayLists = new ArrayList<>();
            }
            for(int i = 0; i < line; i++){
                ArrayList<LotteryBall> tempBallArrayList = new ArrayList<>();
                ArrayList<String> oddsArrayList = null;
                if(max_odds != null){
                    oddsArrayList = new ArrayList<>();
                }
                if(i != full) {
                    for (int j = 0; j < rowNumber; j++) {
                        final LotteryBall lotteryBall = new LotteryBall(context);
                        final int index = (j + i * rowNumber);
                        if(index < contents.size()){
                            lotteryBall.setText(contents.get(index));
                        }

                        if(index < chosenItems.size()) {
                            if (chosenItems.get(index).booleanValue()) {
                                lotteryBall.setSelect();
                            } else {
                                lotteryBall.setUnSelect();
                            }
                        }
                        tempBallArrayList.add(lotteryBall);
                        if(max_odds != null){
                            oddsArrayList.add(max_odds.get(index));
                        }
                        lotteryBall.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(lotteryBall.getSelect()){
                                    lotteryBall.setUnSelect();
                                    chosenItems.set(index, false);
                                    change.change(mRow, index);
                                } else {
                                    lotteryBall.setSelect();
                                    chosenItems.set(index, true);
                                    change.change(mRow, index);
                                }
                            }
                        });
                    }
                } else {
                    for(int j = 0; j < extra; j++){
                        final LotteryBall lotteryBall = new LotteryBall(context);
                        final int index = (j + i * rowNumber);
                        lotteryBall.setText(contents.get(index));
                        if(chosenItems.get(index).booleanValue()){
                            lotteryBall.setSelect();
                        } else {
                            lotteryBall.setUnSelect();
                        }
                        tempBallArrayList.add(lotteryBall);
                        if(max_odds != null){
                            oddsArrayList.add(max_odds.get(index));
                        }
                        lotteryBall.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(lotteryBall.getSelect()){
                                    lotteryBall.setUnSelect();
                                    chosenItems.set(index, false);
                                    change.change(mRow, index);
                                } else {
                                    lotteryBall.setSelect();
                                    chosenItems.set(index, true);
                                    change.change(mRow, index);
                                }
                            }
                        });
                    }
                }
                ballArrayLists.add(tempBallArrayList);
                if(max_odds != null){
                    oddsArrayLists.add(oddsArrayList);
                }
            }

            LinearLayout wholeRow = view.findViewById(R.id.row_layout);

            for(int i = 0; i < ballArrayLists.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(context);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(HORIZONTAL);
                linearLayout.setLayoutParams(layoutParams);

                int sum = 0;
                for(int j = 0; j < ballArrayLists.get(i).size(); j++){
                    LotteryBall lotteryBall = ballArrayLists.get(i).get(j);
                    LayoutParams layoutParams1 = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                    lotteryBall.setLayoutParams(layoutParams1);
                    linearLayout.addView(lotteryBall);
                    sum++;

                    if(max_odds != null){
                        lotteryBall.setOdds(oddsArrayLists.get(i).get(j));
                    } else {
                        lotteryBall.setOdds(null);
                    }
                }
                if(sum < rowNumber){
                    View view = new View(getContext().getApplicationContext());
                    LayoutParams layoutParams1 = new LayoutParams(0, 0, (rowNumber - sum * 1f));
                    view.setLayoutParams(layoutParams1);
                    linearLayout.addView(view);
                }
                wholeRow.addView(linearLayout);
            }

            mTvquan.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectTabInter != null){
                        selectTabInter.onSelectTab(1);
                    }
                    resetBg();
                    mTvquan.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                    mTvquan.setTextColor(context.getResources().getColor(R.color.purples));
                    for(int i = 0; i < ballArrayLists.size(); i++){
                        for(int j = 0; j < ballArrayLists.get(i).size(); j++){
                            ballArrayLists.get(i).get(j).setSelect();
                            chosenItems.set(i * rowNumber + j, true);
                            change.change(mRow, i * rowNumber + j);
                        }
                    }
                }
            });
            mTvqing.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectTabInter != null){
                        selectTabInter.onSelectTab(6);
                    }
                    resetBg();
                    mTvqing.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                    mTvqing.setTextColor(context.getResources().getColor(R.color.purples));
                    for(int i = 0; i < ballArrayLists.size(); i++){
                        for(int j = 0; j < ballArrayLists.get(i).size(); j++){
                            ballArrayLists.get(i).get(j).setUnSelect();
                            chosenItems.set(i * rowNumber + j, false);
                            change.change(mRow, i * rowNumber + j);
                        }
                    }
                }
            });

            mTvxiao.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectTabInter != null){
                        selectTabInter.onSelectTab(3);
                    }
                    resetBg();
                    mTvxiao.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                    mTvxiao.setTextColor(context.getResources().getColor(R.color.purples));
                    for(int i = 0; i < ballArrayLists.size(); i++){
                        for(int j = 0; j < ballArrayLists.get(i).size(); j++){
                            if(i * rowNumber + j < total/2){
                                ballArrayLists.get(i).get(j).setSelect();
                                chosenItems.set(i * rowNumber + j, true);
                                change.change(mRow, i * rowNumber + j);
                            } else {
                                ballArrayLists.get(i).get(j).setUnSelect();
                                chosenItems.set(i * rowNumber + j, false);
                                change.change(mRow, i * rowNumber + j);
                            }
                        }
                    }
                }
            });

            mTvda.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectTabInter != null){
                        selectTabInter.onSelectTab(2);
                    }
                    resetBg();
                    mTvda.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                    mTvda.setTextColor(context.getResources().getColor(R.color.purples));
                    for(int i = 0; i < ballArrayLists.size(); i++){
                        for(int j = 0; j < ballArrayLists.get(i).size(); j++){
                            if(i * rowNumber + j >= total/2){
                                ballArrayLists.get(i).get(j).setSelect();
                                chosenItems.set(i * rowNumber + j, true);
                                change.change(mRow, i * rowNumber + j);
                            } else {
                                ballArrayLists.get(i).get(j).setUnSelect();
                                chosenItems.set(i * rowNumber + j, false);
                                change.change(mRow, i * rowNumber + j);
                            }
                        }
                    }
                }
            });

            mTvdan.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectTabInter != null){
                        selectTabInter.onSelectTab(4);
                    }
                    resetBg();
                    mTvdan.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                    for(int i = 0; i < ballArrayLists.size(); i++){
                        for(int j = 0; j < ballArrayLists.get(i).size(); j++){
                            if(Integer.parseInt(ballArrayLists.get(i).get(j).getText()) % 2 == 0){
                                ballArrayLists.get(i).get(j).setUnSelect();
                                chosenItems.set(i * rowNumber + j, false);
                                change.change(mRow, i * rowNumber + j);
                            } else {
                                ballArrayLists.get(i).get(j).setSelect();
                                chosenItems.set(i * rowNumber + j, true);
                                change.change(mRow, i * rowNumber + j);
                            }
                        }
                    }
                }
            });

            mTvshuang.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectTabInter != null){
                        selectTabInter.onSelectTab(5);
                    }
                    resetBg();
                    mTvshuang.setBackground(context.getResources().getDrawable(R.drawable.bg_light_blue_with_blue_border90));
                    mTvshuang.setTextColor(context.getResources().getColor(R.color.purples));
                    for(int i = 0; i < ballArrayLists.size(); i++){
                        for(int j = 0; j < ballArrayLists.get(i).size(); j++){
                            if(Integer.parseInt(ballArrayLists.get(i).get(j).getText()) % 2 == 0){
                                ballArrayLists.get(i).get(j).setSelect();
                                chosenItems.set(i * rowNumber + j, true);
                                change.change(mRow, i * rowNumber + j);
                            } else {
                                ballArrayLists.get(i).get(j).setUnSelect();
                                chosenItems.set(i * rowNumber + j, false);
                                change.change(mRow, i * rowNumber + j);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface SelectTabInter{
        void onSelectTab(int tab);
    }

    private SelectTabInter selectTabInter;

    public void setSelectInter(SelectTabInter selectTabInter){
        this.selectTabInter = selectTabInter;
    }
}