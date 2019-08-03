scalaVersion := "2.12.6"

lazy val ibMusic = (project in file("."))
  .settings(
    name := "ibmusic",
    version := "0.2.2",
    exportJars := true,
    resolvers += Resolver.JCenterRepository,
    libraryDependencies ++= Seq(
        "net.dv8tion" % "JDA" % "3.8.3_464",
        "com.sedmelluq" % "lavaplayer" % "1.3.19",
        "org.slf4j" % "slf4j-simple" % "1.7.25"
    ),
    mainClass in assembly := Some("de.arraying.ibmusic.Music"),
    assemblyJarName in assembly := "ibmusic.jar"
  )