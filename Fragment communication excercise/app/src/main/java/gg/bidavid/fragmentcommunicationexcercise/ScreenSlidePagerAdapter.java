package gg.bidavid.fragmentcommunicationexcercise;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES=4;

    private InputFragment inputFragment =new InputFragment();
    private OutputFragment outputFragment = new OutputFragment();

    public ScreenSlidePagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return inputFragment;
            case 1:
                return outputFragment;
            case 2:
                return ModularFragment.newInstance("text");
            default:
                return ModularFragment.newInstance("picture");
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
