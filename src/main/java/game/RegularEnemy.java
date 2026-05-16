package game;
import bagel.Image;

public class RegularEnemy extends Enemy {

  public RegularEnemy(double x, double y, Image image, double arrivalTime, double movementSpeed) {
    super(x, y, image, arrivalTime, movementSpeed);
  }

  @Override
  public void move() {
    y += movementSpeed;
  }

  @Override
  public void update() {
    move();
  }
}
