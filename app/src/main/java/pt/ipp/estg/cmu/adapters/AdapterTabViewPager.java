package pt.ipp.estg.cmu.adapters;

import android.support.v4.app.FragmentManager;

/**
 * Created by ivoribeiro on 22-12-2016.
 */

public class AdapterTabViewPager extends AdapterViewPager {
    public AdapterTabViewPager(FragmentManager manager) {
        super(manager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
