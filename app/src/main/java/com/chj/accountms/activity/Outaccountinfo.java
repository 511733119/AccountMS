package com.chj.accountms.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chj.accountms.R;
import com.chj.accountms.dao.OutaccountDAO;
import com.chj.accountms.model.Tb_outaccount;

import java.util.List;

/***
 * 类名：Outaccountinfo
 * 描述：显示支出信息
 * author:陈海俊
 */
public class Outaccountinfo extends Activity {

	TextView title;	//标题
	ListView lvinfo;	//创建ListView对象
	String strType = "";	//创建字符串，记录管理类型
	Button back ; 	//返回按钮
	public static final String FLAG = "id";		//定义变量，作为请求码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	//自定义标题栏
		setContentView(R.layout.outaccountinfo);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.maintitlebar);	//修改标题栏

		back = (Button)findViewById(R.id.back);
		lvinfo = (ListView)findViewById(R.id.outlistview);	//获取布局文件中的Listview组件
		title = (TextView)findViewById(R.id.title);
		title.setText("支出记录");	//设置标题

		showInfo(1);        //调用自定义方法显示支出信息

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Outaccountinfo.this,MainActivity.class);
				finish();
				startActivity(intent);
			}
		});

		lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String strinfo = String.valueOf(((TextView) view).getText());    //获取所点击item的信息
				String strid = strinfo.substring(0, strinfo.indexOf(")"));        //从收入信息中截取支出的编号
				Intent intent = new Intent(Outaccountinfo.this, InfoManage.class);    //创建intent对象
				intent.putExtra(FLAG, new String[]{strid, strType});        //设置传递参数
				finish();
				startActivity(intent);        //执行intent操作
			}
		});
	}

	private void showInfo(int intType) {
		int m = 0;    //定义数组存储的坐标
		String[] strinfos = null;    //定义字符串数组，用来存储支出信息
		ArrayAdapter<String> arrayAdapter = null;    //创建ArrayAdapter对象
		strType = "btnoutinfo";    //为类型赋值
		OutaccountDAO outaccountDAO = new OutaccountDAO(Outaccountinfo.this);    //创OutaccountDAO对象
		//获取所有支出信息，并存储到List集合中
		List<Tb_outaccount> listinfos = outaccountDAO.getScrollData(0, (int) outaccountDAO.getCount());
		strinfos = new String[listinfos.size()];
		for (Tb_outaccount tb_outaccount : listinfos) {        //遍历list集合
			strinfos[m] = tb_outaccount.get_id() + ")" + tb_outaccount.getType()
							+ "      " + String.valueOf(tb_outaccount.getMoney())
							+ "元        " + tb_outaccount.getTime();
			m++;    //标识加1
		}
		//使用字符串数组初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strinfos);
		lvinfo.setAdapter(arrayAdapter);        //为listview设置数据源
	}

	@Override
	protected void onRestart() {
		super.onRestart(); //实现基类的方法
		showInfo(1);    //显示支出信息
	}
}
