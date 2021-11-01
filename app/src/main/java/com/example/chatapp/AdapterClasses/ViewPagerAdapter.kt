package com.example.chatapp.AdapterClasses

import android.widget.ArrayAdapter

import android.os.Bundle

import androidx.annotation.NonNull

import android.widget.TextView

import android.R
import android.util.Log

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentPagerAdapter

import androidx.viewpager.widget.ViewPager

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.chatapp.Fragments.ChatFragment
import com.example.chatapp.Fragments.SearchFragment
import com.example.chatapp.Fragments.SettingsFragment

class ViewPagerAdapter : FragmentManager() {

    class ViewPagerAdapter2(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            if (position == 0) {
                fragment = ChatFragment()
            } else if (position == 1) {
                fragment = SearchFragment()
            } else if (position == 2) {
                fragment = SettingsFragment()
            }
            return fragment!!
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            var title: String? = null
            if (position == 0) {
                title = "Chat"
            } else if (position == 1) {
                title = "Search"
            } else if (position == 2) {
                title = "Settings"
            }
            return title
        }
    }
}