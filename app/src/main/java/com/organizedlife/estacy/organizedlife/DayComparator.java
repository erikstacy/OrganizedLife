package com.organizedlife.estacy.organizedlife;

import java.util.Comparator;

public class DayComparator implements Comparator<Day> {

    public int compare(Day left, Day right) {
        return right.getDayTitle().compareTo(left.getDayTitle());
    }
}
