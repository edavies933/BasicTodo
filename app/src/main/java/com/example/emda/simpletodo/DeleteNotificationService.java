package com.example.emda.simpletodo;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by emda on 2/9/2018.
 */

public class DeleteNotificationService extends IntentService {

    private StrorageDataManager strorageDataManager;
    private ArrayList<ItemToDo> mItemToDos;
    private ItemToDo mItem;

    public DeleteNotificationService(){
        super("DeleteNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        strorageDataManager = new StrorageDataManager(this, MainActivity.FILENAME);
        UUID todoID = (UUID)intent.getSerializableExtra(NotificationService.TODOUUID);

        mItemToDos = loadData();
        if(mItemToDos !=null){
            for(ItemToDo item : mItemToDos){
                if(item.getIdentifier().equals(todoID)){
                    mItem = item;
                    break;
                }
            }

            if(mItem!=null){
                mItemToDos.remove(mItem);
                dataChanged();
                saveData();
            }

        }

    }

    private void dataChanged(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.CHANGE_OCCURED, true);
        editor.apply();
    }

    private void saveData(){
        try{
            strorageDataManager.saveToFile(mItemToDos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private ArrayList<ItemToDo> loadData(){
        try{
            return strorageDataManager.loadFromFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
