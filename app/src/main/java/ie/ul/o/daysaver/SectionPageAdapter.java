package ie.ul.o.daysaver;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ollie on 27/02/2018.
 */

public class SectionPageAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;
    private final List<String> mFragmentTitleList=new ArrayList<>();
    private final List<Fragment> mFragmentList=new ArrayList<>();
    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
        //this.noOfTabs=NumberOfTabs;
    }
    public void addFragments(Fragment fragment,String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitleList.get(position);
    }
    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);

    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
