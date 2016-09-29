package com.chj.accountms.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.accountms.R;
import com.chj.accountms.dao.FlagDAO;
import com.chj.accountms.model.Tb_flag;

public class Accountflag extends Activity {

	TextView title;	//标题
	private EditText ettitle,etflag;	//获得便签标题和内容
	private Button btnsave,btncancel,back;	//获得保存和取消按钮
	FlagDAO flagDAO = null;
	Tb_flag tb_flag = null;
	String flagtitle = "";
	String flag = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.accountflag);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏
		ettitle = (EditText)findViewById(R.id.ettitle);	//获得titile
		etflag = (EditText)findViewById(R.id.etflag);	//获得flag
		btnsave = (Button)findViewById(R.id.btnAdd);	//获得保存按钮
		btncancel = (Button)findViewById(R.id.btnCancel);	//获得取消按钮
		back = (Button)findViewById(R.id.back);	//获得返回按钮
		title = (TextView)findViewById(R.id.title);	//标题

		title.setText("添加便签");

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Accountflag.this,MainActivity.class);
				finish();
				startActivity(intent);
			}
		});
		btnsave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flagtitle = ettitle.getText().toString();	//获得title内容
				flag = etflag.getText().toString();	//获得flag内容
				if(flagtitle.isEmpty() || flag.isEmpty()){
					Toast.makeText(Accountflag.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
					ettitle.setText("");
				}else{
					flagDAO = new FlagDAO(Accountflag.this);	//创建flagDAO对象
					tb_flag = new Tb_flag(flagDAO.getMaxId()+1,flagtitle,flag);	//创建对象并赋值
					flagDAO.add(tb_flag);	//执行添加操作
					Toast.makeText(Accountflag.this,"保存成功",Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(Accountflag.this,MainActivity.class);	//跳转至主界面
					startActivity(intent);
				}
			}
		});
		btncancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
