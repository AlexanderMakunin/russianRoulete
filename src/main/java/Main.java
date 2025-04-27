import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Main {
    private static final ArrayList<Player> players = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    private static int deathCounter = 0;
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        askManyPlayers();
        boolean newGame;
        do {
            newGame = game();
            if (newGame) {
                System.out.println("el juego a sido reseteado");
                resetGame();
            }
        } while (newGame);
    }

    /**
     * Reset the game all player valor to 0
     */
    private static void resetGame() {
        for (Player player : players) {
            player.reset();
            deathCounter = 0;
        }
    }

    /**
     * Ask user how many players will play
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws IOException
     */
    private static void askManyPlayers() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Clip clip = audio(ConfigGame.startGameMusic);
        System.out.println("Cuantos jugadores? el minimo es: " + ConfigGame.minPlayers);
        int manyPlayer;
        do {
            manyPlayer = readNumber();
            if (manyPlayer < ConfigGame.minPlayers) {
                System.err.println("Tiene que ser mayor a " + ConfigGame.minPlayers);
            }
        } while (manyPlayer < 2);
        for (int i = 0; i < manyPlayer; i++) {
            String username = askName();
            Player player = new Player(username);
            players.add(player);
        }
        clip.stop();
    }

    /**
     * Ask user a number
     * @return return valid number
     */
    private static int readNumber() {
        int input = Integer.MIN_VALUE;
        boolean check = false;
        do {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input < 0) {
                    System.err.println("Tine que un numero positivo");
                } else {
                    check = true;
                }
            } catch (NumberFormatException e) {
                System.err.println("Intentelo otra vez");
            }
        } while (!check);
        return input;
    }

    /**
     * Ask user a name
     * @return return the valid name
     */
    private static String askName() {
        String input = "";
        do {
            try {
                System.out.println("Indique un nombre");
                input = scanner.nextLine();
            } catch (NumberFormatException e) {
                System.err.println("introduzca algo");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Show all players
     */
    private static void showPlayers() {
        int lines = 5;
        for (Player player : players) {
            System.out.println(player);
            for (int i = 0; i < lines; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    /**
     * Search the player and make him shoot
     * @param name the player
     * @param audio the audio to make it stop
     * @return if found the player
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws InterruptedException
     */
    private static boolean searchAndShoot(String name, Clip audio) throws LineUnavailableException, UnsupportedAudioFileException, IOException, InterruptedException {
        boolean foundName = false;
        audio.stop();
        Clip calmBreath = audio(ConfigGame.nervousBreathing);
        Thread.sleep(3*1000);
        calmBreath.stop();
        for (Player player : players) {
            if (player.getNombre().equals(name)) {
                if (player.isAlive()) {
                    boolean shoot = player.shoot();
                    if (shoot) {
                        System.out.println(ConfigGame.gunDead);
                        System.out.println("El jugador: " + player.getNombre() + " ha muerto");
                        audio(ConfigGame.audioShot);
                        deathCounter++;
                    } else {
                        System.out.println(ConfigGame.gunAlive);
                        audio(ConfigGame.audioEmptyShot);
                        Thread.sleep(1000);
                        audio(ConfigGame.calmBreathing);
                        System.out.println("El jugador: " + player.getNombre() + " sigue vivo");
                    }
                } else {
                    System.out.println("Ese jugador esta muerto");
                }
                foundName = true;
                break;
            }
        }
        if (!foundName) {
            System.out.println("no se ha encontrado: " + name);
        }
        clearScreen();
        audio.start();
        return foundName;
    }

    /**
     * The game
     * @return if the players want do it again
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws IOException
     * @throws InterruptedException
     */
    private static boolean game() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        Clip clip = audio(ConfigGame.inGameMusic);
        boolean win = false;
        boolean found;
        int round = 1;
        do {
            System.out.println("round: " + round);
            showPlayers();
            do {
                String name = askName();
                found = searchAndShoot(name, clip);
            } while (!found);
            if (deathCounter == players.size()-1) {
                win = true;
                for (Player player : players) {
                    if (player.isAlive()) {
                        clip.stop();
                        System.out.println("El jugador: " + player.getNombre() + " a ganado");
                        System.out.println(player);
                        break;
                    }
                }
            }
            round++;
        } while (!win);
        Clip endGameAudio = audio(ConfigGame.postGameMusic);
        System.out.println("Otra partida?");
        System.out.println("(1 para si, 2 para no)");
        int newGame = readNumber();
        endGameAudio.stop();
        return newGame == 1;
    }

    /**
     * Clean the screen doing 50 prints
     */
    public static void clearScreen() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final int jumps = 50;
        for (int i = 0; i < jumps; i++) {
            System.out.println();
        }
    }

    /**
     * Add audio to the game
     * @param auido the audio wants
     * @return the audio
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private static Clip audio(File auido) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(auido);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        return clip;
    }
}