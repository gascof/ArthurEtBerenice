package com.goudutheatre.arthurberenice;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GetSmsSilentActivity extends AppCompatActivity {
    private int result;
    private static final Uri SMS_URI_INBOX = Uri.parse("content://sms/inbox");
    private List<String> messages = new ArrayList<>();
    private List<String> phoneNrs = new ArrayList<>();
    private List<String> bodys = new ArrayList<>();
    private String Hashfilter = "";

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hashfilter = getIntent().getExtras().getString("HASH_FILTER");

        //génération de la liste
        //cursor = getContentResolver().query(SMS_URI_INBOX, null, null, null, null);
        String[] projection = new String[] { "_id", "address", "person", "body", "date_sent", "type" };
        Cursor cursor = getContentResolver().query(SMS_URI_INBOX, projection, "body LIKE '%" + Hashfilter +"%'", null, "date asc");
        //Cursor cursor = getContentResolver().query(SMS_URI_INBOX, projection, "body LIKE '%#arthurberenice%'", null, "date asc");
        //Cursor cursor = getContentResolver().query(SMS_URI_INBOX, projection, null, null, "date asc");
        //Cursor cur = getContentResolver().query(SMS_URI_INBOX, projection, "address LIKE '%Google%'", null, "date asc");

        if (cursor == null)
        {
            Log.e("GetSmsSilentActivity", "Cannot retrieve the messages");
            result = RESULT_CANCELED;
        }

        if (cursor.moveToFirst() == true)
        {
            result = RESULT_OK;
            do
            {
                final String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                final String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                //final String date_sent = cursor.getString(cursor.getColumnIndexOrThrow("date_sent"));
               // Date date = new Date(cursor.getLong(0));
                //String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
                String date_sent = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date_sent"))));

                messages.add(date_sent + " - " + address + " - " + body);
                phoneNrs.add(address);
                bodys.add(body);


                Log.d("retrieveContacts", "The message with from + '" + address + "' with the body '" + body + "' has been retrieved");
            }
            while (cursor.moveToNext() == true);
        }

        if (cursor.isClosed() == false)
        {
            cursor.close();
        }


        //réponse à l'activité qui a demandé la liste
        Intent intent = new Intent();
        intent.putExtra("liste", (Serializable) messages);
        intent.putExtra("listePhoneNrs", (Serializable) phoneNrs);
        intent.putExtra("listeBodys", (Serializable) bodys);
        setResult(result, intent);
        finish();
    }


}
