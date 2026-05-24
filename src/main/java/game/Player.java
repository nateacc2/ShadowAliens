package game;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

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

  // New fields for hit-recovery state
  private boolean isHitInvincible = false;
  private int hitInvincibleTimer = 0;
  private final int maxHitInvincibleFrames;

  public Player(double x, double y, Image spaceship, Image shield,
                int livesCount, int movementSpeed,
                int projCooldown, int projSpeed, Image projImage, Properties gameProps) {
    super(x, y, spaceship);
    this.invincibleImage = shield;
    this.livesCount = livesCount;
    this.initialLives = livesCount;
    this.movementSpeed = movementSpeed;
    this.projCooldown = projCooldown;
    this.projSpeed = projSpeed;
    this.projImage = projImage;
    this.PLAYER_OFFSET = image.getWidth() / 2;
    this.maxHitInvincibleFrames = Integer.parseInt(gameProps.getProperty("player.hitInvincibilityTime"));
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

    // track hit invincibility duration frame countdown
    if (isHitInvincible) {
      hitInvincibleTimer++;
      if (hitInvincibleTimer >= maxHitInvincibleFrames) {
        isHitInvincible = false;
      }
    }
  }

  public void draw(boolean devInvincible) {
    image.draw(x, y);

    // Displays shield matrix texture if dev-mode, hit-recovery, or power-up invincibility is active
    if (devInvincible || isHitInvincible || invincible) {
      invincibleImage.draw(x, y);
    }
  }

  public void triggerHitInvincibility() {
    isHitInvincible = true;
    hitInvincibleTimer = 0;
    loseLife();
  }

  public PlayerProjectile shoot() {

    // check if shoot cooldown is done
    if (shotTimer == 0) {
      shotTimer = projCooldown;
      return new PlayerProjectile(x, y, projImage, projSpeed);
    }
    return null;
  }

  public int getProjCooldown() {
    return projCooldown;
  }

  public void setProjCooldown(int projCooldown) {
    this.projCooldown = projCooldown;
  }

  public int getMovementSpeed() {
    return movementSpeed;
  }

  public void setMovementSpeed(int movementSpeed) {
    this.movementSpeed = movementSpeed;
  }

  public int getLivesCount() {
    return livesCount;
  }

  public void setLivesCount(int livesCount) {
    this.livesCount = livesCount;
  }

  public void setInvincible(boolean invincible) {
    this.invincible = invincible;
  }

  public void loseLife() {
    livesCount--;
  }

  public boolean isInvincible() {
    return invincible;
  }

  public boolean isHitInvincible() {
    return isHitInvincible;
  }
}