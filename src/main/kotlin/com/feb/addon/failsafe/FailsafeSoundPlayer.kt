package com.feb.addon.failsafe

import com.feb.addon.config.FailsafeConfig
import javax.sound.sampled.AudioSystem

object FailsafeSoundPlayer {

    fun play(name: String) {
        val file = FailsafeConfig.getSoundFile(name) ?: return

        Thread {
            try {
                val clip = AudioSystem.getClip()
                val audio = AudioSystem.getAudioInputStream(file)

                clip.open(audio)
                clip.start()

                clip.addLineListener {
                    if (!clip.isRunning) {
                        clip.close()
                        audio.close()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}