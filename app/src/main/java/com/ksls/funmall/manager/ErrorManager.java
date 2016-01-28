package com.ksls.funmall.manager;

import android.content.Context;
import android.widget.Toast;

import com.ksls.funmall.base.Constants;

public class ErrorManager {

	public static void showError(Context context, String message) {
		if(Constants.debug) {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
	}
}
