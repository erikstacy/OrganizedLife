package com.organizedlife.estacy.organizedlife;

import java.util.Comparator;

public class GeneralNoteComparator implements Comparator<GeneralNote> {

    public int compare(GeneralNote left, GeneralNote right) {
        return left.getNoteTitle().compareTo(right.getNoteTitle());
    }
}
