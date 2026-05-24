package game;

import bagel.Image;
import static game.ShadowAliens.screenHeight;

public abstract class Projectile extends GameObject {
  protected int speed;
  private boolean projExist;

  public Projectile(double x, double y, Image image, int speed) {
    super(x, y, image);
    this.speed = speed;
    projExist = true;
  }

  @Override
  public void update() {
    move();

    // check if off screen
    if (y < 0 || y > screenHeight) {
      projExist = false;
    }
  }

  public void draw() {
    if (projExist) {
      image.draw(x,y);
    }
  }
  
  public boolean isProjExist() {
    return projExist;
  }

  public void setProjExist(boolean projExist) {
    this.projExist = projExist;
  }

  public int getSpeed() {
    return speed;
  }
}
