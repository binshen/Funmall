package com.ksls.funmall.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class TextViewUtil {

	public static void setText(View v, int id, String text) {
		TextView view = (TextView) v.findViewById(id);
		view.setText(text);
	}
	
	public static void setText(Activity v, int id, String text) {
		TextView view = (TextView) v.findViewById(id);
		view.setText(text);
	}
	
	public static void setColor(View v, int id, int color) {
		TextView view = (TextView) v.findViewById(id);
		view.setTextColor(color);
	}
}
