package com.example.emda.simpletodo;

import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private String appVersion = "1";
    private Toolbar toolbar;
    private TextView contactMe;
    private TextView mVersionTextView;
    private CardView cardView;
    String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean nightMode = false;

        theme = getSharedPreferences(MainActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(MainActivity.THEME_SAVED, MainActivity.LIGHTTHEME);
        if (theme.equals(MainActivity.DARKTHEME)) {
            setTheme(R.style.CustomStyle_DarkTheme);
            nightMode = true;
        } else {
            setTheme(R.style.CustomStyle_LightTheme);
            nightMode = false;

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardView = findViewById(R.id.card_view);
        mVersionTextView = findViewById(R.id.aboutVersionTextView);
        mVersionTextView.setText(String.format(getResources().getString(R.string.app_version), appVersion));
        toolbar = findViewById(R.id.toolbar);
        contactMe = findViewById(R.id.aboutContactMe);
        if (!nightMode)
            cardView.setCardBackgroundColor(Color.WHITE);


        contactMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



