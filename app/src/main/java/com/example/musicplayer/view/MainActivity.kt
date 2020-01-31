package com.example.musicplayer.view

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musicplayer.R
import com.example.musicplayer.adapterSongList.Adapter
import com.example.musicplayer.model.Song
import com.example.musicplayer.presenter.MusicPresenter
import com.example.musicplayer.presenter.UserClickPresenter
import kotlinx.android.synthetic.main.activity_main.*

// state of btn : play or pause
enum class BTNSTATE{
    PLAY, PAUSE
}

interface OnReceiveSong{
    fun receive(song: Song, bitmap: Bitmap?, process: Long)
}

class MainActivity : AppCompatActivity() , MainView, Adapter.OnSongClickListener,
    OnInFragClickListener {
    /* variable */

    /* state of the song */
    private var currentSongIndex : Int = 0
    private var currentSongThumbnail : Bitmap? = null

    private var currentBtnState = BTNSTATE.PLAY
    private var currentSongProcess: Long = 0

    /* repo */
    private lateinit var frag: DetailSong
    private var songList: ArrayList<Song> = ArrayList()
    private lateinit var adapter : Adapter

    /* presenter */
    private val userClickPresenter : UserClickPresenter = UserClickPresenter(this)
    private val updateSongListPresenter : MusicPresenter = MusicPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* request permission for read data */
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 123)
            }
        }else{
            createPlayer()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            createPlayer()
        }
        else{
            Toast.makeText(this, "Can't not play any music, shutting down", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


    private fun createPlayer(){
        updateSongListPresenter.receiveRequest(this)

        // running text
        song_name.isSelected = true
        artist_name.isSelected = true


        play_pause.setOnClickListener{
            userClickPresenter.receivePlayCommand(this, currentBtnState, songList[currentSongIndex], currentSongProcess)
        }

        skip_next.setOnClickListener{
            currentSongIndex = (currentSongIndex+1) % songList.size
            onRemoteClickListener()
        }

        skip_previous.setOnClickListener{
            currentSongIndex = (currentSongIndex + songList.size - 1) % songList.size
            onRemoteClickListener()
        }


        play_area.setOnClickListener{
            frag = DetailSong(this, songList[currentSongIndex])

            supportFragmentManager.beginTransaction()
                .replace(root.id, frag)
                .addToBackStack(null)
                .commit()

            frag.receive(songList[currentSongIndex], currentSongThumbnail, currentSongProcess)
        }
    }

    /* override fun down here */

    /* method to contact with detailSong fragment */
    override fun onPlayInFragClick() {
        userClickPresenter.receivePlayCommand(this, currentBtnState, songList[currentSongIndex])
    }

    override fun onSkipNextInFragClick() {
        currentSongIndex = (currentSongIndex+1) % songList.size
        onRemoteClickListener()
        frag.receive(songList[currentSongIndex], currentSongThumbnail, currentSongProcess)

    }

    override fun onSkipPreviousInFragClick() {
        currentSongIndex = (currentSongIndex + songList.size - 1) % songList.size
        onRemoteClickListener()
        frag.receive(songList[currentSongIndex], currentSongThumbnail, currentSongProcess)
    }

    override fun onBackInFragClick() {
        supportFragmentManager.popBackStack()
    }

    override fun onSeekBarClick(progress: Long) {
        userClickPresenter.receivePlayCommand(this, currentBtnState, songList[currentSongIndex], progress)
    }


    /* method to contact with presenter */

    override fun onRemoteClickListener() {
        userClickPresenter.receivePlayCommand(this, currentBtnState, songList[currentSongIndex])

    }

    override fun onSongClickListener(position: Int) {
        this.currentSongIndex = position
        userClickPresenter.receivePlayCommand(this, currentBtnState, songList[currentSongIndex])
    }

    override fun updateListSong(songList: ArrayList<Song>) {
        this.songList = songList

        adapter = Adapter(this)
        adapter.submitList(songList)
        list_music.adapter = adapter
    }

    override fun setState(btnstate: BTNSTATE, bitmap: Bitmap?, process: Long) {
        this.currentBtnState = btnstate
        this.currentSongProcess = process

        if(bitmap != null){
            this.currentSongThumbnail = bitmap
        }

        if(btnstate == BTNSTATE.PLAY){
            play_pause.setImageResource(R.drawable.ic_play)
        }else{
            play_pause.setImageResource(R.drawable.ic_pause)
        }

        song_name.text = songList[currentSongIndex].title
        artist_name.text = songList[currentSongIndex].artistName
        song_img.setImageBitmap(currentSongThumbnail)
    }
}