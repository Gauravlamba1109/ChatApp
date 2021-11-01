package com.example.chatapp.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.AdapterClasses.UserAdapter

import com.example.chatapp.MainActivity
import com.example.chatapp.ModelView.Users
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SearchFragment : Fragment() {
    private var userAdapter: UserAdapter? = null
    private var mUsers: List<Users>? = null
    private var recyclerView: RecyclerView?=null
    private var searchEditText: EditText? = null

    var refUsers : DatabaseReference?=null
    var firebaseUser: FirebaseUser?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.searchlist)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        searchEditText =view.findViewById(R.id.searchUsersET)


        mUsers = ArrayList()
        retrieveAllUsers()

        searchEditText!!.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchForUsers(s.toString().toLowerCase())
            }
        })

        return view
    }

    private fun retrieveAllUsers() {
        var firebaseUserID= FirebaseAuth.getInstance().currentUser!!.uid

        val databaseUrl = "https://chatapp-40111-default-rtdb.asia-southeast1.firebasedatabase.app"
        val refUsers = FirebaseDatabase.getInstance(databaseUrl).reference.child("Users")

        refUsers.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()
                if (searchEditText!!.text.toString() == "") {
                    for (snapshot in p0.children) {
                        val user: Users? = snapshot.getValue(Users::class.java)
                        if ((user!!.getuid()) != firebaseUserID) {
                            (mUsers as ArrayList<Users>).add(user)
                        }
                    }
                    userAdapter = UserAdapter(context!!, mUsers!!, false)
                    recyclerView!!.adapter = userAdapter
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun searchForUsers(str: String){
        var firebaseUserID= FirebaseAuth.getInstance().currentUser!!.uid
        val databaseUrl = "https://chatapp-40111-default-rtdb.asia-southeast1.firebasedatabase.app"
        val queryUsers = FirebaseDatabase.getInstance(databaseUrl).reference
            .child("Users").orderByChild("search")
            .startAt(str)
            .endAt(str+"\uf8ff")

        queryUsers.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                (mUsers as ArrayList<Users>).clear()

                for (snapshot in p0.children){
                    val user : Users? = snapshot.getValue(Users::class.java)
                    if ((user!!.getuid()) != firebaseUserID){
                        (mUsers as ArrayList<Users>).add(user)
                    }
                }
                userAdapter = UserAdapter(context!!,mUsers!!,false)
                recyclerView!!.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        )


    }
}

