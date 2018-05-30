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

            /*TextView tvId=new TextView(context);
            tvId.setPadding(20,0,20,0);
            tvId.setTextColor(Color.rgb(255,255,255));*/

            TextView tvYear=new TextView(context);
            tvYear.setPadding(20,0,20,0);
            tvYear.setTextColor(Color.rgb(255,255,255));

            TextView tvMonth=new TextView(context);
            tvMonth.setPadding(20,0,20,0);
            tvMonth.setTextColor(Color.rgb(255,255,255));

            TextView tvDay=new TextView(context);
            tvDay.setPadding(20,0,20,0);
            tvDay.setTextColor(Color.rgb(255,255,255));

            TextView tvHour=new TextView(context);
            tvHour.setPadding(20,0,20,0);
            tvHour.setTextColor(Color.rgb(255,255,255));

            TextView tvWhere=new TextView(context);
            tvWhere.setPadding(20,0,20,0);
            tvWhere.setTextColor(Color.rgb(255,255,255));

            TextView tvWho=new TextView(context);
            tvWho.setPadding(20,0,20,0);
            tvWho.setTextColor(Color.rgb(255,255,255));

            TextView tvWhat=new TextView(context);
            tvWhat.setPadding(20,0,20,0);
            tvWhat.setTextColor(Color.rgb(255,255,255));

            TextView tvWeight=new TextView(context);
            tvWeight.setPadding(20,0,20,0);
            tvWeight.setTextColor(Color.rgb(255,255,255));

            // ((LinearLayout)convertView).addView(tvId);
            ((LinearLayout)convertView).addView(tvYear);
            ((LinearLayout)convertView).addView(tvMonth);
            ((LinearLayout)convertView).addView(tvDay);
            ((LinearLayout)convertView).addView(tvHour);
            ((LinearLayout)convertView).addView(tvWhere);
            ((LinearLayout)convertView).addView(tvWho);
            ((LinearLayout)convertView).addView(tvWhat);
            ((LinearLayout)convertView).addView(tvWeight);

            holder=new Holder();
            //holder.tvId=tvId;
            holder.tvYear=tvYear;
            holder.tvMonth=tvMonth;
            holder.tvDay=tvDay;
            holder.tvHour=tvHour;
            holder.tvWhere=tvWhere;
            holder.tvWho=tvWho;
            holder.tvWhat=tvWhat;
            holder.tvWeight=tvWeight;
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }

        final Schedule schedule=(Schedule)getItem(position);
        //holder.tvId.setText(schedule.getId()+"");
        holder.tvYear.setText(schedule.getYear()+"년");
        holder.tvMonth.setText(schedule.getMonth()+"월");
        holder.tvDay.setText(schedule.getDay()+"일");
        holder.tvHour.setText(schedule.getHour()+"시");
        holder.tvWhere.setText(schedule.getWhere()+"에서");
        holder.tvWho.setText(schedule.getWho()+"와");
        holder.tvWhat.setText(schedule.getWhat());
        holder.tvWeight.setText(schedule.getWeight()+"");
        return convertView;
    }
}