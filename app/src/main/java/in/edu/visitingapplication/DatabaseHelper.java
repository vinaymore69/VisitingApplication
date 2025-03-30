package in.edu.visitingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Signup.db";

    // Version 3 includes new columns and visitedUsers table
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table for regular visitors
        db.execSQL("CREATE TABLE allUsers(" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT" +
                ")");

        // Table for admin login credentials
        db.execSQL("CREATE TABLE adminUsers(" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT" +
                ")");

        // Table for subordinate (watchman) login credentials
        db.execSQL("CREATE TABLE subordinateUsers(" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT" +
                ")");

        // Table for user requests with detailed information.
        db.execSQL("CREATE TABLE userRequests(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT, " +
                "name TEXT, " +
                "email TEXT, " +
                "phone TEXT, " +
                "purpose TEXT, " +
                "date TEXT, " +
                "time TEXT, " +
                "unique_key TEXT, " +
                "status TEXT DEFAULT 'pending'" +
                ")");

        // Table for visited users (records of those who have entered)
        db.execSQL("CREATE TABLE visitedUsers(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT, " +
                "name TEXT, " +
                "email TEXT, " +
                "phone TEXT, " +
                "purpose TEXT, " +
                "date TEXT, " +
                "time TEXT, " +
                "unique_key TEXT, " +
                "status TEXT" +
                ")");


        // Seed default admin (for admin login)
        db.execSQL("INSERT INTO adminUsers (email, password) VALUES ('vinaymore0110@gmail.com', '123')");
        // Seed default subordinate (watchman) login
        db.execSQL("INSERT INTO subordinateUsers (email, password) VALUES ('vinaymore0110@gmail.com', '1234')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist and recreate with new schema
        db.execSQL("DROP TABLE IF EXISTS allUsers");
        db.execSQL("DROP TABLE IF EXISTS adminUsers");
        db.execSQL("DROP TABLE IF EXISTS subordinateUsers");
        db.execSQL("DROP TABLE IF EXISTS userRequests");
        db.execSQL("DROP TABLE IF EXISTS visitedUsers");  // Ensure this table is dropped if exists
        onCreate(db);
    }


    // Methods for regular visitors
    public Boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = db.insert("allUsers", null, contentValues);
        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("allUsers", new String[]{"email"}, "email=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("allUsers", new String[]{"email"}, "email=? AND password=?", new String[]{email, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        int rowsAffected = db.update("allUsers", values, "email=?", new String[]{email});
        return rowsAffected > 0;
    }

    // Methods for user requests
    public Boolean insertUserRequest(String userId, String name, String email, String phone,
                                     String purpose, String date, String time, String uniqueKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("name", name);
        values.put("email", email);
        values.put("phone", phone);
        values.put("purpose", purpose);
        values.put("date", date);
        values.put("time", time);
        values.put("unique_key", uniqueKey);
        long result = db.insert("userRequests", null, values);
        return result != -1;
    }

    public Cursor getUserRequests() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id, user_id, name, email, phone, purpose, date, time, unique_key, status FROM userRequests", null);
    }

    public Cursor getUserRequestsByStatus(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id, user_id, name, email, phone, purpose, date, time, unique_key, status FROM userRequests WHERE status=?", new String[]{status});
    }

    public Cursor getRequestByUniqueKeyAndDate(String uniqueKey, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id, user_id, name, email, phone, purpose, date, time, unique_key, status FROM userRequests WHERE unique_key=? AND date=?", new String[]{uniqueKey, date});
    }

    // Methods for admin users
    public Boolean insertAdmin(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        long result = db.insert("adminUsers", null, values);
        return result != -1;
    }

    public Boolean checkAdminCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("adminUsers", new String[]{"email"}, "email=? AND password=?", new String[]{email, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor getAdminUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM adminUsers", null);
    }

    // Methods for subordinate (watchman) users
    public Boolean insertSubordinate(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        long result = db.insert("subordinateUsers", null, values);
        return result != -1;
    }

    public Boolean checkSubordinateCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("subordinateUsers", new String[]{"email"}, "email=? AND password=?", new String[]{email, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean updateRequestStatus(int requestId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        int rowsAffected = db.update("userRequests", values, "id=?", new String[]{String.valueOf(requestId)});
        return rowsAffected > 0;
    }

    public Cursor getUserRequestsByUserId(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id, user_id, name, email, phone, purpose, date, time, unique_key, status FROM userRequests WHERE user_id=?", new String[]{userId});
    }

    // New methods for visited users
    public Boolean insertVisitedUser(VisitRequest request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", request.getUserId());
        values.put("name", request.getName());
        values.put("email", request.getEmail());
        values.put("phone", request.getPhone());
        values.put("purpose", request.getPurpose());
        values.put("date", request.getDate());
        values.put("time", request.getTime());
        values.put("unique_key", request.getUniqueKey());
        values.put("status", "entered");  // Mark as entered
        long result = db.insert("visitedUsers", null, values);
        return result != -1;
    }

    public Boolean deleteUserRequest(int requestId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("userRequests", "id=?", new String[]{String.valueOf(requestId)});
        return rows > 0;
    }
}
