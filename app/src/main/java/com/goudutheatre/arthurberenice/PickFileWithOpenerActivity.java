package com.goudutheatre.arthurberenice;
/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

        import android.content.Intent;
        import android.content.IntentSender;
        import android.content.IntentSender.SendIntentException;
        import android.net.Uri;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.common.api.ResultCallback;
        import com.google.android.gms.common.api.Status;
        import com.google.android.gms.drive.Drive;
        import com.google.android.gms.drive.DriveApi;
        import com.google.android.gms.drive.DriveContents;
        import com.google.android.gms.drive.DriveFile;
        import com.google.android.gms.drive.DriveId;
        import com.google.android.gms.drive.OpenFileActivityBuilder;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.List;

/**
 * An activity to illustrate how to pick a file with the
 * opener intent.
 */
public class PickFileWithOpenerActivity extends BaseDemoActivity {

    private static final String TAG = "PickFileWithOpenerAct.";

    private static final int REQUEST_CODE_OPENER = 1;

    private GoogleApiClient client;

    private int intentresult;

    private DriveId mSelectedFileDriveId;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);

        // If there is a selected file, open its contents.
        if (mSelectedFileDriveId != null) {
            open();
            return;
        }

        // Let the user pick a file if there are
        // no files selected by the user.
        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .setMimeType(new String[]{ "text/plain" })
                .build(getGoogleApiClient());
        try {
            startIntentSenderForResult(intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (SendIntentException e) {
            Log.w(TAG, "Unable to send intent", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OPENER && resultCode == RESULT_OK) {
            mSelectedFileDriveId = (DriveId) data.getParcelableExtra(
                    OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void open() {
        DriveFile driveFile =  mSelectedFileDriveId.asDriveFile();
        driveFile.open(getGoogleApiClient(), DriveFile.MODE_READ_ONLY, null)
                .setResultCallback(driveContentsCallback);
        mSelectedFileDriveId = null;
    }

    private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback =
            new ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    List<String> quotes = new ArrayList<>();

                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while opening the file contents");
                        return;
                    }
                    showMessage("File contents opened");
                    DriveContents contents = result.getDriveContents();

                    //reading the input stream
                    BufferedReader reader = new BufferedReader(new InputStreamReader(contents.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                            quotes.add(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String contentsAsString = builder.toString();


                    //showMessage(contentsAsString);
                    intentresult = RESULT_OK;
                    //réponse à l'activité qui a demandé la liste
                    Intent intent = new Intent();
                    intent.putExtra("quotesString", contentsAsString);
                    intent.putExtra("quotes", (Serializable) quotes);
                    setResult(intentresult, intent);
                    finish();
                }
            };
}

