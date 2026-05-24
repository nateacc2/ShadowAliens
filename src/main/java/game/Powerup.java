package game;

import bagel.Image;

import java.awt.*;

import static game.ShadowAliens.screenHeight;

public abstract class Powerup extends GameObject{
  private int arrivalTime;
  protected int movementSpeed;
  private boolean isActive;
  protected boolean isCollected;

  public Powerup(double x, double y, Image image, int arrivalTime) {
    super(x, y, image);
    this.arrivalTime = arrivalTime;
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
  }

  public void draw() {

    if (!isCollected) {
      image.draw(x,y);
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
