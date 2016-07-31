package com.goudutheatre.arthurberenice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    public final static int QUOTES_PICK_REQUEST = 1;
    public final static int QUOTES_SELECT_REQUEST = 2;
    private String quotesString;
    private List<String> quotes = new ArrayList<>();
    private List<String> quotesSelected = new ArrayList<>();
    TextView textViewNrGetSms;

    private String QUOTES_FILENAME = "QuotesSelected";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        try {
            //quotesSelected = readListStringToInternalStorageFile(QUOTES_FILENAME);
            quotesSelected = LocalFileManagerClass.readListStringToInternalStorageFile(QUOTES_FILENAME, this);
            quotes = quotesSelected;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SettingsActivity.", "file "+ QUOTES_FILENAME +" doesn't exist");
        }

        final Button buttonDrivePick = (Button) findViewById(R.id.buttonDrivePick);
        buttonDrivePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PickFileWithOpenerActivity.class);
                startActivityForResult(intent, QUOTES_PICK_REQUEST);
            }
        });

        final Button buttonShowSelect = (Button) findViewById(R.id.buttonSelect);
        buttonShowSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ShowSelectQuotesActivity.class);
                intent.putExtra("liste", (Serializable) quotes);
                startActivityForResult(intent, QUOTES_SELECT_REQUEST);
            }
        });

        textViewNrGetSms = (TextView) findViewById(R.id.textViewSelOverTot);

        updateQuotesListView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUOTES_PICK_REQUEST) {
            if (resultCode == RESULT_OK) {
                quotesString = data.getStringExtra("quotesString");
                quotes = data.getStringArrayListExtra("quotes");
                quotesSelected = quotes;
                updateQuotesListView();
                //writeListStringToInternalStorageFile(QUOTES_FILENAME, quotesSelected);
                LocalFileManagerClass.writeListStringToInternalStorageFile(QUOTES_FILENAME, quotesSelected, this);
                File file = new File(QUOTES_FILENAME);
                if (file.exists()) {
                    Log.e("SettingsActivity.", "file " + QUOTES_FILENAME + " created/updated");
                }else{
                    Log.e("SettingsActivity", "file not created/updated");
                }
            }
        }
        if (requestCode == QUOTES_SELECT_REQUEST) {
            if (resultCode == RESULT_OK) {
                quotesSelected = data.getStringArrayListExtra("quotesSelected");
                updateQuotesListView();
                File file = new File(QUOTES_FILENAME);
                //writeListStringToInternalStorageFile(QUOTES_FILENAME, quotesSelected);
                LocalFileManagerClass.writeListStringToInternalStorageFile(QUOTES_FILENAME, quotesSelected, this);
                if (file.exists()) {
                    Log.e("SettingsActivity.", "file " + QUOTES_FILENAME + " created/updated");
                }else{
                    Log.e("SettingsActivity", "file not created/updated");
                }
            }
        }
    }


    private void updateQuotesListView(){
        final ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quotesSelected));
        textViewNrGetSms.setText(quotesSelected.size()+ " / " + quotes.size());
    }

}