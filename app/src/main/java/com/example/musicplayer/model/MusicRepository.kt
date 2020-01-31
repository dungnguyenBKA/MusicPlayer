package com.example.musicplayer.model

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
import com.example.musicplayer.presenter.MusicPresenter

class MusicRepository(private var musicPresenter: MusicPresenter) {

    @SuppressLint("InlinedApi")
    fun findAllMusic(context: Context){
        val allSong = ArrayList<Song>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.DURATION
        )

        val cursor = context.contentResolver.query(uri, projection,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER)

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    allSong.add(Song(
                        cursor.getLong(0),  // _id
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getString(3),
                        cursor.getLong(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getLong(7)
                        )
                    )
                }while (cursor.moveToNext())
            }
        }

        cursor?.close()
        println(allSong)

        musicPresenter.updateListInMainActivity(allSong)
    }
}