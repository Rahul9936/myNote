package com.example.dear.diary;

import android.app.Activity;
import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

public class Dialog_Pass extends Activity implements OnClickListener,
		OnCheckedChangeListener, OnItemSelectedListener {

	EditText et_pass1, et_pass2, et_answer;
	Button btn_save, btn_cancel;
	CheckBox check;
	Spinner sppin;
	String s = "";
	BackupManager backUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		intitialize();
		SharedPreferences shared = getSharedPreferences("thisPrefs", 0);
		boolean value = shared.getBoolean("CheckBox", false);
		check.setChecked(value);
		checkBoxMethod(value);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.country_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sppin.setAdapter(adapter);
	}

	private void intitialize() {
		// TODO Auto-generated method stub
		et_pass1 = (EditText) findViewById(R.id.setting_edit1);
		et_pass2 = (EditText) findViewById(R.id.setting_edit2);
		et_answer = (EditText) findViewById(R.id.setting_edit3);
		sppin = (Spinner) findViewById(R.id.setting_sniper);
		sppin.setOnItemSelectedListener(this);
		check = (CheckBox) findViewById(R.id.setting_checkBox);
		check.setOnCheckedChangeListener(this);
		findViewById(R.id.setting_btn_save).setOnClickListener(this);
		findViewById(R.id.setting_btn_cancel).setOnClickListener(this);
		backUp = new BackupManager(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.setting_btn_save:
			if(et_pass1.getText().toString().equals(et_pass2.getText().toString())){
			SharedPreferences shar = getSharedPreferences("thisPrefs", 0);
			SharedPreferences.Editor edit = shar.edit();
			edit.putString("preference_password", et_pass1.getText().toString());
//			edit.putString("preference_re_password", et_pass2.toString());
			edit.putString("preference_answer", et_answer.getText().toString());
			edit.putString("preference_spinner_value", s);
			edit.commit();
			backUp.dataChanged();
			Toast.makeText(getApplicationContext(), "Preferences saved",
					Toast.LENGTH_SHORT).show();
			finish();}else
				Toast.makeText(getApplicationContext(), "Passwords does not match",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.setting_btn_cancel:
			finish();
			break;

		default:
			break;
		}
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		checkBoxMethod(isChecked);
	}

	protected void checkBoxMethod(boolean values) {
		if (values) {
			et_pass1.setEnabled(true);et_pass1.setFocusable(true);
			et_pass2.setEnabled(true);et_pass2.setFocusable(true);
			et_answer.setEnabled(true);et_answer.setFocusable(true);
			sppin.setEnabled(true);
		} else {
			et_pass1.setEnabled(false);
			//et_pass1.setFocusable(false);
			et_pass2.setEnabled(false);
			//et_pass2.setFocusable(false);
			et_answer.setEnabled(false);
			//et_answer.setFocusable(false);
			sppin.setEnabled(false);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences share = getSharedPreferences("thisPrefs", 0);
		SharedPreferences.Editor editor = share.edit();
		if (check.isChecked())
			editor.putBoolean("CheckBox", true);
		
		else
			editor.putBoolean("CheckBox", false);
		editor.commit();
		backUp.dataChanged();
	}

	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long arg3) {
		// TODO Auto-generated method stub
		s = parent.getItemAtPosition(pos).toString();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		s = "";
	}
}
