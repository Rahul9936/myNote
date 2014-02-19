package com.example.dear.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Snipper_ extends Activity {

	EditText edit_answer;
	String s = "";
	SharedPreferences shared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.snipper_xml);
		shared = getSharedPreferences("thisPrefs", 0);
		edit_answer = (EditText) findViewById(R.id.snipper_answer);

		Spinner sp = (Spinner) findViewById(R.id.snipper);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.country_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				s = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				s = "";
			}
		});
		findViewById(R.id.snipper_submit).setOnClickListener(
				new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						s = s.trim();
						if (s.equals(shared.getString(
								"preference_spinner_value", null))
								&& edit_answer
										.getText()
										.toString()
										.equals(shared.getString(
												"preference_answer", null))) {
							
							Intent in = new Intent(Snipper_.this,
									ShowList.class);
							startActivity(in);
						}else{
							Toast.makeText(getApplicationContext(),
									"TryAgain", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(Snipper_.this, Password.class);
		startActivity(in);
		
	}
}
