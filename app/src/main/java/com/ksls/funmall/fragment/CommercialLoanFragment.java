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
public class CommercialLoanFragment extends BaseFragment implements OnClickListener {

	private View thisView;
	
	private TextView commercial_repayment_way, commercial_repayment_year, commercial_rate_ratio, commercial_real_rate;
	private EditText commercial_repayment_total;
	
	private int repayment_way = 0;
	private int repayment_year = 20;
	private float rate_ratio = 1f;
	private float c_rate = 0.0655f;
	
	private SharedPreferences sharedPreferences = null;
	private final String SP_KEY = "calculator_2";
	
	private String[] repayment_way_methods = null;
	private String[] rate_ratio_methods = null;
	private int repayment_ratio = 1;
	private int c_total = 200;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		thisView = inflater.inflate(R.layout.fragment_commercial_loan, null);
		return thisView;
	}

	@Override
	public void onStart() {
		super.onStart();
		
		commercial_repayment_way = (TextView) thisView.findViewById(R.id.commercial_repayment_way);
		commercial_repayment_way.setOnClickListener(this);
		
		commercial_repayment_year = (TextView) thisView.findViewById(R.id.commercial_repayment_year);
		commercial_repayment_year.setOnClickListener(this);
		
		commercial_rate_ratio = (TextView) thisView.findViewById(R.id.commercial_rate_ratio);
		commercial_rate_ratio.setOnClickListener(this);
		
		commercial_repayment_total = (EditText) thisView.findViewById(R.id.commercial_repayment_total);
		commercial_real_rate = (TextView) thisView.findViewById(R.id.commercial_real_rate);
				
		thisView.findViewById(R.id.btn_commercial_calculate).setOnClickListener(this);
		
		repayment_way_methods = new String[] {
			getResources().getString(R.string.calculator_method_1), 
			getResources().getString(R.string.calculator_method_2)
		};
		rate_ratio_methods = new String[] {
			getResources().getString(R.string.calculator_rate_type_1), 
			getResources().getString(R.string.calculator_rate_type_2), 
			getResources().getString(R.string.calculator_rate_type_3)
		};
		readSharedPreferences();
		
		TextViewUtil.setText(thisView, R.id.commercial_repayment_total, String.valueOf(c_total));
		TextViewUtil.setText(thisView, R.id.commercial_repayment_way, repayment_way_methods[repayment_way]);
		TextViewUtil.setText(thisView, R.id.commercial_repayment_year, String.valueOf(repayment_year) + getResources().getString(R.string.calculator_year) + String.valueOf(repayment_year * 12) + getResources().getString(R.string.calculator_period));
		TextViewUtil.setText(thisView, R.id.commercial_rate_ratio, rate_ratio_methods[repayment_ratio]);
		TextViewUtil.setText(thisView, R.id.commercial_real_rate, getResources().getString(R.string.calculator_rate_comm) + new BigDecimal(c_rate * rate_ratio * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		
		repayment_way = 0;
		repayment_year = 20;
		rate_ratio = 1f;
		c_rate = 0.0655f;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commercial_repayment_way:
			new AlertDialog.Builder(getActivity()).setItems(
				repayment_way_methods,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						repayment_way = which;
						if (which == 0) {
							commercial_repayment_way.setText(getResources().getString(R.string.calculator_method_1));
						} else {
							commercial_repayment_way.setText(getResources().getString(R.string.calculator_method_2));
						}
					}
				}).show();
			break;
		case R.id.commercial_repayment_year:
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
							commercial_repayment_year.setText(repayment_year + getResources().getString(R.string.calculator_year) + 12 * repayment_year + getResources().getString(R.string.calculator_period));
							switch (repayment_year) {
							case 1:
								c_rate = 0.0600f;
								break;
							case 2:
							case 3:
								c_rate = 0.0615f;
								break;
							case 4:
							case 5:
								c_rate = 0.0640f;
								break;
							default:
								c_rate = 0.0655f;
								break;
							}
							commercial_real_rate.setText(getResources().getString(R.string.calculator_rate_comm) + new BigDecimal(c_rate * rate_ratio * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
						}
					}).show();
			break;
		case R.id.commercial_rate_ratio:
			new AlertDialog.Builder(getActivity()).setItems(
				rate_ratio_methods,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						repayment_ratio = which;
						if (which == 0) {
							commercial_rate_ratio.setText(getResources().getString(R.string.calculator_rate_type_1));
							rate_ratio = 0.85f;
						} else if (which == 1) {
							commercial_rate_ratio.setText(getResources().getString(R.string.calculator_rate_type_2));
							rate_ratio = 1f;
						} else {
							commercial_rate_ratio.setText(getResources().getString(R.string.calculator_rate_type_3));
							rate_ratio = 1.1f;
						}
						commercial_real_rate.setText(getResources().getString(R.string.calculator_rate_comm) + new BigDecimal(c_rate * rate_ratio * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
					}
				}).show();
			break;
		case R.id.btn_commercial_calculate:
			saveSharedPreferences();
			
			Intent intent = new Intent();
			intent.setClass(getActivity(), CalculatorResultActivity.class);
			intent.putExtra("type", 2);
			intent.putExtra("c_rate", new BigDecimal(c_rate * rate_ratio).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
			intent.putExtra("c_total", Integer.parseInt(commercial_repayment_total.getText().toString()));
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
		if(sharedPreferences.getFloat("c_rate", 0) > 0) c_rate = sharedPreferences.getFloat("c_rate", 0);
		if(sharedPreferences.getInt("c_total", 0) > 0) c_total = sharedPreferences.getInt("c_total", 0);
		if(sharedPreferences.getFloat("ratio", 0) > 0) rate_ratio = sharedPreferences.getFloat("ratio", 0);
		if(sharedPreferences.getInt("ratio_key", 0) > 0) repayment_ratio = sharedPreferences.getInt("ratio_key", 0);
	}
	
	private void saveSharedPreferences() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("way", repayment_way);
		editor.putInt("year", repayment_year);
		editor.putFloat("c_rate", c_rate);
		editor.putInt("c_total", Integer.parseInt(commercial_repayment_total.getText().toString()));
		editor.putFloat("ratio", rate_ratio);
		editor.putInt("ratio_key", repayment_ratio);
		editor.commit();
	}
}
