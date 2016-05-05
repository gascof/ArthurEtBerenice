package com.goudutheatre.arthurberenice;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GetSmsSilentActivity extends AppCompatActivity {
    private int result;
    private static final Uri SMS_URI_INBOX = Uri.parse("content://sms/inbox");
    private List<String> messages = new ArrayList<>();

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //génération de la liste
        cursor = getContentResolver().query(SMS_URI_INBOX, null, null, null, null);
        //Type: INTEGER (long)
        //Constant Value: "date_sent"

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
                final String date_sent = cursor.getString(cursor.getColumnIndexOrThrow("date_sent"));
                //cursor.get

                messages.add(date_sent + " - " + address + " - " + body);

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
        setResult(result, intent);
        finish();
    }


}
