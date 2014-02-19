package com.example.dear.diary;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.BackupManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends Activity {

	EditText et_passwd;
	Button btn_submit, btn_spinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences shared = getSharedPreferences("thisPrefs", 0);
		
		if (!shared.getBoolean("CheckBox", false)) {
			Intent inr = new Intent(this, ShowList.class);
			startActivity(inr);
		} else {
			setContentView(R.layout.activity_password);
			et_passwd = (EditText) findViewById(R.id.password);
			btn_submit = (Button) findViewById(R.id.button1);
			btn_spinner = (Button) findViewById(R.id.forgot_password);
			
			btn_submit.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					SharedPreferences shared = getSharedPreferences("thisPrefs", 0);
					if (shared.getString("preference_password", "").equals(
							et_passwd.getText().toString())) {
						Intent in = new Intent(Password.this, ShowList.class);
						startActivity(in);
					}else Toast.makeText(getApplicationContext(), "Incorrect Password",
							Toast.LENGTH_SHORT).show();
				}
			});
			
			btn_spinner.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Password.this, Snipper_.class);
					startActivity(i);
				}
			});
		}
	}
}