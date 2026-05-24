package game;

import bagel.Image;
import static game.ShadowAliens.screenHeight;

public abstract class Powerup extends GameObject{
  private int arrivalTime;
  private int movementSpeed;
  private boolean isActive;
  protected boolean isCollected;

  public Powerup(int x, int y, Image image, int arrivalTime, int movementSpeed) {
    super(x, y, image);
    this.arrivalTime = arrivalTime;
    this.movementSpeed = movementSpeed;
    this.isActive = true;
    this.isCollected = false;
  }

  public void move() {
    y += movementSpeed;
  }

  public void update() {
    move();

    if (y > screenHeight) {
      isActive = false;
    }

    if (isActive) {
      image.draw(x, y);
    }
  }

  public abstract void activate(Player player);
  public abstract void deactivate(Player player);

  public boolean isActive() {
    return isActive;
  }

  public void collect() {
    isCollected = true;
  }

  public boolean isCollected() {
    return isCollected;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

}
