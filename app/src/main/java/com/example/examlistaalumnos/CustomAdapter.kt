package com.example.examlistaalumnos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class CustomAdapter(private val context: Context, private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private var clickListener: ClickListener? = null

    interface ClickListener {
        fun onItemClick(view:View,position: Int)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        Glide.with(context).load(ItemsViewModel.image).into(holder.imageViews)

        // sets the text to the textview from our itemHolder class
        holder.textViews.text = ItemsViewModel.text
        holder.textView2s.text = ItemsViewModel.text1
        holder.textView3s.text = ItemsViewModel.text2

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun setOnItemClickListener(clickListener: ClickListener){
        this.clickListener = clickListener
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val textViews: TextView = itemView.findViewById(R.id.textView)
        val textView2s: TextView = itemView.findViewById(R.id.textView2)
        val textView3s: TextView = itemView.findViewById(R.id.textView3)
        val imageViews: ImageView = itemView.findViewById(R.id.imageview)

        init {
            if (clickListener != null){
                itemView.setOnClickListener(this)
            }
        }

        override fun onClick(itView: View){
            if (itView != null){
                clickListener?.onItemClick(itView,bindingAdapterPosition)
            }
        }

    }
}