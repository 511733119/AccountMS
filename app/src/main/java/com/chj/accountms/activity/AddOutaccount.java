package com.chj.accountms.activity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.accountms.R;
import com.chj.accountms.dao.OutaccountDAO;
import com.chj.accountms.model.Tb_outaccount;

import java.util.Calendar;

/**
 * 新增支出activity
 * 类名：AddOutaccount
 * author:陈海俊
 */
public class AddOutaccount extends Activity {

	private static final int DATE_DIALOG_ID = 0; //创建日期对话框常量
	TextView title ;	//标题
	EditText etmoney,ettime,etplace,etmark;  //创建4个EditText对象
	Spinner ettype;			//创建Spinner对象
	Button btnadd,btnCancel,back;  	//创建Button对象“保存”,“取消”,"返回"
	private int year , month , day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.addoutaccount);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏
		etmoney = (EditText)findViewById(R.id.etmoney); //获取“金额”文本框
		ettime = (EditText)findViewById(R.id.ettime);	//获取“时间”文本框
		etplace = (EditText)findViewById(R.id.etplace);	//获取“付款方”文本框
		etmark = (EditText)findViewById(R.id.etmark);	//获取“备注”文本框
		ettype =(Spinner)findViewById(R.id.ettype);		//获取“类别”下拉列表
		btnadd = (Button) findViewById(R.id.btnAdd);	//获取“添加”按钮
		btnCancel = (Button) findViewById(R.id.btnCancel);	//获取“取消”按钮
		back = (Button) findViewById(R.id.back);	//获取“返回”按钮
		title = (TextView)findViewById(R.id.title);	//标题

		title.setText("记录支出");

		ettime.setOnClickListener(new View.OnClickListener() {//为“时间”文本框设置单击监听事件
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);     //显示日期选择对话框
			}
		});
		Calendar calendar = Calendar.getInstance();  //获取当前系统日期
		year = calendar.get(Calendar.YEAR);		//获取年份
		month = calendar.get(Calendar.MONTH);	//获取月份
		day = calendar.get(Calendar.DAY_OF_MONTH);	//获取天数
		updateDisplay();		//默认显示当前日期

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddOutaccount.this,MainActivity.class);
				finish();
				startActivity(intent);
			}
		});

		btnadd.setOnClickListener(new View.OnClickListener() {	//为“保存”按钮设置监听事件
			@Override
			public void onClick(View v) {
				String money = etmoney.getText().toString(); //获取“金额”文本框的值
				if(!money.isEmpty()){
					OutaccountDAO outaccountDAO = new OutaccountDAO(AddOutaccount.this);

					//为Tb_outaccount实体类赋值
					Tb_outaccount tb_outccount = new Tb_outaccount();
					tb_outccount.set_id(outaccountDAO.getMaxId()+1);
					tb_outccount.setMoney(Double.parseDouble(money));
					tb_outccount.setTime(ettime.getText().toString());
					tb_outccount.setType(ettype.getSelectedItem().toString());
					tb_outccount.setAddress(etplace.getText().toString());
					tb_outccount.setMark(etmark.getText().toString());

					outaccountDAO.add(tb_outccount);	//执行保存至数据库操作
					Toast.makeText(AddOutaccount.this,"保存成功",Toast.LENGTH_SHORT).show();//弹出信息提示
					Intent intent = new Intent(AddOutaccount.this,Outaccountinfo.class);	//跳转到我的支出页面
					finish();
					startActivity(intent);
				}else {
					Toast.makeText(AddOutaccount.this,"输入有效的金额数字",Toast.LENGTH_SHORT).show();//弹出信息提示
				}
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {    //为“取消”按钮设置监听事件
			@Override
			public void onClick(View v) {
				//设置各控件为空
				etmoney.setText("");
				ettime.setText("");
				etmark.setText("");
				etplace.setText("");
				ettype.setSelection(0); //设置“类别”下拉列表默认选择第一项
				ettime.setText("");
				ettime.setHint("2016-09-24");
				Intent intent = new Intent(AddOutaccount.this,MainActivity.class);	//跳转
				finish();
				startActivity(intent);
			}
		});
	}

	private void updateDisplay(){
		//显示获取的时间
		ettime.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day));
	}

	@Override
	protected Dialog onCreateDialog(int id){
		switch(id){
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this,listener ,year, month,day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int theyear, int monthOfYear, int dayOfMonth) {
			year = theyear;
			month = monthOfYear ;
			day = dayOfMonth;
			updateDisplay();
		}
	};


}
