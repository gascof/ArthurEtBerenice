package com.goudutheatre.arthurberenice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowSelectActivity extends AppCompatActivity {
    private int result;

    private List<String> phoneNrs = new ArrayList<>();
    private List<String> phoneNrsSelected = new ArrayList<>();
    private int nrPhones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_select);

        phoneNrs = getIntent().getStringArrayListExtra("liste");

        final ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(new ArrayAdapter<>(ShowSelectActivity.this, android.R.layout.simple_list_item_multiple_choice, phoneNrs));
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        //initiate the selection for all items
        for (int i = 0; i < list.getCount(); i++) {
            list.setItemChecked(i, true);
        }

        // checkbox tous
        final CheckBox checkboxSelectAll = (CheckBox) findViewById(R.id.checkBoxSelectAll);
        checkboxSelectAll.setChecked(true);
        checkboxSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = checkboxSelectAll.isChecked();
                for (int i = 0; i < list.getCount(); i++) {list.setItemChecked(i, checked);}
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkboxSelectAll.setChecked(list.getCount()==list.getCheckedItemCount());
            }
        });


        final Button buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //construction de la liste de résultats
                nrPhones = phoneNrs.size();
                if (nrPhones <= 0) {
                    Log.e("ShowSelectActivity", "No phone number to reply to");
                    result = RESULT_CANCELED;
                }

                if (nrPhones > 0) {
                    result = RESULT_OK;
                    do {
                        if (list.isItemChecked(nrPhones)) {
                            phoneNrsSelected.add(phoneNrs.get(nrPhones - 1));
                        }
                        nrPhones--;
                    } while (nrPhones > 0);
                }
                //réponse à l'activité qui a demandé la liste
                Intent intent = new Intent();
                intent.putExtra("phoneNrsSelected", (Serializable) phoneNrsSelected);
                setResult(result, intent);
                finish();
            }
        });


    }
}
