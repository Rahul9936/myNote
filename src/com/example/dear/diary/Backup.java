package com.example.dear.diary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Backup {

	Context mContext;

	public Backup(Context c) {
		this.mContext = c;
	}

	public File getCurrentDatabaseFile() {
		return new File("/data/data/" + mContext.getPackageName()
				+ "/databases/", "DIARY_DATABASE");
	}

	public File getBackupDatabaseFile() {
		File dir = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/backup");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return new File(dir, "DIARY_DATABASE");
	}

	public final boolean backupDatabase() {
		File from = this.getCurrentDatabaseFile();
		File to = this.getBackupDatabaseFile();
		try {
			copyFile(from, to);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("TAG", "Error backuping up database: " + e.getMessage(), e);
		}
		return false;
	}
	
	public final boolean restoreDatabase(){
		File to = this.getCurrentDatabaseFile();
		File from = this.getBackupDatabaseFile();
		try {
			copyFile(from, to);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("TAG", "Error restoring database: " + e.getMessage(), e);
		}
		return false;
	}

	public static void copyFile(File src, File dst) throws IOException {
		@SuppressWarnings("resource")
		FileInputStream in = new FileInputStream(src);
		@SuppressWarnings("resource")
		FileOutputStream out = new FileOutputStream(dst);
		FileChannel fromChannel = null, toChannel = null;
		try {
			fromChannel = in.getChannel();
			toChannel = out.getChannel();
			fromChannel.transferTo(0, fromChannel.size(), toChannel);
		} finally {
			if (fromChannel != null)
				fromChannel.close();
			if (toChannel != null)
				toChannel.close();
		}
	}
}
