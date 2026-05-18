package game;

import bagel.Image;

public class Explosion extends GameObject {

  private int duration;
  private int timer;
  private boolean isActive;
  private boolean isLarge;

  public Explosion(double x, double y, Image image, int duration, boolean isLarge) {
    super(x, y, image);
    this.duration = duration;
    this.timer = 0;
    this.isActive = true;
    this.isLarge = isLarge;
  }

  public void update() {

    if (isActive) {
      timer++;
      if (timer <= duration) {
        image.draw(x, y);
      } else {
        isActive = false;
      }
    }
  }

  public boolean isActive() {
    return isActive;
  }

  public boolean isLarge() {
    return isLarge;
  }

}
