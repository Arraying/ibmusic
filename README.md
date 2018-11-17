# ibmusic

A bot that will stream audio 24/7 in a specified channel, no commands.

## Docker (recommended)

1) Create a file called `ibmusic.env`.
2) Edit the `ibmusic.env` file to the following:
    ```
    token=YOUR_BOT_TOKEN_HERE
    channel=YOUR_CHANNEL_ID_HERE
    link=YOUR_STREAM_LINK_HERE
    ```
3) Run the docker image with the env file 
(`docker container run --name ibmusic --env-file ibmusic.env arraying/ibmusic`).

## No Docker (not recommended)

1) Compile using `sbt assembly` in the root directory. Alternatively contact me for a prebuilt jar.
2) Wait a few minutes because Scala is pretty slow.
3) Locate the jar (`target/scala_X.X.X/ibmusic.jar`).
4) Set the environment variables:
    - `token` to the bot token provided by Discord.
    - `channel` to the channel ID of the Discord channel.
    - `link` to the stream link.
5) Run the jar (`java -jar ibmusic.jar`).
6) Consider switching to Docker. Docker's great.