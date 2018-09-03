package de.arraying.ibmusic

import com.sedmelluq.discord.lavaplayer.player.{AudioLoadResultHandler, AudioPlayerManager, DefaultAudioPlayerManager}
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.{AudioPlaylist, AudioTrack}
import net.dv8tion.jda.core.{AccountType, JDABuilder}

import scala.util.{Failure, Try}

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
object IBMusic {

  private val playerManager: AudioPlayerManager = new DefaultAudioPlayerManager

  /**
    * Main point and entry of the program.
    * @param args The arguments.
    *             argument 0 -> Bot token.
    *             argument 1 -> Channel ID.
    *             argument 2 -> Link.
    */
  def main(args: Array[String]): Unit = {
    if(args.length < 3) throw new IllegalArgumentException("please provide the token, channel ID and video link (in this order)")
    Try {
      val bot = new JDABuilder(AccountType.BOT)
        .setToken(args(0))
        .build()
        .awaitReady()
      val channel = bot.getVoiceChannelById(args(1))
      if(channel == null) throw new IllegalArgumentException("channel provided does not exist or is not a VC")
      val player = playerManager.createPlayer()
      player.addListener(new IBEvents)
      val manager = channel.getGuild.getAudioManager
      manager.openAudioConnection(channel)
      manager.setSendingHandler(new IBConverter(player))
      AudioSourceManagers.registerRemoteSources(playerManager)
      playerManager.loadItemOrdered(player, args(2), new AudioLoadResultHandler {

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
        override def noMatches(): Unit = throw new IllegalArgumentException("Track not found")

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

      })
    } match {
      case Failure(error) => error.printStackTrace()
      case _ =>
    }
  }

}
