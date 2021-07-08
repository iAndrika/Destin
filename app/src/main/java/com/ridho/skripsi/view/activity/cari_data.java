package com.ridho.skripsi.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;

import com.ridho.skripsi.R;
import com.ridho.skripsi.view.adapter.CariDataAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class cari_data extends AppCompatActivity {
    private RecyclerView rvListData;

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

        rvListData = findViewById(R.id.listTextFile);
        rvListData.setHasFixedSize(true);
        showRecyclerList(arrayText.toArray(new String[0]));

//        rvListData.setLayoutManager(new LinearLayoutManager(this));
//        CariDataAdapter listTextAdapter = new CariDataAdapter(arrayText.toArray(new String[0]));
//        rvListData.setAdapter(listTextAdapter);
    }

    private void showRecyclerList(String[] myArrayData){
        String[] sampleArray = {"sample 1", "sample 2", "sample 3"};
        rvListData.setLayoutManager(new LinearLayoutManager(this));
        CariDataAdapter listTextAdapter = new CariDataAdapter(myArrayData);
        rvListData.setAdapter(listTextAdapter);
    }
}