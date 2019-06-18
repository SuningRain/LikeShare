package sunningrain.github.likeshare.adapter.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import sunningrain.github.likeshare.base.BaseFragment;

/**
 * Created by 27837 on  2019/4/13.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private ArrayList<String> mFragmentsTitle = new ArrayList<>();

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(BaseFragment fragment, String fragmentTitle){
        this.mFragments.add(fragment);
        this.mFragmentsTitle.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsTitle.get(position);
    }
}
