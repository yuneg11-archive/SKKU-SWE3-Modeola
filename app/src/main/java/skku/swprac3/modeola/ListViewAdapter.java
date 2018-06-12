package skku.swprac3.modeola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_MULTI_CHOICE = 0;
    private static final int ITEM_VIEW_TYPE_OX_CHOICE = 1;
    private static final int ITEM_VIEW_TYPE_MAX = 2;

    private ArrayList<ListViewProblemItem> listViewProbList = new ArrayList<ListViewProblemItem>();
    public static ArrayList<Integer> selectedAnswer = new ArrayList<>();

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

    @Override
    public int getItemViewType(int position) {
        return listViewProbList.get(position).getType();
    }



    @Override
    public String toString() {
        return super.toString();
    }

    public ListViewAdapter(){
        for(int i = 0; i < 5; i++){
            selectedAnswer.add(0);
        }
    }

    @Override
    public int getCount() {
        return listViewProbList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        int viewType = getItemViewType(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ListViewProblemItem listViewProblemItem = listViewProbList.get(position);

            switch (viewType){
                case ITEM_VIEW_TYPE_MULTI_CHOICE:
                    convertView = inflater.inflate(R.layout.listview_problem_item, parent, false);
                    TextView itemProblemName = (TextView) convertView.findViewById(R.id.itemProblemName);
                    RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);

                    itemProblemName.setText(listViewProblemItem.getProbName());
                    RadioButton choice1 = ((RadioButton) radioGroup.getChildAt(0));
                    RadioButton choice2 = ((RadioButton) radioGroup.getChildAt(1));
                    RadioButton choice3 = ((RadioButton) radioGroup.getChildAt(2));
                    RadioButton choice4 = ((RadioButton) radioGroup.getChildAt(3));
                    RadioButton choice5 = ((RadioButton) radioGroup.getChildAt(4));

                    choice1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                selectedAnswer.set(position, 1);
                            }
                        }
                    });
                    choice2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                selectedAnswer.set(position, 2);
                            }
                        }
                    });
                    choice3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                selectedAnswer.set(position, 3);
                            }
                        }
                    });
                    choice4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                selectedAnswer.set(position, 4);
                            }
                        }
                    });
                    choice5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                selectedAnswer.set(position, 5);
                            }
                        }
                    });

                    ((RadioButton) radioGroup.getChildAt(0)).setText(listViewProblemItem.getProbItem1());
                    ((RadioButton) radioGroup.getChildAt(1)).setText(listViewProblemItem.getProbItem2());
                    ((RadioButton) radioGroup.getChildAt(2)).setText(listViewProblemItem.getProbItem3());
                    ((RadioButton) radioGroup.getChildAt(3)).setText(listViewProblemItem.getProbItem4());
                    ((RadioButton) radioGroup.getChildAt(4)).setText(listViewProblemItem.getProbItem5());
                    break;
                case ITEM_VIEW_TYPE_OX_CHOICE:
                    convertView = inflater.inflate(R.layout.listview_problem_item_2, parent, false);
                    TextView itemProblemName_OX = (TextView) convertView.findViewById(R.id.itemProblemName);
                    TextView questionText = (TextView) convertView.findViewById(R.id.questionText);
                    RadioGroup radioGroup_OX = (RadioGroup) convertView.findViewById(R.id.radioGroup);

                    itemProblemName_OX.setText(listViewProblemItem.getProbName());
                    questionText.setText(listViewProblemItem.getProbItem3());
                    RadioButton choice_O = ((RadioButton) radioGroup_OX.getChildAt(0));
                    RadioButton choice_X = ((RadioButton) radioGroup_OX.getChildAt(1));

                    choice_O.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                selectedAnswer.set(position, 1);
                            }
                        }
                    });
                    choice_X.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                selectedAnswer.set(position, 2);
                            }
                        }
                    });
            }
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewProbList.get(position);
    }

    public void addProblem(String probName, String[] scheduleTextArray, int type){
        ListViewProblemItem item = new ListViewProblemItem();
        switch (type){
            case ITEM_VIEW_TYPE_MULTI_CHOICE:
                item.setType(type);
                item.setProbName(probName);
                item.setProbItem1(scheduleTextArray[0]);
                item.setProbItem2(scheduleTextArray[1]);
                item.setProbItem3(scheduleTextArray[2]);
                item.setProbItem4(scheduleTextArray[3]);
                item.setProbItem5(scheduleTextArray[4]);
                break;
            case ITEM_VIEW_TYPE_OX_CHOICE:
                item.setType(type);
                item.setProbName(probName);
                item.setProbItem3(scheduleTextArray[0]);
                item.setProbItem1("O");
                item.setProbItem2("X");
                break;
        }
        listViewProbList.add(item);
    }
}
