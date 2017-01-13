package pt.ipp.estg.cmu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 8130031
 * @author 8130258
 */
public class AdapterViewPager extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mTitlesList;

    public AdapterViewPager(FragmentManager manager) {
        super(manager);
        mFragmentList = new ArrayList<>();
        mTitlesList = new ArrayList<String>();
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

    public void addTitle(String title) {
        mTitlesList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitlesList.size() != 0 ? mTitlesList.get(position) : "";
    }
}

