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

	public MyListAdapter MyAdapter;
	public ListView MyList;

	// ��� �ㅼ� �대���
	public TimePicker mTime;
	private int mHour;
	private int mMinute;

	ArrayList<MyItem> arItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
		// save address using preference
	}

	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences pref = getSharedPreferences("PrefTest", 0);
		SharedPreferences.Editor edit = pref.edit();
		String myStr = "서울시 강남구";

		edit.putString("address4", myStr);
		edit.putInt("count", 0);

		edit.commit();

		//
		arItem = new ArrayList<MyItem>();
		MyItem mi;
		mi = new MyItem(R.drawable.cloud_285x123, "서울시 마포구", "30");
		arItem.add(mi);
		mi = new MyItem(R.drawable.rain_285x197, "서울시 서대문구", "25");
		arItem.add(mi);
		mi = new MyItem(R.drawable.snow_93x225, "서울시 관악구", "40");
		arItem.add(mi);
		mi = new MyItem(R.drawable.cloud_285x123,
				pref.getString("address4", ""), "25");
		arItem.add(mi);

		int myColor = pref.getInt("color", 0);
		// MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.icontext,
		// arItem, myColor);
		MyAdapter = new MyListAdapter(this, R.layout.icontext, arItem, myColor);

		// ListView MyList;
		MyList = (ListView) findViewById(R.id.list);
		MyList.setDividerHeight(0);
		MyList.setAdapter(MyAdapter);

	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		
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
		SharedPreferences pref = getSharedPreferences("PrefTest", 0);
		SharedPreferences.Editor edit = pref.edit();

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
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);

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

	class MyItem {
		int Icon;
		String Name;
		String Temperature;
		boolean Selected;

		MyItem(int aIcon, String aName, String aTemperature) {
			Icon = aIcon;
			Name = aName;
			Temperature = aTemperature + "°C";
			Selected = false;
		}
	}

	// Adapter class
	class MyListAdapter extends BaseAdapter {

		Context maincon;
		LayoutInflater Inflater;
		ArrayList<MyItem> arSrc;
		int layout;
		// color
		int currentColor;

		public MyListAdapter(Context context, int alayout,
				ArrayList<MyItem> aarSrc, int aColor) {
			maincon = context;
			Inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arSrc = aarSrc;
			layout = alayout;
			// color
			currentColor = aColor;
		}

		public int getCount() {
			return arSrc.size();
		}

		public String getItem(int position) {
			return arSrc.get(position).Name;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;

			if (convertView == null) {
				convertView = Inflater.inflate(layout, parent, false);
			}
			ImageView img = (ImageView) convertView
					.findViewById(R.id.weather_icon);
			img.setImageResource(arSrc.get(position).Icon);

			TextView textViewAddress = (TextView) convertView
					.findViewById(R.id.address);
			// typeface.
			textViewAddress.setTypeface(Typeface.createFromAsset(getAssets(),
					"RixMGoB.ttf"));
			textViewAddress.setText(arSrc.get(position).Name);

			TextView textViewTemperature = (TextView) convertView
					.findViewById(R.id.temperature);
			textViewTemperature.setText(arSrc.get(position).Temperature);

			// Toggle?
			convertView.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (arSrc.get(pos).Selected == true) {
						v.setBackgroundResource(R.drawable.unselected_720x128);
						arSrc.get(pos).Selected = false;
					} else {
						// elsseeee
						// 색칠하는 함수.
						switchPaint(v, pos);
						arSrc.get(pos).Selected = true;
					}
				}
			});

			// change background
			if (arSrc.get(pos).Selected == false) {
				convertView.setBackgroundResource(R.drawable.unselected_720x128);
				
			} else {
				// elsseeee
				// 색칠하는 함수.
				switchPaint(convertView, pos);
				
			}
			
			return convertView;
		}

		public void switchPaint(View convertView, int pos) {
			switch (currentColor) {
			case 0:
				// blue = 0;
				switch (pos) {
				case 0:
					convertView.setBackgroundResource(R.drawable.switch1_1);
					break;
				case 1:
					convertView.setBackgroundResource(R.drawable.switch1_2);
					break;
				case 2:
					convertView.setBackgroundResource(R.drawable.switch1_3);
					break;
				case 3:
					convertView.setBackgroundResource(R.drawable.switch1_4);
					break;
				case 4:
					convertView.setBackgroundResource(R.drawable.switch1_5);
					break;
				case 5:
					convertView.setBackgroundResource(R.drawable.switch1_6);
					break;
				case 6:
					convertView.setBackgroundResource(R.drawable.switch1_7);
					break;
				case 7:
					convertView.setBackgroundResource(R.drawable.switch1_8);
					break;
				case 8:
					convertView.setBackgroundResource(R.drawable.switch1_9);
					break;
				case 9:
					convertView.setBackgroundResource(R.drawable.switch1_10);
					break;
				}
				break;
			case 1:
				// red = 1;
				switch (pos) {
				case 0:
					convertView.setBackgroundResource(R.drawable.switch2_1);
					break;
				case 1:
					convertView.setBackgroundResource(R.drawable.switch2_2);
					break;
				case 2:
					convertView.setBackgroundResource(R.drawable.switch2_3);
					break;
				case 3:
					convertView.setBackgroundResource(R.drawable.switch2_4);
					break;
				case 4:
					convertView.setBackgroundResource(R.drawable.switch2_5);
					break;
				case 5:
					convertView.setBackgroundResource(R.drawable.switch2_6);
					break;
				case 6:
					convertView.setBackgroundResource(R.drawable.switch2_7);
					break;
				case 7:
					convertView.setBackgroundResource(R.drawable.switch2_8);
					break;
				case 8:
					convertView.setBackgroundResource(R.drawable.switch2_9);
					break;
				case 9:
					convertView.setBackgroundResource(R.drawable.switch2_10);
					break;
				}
				break;
			case 2:
				// purple = 2;
				switch (pos) {
				case 0:
					convertView.setBackgroundResource(R.drawable.switch3_1);
					break;
				case 1:
					convertView.setBackgroundResource(R.drawable.switch3_2);
					break;
				case 2:
					convertView.setBackgroundResource(R.drawable.switch3_3);
					break;
				case 3:
					convertView.setBackgroundResource(R.drawable.switch3_4);
					break;
				case 4:
					convertView.setBackgroundResource(R.drawable.switch3_5);
					break;
				case 5:
					convertView.setBackgroundResource(R.drawable.switch3_6);
					break;
				case 6:
					convertView.setBackgroundResource(R.drawable.switch3_7);
					break;
				case 7:
					convertView.setBackgroundResource(R.drawable.switch3_8);
					break;
				case 8:
					convertView.setBackgroundResource(R.drawable.switch3_9);
					break;
				case 9:
					convertView.setBackgroundResource(R.drawable.switch3_10);
					break;
				}
				break;

			case 3:
				// green = 3;
				switch (pos) {
				case 0:
					convertView.setBackgroundResource(R.drawable.switch4_1);
					break;
				case 1:
					convertView.setBackgroundResource(R.drawable.switch4_2);
					break;
				case 2:
					convertView.setBackgroundResource(R.drawable.switch4_3);
					break;
				case 3:
					convertView.setBackgroundResource(R.drawable.switch4_4);
					break;
				case 4:
					convertView.setBackgroundResource(R.drawable.switch4_5);
					break;
				case 5:
					convertView.setBackgroundResource(R.drawable.switch4_6);
					break;
				case 6:
					convertView.setBackgroundResource(R.drawable.switch4_7);
					break;
				case 7:
					convertView.setBackgroundResource(R.drawable.switch4_8);
					break;
				case 8:
					convertView.setBackgroundResource(R.drawable.switch4_9);
					break;
				case 9:
					convertView.setBackgroundResource(R.drawable.switch4_10);
					break;
				}
				break;
			case 4:
				// pink = 4;
				switch (pos) {
				case 0:
					convertView.setBackgroundResource(R.drawable.switch5_1);
					break;
				case 1:
					convertView.setBackgroundResource(R.drawable.switch5_2);
					break;
				case 2:
					convertView.setBackgroundResource(R.drawable.switch5_3);
					break;
				case 3:
					convertView.setBackgroundResource(R.drawable.switch5_4);
					break;
				case 4:
					convertView.setBackgroundResource(R.drawable.switch5_5);
					break;
				case 5:
					convertView.setBackgroundResource(R.drawable.switch5_6);
					break;
				case 6:
					convertView.setBackgroundResource(R.drawable.switch5_7);
					break;
				case 7:
					convertView.setBackgroundResource(R.drawable.switch5_8);
					break;
				case 8:
					convertView.setBackgroundResource(R.drawable.switch5_9);
					break;
				case 9:
					convertView.setBackgroundResource(R.drawable.switch5_10);
					break;
				}
				break;
			}

		}
	}
}
