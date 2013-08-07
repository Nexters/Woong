package com.example.woong;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;

public class MainActivity extends Activity {
private static final int Alarm = 0;
	
	/*
	 * 알람관련 맴버 변수
	 */
	 // 알람 메니저
	 private AlarmManager mManager;
	 // 설정 일시
	 private GregorianCalendar mCalendar;
	 //일자 설정 클래스
	// private DatePicker mDate;
	 //시작 설정 클래스
	 public TimePicker mTime;
	 
	 
	/*
	 * 통지 관련 맴버 변수
	 */
	 private NotificationManager mNotification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //통지 매니저를 취득
		  mNotification = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		 
		  //알람 매니저를 취득
		  mManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		  //현재 시각을 취득
		  mCalendar = new GregorianCalendar();
		  Log.i("HelloAlarmActivity",mCalendar.getTime().toString());
		  
		  setContentView(R.layout.activity_main);
		
		  mTime = (TimePicker)findViewById(R.id.time_picker);
		  //mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
		  //mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
		  //mTime.setOnTimeChangedListener(this);
		
	}
	 
	public void mOnClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			 startActivity(new Intent(this,SearchActivity.class)); 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*public boolean onPrepareOptionsMenu(Menu menu) {
		//int color = background.getTextColors().getDefaultColor();
		if (iv == Color.RED) {
			menu.findItem(R.id.item4).setChecked(true);
		}
	
		if (color == Color.BLUE) {
			menu.findItem(R.id.item5).setChecked(true);
		}
		return true;
	}*/
    
	
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    
    	case R.id.item1:
			//mBtn.setTextColor(Color.RED);
    		//setBackgroundColor(FF0000);
    		showDialog(Alarm);
    		return true;
    		
    	case R.id.item4:
			//mBtn.setTextColor(Color.RED);
    		//setBackgroundColor(FF0000);
    		
    		return true;
   
    	case R.id.item5:
			setTitleColor(Color.BLUE);
    		return true;
    	}
    	return false;
    }
	
	protected Dialog onCreateDialog(int id) {
		AlertDialog alert;
		
		switch(id) {
		case Alarm:
			final LinearLayout linear = (LinearLayout)View.inflate(this, R.layout.custom_dialog, null);
		
			alert = new AlertDialog.Builder(this)
			.setTitle("Set time")
			.setView(linear)
			.setCancelable(false)
			.setPositiveButton("Set", 
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					setAlarm();
					
				}})
				
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				
			}}).create();
			  
			
			break;
			
		default:
			alert = null;
			
		} 
		
		return alert;
	}
	
	
	//알람의 설정
	  private void setAlarm() {
	   mManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent());
	  Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
	  }
	 
	  //알람의 설정 시각에 발생하는 인텐트 작성 --->여기에 알람설정이나 진동 설정 하면 됨 ㅎㅎㅎ
	  private PendingIntent pendingIntent() {
	   Intent i = new Intent(getApplicationContext(), Dialog2.class);   
	   PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
     
	   return pi;
	  }

	  //시각 설정 클래스의 상태변화 리스너
	  public void onTimeChanged (TimePicker view, int hourOfDay, int minute) {
	  // mCalendar.set (mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), hourOfDay, minute);
		    int currentYY = mCalendar.get(Calendar.YEAR);
			int currentMM = mCalendar.get(Calendar.MONTH);
			int currentDD = mCalendar.get(Calendar.DAY_OF_MONTH);
			
			mCalendar.set(currentYY, currentMM, currentDD, mTime.getCurrentHour(), mTime.getCurrentMinute(),00);

			if(mCalendar.getTimeInMillis() < mCalendar.getTimeInMillis()){
				mCalendar.set(currentYY, currentMM, currentDD+1, mTime.getCurrentHour(), mTime.getCurrentMinute(),00);
				Log.i("TAG",mCalendar.getTimeInMillis()+":");
			}
			
	   Log.i("HelloAlarmActivity",mCalendar.getTime().toString());
	  }
	
}
