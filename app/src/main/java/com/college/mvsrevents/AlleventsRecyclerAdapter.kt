package com.college.mvsrevents

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
class AlleventsRecyclerAdapter(val context: Context, val
itemlist:MutableList<EventsHelperClass>)
    :RecyclerView.Adapter<AlleventsRecyclerAdapter.AlleventsViewHolder>(){
    class AlleventsViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val textview_view:TextView= view.findViewById(R.id.txtview_item)
        val textview_date:TextView=view.findViewById(R.id.txtview_item_date)
        val
                textview_coord:TextView=view.findViewById(R.id.txtview_item_conductedby)
        val imageview:ImageView=view.findViewById(R.id.holder_imgview)
        val registerButton: Button =view.findViewById(R.id.btn_event_register)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AlleventsViewHolder {
        val
                view=LayoutInflater.from(parent.context).inflate(R.layout.viewholder_view,parent,false)
        return AlleventsViewHolder(view)
    }
    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: AlleventsViewHolder, position: Int) {
        val text=itemlist[position].eventname
        holder.textview_date.text=itemlist[position].eventdate
        holder.textview_coord.text="Student-Coordinator"+itemlist[position].eventcoordinator
        holder.textview_view.text=text

        Picasso.get().load(itemlist[position].posters).into(holder.imageview)
        holder.registerButton.setOnClickListener {
        }
    }
    /* private fun getImageFormStorage(ref:String,img:ImageView){
    //val storageReference = FirebaseStorage.getInstance().reference(ref)
    Picasso.with(context).load(ref).into(img)
    }*/
    override fun getItemCount(): Int {
        return itemlist.size
    }
}