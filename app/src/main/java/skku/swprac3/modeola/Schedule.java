package skku.swprac3.modeola;

/**
 * Created by 우석 on 2018-05-14.
 */

public class Schedule {
    private int id;
    private int year,month,day,hour;
    private String where,who,what;
    private int weight;

    public int getId(){
        return id;
    }
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public int getHour(){
        return hour;
    }
    public String getWhere(){
        return where;
    }
    public String getWho(){
        return who;
    }
    public String getWhat(){
        return what;
    }
    public int getWeight(){
        return weight;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setYear(int year){
        this.year=year;
    }
    public void setMonth(int month){
        this.month=month;
    }
    public void setDay(int day){
        this.day=day;
    }
    public void setHour(int hour){
        this.hour=hour;
    }
    public void setWhere(String where){
        this.where=where;
    }
    public void setWho(String who){
        this.who=who;
    }
    public void setWhat(String what){
        this.what=what;
    }
    public void setWeight(int weight){this.weight=weight;}

    public Schedule clone() {
        Schedule schedule = new Schedule();
        schedule.id = this. id;
        schedule.year = this.year;
        schedule.month = this.month;
        schedule.day = this.day;
        schedule.hour = this.hour;
        schedule.where = this.where;
        schedule.who = this.who;
        schedule.what = this.what;
        schedule.weight = this.weight;
        return schedule;
    }
}
