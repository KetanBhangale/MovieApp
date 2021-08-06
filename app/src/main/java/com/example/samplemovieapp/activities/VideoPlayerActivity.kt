package com.example.samplemovieapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.samplemovieapp.databinding.ActivityVideoPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.android.synthetic.main.activity_video_player.view.*


class VideoPlayerActivity : AppCompatActivity() {
    private var _binding:ActivityVideoPlayerBinding?=null
    val binding:ActivityVideoPlayerBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(youtube_player_view)
        val videoKey = intent.getStringExtra("key")
        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoKey!!, 0f)
            }
        })
        _binding!!.closeVideo.setOnClickListener {
            finish()
        }

    }
}