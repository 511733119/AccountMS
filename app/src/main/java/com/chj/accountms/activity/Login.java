package com.chj.accountms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chj.accountms.R;
import com.chj.accountms.dao.PwdDAO;

/**
 * 类名：Login
 * 实现登录，退出
 * author：陈海俊
 */
public class Login extends Activity{
	EditText txtlogin; //创建EditText对象
	Button btnlogin; //创建Button对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.myTheme);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.login);//设置布局文件
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar);	//修改标题栏
		txtlogin = (EditText)findViewById(R.id.txtLogin);//获取密码文本框
		btnlogin = (Button)findViewById(R.id.btnLogin);//获取登录按钮

		btnlogin.setOnClickListener(new OnClickListener() {
			//为登录按钮设置监听事件
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this,MainActivity.class);//创建Intent对象
				PwdDAO pwdDAO = new PwdDAO(Login.this);//创建PwdDAO对象
				//判断是否有密码及是否输入了密码
				if((pwdDAO.getCount()==0 || pwdDAO.find().getPassword().isEmpty())
						&& txtlogin.getText().toString().isEmpty()){
					finish();
					startActivity(intent);//启动主Activity
				}else {
					//判断输入的密码是否与数据库中的密码一致
					if(pwdDAO.find().getPassword().equals(txtlogin.getText().toString())){
						finish();
						startActivity(intent);//启动主Activity
					}else{
						//弹出信息提示
						Toast.makeText(Login.this, "请输入正确的密码!", Toast.LENGTH_SHORT).show();
					}
				}
				txtlogin.setText("");//清空密码文本框
			}
		});

	}
}
