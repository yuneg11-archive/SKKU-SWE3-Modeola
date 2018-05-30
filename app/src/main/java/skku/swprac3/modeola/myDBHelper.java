package skku.swprac3.modeola;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class myDBHelper extends SQLiteOpenHelper {
    private Context context;
    public myDBHelper(Context context){
        super(context,"schedulerDB",null,1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE scheduleTBL (id INTEGER PRIMARY KEY AUTOINCREMENT,Year INTEGER,Month INTEGER,Day INTEGER,Hour INTEGER," +
                "Wher TEXT,Who TEXT,What TEXT,Weight INTEGER);");
    }//id,Year,Month,Day,Hour,Wher,Who,What

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS scheduleTBL");
        onCreate(sqLiteDatabase);
    }
    public void addTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE scheduleTBL (id INTEGER PRIMARY KEY AUTOINCREMENT,Year INTEGER,Month INTEGER,Day INTEGER,Hour INTEGER," +
                "Wher TEXT,Who TEXT,What TEXT,Weight INTEGER);");
    }

    public void deleteSchedule(int id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM scheduleTBL where id = "+id+";");
    }
    public void updateSchedule(int id, int hour, String where, String who, String what, int weight){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("UPDATE scheduleTBL SET Hour = "+hour+", Wher = '"+where+"',Who = '"+who+"', What = '"+what+"', Weight = "+weight+" where id = "+id+";");
    }
    public void addSchedule(Schedule schedule){
        SQLiteDatabase db=getWritableDatabase();
        StringBuffer sb=new StringBuffer();
        sb.append("INSERT INTO scheduleTBL (");
        sb.append("Year,Month,Day,Hour,Wher,Who,What,Weight) ");
        sb.append("VALUES(?,?,?,?,?,?,?,?);");

        db.execSQL(sb.toString(),new Object[]{
                schedule.getYear(),schedule.getMonth(),schedule.getDay(),schedule.getHour(),
                schedule.getWhere(),schedule.getWho(),schedule.getWhat(),schedule.getWeight()});
        Toast.makeText(context,"일정 생성 완료!", Toast.LENGTH_SHORT).show();
    }

    public List getAllSchedule(){
        StringBuffer sb=new StringBuffer();
        sb.append("SELECT id,Year,Month,Day,Hour,Wher,Who,What,Weight FROM scheduleTBL;");

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(sb.toString(),null);

        List allSchedule=new ArrayList();
        Schedule schedule=null;

        while(cursor.moveToNext()){
            schedule=new Schedule();
            schedule.setId(cursor.getInt(0));
            schedule.setYear(cursor.getInt(1));
            schedule.setMonth(cursor.getInt(2));
            schedule.setDay(cursor.getInt(3));
            schedule.setHour(cursor.getInt(4));
            schedule.setWhere(cursor.getString(5));
            schedule.setWho(cursor.getString(6));
            schedule.setWhat(cursor.getString(7));
            schedule.setWeight(cursor.getInt(8));
            allSchedule.add(schedule);
        }
        return allSchedule;
    }




    public List getDateSchedule(int year, int month, int day){

        StringBuffer sb=new StringBuffer();
        sb.append("SELECT id,Year,Month,Day,Hour,Wher,Who,What,Weight FROM scheduleTBL where Year = "+year+
        " and Month = "+(month+1)+" and Day = "+day+";");

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(sb.toString(),null);

        List dateSchedule=new ArrayList();
        Schedule schedule=null;

        while(cursor.moveToNext()){
            schedule=new Schedule();
            schedule.setId(cursor.getInt(0));
            schedule.setYear(cursor.getInt(1));
            schedule.setMonth(cursor.getInt(2));
            schedule.setDay(cursor.getInt(3));
            schedule.setHour(cursor.getInt(4));
            schedule.setWhere(cursor.getString(5));
            schedule.setWho(cursor.getString(6));
            schedule.setWhat(cursor.getString(7));
            schedule.setWeight(cursor.getInt(8));
            dateSchedule.add(schedule);
        }
        return dateSchedule;
    }

    public int getSchedCount(int year, int month, int day){

        StringBuffer sb=new StringBuffer();
        sb.append("SELECT COUNT (*) FROM scheduleTBL where Year = "+year+" and Month = "+(month+1)+" and Day = "+day+";");

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(sb.toString(),null);
        cursor.moveToFirst();
        int n = cursor.getInt(0);
        cursor.close();

        return n;
    }

}
