package com.goudutheatre.arthurberenice;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SendSmsSilentActivity extends AppCompatActivity {
    private int result;
    private static final Uri SMS_URI_INBOX = Uri.parse("content://sms/inbox");
    private List<String> messages = new ArrayList<>();
    private String Hashfilter = "";
    private int NrSelSms = 0;


    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messages = getIntent().getStringArrayListExtra("liste");
        NrSelSms = messages.size();


// envoyer les SMS de réponses aux SMS contenus dans messages

        result = RESULT_OK;

        //réponse à l'activité qui a demandé la liste
        Intent intent = new Intent();
        intent.putExtra("nrSelSms", NrSelSms);
        setResult(result, intent);
        finish();
    }


}
