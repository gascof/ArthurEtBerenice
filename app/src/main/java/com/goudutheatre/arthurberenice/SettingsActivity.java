package com.goudutheatre.arthurberenice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public final static int QUOTES_REQUEST = 1;
    private String quotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button buttonDrivePick = (Button) findViewById(R.id.buttonDrivePick); //accès à l'activité paramètres
        buttonDrivePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PickFileWithOpenerActivity.class);
                startActivityForResult(intent, QUOTES_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUOTES_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "retour de QUOTES_REQUEST", Toast.LENGTH_SHORT).show();
                quotes = data.getStringExtra("quotes");
                TextView textViewQuotes = (TextView) findViewById(R.id.textViewQuotes);
                textViewQuotes.setText(quotes);


            }
        }
    }
}