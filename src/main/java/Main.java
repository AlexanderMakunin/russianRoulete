import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Player> players = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    private static int deathCounter = 0;
    public static void main(String[] args) {
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
        }
    }

    /**
     * Ask user how many players will play
     */
    private static void askManyPlayers() {
        System.out.println("Cuantos jugadores? el minimo es: " + ConfigGame.minPlayers);
        int manyPlayer;
        do {
            manyPlayer = readNumber();
            if (manyPlayer < ConfigGame.minPlayers) {
                System.out.println("Tiene que ser mayor a " + ConfigGame.minPlayers);
            }
        } while (manyPlayer < 2);
        for (int i = 0; i < manyPlayer; i++) {
            String username = askName();
            Player player = new Player(username);
            players.add(player);
        }
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
                    System.out.println("Tine que un numero positivo");
                } else {
                    check = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Intentelo otra vez");
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
                System.out.println("introduzca algo");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Show all players
     */
    private static void showPlayers() {
        for (Player player : players) {
            System.out.println(player);
        }
    }

    /**
     * Search a player and make the player shoot
     * @param name the player name
     * @return if found the player
     */
    private static boolean searchAndShoot(String name) {
        boolean foundName = false;
        for (Player player : players) {
            if (player.getNombre().equals(name)) {
                if (player.isAlive()) {
                    boolean shoot = player.shoot();
                    if (shoot) {
                        System.out.println(ConfigGame.gunDead);
                        System.out.println("El jugador: " + player + " ha muerto");
                        deathCounter++;
                    } else {
                        System.out.println(ConfigGame.gunAlive);
                        System.out.println("El jugador: " + player + " sigue vivo");
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
        return foundName;
    }

    /**
     * The game
     * @return if the player wants play again or not
     */
    private static boolean game() {
        boolean win = false;
        boolean found;
        int round = 1;
        do {
            System.out.println("round: " + round);
            showPlayers();
            do {
                String name = askName();
                found = searchAndShoot(name);
            } while (!found);
            if (deathCounter == players.size()-1) {
                win = true;
                for (Player player : players) {
                    if (player.isAlive()) {
                        System.out.println("El jugador: " + player + " a ganado");
                    }
                    break;
                }
            }
            round++;
        } while (!win);
        System.out.println("Otra partida?");
        System.out.println("(1 para si, 2 para no)");
        int newGame = readNumber();
        return newGame == 1;
    }
}