package com.example.vsomaku.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.vsomaku.R
import com.example.vsomaku.controllers.PhotoController
import com.example.vsomaku.data.Album

class AlbumsAdapter(private val context : Context, private val albums : List<Album>, private val router : Router) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    var onItemClick: ((Album) -> Unit)? = null

    inner class AlbumViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var textView: TextView? = null
        init {
            textView = itemView.findViewById(R.id.tv_title_album)
            itemView.setOnClickListener {
                onItemClick?.invoke(albums[adapterPosition])
                val sp = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
                sp.edit().putInt("albumId", albums[adapterPosition].id).apply()
                val controller = PhotoController()
                router.pushController(RouterTransaction.with(controller))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.rcv_item_album, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.textView?.text = albums[position].id.toString()
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}