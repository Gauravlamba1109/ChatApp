package com.example.chatapp.AdapterClasses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.ModelView.Users
import com.example.chatapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter (
    mContext: Context,
    mUsers:List<Users>,
    isChatCheck:Boolean
)   : RecyclerView.Adapter<UserAdapter.ViewHolder?>()
{
    private val mContext:Context
    private val mUsers:List<Users>
    private var isChatCheck:Boolean

    init{
        this.mUsers=mUsers
        this.isChatCheck=isChatCheck
        this.mContext=mContext
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var userNameTxt:TextView
        var profileImageView : CircleImageView
        var onlineImageView: CircleImageView
        var oflineImageView: CircleImageView
        var lastMessageTxt: TextView

        init {
            userNameTxt=itemView.findViewById(R.id.username_search)
            profileImageView=itemView.findViewById(R.id.profile_image)
            onlineImageView=itemView.findViewById(R.id.user_online)
            oflineImageView=itemView.findViewById(R.id.user_offline)
            lastMessageTxt=itemView.findViewById(R.id.message_last)

        }
    }

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_search_item_layout,viewGroup,false)
        return UserAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val Users: Users =mUsers[i]
        holder.userNameTxt.text = Users!!.getusername()
        Picasso.get().load(Users.getprofile()).placeholder(R.drawable.user).into(holder.profileImageView)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }
}