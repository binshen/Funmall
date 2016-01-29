package com.ksls.funmall.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ksls.funmall.R;

public class Header extends RelativeLayout {

	private LeftHeaderOnClickListener leftHeaderOnClickListener = null;
	private RightHeaderOnClickListener rightHeaderOnClickListener = null;
	
	private ImageView headLeft;
	private TextView headTitle;
	private ImageView headRight;
	private ToggleButton headToggle;

	public Header(Context context) {
		super(context);
	}

	public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int)getResources().getDimension(R.dimen.header_height));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        setLayoutParams(params);
        setBackgroundResource(R.color.header_bg);
        setId(R.id.system_header_layout);
        
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_header); 
        Drawable left_background = a.getDrawable(R.styleable.custom_header_left_background);
        if(left_background != null) {
        	LinearLayout left_layout = new LinearLayout(context);
        	LayoutParams left_layout_params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        	left_layout_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        	left_layout.setLayoutParams(left_layout_params);
        	left_layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (leftHeaderOnClickListener != null) {
						leftHeaderOnClickListener.onLeftHeaderClick(v);
					}
				}
			});
        	headLeft = new ImageView(context);
	        headLeft.setImageDrawable(left_background);
	        LinearLayout.LayoutParams left_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	        left_params.leftMargin = 20;
	        left_params.rightMargin = 20;
	        headLeft.setLayoutParams(left_params);
	        left_layout.addView(headLeft);
	        addView(left_layout);
        }
        
        Drawable title_background = a.getDrawable(R.styleable.custom_header_title_background);
        LayoutParams title_params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        title_params.addRule(RelativeLayout.CENTER_VERTICAL);
        title_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if(title_background != null) {
        	ImageView headTitle = new ImageView(context);
        	headTitle.setImageDrawable(title_background);
        	headTitle.setLayoutParams(title_params);
        	addView(headTitle);
        } else {
        	String title = a.getString(R.styleable.custom_header_caption);
        	headTitle = new TextView(context);
        	headTitle.setText(title);
        	headTitle.setLayoutParams(title_params);
            headTitle.setTextSize(14);
            headTitle.setTextColor(getResources().getColor(R.color.white));
            headTitle.setGravity(Gravity.CENTER);
            addView(headTitle);
        }

        Drawable right_background = a.getDrawable(R.styleable.custom_header_right_background);
        Drawable right_toggle_button = a.getDrawable(R.styleable.custom_header_right_toggle_button);
        if(right_background != null || right_toggle_button != null) {
        	LinearLayout right_layout = new LinearLayout(context);
        	LayoutParams right_layout_params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        	right_layout_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        	right_layout.setGravity(Gravity.CENTER);
        	right_layout.setLayoutParams(right_layout_params);
        	
        	LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	        right_params.leftMargin = 20;
	        right_params.rightMargin = 20;
        	if(right_toggle_button != null) {
        		headToggle = new ToggleButton(context);
    	        headToggle.setLayoutParams(right_params);
    	        headToggle.setText("");
    	        headToggle.setTextOn("");
    	        headToggle.setTextOff("");
    	        headToggle.setBackgroundDrawable(right_toggle_button);
    	        //headToggle.setBackgroundResource(R.drawable.selector_toggle_headright_edit);
    	        headToggle.setOnClickListener(new OnClickListener() {
            		@Override
    				public void onClick(View v) {
    					if (rightHeaderOnClickListener != null) {
    						rightHeaderOnClickListener.onRightHeaderClick(v);
    					}
    				}
    			});
    	        right_layout.addView(headToggle);
        	} else {
        		headRight = new ImageView(context);
    	        headRight.setImageDrawable(right_background);
    	        headRight.setLayoutParams(right_params);
    	        right_layout.addView(headRight);
        	}
        	right_layout.setOnClickListener(new OnClickListener() {
        		@Override
				public void onClick(View v) {
					if (rightHeaderOnClickListener != null) {
						if(headToggle != null) {
							headToggle.toggle();
						}
						rightHeaderOnClickListener.onRightHeaderClick(v);
					}
				}
			});
	        addView(right_layout);
        }
        a.recycle();
    }
    
    public Header(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
    
    public void setLeftHeaderOnClickListener(LeftHeaderOnClickListener l) {
    	leftHeaderOnClickListener = l;
	}
    
    public void setRightHeaderOnClickListener(RightHeaderOnClickListener r) {
    	rightHeaderOnClickListener = r;
	}
	
	public interface LeftHeaderOnClickListener {
		void onLeftHeaderClick(View v);
	}
	
	public interface RightHeaderOnClickListener {
		void onRightHeaderClick(View v);
	}
	
	/////////////////////////////////////////////
	public ImageView getHeadLeft() {
		return headLeft;
	}

	public TextView getTitle() {
		return headTitle;
	}

	public ImageView getHeadRight() {
		return headRight;
	}
	
	public ToggleButton getHeadToggle() {
		return headToggle;
	}
}
