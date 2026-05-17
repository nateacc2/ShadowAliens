package game;
import bagel.Image;

public class ShootingEnemy extends RegularEnemy {
  private int shotTimer;
  private int firingRate;
  private Image projectileImage;
  private int projSpeed;

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
    super.move();

    if (shotTimer > 0) {
      shotTimer--;
    }
  }

  public EnemyProjectile shoot() {
    if (shotTimer == 0) {
      // Reset cooldown timer
      shotTimer = firingRate;
      return new EnemyProjectile(this.x, this.y, projectileImage, projSpeed);
    }
    // not ready to shoot
    return null;
  }

}
