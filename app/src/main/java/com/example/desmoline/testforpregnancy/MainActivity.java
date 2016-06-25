package com.example.desmoline.testforpregnancy;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText _editText, name_field;
    Button test_button, developers_button;
    Spinner cycle_duration;
    private int _day;
    private int _month;
    private int _year;
    private String name;
    private AdapterView.OnItemSelectedListener cycle;

    Calendar cal = Calendar.getInstance();
    private int dayToday = cal.get(Calendar.DAY_OF_MONTH);
    private int monthToday = cal.get(Calendar.MONTH);
    private int yearToday = cal.get(Calendar.YEAR);

    public DatePickerDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner = (Spinner) findViewById(R.id.cycle_duration);
//        TextView test_button = (Button)findViewById(R.id.test_button);
        test_button = (Button)findViewById(R.id.test_button);
        developers_button = (Button)findViewById(R.id.developers_button);
        _editText = (EditText)findViewById(R.id._editText);
        cycle_duration = (Spinner)findViewById(R.id.cycle_duration);
        name_field = (EditText)findViewById(R.id.name);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cycle_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
                spinner.setAdapter(adapter);



//        _editText.setText(dayToday + "/" + (1+monthToday)+"/"+yearToday);
            assert test_button != null;
            test_button.setOnClickListener(new View.OnClickListener() {
                String cycle = cycle_duration.getSelectedItem().toString();

                @Override
                public void onClick(View v) {
                    String name = name_field.getText().toString();

                    if(_editText.getText().toString().trim().length() == 0){
                        String result = "Please make sure you enter the date of your last menstration";
                        alertResults(result);
                    }else if(getNumberOfDays() >= 30){
//                        if number of days is greater that nine months ago
                        if(getNumberOfDays()/30 > 9){
                            String result = "Hello Mrs "+name+", Your last date of menstruation is too far behind, if you haven conceived and given birth by now, then am sorry to tell you this but there's something wrong with you. Please check your doctor";
                            alertResults(result);
                        }else{
                            String result = "Congratulations!!! Mrs "+ name+ ", You are pregnant. You are expected to put to birth in about " + (9-getNumberOfDays()/30)+" months\"";
                            alertResults(result);
                        }

                    }else if(getNumberOfDays() < 0 ){
//                        results_display.setText("You are NOT pregnant");
                        String result = "Negative! You are NOT pregnant";
                        alertResults(result);

                    }else if(getNumberOfDays() < 0 ){
                        String result = "Wrong date. Please make sure you select date of previous menstruation.";
                        alertResults(result);
                    }
                }
            });



        _editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myDialog = new DatePickerDialog(MainActivity.this, 0,
                        new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        _day=dayOfMonth;
                        _month=monthOfYear;
                        _year=year;


                        String date = _day + "/" + (_month+1) + "/" + _year;
                        String num_days = "Number of days: "+getNumberOfDays();
                        updateDisplay();

                    }
                }, yearToday, monthToday, dayToday);
                myDialog.show();

            }
        });


        developers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDevelopersActivity();
            }
        });

    } //End of onCreate method

    public int onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//         int cycle = (int) parent.getItemAtPosition(pos);
//        AdapterView.OnItemSelectedListener cycle = cycle_duration.getOnItemSelectedListener();
        int cycle = (int) cycle_duration.getSelectedItem();
        return cycle;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    // updates the date in the birth date EditText
    private void updateDisplay() {

        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month + 1).append("/").append(_year).append(" "));
    }

    private int getNumberOfDays(){

        Calendar c1 = Calendar.getInstance();
        c1.set(_year,_month, _day );
        Calendar c2=Calendar.getInstance();
        c2.set(yearToday,monthToday,dayToday);

        Date d1=c1.getTime();
        Date d2=c2.getTime();

        long diff=d2.getTime()-d1.getTime();
        int total_days=(int)(diff/(1000*24*60*60));

        return total_days;
    }

    private void alertResults(String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Test Results");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void startDevelopersActivity(){
        Intent intent = new Intent(this, Developers.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
