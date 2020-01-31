package com.example.musicplayer.presenter

import android.content.Context
import android.graphics.Bitmap
import com.example.musicplayer.model.OnUserClickModel
import com.example.musicplayer.model.Song
import com.example.musicplayer.view.BTNSTATE
import com.example.musicplayer.view.MainView

class UserClickPresenter(private var mainView: MainView?) : BasePresenter{
    override fun onDetach() {
        this.onUserClickModel = null
        this.mainView = null
    }

    /* instance of Model */
    private var onUserClickModel : OnUserClickModel ?= OnUserClickModel(this)


    /* contact with View */
    fun receivePlayCommand(context: Context, currentStateBtn : BTNSTATE, currentSong: Song, currentSongProcess: Long = 0){
        onUserClickModel?.handlePlay(context, currentStateBtn, currentSong, currentSongProcess)
    }

    fun tellViewPlayState(btnstate: BTNSTATE, bitmap: Bitmap? = null, process: Long){
        mainView?.setState(btnstate, bitmap, process)
    }
}