package de.arraying.ibmusic

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.{AudioTrack, AudioTrackEndReason}

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
final class IBEvents extends AudioEventAdapter {

  /**
    * When the track ends.
    * @param player The audio player.
    * @param track The audio track.
    * @param endReason The end reason.
    */
  override def onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason): Unit =
    if(endReason.mayStartNext) player.playTrack(track.makeClone) else throw new IllegalStateException(endReason.toString)

}
