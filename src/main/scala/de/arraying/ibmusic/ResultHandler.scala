package de.arraying.ibmusic

import com.sedmelluq.discord.lavaplayer.player.{AudioLoadResultHandler, AudioPlayer}
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.{AudioPlaylist, AudioTrack}

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
final class ResultHandler(private val player: AudioPlayer) extends AudioLoadResultHandler {

  /**
    * When the track is loaded.
    * @param track The track.
    */
  override def trackLoaded(track: AudioTrack): Unit = play(track)

  /**
    * When the playlist is loaded.
    * @param playlist The playlist.
    */
  override def playlistLoaded(playlist: AudioPlaylist): Unit = play(playlist.getTracks.get(0))

  /**
    * When there are no matches.
    */
  override def noMatches(): Unit = throw new IllegalArgumentException("track not found")

  /**
    * When the load failed.
    * @param exception The exception.
    */
  override def loadFailed(exception: FriendlyException): Unit = throw new IllegalStateException(exception)

  /**
    * Plays the audio track.
    * @param audioTrack The audio track.
    */
  private def play(audioTrack: AudioTrack): Unit = player.playTrack(audioTrack)

}
