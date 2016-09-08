package com.shengzidong.keepacounts.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shengzidong.keepacounts.entity.Receipt;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2.
 */
public class MyDataBaseUtils {
    public SQLiteDatabase db;
    Context context;
    String table = "receipt_table";

    public MyDataBaseUtils(Context context) {
        this.context = context;
        SQLiteDatabase.CursorFactory factory = null;
        db = context.openOrCreateDatabase("account.db", context.MODE_PRIVATE, factory);
        String sql = "create table IF NOT EXISTS receipt_table"
                + "(id integer primary key autoincrement,"
                + "category varchar(15) not null,"
                + "time long not null," + "date varchar,lable varchar,location varchar,message varchar,money varchar);";
        db.execSQL(sql);
    }


    public void insertToDataBase(Receipt receipt) {

        String nullColumnHack = null;
        ContentValues values = new ContentValues();

        String category = receipt.getCategory();
        long time = receipt.getTime();
        String timeString = String.valueOf(time);
        String date = receipt.getDate();
        String lable = receipt.getLabel();
        String location = receipt.getLocation();
        String msg = receipt.getMessage();
        float money = receipt.getMoney();
        String moneyString = String.valueOf(money);


        values.put("category", category);
        values.put("time", timeString);
        values.put("date", date);
        values.put("lable", lable);
        values.put("location", location);
        values.put("message", msg);
        values.put("money", moneyString);

        long result = db.insert(table, nullColumnHack, values);
        if (result == -1) {
            Log.d("databaseUtils", "插入失败");
        } else {
            Log.d("databaseUtils", "插入成功");
        }
    }

    public void deleteFromDataBase(Receipt receipt) {
        String category = receipt.getCategory();
        long time = receipt.getTime();
        String timeString = String.valueOf(time);
        String date = receipt.getDate();
//        String lable = receipt.getLabel();
//        String location = receipt.getLocation();
//        String msg = receipt.getMessage();
        float money = receipt.getMoney();
        String moneyString = String.valueOf(money);


        String whereClause = "category=? and time=? and date=? and money=?";
        String[] whereArgs = {category, timeString, date, moneyString};
        int success = db.delete(table, whereClause, whereArgs);
        Log.d("databaseUtils", "删除" + success + "行数据");
    }

    public ArrayList<Receipt> queryFromDataBase() {
        Receipt receipt;
        ArrayList<Receipt> receipts = new ArrayList<>();

        Cursor c = db.query(table, null, null, null, null, null, null, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            receipt = new Receipt();
            receipt.setCategory(c.getString(c.getColumnIndex("category")));
            receipt.setLabel(c.getString(c.getColumnIndex("lable")));
            receipt.setLabel(c.getString(c.getColumnIndex("location")));
            receipt.setMessage(c.getString(c.getColumnIndex("message")));
            receipt.setDate(c.getString(c.getColumnIndex("date")));

            String timeString = c.getString(c.getColumnIndex("time"));
            long time = Long.parseLong(timeString);
            receipt.setTime(time);

            String moneyString = c.getString(c.getColumnIndex("money"));
            float money = Float.parseFloat(moneyString);
            receipt.setMoney(money);

            receipts.add(receipt);
        }

        return receipts;
    }


    public ArrayList<Receipt> queryFromDataBase(long argTime) {

        Receipt receipt;
        ArrayList<Receipt> receipts = new ArrayList<>();

        Cursor c = db.query(table, null, null, null, null, null, null, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            receipt = new Receipt();
            receipt.setCategory(c.getString(c.getColumnIndex("category")));
            receipt.setLabel(c.getString(c.getColumnIndex("lable")));
            receipt.setLabel(c.getString(c.getColumnIndex("location")));
            receipt.setMessage(c.getString(c.getColumnIndex("message")));
            receipt.setDate(c.getString(c.getColumnIndex("date")));

            String timeString = c.getString(c.getColumnIndex("time"));
            long time = Long.parseLong(timeString);
            receipt.setTime(time);

            String moneyString = c.getString(c.getColumnIndex("money"));
            float money = Float.parseFloat(moneyString);
            receipt.setMoney(money);

            long currentTime = System.currentTimeMillis();
            if (argTime != 0) {
                if (currentTime - time < argTime) {
                    receipts.add(receipt);
                }
            } else {
                receipts.add(receipt);
            }
        }

        return receipts;
    }


    public void closeDataBase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
