package com.example.dear.diary;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ShowList extends ListActivity {

	int ps;
	SimpleCursorAdapter scad;
	Database_diary dbd;
	Cursor c;
    SharedPreferences shared;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_diary);
		shared = getSharedPreferences("thisPrefs", 0);
		if(shared.getBoolean("restore", true))
		restoreDb();
		ListView lvd = getListView();
		dbd = new Database_diary(this);
		dbd.open();
		c = dbd.getCursor();
		// c.requery();
		startManagingCursor(c);

		String[] from = new String[] { Database_diary.DB_TEXT,
				Database_diary.DB_DATE };
		int[] to = new int[] { R.id.practice_text, R.id.practice_datetime };

		scad = new SimpleCursorAdapter(this, R.layout.practice, c, from, to);
		scad.notifyDataSetChanged();
		scad.notifyDataSetInvalidated();
		setListAdapter(scad);

		lvd.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShowList.this, ShowRow.class);
				i.putExtra("position", position);
				i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_show_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent i = new Intent(ShowList.this, Edit_Diary.class);
			startActivity(i);
			break;

		case R.id.pref:
			Intent intet = new Intent(ShowList.this, Dialog_Pass.class);
			startActivity(intet);
			break;

		case R.id.backup:
			Backup bc = new Backup(ShowList.this);
			if(bc.backupDatabase())
			Toast.makeText(getApplicationContext(), "backup complete",
					Toast.LENGTH_SHORT).show();
			else 
				Toast.makeText(getApplicationContext(), "backup failed",
						Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu_exit:
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setTitle("Do u really want to exit");
			ad.setMessage("are you sure?");
			ad.setButton("Yes", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			ad.setButton2("No", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// ad.cancel();
				}
			});
			ad.show();
		}
		return false;
	}

	private void restoreDb() {
		// TODO Auto-generated method stub
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setTitle("Do u want to restore previous db");
		ad.setMessage("are you sure?");
		ad.setButton("Yes", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Backup bc = new Backup(ShowList.this);
				if(bc.restoreDatabase())
					Toast.makeText(getApplicationContext(), "restore complete",
							Toast.LENGTH_SHORT).show();
			}
		});
		ad.setButton2("No", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// ad.cancel();
			}
		});
		ad.show();
		
		SharedPreferences.Editor edit = shared.edit();
		edit.putBoolean("restore", false);
		edit.commit();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			if (this.scad != null) {
				this.scad.getCursor().close();
				this.scad = null;
			}
			if (this.c != null) {
				this.c.close();
			}
			if (this.dbd != null) {
				this.dbd.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}