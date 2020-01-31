package com.example.musicplayer.presenter

import android.content.Context
import com.example.musicplayer.model.MusicRepository
import com.example.musicplayer.model.Song
import com.example.musicplayer.view.MainView

class MusicPresenter(private var view: MainView?) : BasePresenter {
    private var musicRepository : MusicRepository = MusicRepository(this)

    override fun onDetach() {
        view = null
    }

    fun updateListInMainActivity(songList : ArrayList<Song>){
        view?.updateListSong(songList)
    }

    // request to model
    fun receiveRequest(context: Context){
        musicRepository.findAllMusic(context)
    }
}