package com.arunkanai.mypetexpert;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Register.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        mydb.execSQL("create Table user(et_email Text primary key,et_password Text,et_Gender Text, et_username Text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {
        mydb.execSQL("drop Table if exists user ");
    }

    public Boolean insertData(String et_email, String et_password, String et_Gender, String et_username) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("et_email", et_email);
        contentValues.put("et_password", et_password);
        contentValues.put("et_Gender", et_Gender);
        contentValues.put("et_username", et_username);

        long result = mydb.insert("user", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Boolean checkemailaddress(String et_email) {

        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from user where et_email = ? ", new String[]{et_email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkemailaddresspasword(String et_email, String et_password) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from user where et_email = ? and et_password = ?", new String[]{et_email, et_password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePassword(String newPassword, String email) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("et_password", newPassword);
        int rowsUpdated = mydb.update("user", values, "et_email = ?", new String[]{email});
        if (rowsUpdated > 0)
            return true;
        else
            return false;
    }


    public ArrayList<UserModal> getLoggedInUserDetails(String email) {
        ArrayList<UserModal> al = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM user WHERE et_email='" + email + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(3);
                String emailaddress = cursor.getString(0);
                String gender = cursor.getString(2);

                UserModal userModal = new UserModal();
                userModal.setUsername(username);
                userModal.setEmailaddress(emailaddress);
                userModal.setGender(gender);

                al.add(userModal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return al;
    }


    public boolean updateProfileHelper(String email, String username, String gender) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("et_username", username); // Replace "et_username" with the actual column name
        values.put("et_Gender", gender); // Replace "et_Gender" with the actual column name

        int rowsUpdated = sqLiteDatabase.update("user", values, "et_email = ?", new String[]{email});
        return rowsUpdated > 0;
    }


    public boolean deleteUserProfileHelper(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = sqLiteDatabase.delete("user", "et_email=?", new String[]{email});
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }


}
