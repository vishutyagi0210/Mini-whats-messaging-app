package com.example.miniwhatsapp.Adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.miniwhatsapp.Fragments.Calls;
import com.example.miniwhatsapp.Fragments.Chats;
import com.example.miniwhatsapp.Fragments.Status;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private final String[] titles = new String[]{"Chats","Status","Calls"};

    public FragmentAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
        fragments.add(new Chats());
        fragments.add(new Status());
        fragments.add(new Calls());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
