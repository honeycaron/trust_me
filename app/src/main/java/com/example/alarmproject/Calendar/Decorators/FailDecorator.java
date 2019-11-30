package com.example.alarmproject.Calendar.Decorators;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.example.alarmproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class FailDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private HashSet<CalendarDay> dates;

    public FailDecorator(Collection<CalendarDay> dates, Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_fail);
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}

