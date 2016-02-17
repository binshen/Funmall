package com.ksls.funmall;

import java.math.BigDecimal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ksls.funmall.base.BaseActivity;

public class CalculatorResultActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_calculator_result);
		
		initHeader();
		setUpViews();
	}
	
	private void setUpViews() {
		Intent intent = getIntent();
		int type = intent.getExtras().getInt("type");
		int way = intent.getExtras().getInt("repayment_way");
		int year = intent.getExtras().getInt("repayment_year");
		
		//贷款月数
		TextView repay_month_total = (TextView) findViewById(R.id.repay_month_total);
		repay_month_total.setText(12 * year + getResources().getString(R.string.calculator_result_label_month));
		
		TextView repay_total_amount = (TextView) findViewById(R.id.repay_total_amount);
		TextView repay_interest_amount = (TextView) findViewById(R.id.repay_interest_amount);
		TextView result_loan_amount = (TextView) findViewById(R.id.loan_total_amount);
		TextView accumulation_rate = (TextView) findViewById(R.id.accumulation_rate);
		TextView commercial_rate = (TextView) findViewById(R.id.commercial_rate);
		TextView monthly_repayment = (TextView) findViewById(R.id.monthly_repayment);
		TextView monthly_repay_first = (TextView) findViewById(R.id.monthly_repay_first);
		TextView monthly_repay_last = (TextView) findViewById(R.id.monthly_repay_last);
		
		int a_total = 0, c_total = 0;
		float a_rate = 0, c_rate = 0;
		double a_total_amount = 0, c_total_amount = 0, a_interest_amount = 0, c_interest_amount = 0;
		switch (type) {
		case 1:
			findViewById(R.id.accumulation_loan_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.commercial_loan_layout).setVisibility(View.GONE);
			
			a_total = intent.getExtras().getInt("a_total");
			//贷款总额
			result_loan_amount.setText(new BigDecimal(a_total).setScale(1, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			a_rate = intent.getExtras().getFloat("a_rate");
			//公积金贷款利率
			accumulation_rate.setText(new BigDecimal(a_rate * 100).setScale(4, BigDecimal.ROUND_HALF_UP) + "%");

			//还款总额
			a_total_amount = getTotal(way, a_total, a_rate, year);
			repay_total_amount.setText(new BigDecimal(a_total_amount).setScale(4, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			//支付利息
			a_interest_amount = a_total_amount - a_total;
			repay_interest_amount.setText(new BigDecimal(a_interest_amount).setScale(4, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			if(way == 0) {
				findViewById(R.id.monthly_repayment_layout).setVisibility(View.VISIBLE);
				findViewById(R.id.monthly_repay_first_layout).setVisibility(View.GONE);
				findViewById(R.id.monthly_repay_last_layout).setVisibility(View.GONE);
				
				//月均还款
				double a_monthly_amount = a_total_amount * 10000 / (year * 12);
				monthly_repayment.setText(new BigDecimal(a_monthly_amount).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
			} else {
				findViewById(R.id.monthly_repayment_layout).setVisibility(View.GONE);
				findViewById(R.id.monthly_repay_first_layout).setVisibility(View.VISIBLE);
				findViewById(R.id.monthly_repay_last_layout).setVisibility(View.VISIBLE);
				
				//首月还款
				float a_principal = a_total * 10000;
				double a_repay_first = a_principal / (year * 12) + a_principal / 12 * a_rate;
				monthly_repay_first.setText(new BigDecimal(a_repay_first).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
				
				//末月还款
				double a_repay_last = a_principal / (year * 12) + a_principal / (year * 12 * 12) * a_rate;
				monthly_repay_last.setText(new BigDecimal(a_repay_last).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
			}
			break;
		case 2:
			findViewById(R.id.accumulation_loan_layout).setVisibility(View.GONE);
			findViewById(R.id.commercial_loan_layout).setVisibility(View.VISIBLE);
			
			c_total = intent.getExtras().getInt("c_total");
			//贷款总额
			result_loan_amount.setText(new BigDecimal(c_total).setScale(1, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			c_rate = intent.getExtras().getFloat("c_rate");
			//商业贷款利率
			commercial_rate.setText(new BigDecimal(c_rate * 100).setScale(4, BigDecimal.ROUND_HALF_UP) + "%");

			//还款总额
			c_total_amount = getTotal(way, c_total, c_rate, year);
			repay_total_amount.setText(new BigDecimal(c_total_amount).setScale(4, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			//支付利息
			c_interest_amount = c_total_amount - c_total;
			repay_interest_amount.setText(new BigDecimal(c_interest_amount).setScale(4, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			if(way == 0) {
				findViewById(R.id.monthly_repayment_layout).setVisibility(View.VISIBLE);
				findViewById(R.id.monthly_repay_first_layout).setVisibility(View.GONE);
				findViewById(R.id.monthly_repay_last_layout).setVisibility(View.GONE);
				
				//月均还款
				double c_monthly_amount = c_total_amount * 10000 / (year * 12);
				monthly_repayment.setText(new BigDecimal(c_monthly_amount).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
			} else {
				findViewById(R.id.monthly_repayment_layout).setVisibility(View.GONE);
				findViewById(R.id.monthly_repay_first_layout).setVisibility(View.VISIBLE);
				findViewById(R.id.monthly_repay_last_layout).setVisibility(View.VISIBLE);
				
				//首月还款
				float c_principal = c_total * 10000;
				double c_repay_first = c_principal / (year * 12) + c_principal / 12 * c_rate;
				monthly_repay_first.setText(new BigDecimal(c_repay_first).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));

				//末月还款
				double c_repay_last = c_principal / (year * 12) + c_principal / (year * 12 * 12) * c_rate;
				monthly_repay_last.setText(new BigDecimal(c_repay_last).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
			}
			break;
		case 3:
			findViewById(R.id.accumulation_loan_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.commercial_loan_layout).setVisibility(View.VISIBLE);
			
			a_total = intent.getExtras().getInt("a_total");
			c_total = intent.getExtras().getInt("c_total");
			//贷款总额
			result_loan_amount.setText(new BigDecimal(a_total + c_total).setScale(1, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			a_rate = intent.getExtras().getFloat("a_rate");
			//公积金贷款利率
			accumulation_rate.setText(new BigDecimal(a_rate * 100).setScale(4, BigDecimal.ROUND_HALF_UP) + "%");
			
			c_rate = intent.getExtras().getFloat("c_rate");
			//商业贷款利率
			commercial_rate.setText(new BigDecimal(c_rate * 100).setScale(4, BigDecimal.ROUND_HALF_UP) + "%");
			
			//还款总额
			a_total_amount = getTotal(way, a_total, a_rate, year);
			c_total_amount = getTotal(way, c_total, c_rate, year);
			repay_total_amount.setText(new BigDecimal(a_total_amount + c_total_amount).setScale(4, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			//支付利息
			a_interest_amount = a_total_amount - a_total;
			c_interest_amount = c_total_amount - c_total;
			repay_interest_amount.setText(new BigDecimal(a_interest_amount + c_interest_amount).setScale(4, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_wan_yuan));
			
			if(way == 0) {
				findViewById(R.id.monthly_repayment_layout).setVisibility(View.VISIBLE);
				findViewById(R.id.monthly_repay_first_layout).setVisibility(View.GONE);
				findViewById(R.id.monthly_repay_last_layout).setVisibility(View.GONE);
				
				//月均还款
				double a_monthly_amount = a_total_amount * 10000 / (year * 12);
				double c_monthly_amount = c_total_amount * 10000 / (year * 12);
				monthly_repayment.setText(new BigDecimal(a_monthly_amount + c_monthly_amount).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
			} else {
				findViewById(R.id.monthly_repayment_layout).setVisibility(View.GONE);
				findViewById(R.id.monthly_repay_first_layout).setVisibility(View.VISIBLE);
				findViewById(R.id.monthly_repay_last_layout).setVisibility(View.VISIBLE);
				
				//首月还款
				float a_principal = a_total * 10000;
				double a_repay_first = a_principal / (year * 12) + a_principal / 12 * a_rate;
				float c_principal = c_total * 10000;
				double c_repay_first = c_principal / (year * 12) + c_principal / 12 * c_rate;
				monthly_repay_first.setText(new BigDecimal(a_repay_first + c_repay_first).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
				
				//末月还款
				double a_repay_last = a_principal / (year * 12) + a_principal / (year * 12 * 12) * a_rate;
				double c_repay_last = c_principal / (year * 12) + c_principal / (year * 12 * 12) * c_rate;
				monthly_repay_last.setText(new BigDecimal(a_repay_last + c_repay_last).setScale(2, BigDecimal.ROUND_HALF_UP) + getResources().getString(R.string.calculator_result_label_yuan));
			}
			break;
		}
	}
	
	private double getTotal(int way, int total, float rate, int year) {
		if(way == 0) {
			return (total * rate / 12 * Math.pow(1 + rate / 12, year * 12) * year * 12) / (Math.pow(1 + rate / 12, year * 12) - 1);
		} else {
			return total + total * rate / 12 * (1 + year * 12) / 2;
		}
	}
}
