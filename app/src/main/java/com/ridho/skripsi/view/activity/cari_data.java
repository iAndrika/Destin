package com.ridho.skripsi.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;

import com.ridho.skripsi.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class cari_data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File example = Environment.getExternalStorageDirectory();
        File file = new File(example, "example.txt");
        StringBuilder textFromFiles = new StringBuilder();

        ArrayList<String> arrayText = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null){
                arrayText.add(line);
            }
        } catch (IOException e) {
            System.out.println("SOMETHING WHEN WRONG ===>" + e);
        }

        System.out.println("READ FILE ==========> " + arrayText);

        setContentView(R.layout.activity_cari_data);
    }
}