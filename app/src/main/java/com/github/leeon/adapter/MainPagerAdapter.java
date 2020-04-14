package com.github.leeon.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.github.leeon.utils.Collections;

import java.util.List;

/**
 * Created by Lyongwang on 2020/4/3 18: 23.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class MainPagerAdapter extends PagerAdapter {
    private List<Fragment> fragments;

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return Collections.isEmpty(fragments) ? 0 : fragments.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
