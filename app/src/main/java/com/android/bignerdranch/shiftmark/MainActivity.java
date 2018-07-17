package com.android.bignerdranch.shiftmark;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.bignerdranch.shiftmark.AnotherActivityes.DaysOptionActivity;
import com.android.bignerdranch.shiftmark.AnotherClasses.Pager.PageAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    PageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new PageAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pager.setCurrentItem(5);
        pager.setOffscreenPageLimit(1);
        adapter.finishUpdate(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.defaultSettings:{
                Intent intent = new Intent(this, DaysOptionActivity.class);
                intent.putExtra("date", getResources().getString(R.string.set_message_to_settings) );
                intent.putExtra("mod",true);
                startActivity(intent);
            }
            case R.id.depressia:{

            }
        }
        return true;

        
    }
}
