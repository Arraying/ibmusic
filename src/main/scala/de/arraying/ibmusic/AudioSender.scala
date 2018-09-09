package de.arraying.ibmusic

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame
import net.dv8tion.jda.core.audio.AudioSendHandler

/**
  * Copyright 2018 Arraying
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
final class AudioSender(private val audioPlayer: AudioPlayer) extends AudioSendHandler {

  private var lastFrame: AudioFrame = _

  /**
    * Whether or not the player can provide audio.
    * @return True if it can, false otherwise.
    */
  override def canProvide: Boolean = {
    if(lastFrame == null) lastFrame = audioPlayer.provide()
    lastFrame != null
  }

  /**
    * Provides the API with 20 milliseconds worth of audio.
    * @return The audio.
    */
  override def provide20MsAudio(): Array[Byte] = {
    if(lastFrame == null) lastFrame = audioPlayer.provide()
    val data: Array[Byte] = if(lastFrame == null) null else lastFrame.getData
    lastFrame = null
    data
  }

  /**
    * Whether or not the encoding is Opus.
    * @return Always true, #OpusMustardrace.
    */
  override def isOpus: Boolean = true

}
