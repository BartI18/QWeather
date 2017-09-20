package ua.com.android.b.art.boka.qweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class CurrentCityActivity extends AppCompatActivity {

    private ViewPager currentVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_city);
        getSupportActionBar().hide();

        currentVP = (ViewPager) findViewById(R.id.currentVP);

        currentVP.setAdapter(new MainAdapter(getSupportFragmentManager()));
    }

    private class MainAdapter extends FragmentPagerAdapter {

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return getString(R.string.today);
                case 1: return getString(R.string.daily);
            }
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new CurrentCityTodayFragment();
                case 1: return new CurrentCityDailyFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
