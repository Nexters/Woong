package com.example.woong;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {
	String[] list;
	AutoCompleteTextView test;
	ArrayAdapter<String> myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		list = getResources().getStringArray(R.array.address);
		test = (AutoCompleteTextView) findViewById(R.id.acTextView);

		myAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, list);
		test.setAdapter(myAdapter);

		// AutoCompleteTextView 자동완성 리스트에 클릭 이벤트
		test.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.acTextView);
				// TextView textView = (TextView) findViewById(R.id.myTextView);
				// textView.setText(autoComplete.getText().toString());

				// 열려있는 키패드 닫기
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(autoComplete.getWindowToken(), 0);

				// preference, xml 파일에 저장.
				SharedPreferences pref = getSharedPreferences("PrefTest1", 0);
				SharedPreferences.Editor edit = pref.edit();

				// 현재 배열 개수 구하기
				int temp = pref.getInt("count", 0);

				// 10보다 작으면 저장하고 한 개 추가 
				if (temp < 10) {
					edit.putString("address"+temp, autoComplete.getText().toString());
					edit.putInt("count", temp+1);
				}
				edit.commit();

				// 액티비티 닫기.
				finish();
			}
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
}
