package com.example.dear.diary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Edit_Diary extends Activity {

	int position;
	EditText text_diary;
	private static int RESULT_LOAD_IMAGE = 1;
	private static int counter = 0;
	public static String picturePath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.new_layout);

		text_diary = (EditText) findViewById(R.id.editText1);
		Typeface localTF = Typeface.createFromAsset(getAssets(),
				"fonts/pristina.ttf");
		this.text_diary.setTypeface(localTF);

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

		// save
		case R.id.edit_diary_ok:
			
				uploadAndDownload(picturePath);
			
			Edit_Diary.this.finish();
			break;

		// cancel
		case R.id.menu_gallery:
			
			Edit_Diary.this.finish();
			break;

		// image adding
		case R.id.edit_diary_addimg:
			/*Intent intent = new Intent(Edit_Diary.this, MyImages.class);
			startActivity(intent);*/
			Intent in = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(in, RESULT_LOAD_IMAGE);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {

			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			 picturePath = cursor.getString(columnIndex);
			cursor.close();
			ImageView imageView = (ImageView) findViewById(R.id.imgid);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			//uploadAndDownload(picturePath);
		} 
	}

	private void uploadAndDownload(String pic) {

		Database_diary Db_Diary = new Database_diary(this);
		Db_Diary.open();
		position = Db_Diary.getRowNo() + 1;
//		if (counter == 0) {
			String st = text_diary.getText().toString();
			Db_Diary.insert_diary(position, st, pic);
			Db_Diary.close();
		/*} else
			Db_Diary.updateImagePath(position, pic);
		Db_Diary.close();*/
		//counter++;
		
	}

}
