package game;
import bagel.DrawOptions;
import bagel.Image;
/**
 * An abstract class that represents a general enemy spaceship.
 * Provides shared behavior for tracking life status, handling spawn timing,
 * checking screen boundaries, and rendering rotated images.
 *
 * @author Nathan S.
 */
public abstract class Enemy extends GameObject {
  protected double arrivalTime;
  protected double movementSpeed;
  protected double enemyOffset;
  private boolean isAlive;

  /**
   * Initializes the attributes of an enemy ship, including its position,
   * image asset, scheduled spawn time, and speed tracking.
   *
   * @param x             The starting horizontal coordinate.
   * @param y             The starting vertical coordinate.
   * @param image         The visual representation used to draw the enemy.
   * @param arrivalTime   The game frame count when this enemy should appear.
   * @param movementSpeed How fast an enemy should move per frame.
   */
  public Enemy(double x, double y, Image image, double arrivalTime, double movementSpeed) {
    super(x, y, image);
    this.arrivalTime = arrivalTime;
    this.movementSpeed = movementSpeed;
    this.isAlive = true;
    this.enemyOffset = image.getWidth() / 2;

  }
  /**
   * Calculates the next movement step for enemy ships
   * Must be implemented by specific child classes to define their unique movement.
   */
  public abstract void move();

  /**
   * Updates the movement patterns, and behaviors of the enemy.
   * Must be implemented by specific child classes to run every frame.
   */
  public abstract void update();

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    this.isAlive = alive;
  }

  /**
   * Draws the enemy ship image onto the game screen rotated 90 degrees
   * so it faces downward.
   */
  public void draw() {
    DrawOptions rotate = new DrawOptions();
    rotate.setRotation(Math.toRadians(90));
    image.draw(x, y, rotate);
  }

  /**
   * Checks if the enemy ship has completely traveled past the intialised gamescreen
   *
   * @param screenHeight The current vertical boundary height of the game window.
   * @return true if the enemy's vertical position is past the screen height; false otherwise.
   */
  public boolean isOutOfScreen(double screenHeight) {
    return y > screenHeight;
  }

  public double getArrivalTime() {
    return arrivalTime;
  }

}
