package by.belstu.lab_06db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Владислав on 11.11.2016.
 */
public class DBController extends SQLiteOpenHelper {
    public DBController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Lab_06DB.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table STUDGROUPS "
                + "(IDGROUP integer not null,"
                + " FACULTY text not null, "
                + "COURSE integer not null,"
                + "NAME text not null,"
                + "HEAD text not null,"
                + "constraint IDGROUP_pk primary key(IDGROUP) );");
        db.execSQL("create table STUDENTS "
                + "(IDGROUP integer not null,"
                + " IDSTUDENT integer not null, "
                + "NAME text not null,"
                + "constraint IDGROUP_fk foreign key(IDGROUP) references STUDGROUPS(IDGROUP)"
                + "on delete cascade on update cascade );");
        if(!db.isReadOnly()){
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDGROUPS;");
        db.execSQL("DROP TABLE IF EXISTS STUDENTS;");
        db.execSQL("CREATE VIEW countStudents as SELECT Tgroups.IDGROUP, Tgroups.HEAD, csn" +
                "From STUDGROUPS as Tgroups left join (" +
                " SELECT s.IDGROUP, Count(s.NAME)csn" +
                " From STUDENTS as s" +
                " group by s.IDGROUP" +
                ") as Tstudents ON Tstudents.IDGROUP = Tgroups.IDGROUP;");
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        /*db.execSQL("CREATE VIEW countStudents as SELECT Tgroups.IDGROUP, Tgroups.HEAD, COALESCE(Tstudents.csn,0)" +
                "From STUDGROUPS as Tgroups left join (" +
                " SELECT s.IDGROUP, Count(s.IDSTUDENT)csn" +
                " From STUDENTS as s" +
                " group by s.IDGROUP" +
                ") as Tstudents ON Tstudents.IDGROUP = Tgroups.IDGROUP;");*/
        db.execSQL("PRAGMA foreign_key=ON");
        if(!db.isReadOnly()){
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
}
