package me.jessyan.armscomponent.commonres.weight;
/**
 * ws
 * 19.2.24
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

import me.jessyan.armscomponent.commonres.R;

public class SeparatedEditText02 extends android.support.v7.widget.AppCompatEditText {

    private static final int TYPE_HOLLOW = 1;//空心//单行
    private static final int TYPE_SOLID = 2;//实心 //单行
    private static final int TYPE_UNDERLINE = 3;//下划线
    private static final int TYPE_CELL = 4;//单元格（带边框的单元格）

    private Paint borderPaint;//边界画笔
    private Paint blockPaint;//实心块画笔
    private Paint whitePaint;//实心块白笔
    private Paint textPaint;
    private Paint cursorPaint;

    private RectF borderRectF;
    private RectF boxRectF;//小方块、小矩形

    private int width;//可绘制宽度
    private int height;//可绘制高度

    private int boxWidth;//方块宽度
    private int boxHeight;//方块高度


    private int spacingHorizontal;//方块之间水平间隙
    private int spacingVertical;//方块之间垂直间隙
    private int corner;//圆角
    private int maxLength;//最大单元格数
    private int singLineMaxLength = 5;//单行最大单元格数
    private int borderWidth;//边界粗细
    private boolean password;//是否是密码类型
    private boolean showCursor;//显示光标
    private int cursorDuration;//光标闪动间隔
    private int cursorWidth;//光标宽度
    private int cursorColor;//光标颜色
    private int type;//实心方式、空心方式内部的颜色
    private int borderColor; //边框线的颜色
    private int blockColor;//实心时内部的颜色
    private int insideColor = Color.WHITE;//当是单元格的时候，
    private int textColor;

    private boolean isCursorShowing;

    private CharSequence contentText;

    private TextChangedListener textChangedListener;

    private Timer timer;
    private TimerTask timerTask;

    public SeparatedEditText02(Context context) {
        this(context, null);
    }

    public SeparatedEditText02(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeparatedEditText02(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLongClickable(false);
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SeparatedEditText);

        password = ta.getBoolean(R.styleable.SeparatedEditText_password, false);
        showCursor = ta.getBoolean(R.styleable.SeparatedEditText_showCursor, true);
        borderColor = ta.getColor(R.styleable.SeparatedEditText_borderColor, ContextCompat.getColor(getContext(), R.color.cardview_dark_background));
        blockColor = ta.getColor(R.styleable.SeparatedEditText_blockColor, ContextCompat.getColor(getContext(), R.color.public_colorPrimary));
        textColor = ta.getColor(R.styleable.SeparatedEditText_textColor, ContextCompat.getColor(getContext(), R.color.cardview_dark_background));
        cursorColor = ta.getColor(R.styleable.SeparatedEditText_cursorColor, ContextCompat.getColor(getContext(), R.color.cardview_dark_background));
        corner = (int) ta.getDimension(R.styleable.SeparatedEditText_corner, 0);
        spacingHorizontal = (int) ta.getDimension(R.styleable.SeparatedEditText_blockHorizontalSpacing, 0);
        spacingVertical = (int) ta.getDimension(R.styleable.SeparatedEditText_blockVerticalSpacing, 0);
        type = ta.getInt(R.styleable.SeparatedEditText_separateType, TYPE_HOLLOW);
        maxLength = ta.getInt(R.styleable.SeparatedEditText_maxLength, 6);
        cursorDuration = ta.getInt(R.styleable.SeparatedEditText_cursorDuration, 500);
        cursorWidth = (int) ta.getDimension(R.styleable.SeparatedEditText_cursorWidth, 2);
        borderWidth = (int) ta.getDimension(R.styleable.SeparatedEditText_borderWidth, 5);

        boxWidth = (int) ta.getDimension(R.styleable.SeparatedEditText_boxWidth, 5);
        boxHeight = (int) ta.getDimension(R.styleable.SeparatedEditText_boxHeigh, 5);

        singLineMaxLength = (int) ta.getInteger(R.styleable.SeparatedEditText_singleLineMaxNum, 5);

        ta.recycle();

        init();

    }


    public void setCorner(int corner) {
        this.corner = corner;
        postInvalidate();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        postInvalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        postInvalidate();
    }

    public void setPassword(boolean password) {
        this.password = password;
        postInvalidate();
    }

    public void setShowCursor(boolean showCursor) {
        this.showCursor = showCursor;
        postInvalidate();
    }

    public void setCursorDuration(int cursorDuration) {
        this.cursorDuration = cursorDuration;
        postInvalidate();
    }

    public void setCursorWidth(int cursorWidth) {
        this.cursorWidth = cursorWidth;
        postInvalidate();
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
        postInvalidate();
    }

    public void setType(int type) {
        this.type = type;
        postInvalidate();
    }


    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }


    private void init() {
        this.setFocusableInTouchMode(true);
        this.setFocusable(true);
        this.requestFocus();
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        new Handler().postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 500);

        blockPaint = new Paint();
        blockPaint.setAntiAlias(true);
        blockPaint.setColor(blockColor);
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setStrokeWidth(1);

        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(insideColor);
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setStrokeWidth(1);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(1);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        cursorPaint = new Paint();
        cursorPaint.setAntiAlias(true);
        cursorPaint.setColor(cursorColor);
        cursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cursorPaint.setStrokeWidth(cursorWidth);

        borderRectF = new RectF();
        boxRectF = new RectF();

        if (type == TYPE_HOLLOW)
            spacingHorizontal = 0;

        timerTask = new TimerTask() {
            @Override
            public void run() {
                isCursorShowing = !isCursorShowing;
                postInvalidate();
            }
        };
        timer = new Timer();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        borderRectF.set(0, 0, width, height);

        textPaint.setTextSize(boxWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRect(canvas);

        drawText(canvas, contentText);

        drawCursor(canvas);

    }

    /**
     * 绘制光标
     *
     * @param canvas
     */
    private void drawCursor(Canvas canvas) {
        if (!isCursorShowing && showCursor && contentText.length() < maxLength && hasFocus()) {
            int cursorPosition = contentText.length()%singLineMaxLength + 1;
            int curLine = contentText.length()/singLineMaxLength;
            int startX = spacingHorizontal * (cursorPosition) + boxWidth * (cursorPosition - 1) + boxWidth / 2;
            int startY = (boxHeight+spacingVertical)*curLine + boxHeight / 4;
            int endX = startX;
            int endY = startY+boxHeight/2;//boxHeight - boxHeight / 4;
            canvas.drawLine(startX, startY, endX, endY, cursorPaint);
        }
    }

    private void drawRect(Canvas canvas) {
        for (int i = 0; i < maxLength; i++) {
            int left = spacingHorizontal * (i%singLineMaxLength + 1) + boxWidth * ((int)(i%singLineMaxLength));
            int top =i/singLineMaxLength*spacingVertical+((int)(i/singLineMaxLength))*boxHeight;
            int right = left + boxWidth;
            int bottoom = top+boxHeight;

            boxRectF.set(left, top,right,bottoom);

            if (type == TYPE_SOLID) {
                canvas.drawRoundRect(boxRectF, corner, corner, blockPaint);
            } else if (type == TYPE_UNDERLINE) {
                canvas.drawLine(boxRectF.left, boxRectF.bottom, boxRectF.right, boxRectF.bottom, borderPaint);
            } else if (type == TYPE_HOLLOW) {
                if (i == 0 || i == maxLength)
                    continue;
                canvas.drawLine(boxRectF.left, boxRectF.top, boxRectF.left, boxRectF.bottom, borderPaint);
            }else if (type == TYPE_CELL) {
                blockPaint.setColor(borderColor);
                canvas.drawRoundRect(boxRectF, corner, corner, blockPaint);
                boxRectF.set(left+borderWidth, top+borderWidth,right-borderWidth,bottoom-borderWidth);
                canvas.drawRoundRect(boxRectF, corner, corner, whitePaint);
            }
        }

        if (type == TYPE_HOLLOW)
            canvas.drawRoundRect(borderRectF, corner, corner, borderPaint);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        contentText = text;
        invalidate();

        if (textChangedListener != null)
            if (text.length() == maxLength)
                textChangedListener.textCompleted(text);
            else textChangedListener.textChanged(text);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //cursorFlashTime为光标闪动的间隔时间
        timer.scheduleAtFixedRate(timerTask, 0, cursorDuration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        return true;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        CharSequence text = getText();
        if (text != null) {
            if (selStart != text.length() || selEnd != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }
        super.onSelectionChanged(selStart, selEnd);
    }

    private void drawText(Canvas canvas, CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            int startX = spacingHorizontal * (i%singLineMaxLength + 1) + boxWidth * (i%singLineMaxLength);
            int startY = i/singLineMaxLength*spacingVertical+i/singLineMaxLength*boxHeight;

            int baseX = 0,baseY =0;
            baseX = (int) (startX + boxWidth / 2 - textPaint.measureText(String.valueOf(charSequence.charAt(i))) / 2);
            if (maxLength < singLineMaxLength) {
                baseY = (int) (startY + boxHeight / 2 - (textPaint.descent() + textPaint.ascent()) / 2);
            }else {
                baseY = (int) (startY + boxHeight / 2 - (textPaint.descent() + textPaint.ascent()) / 2);
            }
            int centerX = startX + boxWidth / 2;
            int centerY = startY + boxHeight / 2;
            int radius = Math.min(boxWidth, boxHeight) / 6;
            if (password)
                canvas.drawCircle(centerX, centerY, radius, textPaint);
            else
                canvas.drawText(String.valueOf(charSequence.charAt(i)), baseX, baseY, textPaint);
        }

    }

    public void setTextChangedListener(TextChangedListener listener) {
        textChangedListener = listener;
    }

    public void clearText() {
        setText("");
    }

    /**
     * 密码监听者
     */
    public interface TextChangedListener {
        /**
         * 输入/删除监听
         *
         * @param changeText 输入/删除的字符
         */
        void textChanged(CharSequence changeText);

        /**
         * 输入完成
         */
        void textCompleted(CharSequence text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int boxNum = maxLength<singLineMaxLength?maxLength:singLineMaxLength;
        int lineNum = maxLength/singLineMaxLength+1;
        int width = boxWidth*boxNum+spacingHorizontal*(boxNum+1);
        int height = boxHeight*lineNum+spacingVertical*(lineNum-1);
        setMeasuredDimension(width,height);
    }

}

/****使用*************/
//<com.kenny.separatededittext.SeparatedEditText02
//        android:id="@+id/edit_hollow02"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//
//        app:corner="10dp"
//        app:cursorColor="@android:color/holo_orange_light"
//        app:cursorWidth="3dp"
//        app:textColor="@color/colorAccent"
//        app:borderWidth="2dp"
//
//        android:inputType="number"
//
//        app:separateType="@integer/type_border"
//        app:singleLineMaxNum="5"
//        app:maxLength="9"
//        app:boxHeigh="50dp"
//        app:boxWidth="50dp"
//        app:blockHorizontalSpacing="6dp"
//        app:blockVerticalSpacing="5dp"
//        app:borderColor="@android:color/holo_blue_light"
//
//        />
