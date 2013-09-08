package com.example.woong;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LauncherActivity.ListItem;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private static final int Alarm = 0;
	Toast mToast;

	// pointer variable of MainActivity.
	public static Context mContext;
	
	SharedPreferences pref;
	SharedPreferences.Editor edit;
	public MyListAdapter MyAdapter;
	public ListView MyList;
	ArrayList<MyItem> arItem;

	// ��� �ㅼ� �대���
	public TimePicker mTime;
	private int mHour;
	private int mMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pref = getSharedPreferences("PrefTest1", 0);
		edit = pref.edit();
		
		mContext = this;	
		arItem = new ArrayList<MyItem>();
	}

	@Override
	public void onResume() {
		super.onResume();

		// 현재 배열 개수를 받아옴.
		int max = pref.getInt("count", 0);
		int myColor = pref.getInt("color", 0);

		arItem.clear();
		
		MyItem mi;
		for (int i=0; i<max; i++) {
			mi = new MyItem(pref.getString("address"+i, ""), 
					25.5,
					R.drawable.cloud_285x123, 
					pref.getBoolean("isSelected"+i, false));
			arItem.add(mi);
		}

		// 어댑터 생성
		MyAdapter = new MyListAdapter(this, R.layout.icontext, arItem, myColor);

		// 리스트뷰와 어댑터 연결
		MyList = (ListView) findViewById(R.id.list);
		MyList.setDividerHeight(0);
		MyList.setAdapter(MyAdapter);
	}

	@Override
	public void onPause() {
		
		super.onPause();
		
		int max = pref.getInt("count", 0);
		
		for (int i = 0; i < max; i++) {
			edit.putString("address" + i, arItem.get(i).Address);
			edit.putBoolean("isSelected" + i, arItem.get(i).IsSelected);
		}
		edit.commit();
	}

	// Plus Button.
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			startActivity(new Intent(this, SearchActivity.class));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * public boolean onPrepareOptionsMenu(Menu menu) { //int color =
	 * background.getTextColors().getDefaultColor(); if (iv == Color.RED) {
	 * menu.findItem(R.id.item4).setChecked(true); }
	 * 
	 * if (color == Color.BLUE) { menu.findItem(R.id.item5).setChecked(true); }
	 * return true; }
	 */

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		// Alarm Menu
		case R.id.item1:
			// mBtn.setTextColor(Color.RED);
			// setBackgroundColor(FF0000);
			showDialog(Alarm);
			return true;

			// Info Menu
		case R.id.item3:
			startActivity(new Intent(this, InfoActivity.class));
			return true;

			// Changing color in menu2
		case R.id.item5:
			// blue = 0
			edit.putInt("color", 0);
			edit.commit();

			// code for MainActivity re-drawing.
			((MainActivity) (MainActivity.mContext)).onResume();
			return true;
		case R.id.item6:
			// red = 1
			edit.putInt("color", 1);
			edit.commit();
			((MainActivity) (MainActivity.mContext)).onResume();
			return true;
		case R.id.item7:
			// purple = 2
			edit.putInt("color", 2);
			edit.commit();
			((MainActivity) (MainActivity.mContext)).onResume();
			return true;
		case R.id.item8:
			// green = 3
			edit.putInt("color", 3);
			edit.commit();
			((MainActivity) (MainActivity.mContext)).onResume();
			return true;
		case R.id.item9:
			// pink = 4
			edit.putInt("color", 4);
			edit.commit();
			((MainActivity) (MainActivity.mContext)).onResume();
			return true;
		}
		return false;
	}

	// TimePicker 由ъ���
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

		}
	};

	protected Dialog onCreateDialog(int id) {
		AlertDialog alert;

		switch (id) {
		case Alarm:
			setAlarm();
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
		}

		// return alert;
		return null;
	}

	// ������ㅼ�
	private void setAlarm() {
		Intent intent = new Intent(MainActivity.this, RepeatingAlarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0,
				intent, 0);

		// We want the alarm to go off 30 seconds from now.
		long firstTime = SystemClock.elapsedRealtime(); // 0�쇰� �명�
		firstTime += (mHour * 1000 * 60 * 60) + (mMinute * 1000 * 60); // �ㅼ����

		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,
				86400 * 1000, sender);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = Toast.makeText(MainActivity.this,
				R.string.repeating_scheduled, Toast.LENGTH_LONG);
		mToast.show();

		// return sender;

	}

	// 배열 지우는 함수. 
	public void deleteItem(int position) {
		int temp = pref.getInt("count", 0);
		
		if(temp != 0) {
			arItem.remove(position);
			edit.putInt("count", temp-1);
			
			for(int i=0; i<arItem.size(); i++) {
				edit.putString("address"+i, arItem.get(i).Address);
				edit.putBoolean("isSelected"+i, arItem.get(i).IsSelected);
			}
			edit.commit();

			((MainActivity) MainActivity.mContext).onResume();
		}
	}
}
