package com.example.emda.simpletodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ReminderActivity extends AppCompatActivity{
    private TextView mtoDoTextTextView;
    private Button mRemoveToDoButton;
    private MaterialSpinner mSnoozeSpinner;
    private String[] snoozeOptionsArray;
    private StrorageDataManager strorageDataManager;
    private ArrayList<ItemToDo> mItemToDos;
    private ItemToDo mItem;
    public static final String EXIT = "com.example.simpletodo.exit";
    private TextView mSnoozeTextView;
    String theme;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        theme = getSharedPreferences(MainActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
        if(theme.equals(MainActivity.LIGHTTHEME)){
            setTheme(R.style.CustomStyle_LightTheme);
        }
        else{
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        super.onCreate(savedInstanceState);
        strorageDataManager = new StrorageDataManager(this, MainActivity.FILENAME);
        mItemToDos = MainActivity.getLocallyStoredData(strorageDataManager);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));



        Intent i = getIntent();
        UUID id = (UUID)i.getSerializableExtra(NotificationService.TODOUUID);
        mItem = null;
        for(ItemToDo itemToDo : mItemToDos){
            if (itemToDo.getIdentifier().equals(id)){
                mItem = itemToDo;
                break;
            }
        }

        snoozeOptionsArray = getResources().getStringArray(R.array.snooze_options);

        mRemoveToDoButton = findViewById(R.id.toDoReminderRemoveButton);
        mtoDoTextTextView = findViewById(R.id.toDoReminderTextViewBody);
        mSnoozeTextView = findViewById(R.id.reminderViewSnoozeTextView);
        mSnoozeSpinner = findViewById(R.id.todoReminderSnoozeSpinner);

        mtoDoTextTextView.setText(mItem.getToDoText());

        if(theme.equals(MainActivity.LIGHTTHEME)){
            mSnoozeTextView.setTextColor(getResources().getColor(R.color.secondary_text));
        }
        else{
            mSnoozeTextView.setTextColor(Color.WHITE);
            mSnoozeTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_snooze_white_24dp,0,0,0
            );
        }

        mRemoveToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemToDos.remove(mItem);
                changeOccurred();
                saveData();
                closeApp();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_text_view, snoozeOptionsArray);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSnoozeSpinner.setAdapter(adapter);

    }

    private void closeApp(){
        Intent i = new Intent(ReminderActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(EXIT, true);
        editor.apply();
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }
    private void changeOccurred(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.CHANGE_OCCURED, true);
        editor.apply();
    }

    private Date addTimeToDate(int mins){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, mins);
        return calendar.getTime();
    }
    private int valueFromSpinner(){
        switch (mSnoozeSpinner.getSelectedItemPosition()){
            case 0:
                return 10;
            case 1:
                return 30;
            case 2:
                return 60;
            default:
                return 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toDoReminderDoneMenuItem:
                Date date = addTimeToDate(valueFromSpinner());
                mItem.setToDoDate(date);
                mItem.setHasReminder(true);
                changeOccurred();
                saveData();
                closeApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void saveData(){
        try{
            strorageDataManager.saveToFile(mItemToDos);
        }
        catch (JSONException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}