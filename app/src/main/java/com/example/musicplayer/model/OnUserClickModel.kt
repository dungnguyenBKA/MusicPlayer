package com.example.musicplayer.model

import android.content.Context
import com.example.musicplayer.presenter.UserClickPresenter
import com.example.musicplayer.view.BTNSTATE
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.util.Size


class OnUserClickModel(private var userClickPresenter: UserClickPresenter) {

    private var mediaPlayer: MediaPlayer? = null

    fun handlePlay(context: Context, stateBtn : BTNSTATE, song: Song, currentSongProcess: Long = 0){

        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(context, song.contentUri)
        }

        if(stateBtn == BTNSTATE.PLAY){
            mediaPlayer?.apply {
                seekTo(currentSongProcess.toInt())
                start()

                setOnCompletionListener {
                    onDestroy()
                }
            }

            userClickPresenter.tellViewPlayState(BTNSTATE.PAUSE,null, mediaPlayer?.currentPosition!!.toLong())
        }else{
            mediaPlayer?.pause()
            userClickPresenter.tellViewPlayState(BTNSTATE.PLAY, null, mediaPlayer?.currentPosition!!.toLong())
        }
        //val thumbnail: Bitmap = context.contentResolver.loadThumbnail(song.contentUri, Size(480,480), null)
    }

    private fun onDestroy(){
        mediaPlayer?.release()
        mediaPlayer = null
    }
}