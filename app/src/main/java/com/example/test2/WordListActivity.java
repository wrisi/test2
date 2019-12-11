package com.example.test2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class WordListActivity extends AppCompatActivity {
    DBHelper dbHelper=new DBHelper(this);
    List<Trans> transList=new ArrayList<>();
    boolean isLongClicked=false;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor=db.query("WordList",null,null,null,null,null,null);

        listView=(ListView)findViewById(R.id.list_view);
        if(cursor.moveToNext()){
            while(cursor.moveToNext()){
                Trans trans=new Trans(cursor.getInt(cursor.getColumnIndex("_id")),cursor.getString(cursor.getColumnIndex("WORD")),cursor.getString(cursor.getColumnIndex("TRANS")));
                transList.add(trans);
            }
            cursor.close();
        }
        transAdapter transAdapter=new transAdapter(WordListActivity.this,R.layout.item,transList);
        listView.setAdapter(transAdapter);
        transAdapter.notifyDataSetChanged();
    }


}
