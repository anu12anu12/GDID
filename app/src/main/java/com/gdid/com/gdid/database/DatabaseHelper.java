package com.gdid.com.gdid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.gdid.activities.ActivityStoredCodes;
import com.gdid.material_management.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Van Destroyer on 2/22/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Barcode_db";
    public static final String TABLE_NAME = "barcode";
    public static final String COLUMN_1 = "code";

    public GridView gridView;
    public static ArrayList<String> ArrayofCode = new ArrayList<String>();
    public Context mcontext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        mcontext = context;
        //onCreate(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME  + "("+ COLUMN_1+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public boolean instertData(String barcode)
    {
        boolean temp = false;
        long results;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1,barcode);
        results = db.insert(TABLE_NAME,null,contentValues);

        if(results == -1)
        {
            temp = false;
        }
        else
        {
            temp = true;
        }
        return temp;
    }

    public GridView searchData(ActivityStoredCodes activity)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        GridView gridView = activity.gridView;
        List<String> list = new ArrayList<>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_gallery_item,list);
        dataAdapter.setDropDownViewResource(R.layout.activity_stored_codes);

        try
        {
            File dbFile = mcontext.getDatabasePath(DATABASE_NAME);
            if(dbFile.exists())
            {
                db = SQLiteDatabase.openDatabase(dbFile.getPath(),null, Context.MODE_PRIVATE);
                //db = SQL
                Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);

                if(cursor !=null)
                {
                    if(cursor.moveToFirst())
                    {
                        do
                        {
                            String code = cursor.getString(cursor.getColumnIndex(COLUMN_1));
                            list.add(code);
                            gridView.setAdapter(dataAdapter);
                        }
                        while(cursor.moveToNext());
                    }
                    else
                    {
                        return null;
                    }
                    cursor.close();
                    db.close();
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }


        }
        catch (Exception ex)
        {
            return null;
        }
        return gridView;
    }


}