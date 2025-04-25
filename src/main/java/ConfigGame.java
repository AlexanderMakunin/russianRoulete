import java.io.File;

public class ConfigGame {
    public static final int balasCargador = 6;
    public static final int minPlayers = 2;
    public static final String gunAlive = """
                        .-.____________________.-.
                 ___ _.' .-----.    _____________|======+----------------+
                /_._/   (      |   /_____________|      |      YOU       |
                  /      `  _ ____/                     |   STIL ALIVE   |
                 |_      .\\( \\\\                         |________________|
                .'  `-._/__`_//
              .'       |""\""'
             /        /
            /        |
            |        '
            |         \\
            `-._____.-'
            """;
    public static final File audioEmptyShot = new File("src/main/java/audio/emptyShot.wav");
    public static final String gunDead = """
                      ^
                     | |
                   @#####@
                 (###   ###)-.
               .(###     ###) \\
              /  (###   ###)   )
             (=-  .@#####@|_--"
             /\\    \\_|l|_/ (\\
            (=-\\     |l|    /
             \\  \\.___|l|___/
             /\\      |_|   /
            (=-\\._________/\\
             \\             /
               \\._________/
                 #  ----  #
                 #   __   #
                 \\########/
            """;
    public static final File audioShot = new File("src/main/java/audio/pistoKillAudio.wav");
}
