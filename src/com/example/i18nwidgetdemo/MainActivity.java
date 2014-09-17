package com.example.i18nwidgetdemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.webkit.WebView;

public class MainActivity extends Activity {
  final static int MAX_FILE_SIZE = 10 * 1024 * 1024;
  WebView mWebView;
  Context context = this;
  String html;
  String mimeType;
  String encoding;
  int width, height;

  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Display display = getWindowManager().getDefaultDisplay();
    width = display.getWidth(); // deprecated
    height = display.getHeight(); // deprecated

    mWebView = (WebView) findViewById(R.id.webview);
    mWebView.getSettings().setPluginsEnabled(true);
    mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    mWebView.setBackgroundColor(Color.parseColor("#FFFFFF"));
    mWebView.getSettings().setAllowFileAccess(true);
    mWebView.getSettings().setLoadWithOverviewMode(true);
    mWebView.getSettings().setUseWideViewPort(true);
    // mWebView.loadUrl("file:///android_asset/testing.html");
    // html =
    // "<object width=\"100%\" height=\"600\"> <param name=\"movie\" value=\"file:///android_asset/science2.swf\"> <embed src=\"file:///android_asset/science2.swf\" width=\"800\" height=\"600\"> </embed> </object>";
    // html =
    // "<object width=\"0%\" height=\"100%\"><param name=\"movie\" value=\"data.swf\"><param name=\"scale\" value=\"exactfit\"><param name=\"quality\" value=\"high\" /><embed src=\"file:///android_asset/alphabettrain.swf\"width=\"100%\" height=\"100%\" scale=\"exactfit\"></embed></object>";
    checkSDCardWritePermission();
    if (width < 1000) {
      html = "<html><head><style>body{background-color:#FFFFFF;}</style></head><body><center><object width=\"76%\" height=\"100%\" ><param name=\"movie\" value=\"data.swf\"><param name=\"quality\" value=\"high\" /><embed src=\"file:///android_asset/science.swf\" width=\"76%\" height=\"100%\" ></embed></object></center></body></html>";
    } else if (width < 1100 && width > 1000) {
      html = "<html><head><style>body{background-color:#FFFFFF;}</style></head><body><center><object width=\"100%\" height=\"100%\" ><param name=\"movie\" value=\"data.swf\"><param name=\"quality\" value=\"high\" /><embed src=\"file:///android_asset/science.swf\" width=\"100%\" height=\"100%\" ></embed></object></center></body></html>";
    } else if (width > 1100) {
      html = "<html><head><style>body{background-color:#FFFFFF;}</style></head><body><center><object width=\"100%\" height=\"100%\" ><param name=\"movie\" value=\"data.swf\"><param name=\"quality\" value=\"high\" /><embed src=\"file:///android_asset/science.swf\" width=\"100%\" height=\"100%\" ></embed></object></center></body></html>";
    }
    mimeType = "text/html";
    encoding = "utf-8";
    mWebView.loadDataWithBaseURL("null", html, mimeType, encoding, "");
    
  }

  @Override
  protected void onStop() {
    super.onStop();
    mWebView.stopLoading();
    mWebView.destroy();

  }

  @Override
  protected void onPause() {
    super.onPause();
    mWebView.getSettings().setJavaScriptEnabled(false);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mWebView.getSettings().setJavaScriptEnabled(true);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    // TODO Auto-generated method stub
    if (hasFocus) {
      try {
        Class.forName("android.webkit.WebView").getMethod("onResume", (Class[]) null)
            .invoke(mWebView, (Object[]) null);
      } catch (Exception e) {

      }
      mWebView.resumeTimers();
      mWebView.loadDataWithBaseURL("null", html, mimeType, encoding, "");
    } else {
      try {
        Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null)
            .invoke(mWebView, (Object[]) null);
      } catch (Exception e) {

      }
      mWebView.pauseTimers();
    }
    super.onWindowFocusChanged(hasFocus);
  }

  public void checkSDCardWritePermission() {
    try {
      String data = Util.getDBPath();
      File file = new File(Util.getDBPath() + "/hello.xml");
      OutputStream out = new FileOutputStream(file);
      byte[] byte_array = "Its working !!!!".getBytes();
      out.write(byte_array);
      out.close();

      file.delete();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      String error = e.getMessage();
      showAlertDialog("" + error);
      e.printStackTrace();
    }

  }

  public void showAlertDialog(String message) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(message).setCancelable(false).setTitle(R.string.app_name)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
          }
        });

    AlertDialog alert = builder.create();
    alert.show();
  }
}
