package gg.bidavid.fragmentcommunicationexcercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements  SubmitClickListener{

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
    private OutputFragment outputFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setUpPager();
    }


    private void initViews(){
        mViewPager=findViewById(R.id.viewPager);
        mTabLayout=findViewById(R.id.tab);
    }

    private void setUpPager() {
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setUpTab();
    }

    private void setUpTab() {
        for(int i =0; i< mTabLayout.getTabCount();i++){
            mTabLayout.getTabAt(i).setText(String.format(Locale.getDefault(), "#%d",i+1));
        }
    }

    @Override
    public void onSubmitClicked(String message) {
        outputFragment = (OutputFragment) pagerAdapter.getItem(1);
        outputFragment.displayMessage(message);
        mViewPager.setCurrentItem(1);
    }
}
