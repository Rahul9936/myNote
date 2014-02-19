package com.example.dear.diary;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class Edit extends Activity{

	EditText et;
	String picturePath = "";
	int pos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.edit1);
		
		et = (EditText) findViewById(R.id.editText1);
		
		Bundle basket  = new Bundle();
		basket = getIntent().getExtras();
	    pos = basket.getInt("position");
		
		Database_diary db = new Database_diary(this);
		db.open();
		String s = db.getInfo(pos);
		db.close();
		Typeface localTF = Typeface.createFromAsset(getAssets(), "fonts/pristina.ttf");
	    this.et.setTypeface(localTF);
		et.setText(s);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_diary_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		//save 
		case R.id.edit_diary_ok:
			String st = et.getText().toString();
			Database_diary Db_Diary = new Database_diary(this);
			Db_Diary.open();
			int position = pos;
			Log.d("POsition of row id is", "" + (position + 1));
			Db_Diary.updateText(st, position);
			Db_Diary.close();
/*
			Toast.makeText(getApplicationContext(), "Note Added"+ pos,
					Toast.LENGTH_SHORT).show();*/
			/*Intent inte = new Intent(Edit.this, ShowList.class);
			startActivity(inte);*/
			finish();
			break;
			
			//cancel
		case R.id.menu_gallery:
			Edit.this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
/*	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(Edit.this, ShowRow.class);
		startActivity(intent);
	}*/
}
