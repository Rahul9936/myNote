package com.example.dear.diary;


import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;


public class MyBackUpAgent extends BackupAgentHelper {

	public static final String MY_PREFS = "thisPrefs";
	static final String MY_PREFS_BACKUP_KEY = "myprefs";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(
				this, MY_PREFS);
		addHelper(MY_PREFS_BACKUP_KEY, helper);
	}

}
