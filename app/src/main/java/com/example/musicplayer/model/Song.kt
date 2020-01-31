package com.example.musicplayer.model

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

class Song(
    var id: Long,

    var title: String,

    var albumId: Long,

    var albumName : String,

    var artistId : Long,

    var artistName : String,

    var year: Int,

    var duration: Long
) {

    var contentUri : Uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

    override fun toString() : String{
        return "$title-$artistName"
    }
}