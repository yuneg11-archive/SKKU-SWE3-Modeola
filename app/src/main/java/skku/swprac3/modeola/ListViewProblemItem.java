package skku.swprac3.modeola;

public class ListViewProblemItem {
    private String probName;
    private String probItem1;
    private String probItem2;
    private String probItem3;
    private String probItem4;
    private String probItem5;

    private int year,month,day,hour;
    private String where,who,what;
    private int weight;
    private boolean isAnswer;

    ListViewProblemItem () { }

    ListViewProblemItem(int year, int month, int day, int hour, String where, String who, String what, int weight, boolean isAnswer){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.where = where;
        this.who = who;
        this.what = what;
        this.weight = weight;
        this.isAnswer = false;
    }

    public String getProbName(){
        return this.probName;
    }

    public String getProbItem1(){
        return this.probItem1;
    }

    public String getProbItem2(){
        return this.probItem2;
    }

    public String getProbItem3(){
        return this.probItem3;
    }

    public String getProbItem4(){
        return this.probItem4;
    }

    public String getProbItem5(){
        return this.probItem5;
    }

    public void setProbName(String probName){
        this.probName = probName;
    }

    public void setProbItem1(String probItem1){
        this.probItem1 = probItem1;
    }

    public void setProbItem2(String probItem2){
        this.probItem2 = probItem2;
    }

    public void setProbItem3(String probItem3){
        this.probItem3 = probItem3;
    }

    public void setProbItem4(String probItem4){
        this.probItem4 = probItem4;
    }

    public void setProbItem5(String probItem5){
        this.probItem5 = probItem5;
    }


    public void setYear(int year){
        this.year = year;
    }
    public void setMonth(int month){
        this.month = month;
    }
    public void setDay(int day){
        this.day = day;
    }
    public void setHour(int hour){
        this.hour = hour;
    }
    public void setWhere(String where){
        this.where = where;
    }
    public void setWho(String who){
        this.who = who;
    }
    public void setWhat(String what){
        this.what = what;
    }
    public void setAnswer(boolean answer){
        this.isAnswer = answer;
    }
}

