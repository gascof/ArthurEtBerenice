package com.goudutheatre.arthurberenice;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalFileManagerClass {
    private List<String> liste = new ArrayList<>();
    private String fileName;
    private String action;
    private int result;


    //public final static int ACTION_READ_LIST = 1;
    //public final static int ACTION_WRITE_LIST = 2;


    static public void writeListStringToInternalStorageFile(String fileName, List<String> liste, Context myContext) {
        String eol = System.getProperty("line.separator");
        int nrItems = liste.size();
        BufferedWriter writer = null;
        try {
            writer =
                    new BufferedWriter(new OutputStreamWriter(myContext.openFileOutput(fileName,
                            Context.MODE_PRIVATE)));
            if (nrItems > 0) {
                for (int i = 0; i < nrItems; i++) {
                    writer.write(liste.get(i) + eol);
                }
            }
            Log.e("SettingsActivity", "writer ok");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SettingsActivity", "exception");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static public List<String> readListStringToInternalStorageFile(String fileName, Context myContext) {
        List<String> listeString = new ArrayList<>();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(myContext.openFileInput(fileName)));
            String line;
            while ((line = input.readLine()) != null) {
                listeString.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listeString;
    }

    static public void writeStringToInternalStorageFile(String fileName, String string, Context myContext) {
        String eol = System.getProperty("line.separator");
        BufferedWriter writer = null;
        try {
            writer =
                    new BufferedWriter(new OutputStreamWriter(myContext.openFileOutput(fileName,
                            Context.MODE_PRIVATE)));
            writer.write(string + eol);
            Log.e("SettingsActivity", "writer ok");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SettingsActivity", "exception");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static public String readStringToInternalStorageFile(String fileName, Context myContext) {
        String string ="";
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(myContext.openFileInput(fileName)));
            String line;
            while ((line = input.readLine()) != null) {
                string=line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return string;
    }

}
