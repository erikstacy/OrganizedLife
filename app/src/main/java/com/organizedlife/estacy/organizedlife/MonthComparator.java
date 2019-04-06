package com.organizedlife.estacy.organizedlife;

import java.util.Comparator;

public class MonthComparator implements Comparator<Month> {

    public int compare(Month left, Month right) {
        return right.getMonthTitle().compareTo(left.getMonthTitle());
    }
}
