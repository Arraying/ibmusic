package de.arraying.ibmusic

import com.sedmelluq.discord.lavaplayer.player.{AudioPlayerManager, DefaultAudioPlayerManager}
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.core.entities.VoiceChannel
import net.dv8tion.jda.core.JDABuilder

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
object Music extends App {

  if(args.length < 3) throw new IllegalArgumentException("provide token, channel, link in said order as runtime parameters")
  Try {
    val bot = new JDABuilder(args(0))
      .build()
      .awaitReady()
    bot.getVoiceChannelById(args(1)) match {
      case channel: VoiceChannel =>
        val playerManager: AudioPlayerManager = new DefaultAudioPlayerManager
        AudioSourceManagers.registerRemoteSources(playerManager)
        val player = playerManager.createPlayer()
        player.addListener(new AudioAdapter)
        val manager = channel.getGuild.getAudioManager
        manager.setSendingHandler(new AudioSender(player))
        manager.openAudioConnection(channel)
        playerManager.loadItemOrdered(player, args(2), new ResultHandler(player))
      case null => throw new IllegalArgumentException("channel invalid")
    }
  } match {
    case Failure(error) => error.printStackTrace()
    case _ =>
  }

}
