package como.example.noman.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheDatabase extends SQLiteOpenHelper {

    public static String databaseName = "student.db";
    public static String tableName = "BasicInfo";
    public static final String[] a = {"1", "2", "3", "4"};
    public static String col1 = "RollNo";
    public static String col2 = "Name";
    public static String col3 = "Faculty";
    public static String col4 = "Marks";

    public CacheDatabase(Context _context) {
        super(_context, databaseName, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        String sql = "create table " + tableName +
                "("
                + col1 + " integer primary key autoincrement,"
                + col2 + " Text, "
                + col3 + " Text, "
                + col4 + " integer"+")";
        _db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        _db.execSQL("drop table if exists "+tableName);
        onCreate(_db);
    }
}
