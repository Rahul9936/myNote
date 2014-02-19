package com.example.dear.diary;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowRow extends Activity {

	EditText txt_text;
	TextView txt_date;
	String s;
	ImageView image;
	int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.show_row);
		image = (ImageView) findViewById(R.id.imageId);
		txt_text = (EditText) findViewById(R.id.text_row);
		txt_date = (TextView) findViewById(R.id.show_row_date);

		Bundle basket = new Bundle();
		basket = getIntent().getExtras();
		i = basket.getInt("position");
		i = i + 1;
		Database_diary db_sanjeev = new Database_diary(ShowRow.this);
		db_sanjeev.open();
		s = db_sanjeev.getTextFromFb(i);
		String sr = db_sanjeev.getDate(i);
		String img = db_sanjeev.getImgPath(i);
		
		if(!img.equals(""))
		image.setImageBitmap(BitmapFactory.decodeFile(img));
		db_sanjeev.close();
		Date d = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);

		try {
			d = df.parse(sr);

		} catch (Exception e) {

		}
		Typeface font_date = Typeface.createFromAsset(getAssets(),
				"fonts/buxtonsketch.ttf");
		txt_text.setTypeface(font_date);
		txt_text.setText(s);

		Typeface font_text = Typeface.createFromAsset(getAssets(),
				"fonts/pristina.ttf");
		txt_date.setTypeface(font_text);
		txt_date.setPaintFlags(txt_date.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		txt_date.setText(df.format(d));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.delete, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_delete:
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setTitle("Delete Note");
			dialog.setMessage("Are u sure?");
			dialog.setButton("Yes", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Database_diary db = new Database_diary(ShowRow.this);
					db.open();
					db.dBdelete(i);
					db.close();
					ShowRow.this.finish();
				}
			});
			dialog.setButton2("No", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			dialog.show();
            
			break;
		case R.id.menu_edit:
			/*
			 * Intent in = new Intent(ShowRow.this, Edit.class);
			 * in.putExtra("position", i);
			 * in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(in);
			 */
			// ShowRow.this.onPause();
			String st = txt_text.getText().toString();
			Database_diary Db_Diary = new Database_diary(this);
			Db_Diary.open();

			Log.d("POsition of row id is", "" + (i + 1));
			Db_Diary.updateText(st, i);
			Db_Diary.close();
			break;
		case R.id.share:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, s);
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, s));
			break;
		}
		
		return false;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent inte = new Intent(ShowRow.this, ShowList.class);
		inte.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(inte);
	}

}
