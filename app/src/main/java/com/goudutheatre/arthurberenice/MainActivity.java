package com.goudutheatre.arthurberenice;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;


public class MainActivity extends AppCompatActivity {

    public final static int GETSMSSILENT_BUTTON_REQUEST = 1;
    private List<String> messages = new ArrayList<>();
    private int NrGetSms = 0;
    private int NrSelSms = 0;
    //private int dateStart;

    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    private TextView dateStart, dateEnd;
    private String dateInit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get current date by calender
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
        dateInit = (day) +"/"+ (month+1) +"/"+ (year);//new StringBuilder()
                // Month is 0 based, just add 1
               // .append(month + 1).append("-").append(day).append("-")
                //.append(year).append(" ");

        // Show current date
        dateStart = (TextView) findViewById(R.id.dateStart);
        dateEnd = (TextView) findViewById(R.id.dateEnd);
        dateStart.setText(dateInit);
        dateEnd.setText(dateInit);


        final Button buttonParam = (Button) findViewById(R.id.buttonParam); //accès à l'activité paramètres
        buttonParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        final Button buttonGetSms = (Button) findViewById(R.id.buttonGetSms);//accès à l'activité getsms avec affichage
        buttonGetSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetSmsActivity.class);
                startActivity(intent);
            }
        });

        final Button buttonGetSmsSilent = (Button) findViewById(R.id.buttonGetSmsSilent);
        buttonGetSmsSilent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetSmsSilentActivity.class);
                startActivityForResult(intent, GETSMSSILENT_BUTTON_REQUEST);
            }
        });

        final ListView list = (ListView) findViewById(android.R.id.list);
        final Button buttonShowSms = (Button) findViewById(R.id.buttonShowSms);
        buttonShowSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messages != null)
                {
                    list.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, messages));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode== GETSMSSILENT_BUTTON_REQUEST){
            if (resultCode == RESULT_OK) {
                // On affiche le bouton qui a été choisi
                Toast.makeText(this, "réponse de GetSmsSilentActivity ", Toast.LENGTH_SHORT).show();
                messages = data.getStringArrayListExtra("liste");
                NrGetSms = messages.size();
                TextView textViewNrGetSms = (TextView) findViewById(R.id.textNrGetSms);
                textViewNrGetSms.setText(""+NrGetSms);

            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
