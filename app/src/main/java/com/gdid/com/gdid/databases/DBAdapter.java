package com.gdid.com.gdid.databases;

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
 * Created by anupamsi on 3/3/2017.
 */
public class DBAdapter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Barcode_db";
    public static final String TABLE_NAME = "barcode";
    public static final String COLUMN_1 = "code";
    public static DBAdapter mDbAdapter;

    public GridView gridView;
    public static ArrayList<String> ArrayofCode = new ArrayList<String>();
    public Context mContext;

    public synchronized static DBAdapter getInstance(Context aContext) {
        if (mDbAdapter == null) {
            mDbAdapter = new DBAdapter(aContext);
        }
        return mDbAdapter;
    }

    private DBAdapter(Context aContext) {
        super(aContext, DATABASE_NAME, null, 1);
        SQLiteDatabase db = getWritableDatabase();
        mContext = aContext;
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

    public boolean insertData(String barcode)
    {
        boolean temp = false;
        long results;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, barcode);
        results = db.insert(TABLE_NAME, null, contentValues);
        temp = true;
        if(results == -1)
        {
            temp = false;
        }

        return temp;
    }

    public GridView searchData(ActivityStoredCodes activity)
    {
        SQLiteDatabase db = getReadableDatabase();
        GridView gridView = activity.gridView;
        List<String> list = new ArrayList<>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_gallery_item,list);
        dataAdapter.setDropDownViewResource(R.layout.activity_stored_codes);
        Cursor cursor = null;
        try
        {
            File dbFile = mContext.getDatabasePath(DATABASE_NAME);
            db = SQLiteDatabase.openDatabase(dbFile.getPath(),null, Context.MODE_PRIVATE);
            //db = SQL
            cursor = db.rawQuery("select * from "+TABLE_NAME, null);

            if(cursor !=null)
            {
                while(cursor.moveToNext())
                {
                    String code = cursor.getString(cursor.getColumnIndex(COLUMN_1));
                    list.add(code);
                    gridView.setAdapter(dataAdapter);
                }

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return gridView;
    }

}
