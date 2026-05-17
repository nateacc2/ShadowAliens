package game;

import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class Player extends GameObject {
  private boolean invincible = false;
  private Image invincibleImage;
  private int livesCount;
  private int movementSpeed;
  private double PLAYER_OFFSET;



  public Player(double x, double y, Image spaceship, Image shield,
                int livesCount, int movementSpeed) {
    super(x, y, spaceship);
    this.invincibleImage = shield;
    this.livesCount = livesCount;
    this.movementSpeed = movementSpeed;
    PLAYER_OFFSET = image.getWidth() / 2;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public void update(Input input) {

    // implementing player movements
    if (input.isDown(Keys.A)) {
      // check if spaceship is at left screen boundary
      if (x <= PLAYER_OFFSET){
        // if at boundary, stay there
        x = PLAYER_OFFSET;
      } else {
        x -= movementSpeed;
      }
    }

    if (input.isDown(Keys.D)) {
      if (x >= ShadowAliens.screenWidth - PLAYER_OFFSET) {
        x = ShadowAliens.screenWidth - PLAYER_OFFSET;
      } else {
        x += movementSpeed;
      }
    }

    image.draw(x,y);
  }

}
