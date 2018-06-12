package skku.swprac3.modeola;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

    public List<Schedule> getScheduleForProblem(int number) {
        ArrayList<Schedule> schedules = (ArrayList<Schedule>) getAllSchedule();

        // Temporary toast for schedule shortage
        if(schedules.size() < number) {
            Toast.makeText(context, "Not enough schedules!", Toast.LENGTH_SHORT).show();
        }

        // Sort schedules with their priority (date, weight, etc...)
        Collections.sort(schedules, priorityComparator);

        // Remain only "number" schedules
        Iterator it = schedules.iterator();
        for(int i = 0; i < number; i++) {
            it.next();
        }
        while(it.hasNext()) {
            it.next();
            it.remove();
        }

        return schedules;
    }

    static Comparator<Schedule> priorityComparator = new Comparator<Schedule>(){
        public int compare(Schedule schedule1, Schedule schedule2){
            // Descending order
            return calculatePriority(schedule2) - calculatePriority(schedule1);
        }
        public int calculatePriority(Schedule schedule) {
            // Priority principle
            // Priority == Weight of schedule
            return schedule.getWeight();
        }
    };

    public Problems getProblems(int probType1num, int probType2num) {
        List<Schedule> schedules = getScheduleForProblem(probType1num * 2 + probType2num * 5);
        List<String> names = new ArrayList<String>();
        List<String> contents = new ArrayList<String>();
        List<Integer> answers = new ArrayList<Integer>();
        List<Integer> types = new ArrayList<Integer>();
        Random random = new Random();

        // Construct types and answers
        for(int i = 0; i < probType1num; i++) {
            types.add(1);
            answers.add(random.nextInt(2));
        }
        for(int i = 0; i < probType2num; i++) {
            types.add(2);
            answers.add(random.nextInt(5));
        }

        // Construct contents
        int scheduleBaseCur = 0;
        int problemCur = 0;
        Collections.shuffle(schedules);
        for(int i = 0; i < probType1num; i++) {
            if(answers.get(problemCur) == 1) {
                int property = random.nextInt(4);
                if(property == 0) { // When
                    schedules.get(scheduleBaseCur).setYear(schedules.get(scheduleBaseCur+1).getYear());
                    schedules.get(scheduleBaseCur).setMonth(schedules.get(scheduleBaseCur+1).getMonth());
                    schedules.get(scheduleBaseCur).setDay(schedules.get(scheduleBaseCur+1).getDay());
                    schedules.get(scheduleBaseCur).setHour(schedules.get(scheduleBaseCur+1).getHour());
                } else if(property == 1) { // Where
                    schedules.get(scheduleBaseCur).setWhere(schedules.get(scheduleBaseCur+1).getWhere());
                } else if(property == 2) { // Who
                    schedules.get(scheduleBaseCur).setWho(schedules.get(scheduleBaseCur+1).getWho());
                } else if(property == 3) { // What
                    schedules.get(scheduleBaseCur).setWhat(schedules.get(scheduleBaseCur+1).getWhat());
                }
            }
            Schedule s = schedules.get(scheduleBaseCur);
            names.add(makeContents(s.getYear(), s.getMonth(), s.getDay(), s.getHour(), s.getWhere(), s.getWho(), s.getWhat()));
            contents.add("");
            contents.add("");
            problemCur++;
            scheduleBaseCur+=2;
        }
        for(int i = 0; i < probType2num; i++) {
            int property = random.nextInt(4);
            List<Integer> contentShuffle = new ArrayList<Integer>();
            contentShuffle.add(0);
            contentShuffle.add(1);
            contentShuffle.add(2);
            contentShuffle.add(3);
            Collections.shuffle(contentShuffle);
            if(property == 0) { // When
                int[][] time = new int[4][4];
                for(int j = 0; j < 4; j++) {
                    time[j][0] = schedules.get(scheduleBaseCur+j).getYear();
                    time[j][1] = schedules.get(scheduleBaseCur+j).getMonth();
                    time[j][2] = schedules.get(scheduleBaseCur+j).getDay();
                    time[j][3] = schedules.get(scheduleBaseCur+j).getHour();
                }
                for(int j = 0; j < 4; j++) {
                    schedules.get(scheduleBaseCur+j).setYear(time[contentShuffle.get(j)][0]);
                    schedules.get(scheduleBaseCur+j).setMonth(time[contentShuffle.get(j)][1]);
                    schedules.get(scheduleBaseCur+j).setDay(time[contentShuffle.get(j)][2]);
                    schedules.get(scheduleBaseCur+j).setHour(time[contentShuffle.get(j)][3]);
                }
            } else if(property == 1) { // Where
                String[] where = new String[4];
                for(int j = 0; j < 4; j++)
                    where[j] = schedules.get(scheduleBaseCur+j).getWhere();
                for(int j = 0; j < 4; j++)
                    schedules.get(scheduleBaseCur+j).setWhere(where[contentShuffle.get(j)]);
            } else if(property == 2) { // Who
                String[] who = new String[4];
                for(int j = 0; j < 4; j++)
                    who[j] = schedules.get(scheduleBaseCur+j).getWhere();
                for(int j = 0; j < 4; j++)
                    schedules.get(scheduleBaseCur+j).setWhere(who[contentShuffle.get(j)]);
            } else if(property == 3) { // What
                String[] what = new String[4];
                for(int j = 0; j < 4; j++)
                    what[j] = schedules.get(scheduleBaseCur+j).getWhere();
                for(int j = 0; j < 4; j++)
                    schedules.get(scheduleBaseCur+j).setWhere(what[contentShuffle.get(j)]);
            }
            for(int j = 0; j < 5; j++) {
                Schedule s = schedules.get(scheduleBaseCur+j);
                contents.add(makeContents(s.getYear(), s.getMonth(), s.getDay(), s.getHour(), s.getWhere(), s.getWho(), s.getWhat()));
            }
            if(answers.get(i) != 4)
                Collections.swap(contents, scheduleBaseCur+answers.get(problemCur), scheduleBaseCur+4);
            names.add("");
            problemCur++;
            scheduleBaseCur+=5;
        }

        return new Problems(names, contents, answers, types);
    }

    String makeContents(int year, int month, int day, int hour, String where, String who, String what) {
        return String.format("나는 \'%d년 %d월 %d일 %d시\'에 \'%s\'에서 \'%s\'와 \'%s\'을(를) 한다.", year, month, day, hour, where, who, what);
    }
}
