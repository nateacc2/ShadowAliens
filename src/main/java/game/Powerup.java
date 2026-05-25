package game;

import bagel.Image;

import java.awt.*;

import static game.ShadowAliens.screenHeight;

/**
 * Abstract parent class for all Powerup game objects.
 * It provides shared logic for managing spawn timer, downward movement,
 * off-screen cleanup and rendering.
 *
 * @author Nathan S.
 */
public abstract class Powerup extends GameObject{
  private int arrivalTime;
  protected int movementSpeed;
  private boolean isActive;
  protected boolean isCollected;

  /**
   * Initializes powerup object and position coordinates, image and arrivaltime according
   * to gamedata file.
   *
   * @param x           The horizontal starting center coordinate.
   * @param y           The vertical starting center coordinate.
   * @param image       The visual graphic asset used to draw the item.
   * @param arrivalTime The game frame count when this item enters the screen.
   */
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

  /**
   * Gives the power-up's bonus stats to the player. Child classes implement their own logic
   * to define exactly what change they make (like adding a life or boosting speed).
   *
   * @param player The player ship receiving the powerup.
   */
  public abstract void activate(Player player);

  /**
   * Takes away the power-up's bonus stats when the timer runs out. This ensures
   * the player goes back to normal instead of keeping the upgrade forever.
   *
   * @param player The player ship losing the powerup.
   */
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
