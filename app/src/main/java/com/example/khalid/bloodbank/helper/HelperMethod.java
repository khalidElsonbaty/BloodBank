package com.example.khalid.bloodbank.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HelperMethod {
    private static Calendar myCalendar;
    private static ProgressDialog checkDialog;


    public static void replace(Fragment fragment, FragmentManager supportFragmentManager, int id,TextView toolbar_Title, String title){
       FragmentTransaction fragmentTransaction= supportFragmentManager.beginTransaction();
       fragmentTransaction.replace(id,fragment);
       fragmentTransaction.addToBackStack(null);
       fragmentTransaction.commit();
       if((toolbar_Title != null)){
           toolbar_Title.setText(title);
       }
    }

    public static void getDate(final EditText editText, Activity activity){
         myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {


                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }

        };
        new DatePickerDialog(activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private static void updateLabel(EditText editText) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    public static void showProgressDialog(Activity activity, String title) {
        try {
            if (checkDialog == null) {
                checkDialog = new ProgressDialog(activity);
                checkDialog.setMessage(title);
                checkDialog.setIndeterminate(false);
                checkDialog.setCancelable(false);
            }
            checkDialog.show();

        } catch (Exception e) {

        }
    }

    public static void dismissProgressDialog() {
        try {
            if (checkDialog != null && checkDialog.isShowing()) {
                checkDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }
}
