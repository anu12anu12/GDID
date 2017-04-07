package com.gdid.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.gdid.com.gdid.database.DatabaseHelper;
import com.gdid.material_management.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivityStoredCodes extends AppCompatActivity {

    public GridView gridView;
    SQLiteDatabase exDb;
    DatabaseHelper myDb;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_codes);

        gridView = (GridView)findViewById(R.id.dataView);

        if(!searchData())
        {
            Toast mytoast= Toast.makeText(getApplicationContext(), "No data retrieved.", Toast.LENGTH_LONG);
            mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            mytoast.show();
        }
    }

    public boolean searchData()
    {
        List<String> list = new ArrayList<>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_gallery_item,list);
        dataAdapter.setDropDownViewResource(R.layout.activity_stored_codes);

        try
        {
            File dbFile = getApplicationContext().getDatabasePath("Barcode_db");
            if(dbFile.exists())
            {
                //db = SQLiteDatabase.openDatabase(dbFile.getPath(),null, Context.MODE_PRIVATE);
                Context context = getApplicationContext();
                db = context.openOrCreateDatabase("Barcode_db",Context.MODE_PRIVATE,null);
                //SQLiteDatabase.openOrCreateDatabase("Barcode_db",(SQLiteDatabase.CursorFactory)Context.MODE_PRIVATE,null);
                Cursor cursor = db.rawQuery("select * from "+"barcode", null);

                if(cursor !=null)
                {
                    if(cursor.moveToFirst())
                    {
                        do
                        {
                            String code = cursor.getString(cursor.getColumnIndex("code"));
                            list.add(code);
                            gridView.setAdapter(dataAdapter);

                        }
                        while(cursor.moveToNext());
                    }
                    else
                    {
                        return false;
                    }
                    cursor.close();
                    db.close();
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }


        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }
}
