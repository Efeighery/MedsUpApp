package com.example.medsupapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
/*
 * Class name: TimePickerFragments.java
 * Date: 25/2/2023
 * @author: Eoghan Feighery, x19413886
 * Version: Revision 1
 */

/*
 * @reference: https://gist.github.com/codinginflow/a26b41c07c1c2373f6aa92726ae92018/TimePickerFragments.java
 */
public class TimePickerFragments extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Here, the Calendar and Time objects taken from the TimePicker object is used to help return
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hours, minutes, DateFormat.is24HourFormat(getActivity()));
    }
}
