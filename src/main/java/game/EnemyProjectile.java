package game;

import bagel.Image;

public class EnemyProjectile extends Projectile {

  public EnemyProjectile(double x, double y, Image image, int speed) {
    super(x, y, image, speed);
  }

  @Override
  public void move() {
    // enemy projectile moves down screen
    y += speed;
  }
}
