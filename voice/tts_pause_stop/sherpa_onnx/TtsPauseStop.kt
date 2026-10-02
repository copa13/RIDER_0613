package com.rider.tts_pause_stop.sherpa_onnx

import android.media.MediaPlayer
import java.io.File

class TtsPauseStop {

    private var player: MediaPlayer? = null

    fun play(audioFile: File) {
        stop()

        player = MediaPlayer().apply {
            setDataSource(audioFile.absolutePath)
            prepare()
            start()
        }
    }

    fun pause() {
        player?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    fun resume() {
        player?.start()
    }

    fun stop() {
        player?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }

        player = null
    }

    fun isPlaying(): Boolean {
        return player?.isPlaying == true
    }
}
