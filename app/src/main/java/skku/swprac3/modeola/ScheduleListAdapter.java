package skku.swprac3.modeola;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 우석 on 2018-05-14.
 */

public class ScheduleListAdapter extends BaseAdapter {
    private List allSchedule;
    private Context context;

    public ScheduleListAdapter(List allSchedule, Context context){
        this.allSchedule=allSchedule;
        this.context=context;
    }



    @Override
    public int getCount(){
        return this.allSchedule.size();
    }
    @Override
    public Object getItem(int position){
        return this.allSchedule.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Holder holder=null;
        if(convertView==null){
            convertView=new LinearLayout(context);
            ((LinearLayout)convertView).setOrientation(LinearLayout.HORIZONTAL);


            Schedule schedule=(Schedule)getItem(position);

            TextView tvMain=new TextView(context);
            tvMain.setPadding(20,0,20,0);

            tvMain.setText(schedule.getYear() + " 년 " + schedule.getMonth() + " 월 " + schedule.getDay() + " 일 "
                    + schedule.getHour() + " 시 " + schedule.getWhere() + " 에서 " + schedule.getWho() + " 와 " + schedule.getWhat() + "  중요도: " + schedule.getWeight());

            ((LinearLayout)convertView).addView(tvMain);

        }else{
            holder=(Holder)convertView.getTag();
        }

        final Schedule schedule=(Schedule)getItem(position);
        return convertView;
    }
}