package ua.com.android.b.art.boka.qweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AddNewCityI{

    private ViewPager mainVP;
    private ListCityFragment listCityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        listCityFragment = new ListCityFragment();

        init();

    }


    private void init(){
        mainVP = (ViewPager) findViewById(R.id.ourViewPager);
        mainVP.setAdapter(new MainAdapter(getSupportFragmentManager()));
    }

    public static boolean hasConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }

    @Override
    public void addNewCity(String city) {
        SharedPreferences sh = getPreferences(MODE_PRIVATE);
        Map<String,String> list = new HashMap<>();
        list.putAll((Map<? extends String, ? extends String>) sh.getAll());
        if(list.containsValue(city)==false) {
            SharedPreferences.Editor edit = sh.edit();
            edit.putString("key_" + city, city);
            edit.apply();
            listCityFragment.updateList();
        }
    }

    private class MainAdapter extends FragmentPagerAdapter {

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return getString(R.string.search_fragment);
                case 1: return getString(R.string.saved_fragment);
            }
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new SearchCityFragment();
                case 1: return listCityFragment;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
