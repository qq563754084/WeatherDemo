package com.weatherdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weatherdemo.domain.Index;
import com.weatherdemo.domain.Weather;
import com.weatherdemo.domain.WeatherData;
import com.weatherdemo.net.GetWeather;
import com.weatherdemo.utils.Config;

import java.util.List;

import cn.taurusxi.guidebackgroundcoloranimation.library.ColorAnimationView;

public class AtyMain extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        getWeather();

    }
    private void getWeather() {
        new GetWeather(Config.LOCATION,new GetWeather.SuccessCallback() {
            @Override
            public void onSuccess(Weather weather) {
                init(weather);
                findViewById(R.id.wait).setVisibility(View.GONE);
            }
        },new GetWeather.FailCallback() {
            @Override
            public void onFail() {
                findViewById(R.id.pbar).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.ptext)).setText("网络连接失败啦TAT");
            }
        });
    }

    private void init(Weather weather) {
        MyFragmentStatePager adapter = new MyFragmentStatePager(getSupportFragmentManager(),weather);
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        colorAnimationView.setmViewPager(viewPager, weather.getDatas().size());
    }
    public class MyFragmentStatePager extends FragmentStatePagerAdapter {
        private Weather weather;
        public MyFragmentStatePager(FragmentManager fm,Weather weather ){
            super(fm);
            this.weather=weather;
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment(weather,position);
        }

        @Override
        public int getCount() {
            return weather.getDatas().size();
        }
    }

    @SuppressLint("ValidFragment")
    public class MyFragment extends Fragment {
        private int position;
        private Weather weather;
        public MyFragment(Weather weather,int position) {
            this.position = position;
            this.weather=weather;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            WeatherData data = weather.getDatas().get(position);
            List<Index> indexes= weather.getIndexs();
            RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.frg_index, container, false);
            TextView temperature = (TextView) view.findViewById(R.id.temperature);
            TextView date = (TextView) view.findViewById(R.id.date);
            TextView weathert = (TextView) view.findViewById(R.id.weather);
            TextView location = (TextView) view.findViewById(R.id.location);

            temperature.setText(data.getTemperature());
            date.setText(data.getDate());
            weathert.setText(data.getWeather());
            location.setText(weather.getCurrentCity());
            if (position==0) {
                ListView listView = (ListView) view.findViewById(R.id.listView);
                ListAdapter adapter = new ListAdapter(AtyMain.this, indexes);
                listView.setAdapter(adapter);
            }



            return view;
        }
    }
}
