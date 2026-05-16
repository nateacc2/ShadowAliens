package game;

import bagel.Image;

public class PlayerProjectile extends Projectile {

  public PlayerProjectile(double x, double y, Image image, int speed) {
    super(x, y, image, speed);
  }

  @Override
  public void move() {
    // player projectile moves up screen
    y -= speed;
  }
}
