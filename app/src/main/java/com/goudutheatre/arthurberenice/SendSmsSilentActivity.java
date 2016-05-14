package com.goudutheatre.arthurberenice;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SendSmsSilentActivity extends AppCompatActivity {
    private int result;
    private static final Uri SMS_URI_INBOX = Uri.parse("content://sms/inbox");
    private List<String> phoneNrs = new ArrayList<>();
    private String Hashfilter = "";
    private int NrSelSms = 0;
    private int NrSmsToSend = 0;
    private static final Uri SMS_URI_OUTBOX = Uri.parse("content://sms/inbox");
    private String smsText = "sms réponse Arthur & Bérénice";


    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNrs = getIntent().getStringArrayListExtra("liste");
        NrSelSms = phoneNrs.size();



// envoyer les SMS de réponses aux SMS contenus dans messages
        if (NrSelSms<=0)
        {
            Log.e("SendSmsSilentActivity", "No message to reply to");
            result = RESULT_CANCELED;
        }

        if (NrSelSms>0)
        {
            result = RESULT_OK;
            NrSmsToSend = NrSelSms;
            //final String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            Toast.makeText(this, "trying to send sms.", Toast.LENGTH_LONG).show();

            do
            {
                sendSms(phoneNrs.get(NrSmsToSend-1), smsText);
                NrSmsToSend --;
            }
            while (NrSmsToSend >0);
            result = RESULT_OK;
        }


        //réponse à l'activité qui a demandé la liste
        Intent intent = new Intent();
        intent.putExtra("nrSelSms", NrSelSms);
        setResult(result, intent);
        finish();
    }


    public void sendSms(String phoneNumber, String smsBody) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, smsBody, null, null);
    }

}
