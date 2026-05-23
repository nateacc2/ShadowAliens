package game;

import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class Player extends GameObject {

  private boolean invincible = false;
  private Image invincibleImage;
  private int livesCount;
  private int initialLives;
  private int movementSpeed;
  private final double PLAYER_OFFSET;
  private int projCooldown;
  private int shotTimer = 0;
  private int projSpeed;
  private Image projImage;
  private PlayerProjectile currentProjectile;

  public Player(double x, double y, Image spaceship, Image shield,
                int livesCount, int movementSpeed,
                int projCooldown, int projSpeed, Image projImage) {
    super(x, y, spaceship);
    this.invincibleImage = shield;
    this.livesCount = livesCount;
    this.initialLives = livesCount;
    this.movementSpeed = movementSpeed;
    this.projCooldown = projCooldown;
    this.projSpeed = projSpeed;
    this.projImage = projImage;
    this.PLAYER_OFFSET = image.getWidth() / 2;
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

    // track cooldown
    if (shotTimer > 0) {
      shotTimer--;
    }
  }

  public void draw() {
    image.draw(x, y);
    if (invincible) {
      invincibleImage.draw(x, y);
    }
  }

  public PlayerProjectile shoot() {

    // check if shoot cooldown is done
    if (shotTimer == 0) {
      shotTimer = projCooldown;
      return new PlayerProjectile(x, y, projImage, projSpeed);
    }
    return null;
  }

  public void addLife() {
    if (livesCount < initialLives) {
      livesCount++;
    }
  }

  public void setInvincible(boolean invincible) {
    this.invincible = invincible;
  }

  public void reduceCooldown() {
    projCooldown = Math.max(1, projCooldown / 3);
  }

  public void increaseSpeed() {
    movementSpeed *= 2;
  }

  public int getLivesCount() {
    return livesCount;
  }

  public void loseLife() {
    livesCount--;
  }

  public boolean isInvincible() {
    return invincible;
  }
}
