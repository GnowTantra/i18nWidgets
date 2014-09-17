package com.example.i18nwidgetdemo;

import gujaratirendering.IndicTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FontTesting extends Activity {
  IndicTextView tv1;
  Button cont;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.font_test_layout);

    Typeface tf = Typeface.createFromAsset(getAssets(), "shruti.ttf");
    AssetManager am = getAssets();
    InputStream inputStream = null;
    try {
      inputStream = am.open("shruti.ttf");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    File font = createFileFromInputStream(inputStream);

    tv1 = (IndicTextView) findViewById(R.id.textView1);
    tv1.setTypeface(tf);
    String fontPath = font.getAbsolutePath();
    tv1.setFontPath(fontPath);
    // tv1.setTextSize();
    //tv1.setTextColor(0xFF0000FF);

    
    tv1.setText("Text : " + "ગુજરાતી ટેક્ટ યશસ્વી.");
    cont = (Button) findViewById(R.id.button1);
    cont.setOnClickListener(new OnClickListener() {

     /* @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent in = new Intent(FontTesting.this, ExtraActivity.class);
        startActivity(in);
      }
     */
	
    });
  }

  private File createFileFromInputStream(InputStream inputStream) {

    try {
      File f = new File("/data/data/com.example.i18nwidgetdemo", "Lohit-Gujarati.ttf");
      OutputStream outputStream = new FileOutputStream(f);
      byte buffer[] = new byte[1024];
      int length = 0;

      while ((length = inputStream.read(buffer)) > 0) {
        outputStream.write(buffer, 0, length);
      }

      outputStream.close();
      inputStream.close();

      return f;
    } catch (IOException e) {
      // Logging exception
    }

    return null;
  }
}
