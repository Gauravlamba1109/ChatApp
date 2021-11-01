package com.example.chatapp.ModelView

class Users(
    private var uid: String ="",
    private var username: String="",
    private var profile: String="",
    private var cover: String="",
    private var status: String="",
    private var search: String="",
    private var instagram: String="",
    private var facebook: String="",
    private var website: String=""
) {

    fun getuid():String?{
        return uid
    }
    fun getusername():String?{
        return username
    }
    fun getprofile():String?{
        return profile
    }
    fun getcover():String?{
        return cover
    }
    fun getstatus():String?{
        return status
    }
    fun getsearch():String?{
        return search
    }
    fun getfacebook():String?{
        return facebook
    }
    fun getinstagram():String?{
        return instagram
    }
    fun getwebsite():String?{
        return website
    }

}