package game;
import bagel.Image;

/**
 * Represents a type of enemy ship that can shoot projectiles.
 * It combines regular downward movement with a weapon that is able
 * to fire at the player at set intervals counted in frames.
 *
 * @author Nathan S.
 */
public class ShootingEnemy extends Enemy {
  private int shotTimer;
  private int firingRate;
  private Image projectileImage;
  private int projSpeed;

  // track how many frames after enemy has spawned
  private int activeFrames = 0;

  /**
   * Sets up a shooting enemy with its movement stats, projectile speed,
   * projectile image, and sets the initial weapon cooldown to match the firing rate.
   *
   * @param x               The starting horizontal point.
   * @param y               The starting vertical point.
   * @param image           The visual representation used to draw this enemy ship.
   * @param arrivalTime     The arrival time of when enemy should spawn countd in frames
   * @param movementSpeed   The number of pixels the ship moves down the screen per frame.
   * @param firingRate      The cooldown required between weapon shots.
   * @param shotTimer       The initial countdown value used for weapon reloading.
   * @param projSpeed       The downward speed of this enemy's projectiles.
   * @param projectileImage The visual representation of enemy projectiles
   */
  public ShootingEnemy(double x, double y, Image image, double arrivalTime, double movementSpeed,
                       int firingRate, int shotTimer, int projSpeed, Image projectileImage) {
    super(x, y, image, arrivalTime, movementSpeed);
    this.shotTimer = firingRate;
    this.firingRate = firingRate;
    this.projectileImage = projectileImage;
    this.projSpeed = projSpeed;
  }

  /**
   * Moves the enemy ship down the game screen
   */
  @Override
  public void move() {
    y += movementSpeed;
  }

  /**
   * Moves the enemy down and increments its weapon reload timer.
   * The reload timer stops counting if the enemy hasn't spawned yet,
   * which prevents it from shooting while hidden off-screen.
   */
  public void update() {

    activeFrames++;

    move();

    // don't increment shotTimer if enemy is not in screen
    if (activeFrames < arrivalTime) {
      return;
    }


    if (shotTimer > 0) {
      shotTimer--;
    }

  }

  /**
   * Spawns a new enemy projectile cooldown hits zero. It returns null
   * if the weapon is still cooling down or if the ship hasn't actually arrived
   * on screen yet, letting the main screen manager know that no shot was fired.
   *
   * @return A new EnemyProjectile instance if ready; null if reloading or off-screen.
   */
  public EnemyProjectile shoot() {

    // enemy not in screen
    if (activeFrames < arrivalTime) {
      // exits and tells Battlescreen "no projectile spawned"
      return null;
    }

    if (shotTimer == 0) {
      // Reset cooldown timer
      shotTimer = firingRate;
      return new EnemyProjectile(this.x, this.y, projectileImage, projSpeed);
    }
    // not ready to shoot
    return null;
  }
}
