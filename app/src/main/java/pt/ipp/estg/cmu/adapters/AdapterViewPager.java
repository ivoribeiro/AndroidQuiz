package pt.ipp.estg.cmu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by navega on 11/19/16.
 */

public class AdapterViewPager extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mTitlesList;

    public AdapterViewPager(FragmentManager manager) {
        super(manager);
        mFragmentList = new ArrayList<>();
        mTitlesList = null;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}

