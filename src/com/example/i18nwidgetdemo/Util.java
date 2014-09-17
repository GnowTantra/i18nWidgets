package com.example.i18nwidgetdemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Util {

  public static String DB_PATH;

  public static String getDeviceUniqueId(Context context) {
    TelephonyManager manager = (TelephonyManager) context
        .getSystemService(Context.TELEPHONY_SERVICE);
    final String tmDevice, tmSerial, tmPhone, androidId;
    // tmDevice = "" + manager.getDeviceId();
    // tmSerial = "" + manager.getSimSerialNumber();
    androidId = ""
        + android.provider.Settings.Secure.getString(context.getContentResolver(),
            android.provider.Settings.Secure.ANDROID_ID);

    // UUID deviceUuid = new UUID(androidId.hashCode()/*,
    // ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode()*/);
    String deviceId = androidId.toString();
    Log.e("Device Id", deviceId);
    return deviceId;
  }

  public static String getDBPath() {
    /*
     * DB_PATH = System.getenv("SECONDARY_STORAGE"); if(DB_PATH==null){ DB_PATH =
     * "/mnt/sdcard/external_sd";Environment.getExternalStorageDirectory().getAbsolutePath(); }
     * return DB_PATH;
     */
    File file = new File("/system/etc/vold.fstab");
    FileReader fr = null;
    BufferedReader br = null;

    try {
      fr = new FileReader(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try {
      if (fr != null) {
        br = new BufferedReader(fr);
        String s = br.readLine();
        while (s != null) {
          if (s.startsWith("dev_mount")) {
            String[] tokens = s.split("\\s");
            DB_PATH = tokens[2]; // mount_point
            if (!Environment.getExternalStorageDirectory().getAbsolutePath().equals(DB_PATH)) {
              break;
            }
          }
          s = br.readLine();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fr != null) {
          fr.close();
        }
        if (br != null) {
          br.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return DB_PATH;
    // return DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
  }

  public static String getInternalDBPath() {
    /*
     * DB_PATH = System.getenv("SECONDARY_STORAGE"); if(DB_PATH==null){ DB_PATH =
     * "/mnt/sdcard/external_sd";Environment.getExternalStorageDirectory().getAbsolutePath(); }
     * return DB_PATH;
     */
    File file = new File("/system/etc/vold.fstab");
    FileReader fr = null;
    BufferedReader br = null;

    try {
      fr = new FileReader(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try {
      if (fr != null) {
        br = new BufferedReader(fr);
        String s = br.readLine();
        while (s != null) {
          if (s.startsWith("dev_mount")) {
            String[] tokens = s.split("\\s");
            DB_PATH = tokens[2]; // mount_point
            if (!Environment.getExternalStorageDirectory().getAbsolutePath().equals(DB_PATH)) {
              break;
            }
          }
          s = br.readLine();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fr != null) {
          fr.close();
        }
        if (br != null) {
          br.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // return DB_PATH;
    return DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
  }

}
