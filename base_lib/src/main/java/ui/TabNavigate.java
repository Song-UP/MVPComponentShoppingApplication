package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.lib.R;

public class TabNavigate extends LinearLayout implements View.OnClickListener {
    LinearLayout container;
    LineBlockScrollView scrollBlock;
    View line;

    //配置数据
    int selected;
    String[] titles;
    int[] icons;
    int linColor;
    float linPer;
    int titleStyle;
    int iconSize;

    //监听器
    onSelectedListener listener;

    public TabNavigate(Context context) {
        this(context, null);
    }

    public TabNavigate(Context context, AttributeSet attrs) {
        super(context, attrs);
        //装载视图
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(LayoutInflater.from(getContext()).inflate(R.layout.view_tab_navigate, null), params);
        container = findViewById(R.id.tab_navigate_container);
        scrollBlock = findViewById(R.id.tab_navigate_block);
        line = findViewById(R.id.line);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabNavigate);
        selected = a.getInt(R.styleable.TabNavigate_tab_navigate_current_position, 0);
        titleStyle = a.getResourceId(R.styleable.TabNavigate_tab_navigate_tabs_title_style, R.style.style_tabs_text);
        linColor = a.getResourceId(R.styleable.TabNavigate_tab_navigate_line_color, R.color.yellow_bar);
        linPer = a.getFloat(R.styleable.TabNavigate_tab_navigate_line_percent, 1.0f);
        iconSize = a.getLayoutDimension(R.styleable.TabNavigate_tab_navigate_tabs_icon_size, 16 * (int) getResources().getDisplayMetrics().density);

        ViewGroup.LayoutParams p = scrollBlock.getLayoutParams();        line.setVisibility(a.getBoolean(R.styleable.TabNavigate_tab_navigate_tabs_line_space_visiable, true) ? VISIBLE : GONE);

        p.height = a.getLayoutDimension(R.styleable.TabNavigate_tab_navigate_tabs_line_space_height, 4 * (int) getResources().getDisplayMetrics().density);
        scrollBlock.setLayoutParams(p);

        int strsId = a.getResourceId(R.styleable.TabNavigate_tab_navigate_tabs_title, 0);
        if (strsId != 0) {
            if (isInEditMode()) {
                titles = new String[]{"tab0", "tab1", "tab2"};
            } else {
                titles = getResources().getStringArray(strsId);
            }
        }
        int iconId = a.getResourceId(R.styleable.TabNavigate_tab_navigate_tabs_icon, 0);
        if (iconId != 0 && !isInEditMode()) {
            TypedArray ar = getResources().obtainTypedArray(iconId);
            icons = new int[ar.length()];
            for (int i = 0; i < ar.length(); i++)
                icons[i] = ar.getResourceId(i, 0);
            ar.recycle();
        }
        a.recycle();

        //初始化Tab Item
        setContainer(selected);
        setViewEnable(selected);
    }

    /**
     * 初始化Tab Item
     *
     * @param selected 当前选中位置
     */
    private void setContainer(int selected) {
        this.selected = selected;

        //计算tab item的个数
        int length = Math.min(titles == null ? Integer.MAX_VALUE : titles.length, icons == null ? Integer.MAX_VALUE : icons.length);
        if (titles == null && icons == null) {
            length = 0;
        }

        container.removeAllViews();
        for (int i = 0; i < length; i++) {
            addTabItem(i, i == selected);
        }

        scrollBlock.setPercent(linPer);
        scrollBlock.setLineColor(linColor);
        scrollBlock.setNum(length);
        scrollBlock.setSelectedPosition(selected);
    }

    /**
     * 添加tab item
     *
     * @param position 当前位置
     * @param selected 选中位置
     */
    public void addTabItem(int position, boolean selected) {
        LinearLayout cell = new LinearLayout(getContext());

        if (icons != null) {
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new ViewGroup.LayoutParams(iconSize, iconSize));
            image.setImageResource(icons[position]);
            cell.addView(image);
        }

        if (titles != null) {
            int padding = icons == null ? 0 : 8 * (int) getResources().getDisplayMetrics().density;
            TextView text = new TextView(getContext());
            text.setTextAppearance(getContext(), titleStyle);
            text.setPadding(padding, 0, 0, 0);
            text.setText(titles[position]);
            cell.addView(text);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        cell.setGravity(Gravity.CENTER);
        container.addView(cell, params);

        cell.setId(position);
        cell.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setSelected(v.getId());
        if (listener != null) {
            listener.onSeletedPosition(this, v, v.getId());
        }
    }

    public void setSelected(int position) {
        this.selected = position;
        setViewEnable(position);
        scrollBlock.setSelectedPosition(position);
    }

    public int getSelected() {
        return this.selected;
    }

    private void setViewEnable(int posiiton) {
        ViewGroup cell = null;
        for (int i = 0; i < container.getChildCount(); i++) {
            cell = (ViewGroup) container.getChildAt(i);
            cell.setEnabled(i != posiiton);
            for (int j = 0; j < cell.getChildCount(); j++) {
                cell.getChildAt(j).setEnabled(i != posiiton);
                if(cell.getChildAt(j) instanceof TextView) {
                    if (cell.getChildAt(j).isEnabled()) {
                        ((TextView) cell.getChildAt(j)).setTypeface(null, Typeface.NORMAL);
                    } else {
                        ((TextView) cell.getChildAt(j)).setTypeface(null, Typeface.BOLD);
                    }
                }
            }
        }
    }

    public void setCellText(int position, String s){
        ViewGroup cell = (ViewGroup) container.getChildAt(position);
        for (int j = 0; j < cell.getChildCount(); j++) {
            if(cell.getChildAt(j) instanceof TextView) {
                ((TextView) cell.getChildAt(j)).setText(s);
                break;
            }
        }
    }

    public void setLinColor(int resId) {
        scrollBlock.setLineColor(resId);
    }

    public void setSelectedListener(onSelectedListener listener) {
        this.listener = listener;
    }

    public interface onSelectedListener {
        void onSeletedPosition(ViewGroup parent, View view, int position);
    }
}
