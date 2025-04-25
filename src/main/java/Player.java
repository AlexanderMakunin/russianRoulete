import java.util.Objects;
import java.util.Random;


public class Player {
    private final String nombre;
    private final boolean[] chamber = new boolean[ConfigGame.balasCargador];
    private int loadedBullet;
    private boolean alive = true;
    private final Random random = new Random();
    private int count = 0;

    public Player(String nombre) {
        this.nombre = nombre;
        loadedBullet = random.nextInt(chamber.length);
        chamber[loadedBullet] = true;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isAlive() {
        return alive;
    }

    /**
     * Reset values to 0
     */
    public void reset() {
        alive = true;
        count = 0;
        chargeTheChamber();
    }

    /**
     * Charge the chamber
     */
    public void chargeTheChamber() {
        loadedBullet = random.nextInt(chamber.length);
        for (int i = 0; i < chamber.length; i++) {
            chamber[i] = i == loadedBullet;
        }
    }

    /**
     * Check if the bullet is charged
     * @return if killed or not
     */
    public boolean shoot() {
        if (chamber[count]) {
            alive = false;
            count++;
            return true;
        } else {
            count++;
            return false;
        }
    }

    /**
     * Text to check if the user is alive or not
     * @return the status
     */
    private String status() {
        return alive ? "Alive" : "Dead";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(nombre, player.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    @Override
    public String toString() {
        return "Player{" +
                "nombre = " + nombre + '\'' +
                ", " + status() + '\'' +
                ", contador de disparos = " + count +
                '}';
    }
}
