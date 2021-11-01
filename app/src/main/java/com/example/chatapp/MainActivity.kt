package com.example.chatapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.chatapp.Fragments.ChatFragment
import com.example.chatapp.Fragments.SearchFragment
import com.example.chatapp.Fragments.SettingsFragment
import com.google.android.material.tabs.TabLayout
import kotlin.collections.ArrayList
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.chatapp.ModelView.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.chatapp.AdapterClasses.ViewPagerAdapter


class MainActivity : AppCompatActivity() {

    var refUsers: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val databaseUrl = "https://chatapp-40111-default-rtdb.asia-southeast1.firebasedatabase.app"
        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance(databaseUrl).reference.child("Users")
            .child(firebaseUser!!.uid)


        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val viewPagerAdapter =
            ViewPagerAdapter(
                supportFragmentManager
            )

        viewPagerAdapter.addFragment(ChatFragment(), "Chats")
        viewPagerAdapter.addFragment(SearchFragment(), "Search")
        viewPagerAdapter.addFragment(SettingsFragment(), "Settings")


        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        //display username and profile
        refUsers!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val Users: Users? = p0.getValue(Users::class.java)

                    val user_name: TextView = findViewById(R.id.username_main)
                    val imageview_user_main: ImageView = findViewById(R.id.imageview_user_main)

                    user_name.text = Users!!.getusername()
                    Picasso.get().load(Users.getprofile()).placeholder(R.drawable.user)
                        .into(imageview_user_main)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {
                Toast.makeText(
                    applicationContext,
                    "Selected page position: $position", Toast.LENGTH_SHORT
                ).show()
            }

            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Code goes here
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Code goes here
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Welcome::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
            return true
        }
        return false
    }


    class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager, BEHAVIOR_SET_USER_VISIBLE_HINT) {

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int): CharSequence {
            return titles[i]
        }
    }



}