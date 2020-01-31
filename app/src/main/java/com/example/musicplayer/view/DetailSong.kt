package com.example.musicplayer.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.musicplayer.R
import com.example.musicplayer.model.Song
import kotlinx.android.synthetic.main.detail_song.*

interface OnInFragClickListener{
    fun onBackInFragClick()
    fun onPlayInFragClick()
    fun onSkipNextInFragClick()
    fun onSkipPreviousInFragClick()
    fun onSeekBarClick(progress: Long)
}

@SuppressLint("SetTextI18n")
class DetailSong(private var mOnInFragClickListener: OnInFragClickListener, private var currentSong: Song) : Fragment(), OnReceiveSong{

    private lateinit var start: TextView
    private lateinit var end: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var songName: TextView
    private lateinit var artistName: TextView
    private lateinit var songImage: ImageView
    private lateinit var back:ImageView
    private lateinit var skipNext: ImageView
    private lateinit var play: ImageView
    private lateinit var skipPrev: ImageView


    private lateinit var currentSongThumbnail: Bitmap
    //private lateinit var currentSong: Song

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("onCreateView call.")

        val view = inflater.inflate(R.layout.detail_song, container, false)

        init(view)


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mOnInFragClickListener.onSeekBarClick(seekBar?.progress!!.toLong())
                val startMin: Int = (seekBar.progress/1000)/60
                val startSec: Int = (seekBar.progress/1000)%60

                start.text = "$startMin:$startSec"
            }
        })


        back.setOnClickListener{
            mOnInFragClickListener.onBackInFragClick()
        }

        play.setOnClickListener{
            mOnInFragClickListener.onPlayInFragClick()
        }

        skipNext.setOnClickListener{
            mOnInFragClickListener.onSkipNextInFragClick()
        }

        skipPrev.setOnClickListener{
            mOnInFragClickListener.onSkipPreviousInFragClick()
        }

        return view
    }

    private fun init(view: View) {
        start = view.findViewById(R.id.start_time)
        end = view.findViewById(R.id.end_time)
        seekBar = view.findViewById(R.id.music_seekBar)
        songName = view.findViewById(R.id.song_name_detail)
        artistName = view.findViewById(R.id.artist_name_detail)
        songImage = view.findViewById(R.id.song_img_detail)
        skipNext = view.findViewById(R.id.skip_next)
        skipPrev = view.findViewById(R.id.skip_previous)
        play = view.findViewById(R.id.play_pause)
        back = view.findViewById(R.id.back)
    }

    /* update new song */
    override fun receive(song: Song, bitmap: Bitmap?, process: Long) {
        this.currentSong = song

        if(bitmap != null) {
            this.currentSongThumbnail = bitmap
        }

        if(music_seekBar!= null)
        music_seekBar.progress = process.toInt()

//        song_name_detail.text = currentSong.title
//        artist_name_detail.text = currentSong.artistName
//        song_img_detail.setImageBitmap(currentSongThumbnail)
//
//
//        music_seekBar.max = currentSong.duration.toInt()
//
//        val endMin: Int = ((song.duration/1000)/60).toInt()
//        val endSec: Int = ((song.duration/1000)%60).toInt()
//
//        end.text = "$endMin:$endSec"

        println("receive call.")
    }
}

