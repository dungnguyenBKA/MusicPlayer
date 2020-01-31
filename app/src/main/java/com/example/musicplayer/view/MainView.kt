package com.example.musicplayer.view

import android.graphics.Bitmap
import com.example.musicplayer.model.Song

interface MainView {

    fun setState(btnstate: BTNSTATE, bitmap: Bitmap?, process: Long)

    fun onRemoteClickListener()

    fun updateListSong(songList: ArrayList<Song>)
}