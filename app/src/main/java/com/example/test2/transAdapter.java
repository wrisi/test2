package com.example.test2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;




import java.util.List;
class transAdapter extends ArrayAdapter<Trans> {
    private int mResourceId;
    private DBHelper dbHelper;
    List<Trans> array;

    public transAdapter(Context context, int textViewResourceId, List<Trans> arrayList) {
        super(context, textViewResourceId,arrayList);
        array=arrayList;
        this.mResourceId = textViewResourceId;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Trans trans=getItem(position);
        View view;
        dbHelper=new DBHelper(getContext());
        final SQLiteDatabase db=dbHelper.getWritableDatabase();
        final ViewHolder holder;
        Log.d("this","11111");


        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(mResourceId,parent,false);
            holder=new ViewHolder();
            view.setTag(holder);
        }
        else{
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }
        holder.textView1=(TextView)view.findViewById(R.id.beforeview);
        holder.textView2=(TextView)view.findViewById(R.id.afterview);
        holder.delete=(Button)view.findViewById(R.id.delete);
        if (trans != null) {
            holder.textView1.setText(trans.getBefore());
        }
        if (trans != null) {
            holder.textView2.setText(trans.getAfter());
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("this","11111");
                deleteItem(position,trans,db);
            }
        });
        return view;
    }

    private void deleteItem(int pos, Trans trans, SQLiteDatabase db) {
        array.remove(pos);

        db.delete(DBHelper.TABLE_NAME,"Word='"+trans.getBefore()+"'",null);
        this.notifyDataSetChanged();
    }

    private static class ViewHolder{


        TextView textView1;
        TextView textView2;
        Button delete;
    }
}
