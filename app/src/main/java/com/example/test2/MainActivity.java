package com.example.test2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import demo.TransApi;
import demo.youdaoAPI;

//周琦斩获CBA大奖，3比2再超阿联，完胜郭少王哲林已是第一人
public class MainActivity extends AppCompatActivity {
    String Entered,Result;
    String APP_ID = "20191016000341842";
    String SECURITY_KEY = "GUUoG19cmg49fIIuUsDY";
    TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase db= dbHelper.getWritableDatabase();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final EditText E1=(EditText)findViewById(R.id.E1);
        final TextView T1=(TextView)findViewById(R.id.T1);
        final EditText E2=(EditText)findViewById(R.id.E2);
        final TextView T2=(TextView)findViewById(R.id.T2);
        final Button B1=(Button)findViewById(R.id.B1);
        final Button B2=(Button)findViewById(R.id.B2);
        final Button B3=(Button)findViewById(R.id.B3);
        final Button B4=(Button)findViewById(R.id.B4);
        final Button B5=(Button)findViewById(R.id.B5);
        final Button B6=(Button)findViewById(R.id.B6);
        final Button B7=(Button)findViewById(R.id.WordList);
        final LinearLayout LC=(LinearLayout)findViewById(R.id.LC);
        final LinearLayout LE=(LinearLayout)findViewById(R.id.LE);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Entered=E1.getText().toString();
                if(Entered.equals("")){
                    Toast.makeText(MainActivity.this,"Enter something",Toast.LENGTH_SHORT).show();
                }
                else {
                    String selection="WORD='"+Entered+"'";
                    String T=api.getTransResult(Entered,"en","zh");
                    Result=decodeUnicode(T.substring(T.indexOf("dst")+6,T.indexOf("}]")-1));
                    T1.setText(Result);
                    Cursor cursor=db.query("WordList",null,selection,null,null,null,null);
                    if(!cursor.moveToFirst()){
                        B2.setVisibility(View.VISIBLE);
                    }
                    cursor.close();
                }
                hideKeyboard();
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
                values.put(DBHelper.Word,Entered);
                values.put(DBHelper.Translated,Result);
                db.insert(DBHelper.TABLE_NAME, null, values);
                Toast.makeText(getApplicationContext(),"successfully added",Toast.LENGTH_SHORT).show();
                B2.setVisibility(View.INVISIBLE);
            }
        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LE.setVisibility(View.VISIBLE);
                LC.setVisibility(View.INVISIBLE);
                T2.setText("");
                E2.setText("");
            }
        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LC.setVisibility(View.VISIBLE);
                LE.setVisibility(View.INVISIBLE);
                T1.setText("");
                E1.setText("");
            }
        });
        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Entered=E2.getText().toString();
                if(Entered.equals("")){
                    Toast.makeText(MainActivity.this, "Enter something", Toast.LENGTH_SHORT).show();
                }
                else{
                    String selection="WORD='"+Entered+"'";
                    String T=api.getTransResult(Entered,"zh","en");
                    Result=decodeUnicode(T.substring(T.indexOf("dst")+6,T.indexOf("}]")-1));
                    T2.setText(Result);
                    Cursor cursor=db.query("WordList",null,selection,null,null,null,null);
                    if(!cursor.moveToFirst()){
                        B6.setVisibility(View.VISIBLE);
                    }
                    cursor.close();
                }
                hideKeyboard();
            }
        });
        B6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
                values.put(DBHelper.Word,Entered);
                values.put(DBHelper.Translated,Result);
                db.insert(DBHelper.TABLE_NAME, null, values);
                Toast.makeText(getApplicationContext(),"successfully added",Toast.LENGTH_SHORT).show();
                B6.setVisibility(View.INVISIBLE);
            }
        });
        B7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WordListActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_cart://监听菜单按钮
                Intent intent = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
    private void hideKeyboard() {
        View viewFocus = this.getCurrentFocus();
        if (viewFocus != null) {
            InputMethodManager imManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imManager != null) {
                imManager.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
            }
        }
    }
}