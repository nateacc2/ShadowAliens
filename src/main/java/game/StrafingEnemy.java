package game;
import bagel.Image;

public class StrafingEnemy extends Enemy {

  private boolean movingLeft;
  private double screenWidth;

  public StrafingEnemy(double x, double y, Image image, double arrivalTime, double movementSpeed,
                       double screenWidth) {
    super(x, y, image, arrivalTime, movementSpeed);
    // evaluates to true if enemy spawn on the left side
    movingLeft = x < screenWidth / 2;
  }

  @Override
  public void move() {
    y += movementSpeed;

    if (movingLeft) {
      x -= movementSpeed;
      if (x <= enemyOffset) {
        x = enemyOffset;
        movingLeft = false;
      }
    } else {
      x += movementSpeed;
      if (x >= screenWidth - enemyOffset) {
        x = screenWidth - enemyOffset;
        movingLeft = true;
      }
    }



  }

  @Override
  public void update() {
    move();
  }
}