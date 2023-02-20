package ru.michanic.mymot.Kotlin.UI.Activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import ru.michanic.mymot.R

class VideoViewActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private var videoId = ""
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        val intent = intent
        videoId = intent.getStringExtra("videoId")
        val youTubeView = findViewById<View>(R.id.youtube_view) as YouTubePlayerView
        youTubeView.initialize(DEVELOPER_KEY, this)
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        error: YouTubeInitializationResult
    ) {
        Toast.makeText(this, "Ошибка! $error", Toast.LENGTH_LONG)
            .show()
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer, wasRestored: Boolean
    ) {
        player.loadVideo(videoId)
    }

    companion object {
        private const val DEVELOPER_KEY = "AIzaSyDgXIyncKP8yvta4U0pEhGikCUirliV_oU"
    }
}