package com.ksls.funmall.fragment;

import java.math.BigDecimal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ksls.funmall.CalculatorResultActivity;
import com.ksls.funmall.R;
import com.ksls.funmall.base.BaseFragment;
import com.ksls.funmall.util.TextViewUtil;

@SuppressLint("InflateParams") 
public class AccumulationLoanFragment extends BaseFragment implements OnClickListener {

	private View thisView;
	
	private TextView accumulation_repayment_way, accumulation_repayment_year, accumulation_real_rate;
	private EditText accumulation_repayment_total;
	
	private int repayment_way = 0;
	private int repayment_year = 20;
	private float a_rate = 0.0450f;
	
	private SharedPreferences sharedPreferences = null;
	private final String SP_KEY = "calculator_1";
	
	private String[] repayment_way_methods = null;
	private int a_total = 30;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		thisView = inflater.inflate(R.layout.fragment_accumulation_loan, null);
		return thisView;
	}

	@Override
	public void onStart() {
		super.onStart();
		
		accumulation_repayment_way = (TextView) thisView.findViewById(R.id.accumulation_repayment_way);
		accumulation_repayment_way.setOnClickListener(this);
		
		accumulation_repayment_year = (TextView) thisView.findViewById(R.id.accumulation_repayment_year);
		accumulation_repayment_year.setOnClickListener(this);
		
		accumulation_repayment_total = (EditText) thisView.findViewById(R.id.accumulation_repayment_total);
		accumulation_real_rate = (TextView) thisView.findViewById(R.id.accumulation_real_rate);
		
		thisView.findViewById(R.id.btn_accumulation_calculate).setOnClickListener(this);
		
		
		repayment_way_methods = new String[] {
			getResources().getString(R.string.calculator_method_1), 
			getResources().getString(R.string.calculator_method_2)
		};
		readSharedPreferences();
		
		TextViewUtil.setText(thisView, R.id.accumulation_repayment_total, String.valueOf(a_total));
		TextViewUtil.setText(thisView, R.id.accumulation_repayment_way, repayment_way_methods[repayment_way]);
		TextViewUtil.setText(thisView, R.id.accumulation_repayment_year, String.valueOf(repayment_year) + getResources().getString(R.string.calculator_year) + String.valueOf(repayment_year * 12) + getResources().getString(R.string.calculator_period));
		TextViewUtil.setText(thisView, R.id.accumulation_real_rate, getResources().getString(R.string.calculator_rate_accu) + new BigDecimal(a_rate * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		
		repayment_way = 0;
		repayment_year = 20;
		a_rate = 0.0450f;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.accumulation_repayment_way:
			new AlertDialog.Builder(getActivity()).setItems(
				repayment_way_methods,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							accumulation_repayment_way.setText(getResources().getString(R.string.calculator_method_1));
							repayment_way = 0;
						} else {
							accumulation_repayment_way.setText(getResources().getString(R.string.calculator_method_2));
							repayment_way = 1;
						}
					}
				}).show();
			break;
		case R.id.accumulation_repayment_year:
			String[] str_years = new String[30];
			for (int i = 1; i <= 30; i++) {
				str_years[i-1] = i + getResources().getString(R.string.calculator_year) + 12 * i + getResources().getString(R.string.calculator_period);
			}
			new AlertDialog.Builder(getActivity()).setItems(
					str_years,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							repayment_year = which + 1;
							accumulation_repayment_year.setText(repayment_year + getResources().getString(R.string.calculator_year) + 12 * repayment_year + getResources().getString(R.string.calculator_period));
							if(repayment_year <= 5) {
								a_rate = 0.0400f;
							} else {
								a_rate = 0.0450f;
							}
							accumulation_real_rate.setText(getResources().getString(R.string.calculator_rate_accu) + new BigDecimal(a_rate * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
						}
					}).show();
			break;
		case R.id.btn_accumulation_calculate:
			saveSharedPreferences();
			
			Intent intent = new Intent();
			intent.setClass(getActivity(), CalculatorResultActivity.class);
			intent.putExtra("type", 1);
			intent.putExtra("a_rate", new BigDecimal(a_rate).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
			intent.putExtra("a_total", Integer.parseInt(accumulation_repayment_total.getText().toString()));
			intent.putExtra("repayment_way", repayment_way);
			intent.putExtra("repayment_year", repayment_year);
			startActivity(intent);
			break;
		}
	}
	
	private void readSharedPreferences() {
		sharedPreferences = getActivity().getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
		if(sharedPreferences.getInt("way", 0) > 0) repayment_way = sharedPreferences.getInt("way", 0);
		if(sharedPreferences.getInt("year", 0) > 0) repayment_year = sharedPreferences.getInt("year", 0);
		if(sharedPreferences.getFloat("a_rate", 0) > 0) a_rate = sharedPreferences.getFloat("a_rate", 0);
		if(sharedPreferences.getInt("a_total", 0) > 0) a_total = sharedPreferences.getInt("a_total", 0);
	}
	
	private void saveSharedPreferences() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("way", repayment_way);
		editor.putInt("year", repayment_year);
		editor.putFloat("a_rate", a_rate);
		editor.putInt("a_total", Integer.parseInt(accumulation_repayment_total.getText().toString()));
		editor.commit();
	}
}
