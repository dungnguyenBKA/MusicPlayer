package com.example.musicplayer.adapterSongList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.model.Song
import kotlinx.android.synthetic.main.single_song_layout.view.*

class Adapter(var mOnSongClickListener : OnSongClickListener ) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var songList : ArrayList<Song> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.single_song_layout ,parent, false)
        return ViewHolder(itemView, mOnSongClickListener)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val title = songList.get(position).title
        val artistName = songList.get(position).artistName

        if(title.isNotEmpty()){
            holder.title.text = title
        }

        if(artistName.isNotEmpty()){
            holder.artistName.text = artistName
        }

        holder.itemView.setOnClickListener(){
            holder.mOnSongClickListener.onSongClickListener(position)
        }
    }

    class ViewHolder(itemView: View,
                     var mOnSongClickListener : OnSongClickListener) : RecyclerView.ViewHolder(itemView) {
        var title : TextView = itemView.title
        var artistName : TextView = itemView.artistName
    }

    fun submitList(songList : ArrayList<Song>){
        this.songList = songList
    }

    interface OnSongClickListener{
        fun onSongClickListener(position: Int)
    }
}