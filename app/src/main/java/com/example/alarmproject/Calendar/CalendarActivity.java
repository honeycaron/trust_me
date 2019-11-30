package com.example.alarmproject.Calendar;


import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmproject.Calendar.Decorators.FailDecorator;
import com.example.alarmproject.Calendar.Decorators.OnedayDecorator;
import com.example.alarmproject.Calendar.Decorators.SaturdayDecorator;
import com.example.alarmproject.Calendar.Decorators.SuccessDecorator;
import com.example.alarmproject.Calendar.Decorators.SundayDecorator;
import com.example.alarmproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class CalendarActivity extends AppCompatActivity {

    private final OnedayDecorator onedayDecorator = new OnedayDecorator();
    Cursor cursor;
    MaterialCalendarView materialCalendarView;
    TextView tv_list; //show success and fail of alarms
    boolean isFailAlarm = true; //그날 알람의 성공/실패 여부!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        tv_list = findViewById(R.id.calendar_tv_list);
        materialCalendarView = findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2019, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2050, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(), new SaturdayDecorator(), onedayDecorator
        );
        //임의로 넣는 값. 달력에 성공/실패 넣어보기 위함.
        //alarm 받아서 리스트 넣어서 돌려가며 처리해야 할 듯..?
        //왜인지 모르겠지만 자꾸 nullpointexception이 나서 apisimulator에서 for문 안에서. 마지막은 dummy data
        String[] fail_result_example = {"2019/11/3", "2019/11/5", "2019/12/30", "2019/11/17", "0000/0/0"};

        new ApiSimulator(fail_result_example).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth() + 1;
                int day = date.getDay();

                String shot_day = year + "년 " + month + "월 " + day + "일";

                materialCalendarView.clearSelection();
                Toast.makeText(getApplicationContext(), shot_day, Toast.LENGTH_LONG).show();

                //버튼 누르면 db에서 실패한 거 성공한 거 리스트 보여주기

            }
        });
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
        String[] Time_Result;
        int Time_Result_length;

        public ApiSimulator(String[] Time_Result) {
            this.Time_Result = Time_Result;
            Time_Result_length = Time_Result.length;
        }

        @Override
        protected List<CalendarDay> doInBackground(Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            //특정 날짜 달력에 점 표시해주는 코드
            // 월은 0이 1월로 처리됨. 년/일은 그대로임
            //string 문자열인 Time_Result를 받아와서 /를 기준으로 자르고 string을 int로 변환
            for (int i = 0; i < Time_Result_length; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split("/");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year, month - 1, dayy);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            materialCalendarView.addDecorator(new FailDecorator(calendarDays, CalendarActivity.this));
            //materialCalendarView.addDecorator(new SuccessDecorator(calendarDays, CalendarActivity.this));
            /*if(isFailAlarm){ //(Databasehelper.getSuccessOrFail 같이  db 헬퍼에서 달성여부 받아서) alarm.IsFailed 같은거
                materialCalendarView.addDecorator(new FailDecorator(calendarDays, CalendarActivity.this));
            }else{
                materialCalendarView.addDecorator(new SuccessDecorator(calendarDays, CalendarActivity.this));
            }*/
        }
    }
}
