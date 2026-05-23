package game;

import bagel.Image;

public class Explosion extends GameObject {

  private int duration;
  private int timer;
  private boolean isActive;

  public Explosion(double x, double y, Image image, int duration) {
    super(x, y, image);
    this.duration = duration;
    this.timer = 0;
    this.isActive = true;
  }

  public void update() {

    if (isActive) {
      timer++;
      if (timer >= duration) {
        isActive = false;
      }
    }
  }

  public void draw() {
    if (isActive) {
      image.draw(x, y);
      if (timer <= duration) {
        image.draw(x, y);
      }
    }
  }

  public boolean isActive() {
    return isActive;
  }

}
