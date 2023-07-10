package com.example.miniwhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.miniwhatsapp.Adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    TabLayout tabs;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setIds();
        setSupportActionBar(toolbar);



        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.for_main_activity , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.MenuSettings:
                intent = new Intent(MainActivity.this , SettingsLayout.class);
                startActivity(intent);
                break;
            case R.id.MenuLogOut:
                intent = new Intent(MainActivity.this , SignInActivity.class);
                startActivity(intent);
                userAlreadyLogIn(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setIds(){
        toolbar = findViewById(R.id.ToolBar);
        tabs = findViewById(R.id.MainActivityTabs);
        viewPager = findViewById(R.id.MainActivityViewPager);
    }

    public void userAlreadyLogIn(boolean trueOrFalse){
        SharedPreferences preferences = getSharedPreferences("logIn" , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isDone",trueOrFalse);
        editor.apply();
    }
}