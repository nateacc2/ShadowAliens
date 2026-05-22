package game;
import bagel.DrawOptions;
import bagel.Image;

public abstract class Enemy extends GameObject {
  protected double arrivalTime;
  protected double movementSpeed;
  protected double enemyOffset;
  private boolean isAlive;

  public Enemy(double x, double y, Image image, double arrivalTime, double movementSpeed) {
    super(x, y, image);
    this.arrivalTime = arrivalTime;
    this.movementSpeed = movementSpeed;
    this.isAlive = true;
    this.enemyOffset = image.getWidth() / 2;

  }

  public abstract void move();
  public abstract void update();

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    this.isAlive = alive;
  }

  public void draw() {
    DrawOptions rotate = new DrawOptions();
    rotate.setRotation(Math.toRadians(90));
    image.draw(x, y, rotate);
  }


  public double getArrivalTime() {
    return arrivalTime;
  }

}
