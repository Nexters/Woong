package com.example.woong;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class IntroActivity extends Activity {

	Handler h = new Handler();
	Runnable r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		r = new Runnable() {
			public void run() {
				finish();
				Intent i = new Intent(IntroActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			 }
	};

	    h.postDelayed(r,2000); //2000 시간 설정 1000->1초

	}  	
	
	protected void noDestroy() {
		super.onDestroy();
		h.removeCallbacks(r);
	}
	public void onBackPressed(){} //splash 이미지 띄우는 과정에 백 버튼을 누를 수도 있다. 백버튼 막기

}

