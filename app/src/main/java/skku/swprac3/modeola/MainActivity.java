package skku.swprac3.modeola;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import skku.swprac3.modeola.flexiblecalendar.FlexibleCalendarView;
import skku.swprac3.modeola.flexiblecalendar.exception.HighValueException;
import skku.swprac3.modeola.flexiblecalendar.view.BaseCellView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {

    // Views
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    myDBHelper myHelper;
    ListView lvSchedule;

    List<CustomEvent>[] colorLst = null;

    private Map<Integer, List<CustomEvent>> eventMap;
    private FlexibleCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ToolBar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Modeola");

        // Drawer and Navigation
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Calendar View Set
        setCalendar();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_debug1) { // Clear Auth State
            new AuthStateDAL(this).clearAuthState();
            Toast.makeText(this, "Clear Auth State", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_debug2) { // Connect ARTIK
            Intent gotoArtik = new Intent(getApplicationContext(), ArtikConnectActivity.class);
            gotoArtik.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(gotoArtik);
        } else if (id == R.id.nav_debug3) { // Start Service
            Intent serviceStart = new Intent(getApplicationContext(), ArtikNotificationService.class);
            startService(serviceStart);
        } else if (id == R.id.nav_debug4) { // Stop Service
            Intent serviceStop = new Intent(getApplicationContext(), ArtikNotificationService.class);
            stopService(serviceStop);
        } else if (id == R.id.nav_debug5) {
            ArtikConfig.debugNotification = true;
            Toast.makeText(this, "Debug Notification", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setCalendar() {
        myHelper=new myDBHelper(this);
        lvSchedule=new ListView(this);

        final ListView lvBotSched = (ListView) findViewById(R.id.lvBotSched);

        Date currentDate = new Date(System.currentTimeMillis());

        final int cur_year = (currentDate.getYear()+1900);
        final int cur_month = currentDate.getMonth()+1;
        int cur_day = currentDate.getDate();

        initializeEvents();
        fillEvents(cur_year, cur_month-1);

        Button btn = (Button) findViewById(R.id.date_picker);
        btn.setText(cur_year + " 년  " + cur_month + " 월");


        calendarView = (FlexibleCalendarView) findViewById(R.id.calendar_view);
        calendarView.setMonthViewHorizontalSpacing(10);
        calendarView.setMonthViewVerticalSpacing(10);
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                //Toast.makeText(MainActivity.this, "" + year + " " + (month + 1), Toast.LENGTH_SHORT).show();
                Button btn = (Button) findViewById(R.id.date_picker);
                btn.setText(year + " 년  " + (month + 1) + " 월");
                initializeEvents();
                fillEvents(year, month);
                calendarView.refresh();
            }
        });

        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar_date_cell_view, null);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar_week_cell_view, null);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });

        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<CustomEvent> getEventsForTheDay(int year, int month, int day) {
                return getEvents(year, month, day);
            }
        });
        calendarView.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {
                final int dbyear,dbmonth,dbday;
                dbyear=year;
                dbmonth=month+1;
                dbday=day;


                lvSchedule.setVisibility(View.VISIBLE);
                if(myHelper==null)
                    myHelper=new myDBHelper(MainActivity.this);

                //List allSchedule=myHelper.getAllSchedule();
                //lvSchedule.setAdapter(new ScheduleListAdapter(allSchedule,MainActivity.this));

                List dateSchedule=myHelper.getDateSchedule(year,month,day);
                lvSchedule.setAdapter(new ScheduleListAdapter(dateSchedule,MainActivity.this));

                final LinearLayout layout0=new LinearLayout(MainActivity.this);
                layout0.setOrientation(LinearLayout.VERTICAL);
                layout0.addView(lvSchedule);


                //bottm list edit ========
                refreshBot(year, month, day);

                final ListView lvBotSched = (ListView) findViewById(R.id.lvBotSched);

                lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final Schedule item = (Schedule)adapterView.getAdapter().getItem(i);

                        final AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);

                        dialog.setTitle("수정 혹은 삭제하시겠습니까?")
                                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        myHelper.deleteSchedule(item.getId());
                                        List dateSchedule2;
                                        layout0.removeAllViews();
                                        dateSchedule2=myHelper.getDateSchedule(dbyear,dbmonth-1,dbday);
                                        lvSchedule.setAdapter(new ScheduleListAdapter(dateSchedule2,MainActivity.this));
                                        layout0.addView(lvSchedule);
                                        Toast.makeText(MainActivity.this,"삭제가 완료되었습니다",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setNegativeButton("수정", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        LinearLayout layout=new LinearLayout(MainActivity.this);
                                        layout.setOrientation(LinearLayout.VERTICAL);

                                        final TextView timename=new TextView(MainActivity.this);
                                        timename.setText("시간을 선택하세요");
                                        final TimePicker timepick=new TimePicker(MainActivity.this);
                                        timepick.setHour(item.getHour());
                                        layout.addView(timename);
                                        layout.addView(timepick);

                                        AlertDialog.Builder dialog2=new AlertDialog.Builder(MainActivity.this);

                                        dialog2.setTitle("시간 정보를 입력해주세요")
                                                .setView(layout)
                                                .setPositiveButton("다음", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        LinearLayout layout3=new LinearLayout(MainActivity.this);
                                                        layout3.setOrientation(LinearLayout.VERTICAL);

                                                        final TextView wherename=new TextView(MainActivity.this);
                                                        wherename.setText("장소를 입력하세요");
                                                        final EditText wherepick=new EditText(MainActivity.this);
                                                        wherepick.setText(item.getWhere());

                                                        final TextView whoname=new TextView(MainActivity.this);
                                                        whoname.setText("이름을 입력하세요");
                                                        final EditText whopick=new EditText(MainActivity.this);
                                                        whopick.setText(item.getWho());

                                                        final TextView whatname=new TextView(MainActivity.this);
                                                        whatname.setText("할 일을 입력하세요");
                                                        final EditText whatpick=new EditText(MainActivity.this);
                                                        whatpick.setText(item.getWhat());

                                                        final NumberPicker weightpick=new NumberPicker(MainActivity.this);
                                                        weightpick.setMinValue(0);
                                                        weightpick.setMaxValue(10);
                                                        weightpick.setValue(item.getWeight());

                                                        layout3.addView(wherename);
                                                        layout3.addView(wherepick);
                                                        layout3.addView(whoname);
                                                        layout3.addView(whopick);
                                                        layout3.addView(whatname);
                                                        layout3.addView(whatpick);

                                                        layout3.addView(weightpick);

                                                        AlertDialog.Builder dialog3=new AlertDialog.Builder(MainActivity.this);
                                                        dialog3.setTitle("장소,이름,할일,중요도를 입력해주세요")
                                                                .setView(layout3)
                                                                .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                                                                    @TargetApi(23)
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        int hour;

                                                                        hour=timepick.getHour();

                                                                        String where,who,what;
                                                                        where=wherepick.getText().toString();
                                                                        who=whopick.getText().toString();
                                                                        what=whatpick.getText().toString();

                                                                        int weight;
                                                                        weight=weightpick.getValue();


                                                                        if(myHelper==null)
                                                                            myHelper=new myDBHelper(MainActivity.this);

                                                                        myHelper.updateSchedule(item.getId(),hour,where,who,what,weight);
                                                                        List dateSchedule2;
                                                                        layout0.removeAllViews();
                                                                        dateSchedule2=myHelper.getDateSchedule(dbyear,dbmonth-1,dbday);
                                                                        lvSchedule.setAdapter(new ScheduleListAdapter(dateSchedule2,MainActivity.this));
                                                                        layout0.addView(lvSchedule);
                                                                        Toast.makeText(MainActivity.this,"수정이 완료되었습니다",Toast.LENGTH_SHORT).show();

                                                                        refreshBot(dbyear, dbmonth-1, dbday);
                                                                    }
                                                                })
                                                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        List dateSchedule2;
                                                                        layout0.removeAllViews();
                                                                        dateSchedule2=myHelper.getDateSchedule(dbyear,dbmonth-1,dbday);
                                                                        lvSchedule.setAdapter(new ScheduleListAdapter(dateSchedule2,MainActivity.this));
                                                                        layout0.addView(lvSchedule);
                                                                    }
                                                                })
                                                                .create().show();

                                                    }
                                                })
                                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        List dateSchedule2;
                                                        layout0.removeAllViews();
                                                        dateSchedule2=myHelper.getDateSchedule(dbyear,dbmonth-1,dbday);
                                                        lvSchedule.setAdapter(new ScheduleListAdapter(dateSchedule2,MainActivity.this));
                                                        layout0.addView(lvSchedule);

                                                    }
                                                })
                                                .create().show();

                                    }
                                }).show();

/*
                        List dateSchedule2;
                        layout0.removeAllViews();
                        dateSchedule2=myHelper.getDateSchedule(dbyear,dbmonth-1,dbday);
                        lvSchedule.setAdapter(new ScheduleListAdapter(dateSchedule2,MainActivity.this));
                        layout0.addView(lvSchedule);
                        */

                    }
                });

                AlertDialog.Builder dialog1=new AlertDialog.Builder(MainActivity.this);
                dialog1.setTitle("현재 일정")
                        .setView(layout0)
                        .setPositiveButton("일정생성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LinearLayout layout=new LinearLayout(MainActivity.this);
                                layout.setOrientation(LinearLayout.VERTICAL);

                                final TextView timename=new TextView(MainActivity.this);
                                timename.setText("시간을 선택하세요");
                                final TimePicker timepick=new TimePicker(MainActivity.this);

                                layout.addView(timename);
                                layout.addView(timepick);

                                AlertDialog.Builder dialog2=new AlertDialog.Builder(MainActivity.this);
                                dialog2.setTitle("시간 정보를 입력해주세요")
                                        .setView(layout)
                                        .setPositiveButton("다음", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                LinearLayout layout3=new LinearLayout(MainActivity.this);
                                                layout3.setOrientation(LinearLayout.VERTICAL);

                                                final TextView wherename=new TextView(MainActivity.this);
                                                wherename.setText("장소를 입력하세요");
                                                final EditText wherepick=new EditText(MainActivity.this);

                                                final TextView whoname=new TextView(MainActivity.this);
                                                whoname.setText("이름을 입력하세요");
                                                final EditText whopick=new EditText(MainActivity.this);

                                                final TextView whatname=new TextView(MainActivity.this);
                                                whatname.setText("할 일을 입력하세요");
                                                final EditText whatpick=new EditText(MainActivity.this);


                                                final NumberPicker weightpick=new NumberPicker(MainActivity.this);
                                                weightpick.setMinValue(0);
                                                weightpick.setMaxValue(10);

                                                layout3.addView(wherename);
                                                layout3.addView(wherepick);
                                                layout3.addView(whoname);
                                                layout3.addView(whopick);
                                                layout3.addView(whatname);
                                                layout3.addView(whatpick);

                                                layout3.addView(weightpick);

                                                AlertDialog.Builder dialog3=new AlertDialog.Builder(MainActivity.this);
                                                dialog3.setTitle("장소,이름,할일,중요도를 입력해주세요")
                                                        .setView(layout3)
                                                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                                                            @TargetApi(23)
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                int hour;

                                                                hour=timepick.getHour();

                                                                String where,who,what;
                                                                where=wherepick.getText().toString();
                                                                who=whopick.getText().toString();
                                                                what=whatpick.getText().toString();

                                                                int weight;
                                                                weight=weightpick.getValue();


                                                                if(myHelper==null)
                                                                    myHelper=new myDBHelper(MainActivity.this);

                                                                Schedule schedule=new Schedule();
                                                                schedule.setYear(dbyear);
                                                                schedule.setMonth(dbmonth);
                                                                schedule.setDay(dbday);
                                                                schedule.setHour(hour);
                                                                schedule.setWhere(where);
                                                                schedule.setWho(who);
                                                                schedule.setWhat(what);
                                                                schedule.setWeight(weight);

                                                                myHelper.addSchedule(schedule);

                                                                refreshBot(dbyear, dbmonth-1, dbday);

                                                                layout0.removeAllViews();

                                                                initializeEvents();
                                                                fillEvents(dbyear, dbmonth-1);
                                                                calendarView.refresh();
                                                            }
                                                        })
                                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                layout0.removeAllViews();

                                                            }
                                                        })
                                                        .create().show();

                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                layout0.removeAllViews();

                                            }
                                        })
                                        .create().show();
                            }
                        })
                        .setNegativeButton("뒤로", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                layout0.removeAllViews();

                            }
                        })
                        .create().show();
            }
        });

        findViewById(R.id.date_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, MainActivity.this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

                calendarView.refresh();
                //initializeEvents();
            }
        });

        refreshBot(cur_year, cur_month-1, cur_day);
    }

    public void refreshBot(int year, int month, int day){
        myHelper=new myDBHelper(this);
        ListView lvBotSched = (ListView) findViewById(R.id.lvBotSched);

        List dateSchedule=myHelper.getDateSchedule(year,month,day);
        lvBotSched.setAdapter(new ScheduleListAdapter(dateSchedule,MainActivity.this));
    }

    private void initializeEvents() {
        eventMap = new HashMap<>();
        //eventMap=myHelper.PutSchedule(calendarView.getCurrentYear(),calendarView.getCurrentMonth());
    }
    public List<CustomEvent> getEvents(int year, int month, int day) {

        return eventMap.get(day);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        try {

            calendarView.selectDate(year, monthOfYear, dayOfMonth);
            refreshBot(year, monthOfYear, dayOfMonth);
            initializeEvents();

        } catch (HighValueException e) {
            e.printStackTrace();
        }
    }

    private void fillEvents(int year, int month) {

        if (myHelper == null) myHelper = new myDBHelper(MainActivity.this);
        if (colorLst == null){  //if null, initialize. else, clear.
            colorLst = new ArrayList[31];
            for(int i=0; i < 31; i++){
                colorLst[i] = new ArrayList<>();
            }
        }
        else{
            for(int i=0; i < 31; i++){
                colorLst[i].clear();
            }
        }

        int days;
        switch(month){
            case 0: case 2: case 4: case 6: case 7: case 9: case 11:
                days = 31;
                break;
            case 1:
                days = 28;
                break;
            case 3: case 5: case 8: case 10:
                days = 30;
                break;
            default:
                days = 0;
        }

        for(int i=0; i < days; i++){
            int eventNum = myHelper.getSchedCount(year, month, i+1);
            //Toast.makeText(MainActivity.this, eventNum, Toast.LENGTH_SHORT).show();
            for(int j=0; j < eventNum; j++) {
                colorLst[i].add(new CustomEvent(android.R.color.holo_red_dark));
            }
            if(eventNum != 0) eventMap.put(i+1, colorLst[i]);
        }

        //Toast.makeText(MainActivity.this,"fillEvents date " + year + "  " + month,Toast.LENGTH_SHORT).show();
    }
}