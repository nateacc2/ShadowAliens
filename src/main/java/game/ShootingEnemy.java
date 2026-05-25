package game;
import bagel.Image;

public class ShootingEnemy extends Enemy {
  private int shotTimer;
  private int firingRate;
  private Image projectileImage;
  private int projSpeed;

  // track how many frames after enemy has spawned
  private int activeFrames = 0;

  public ShootingEnemy(double x, double y, Image image, double arrivalTime, double movementSpeed,
                       int firingRate, int shotTimer, int projSpeed, Image projectileImage) {
    super(x, y, image, arrivalTime, movementSpeed);
    this.shotTimer = firingRate;
    this.firingRate = firingRate;
    this.projectileImage = projectileImage;
    this.projSpeed = projSpeed;
  }

  @Override
  public void move() {
    y += movementSpeed;
  }

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
