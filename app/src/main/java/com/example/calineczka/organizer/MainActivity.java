package com.example.calineczka.organizer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    TextView showTasks;
    EditText addTask;
    Button addButton;
    Button removeButton;
    String filename =  "Saved_Organizer";
    String task;
    FileOutputStream fileOutputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTasks = (TextView) findViewById(R.id.showListView);
        addTask = (EditText) findViewById(R.id.taskText);
        addButton = (Button) findViewById(R.id.addButton);
        removeButton = (Button) findViewById(R.id.removeButton);

        readSavedData();
    }



    @Override
    protected void onStart() {
        super.onStart();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    task = addTask.getText().toString();
                    if (!isEmpty()) {
                        showTasks.append(task + "\n");
                        saveTasks();
                        showMessageSave();
                        clearEditText();
                    } else {
                        requireText();
                    }
                }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTasks.setText("");
            }
        });
    }




    private void readSavedData() {
        try {
            FileInputStream fileInputStream = openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String read = bufferedReader.readLine();
            while(read!=null){
                showTasks.append(read+"\n");
                read = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isEmpty() {
        return task.equals("");
    }

    private void requireText() {
        String messageEmpty = "Enter text";
        Toast.makeText(MainActivity.this, messageEmpty, Toast.LENGTH_SHORT).show();
    }

    private void clearEditText() {
        addTask.selectAll();
        addTask.setText("");
    }

    private void showMessageSave() {
        String message = "Task saved";
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveTasks() {
        try {
            fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write(showTasks.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTasks();
    }
}


