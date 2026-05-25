package game;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/**
 * Represents the player's ship character.
 * Manages keyboard movement, boundary checks, weapon cooldown intervals,
 * health status tracking, and temporary invincibility statuses.
 *
 * @author Nathan S.
 */
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

  // Fields for hit-recovery state
  private boolean isHitInvincible = false;
  private int hitInvincibleTimer = 0;
  private final int maxHitInvincibleFrames;

  /**
   * Constructs the player's spaceship instance. Initializes its movement properties,
   * initial weapon speeds, reload timing, and sets up the shield recovery duration
   * used when the player takes damage.
   *
   * @param x             The starting horizontal coordinate on the screen.
   * @param y             The starting vertical coordinate on the screen.
   * @param spaceship     The visual representation for the player's spaceship.
   * @param shield        The visual representation displayed during invincibility.
   * @param livesCount    The starting number of lives a player has.
   * @param movementSpeed The speed of how fast a player moves across the screen.
   * @param projCooldown  The frame delay required between player shots.
   * @param projSpeed     The upward speed of player projectiles.
   * @param projImage     The representation of player's projectiles.
   * @param gameProps    The properties file used to load player's specs.
   */
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

  /**
   * Processes player action inputs, It updates coordinates based on directional keys,
   * limits positions to keep the ship completely inside the
   * game screen, and tracks active cooldowns and damage-recovery timers.
   *
   * @param input The current keyboard/mouse input.
   */
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

  /**
   * Renders the player's spaceship image. It conditionally renders a
   * shield on top if developer mode, a power-up effect, or a post-hit recovery is activated
   *
   * @param devInvincible Flag confirming if developer mode invincibility is active.
   */
  public void draw(boolean devInvincible) {
    image.draw(x, y);

    // Displays shield matrix texture if dev-mode, hit-recovery, or power-up invincibility is active
    if (devInvincible || isHitInvincible || invincible) {
      invincibleImage.draw(x, y);
    }
  }

  /**
   * Activates a temporary safety period and deducts a health point.
   * This prevents multiple overlapping entities from killing a player too quickly
   */
  public void triggerHitInvincibility() {
    isHitInvincible = true;
    hitInvincibleTimer = 0;
    loseLife();
  }

  /**
   * Generates a new projectile at the player's current location if the firing
   * cooldown timer has reached zero, resetting the reload delay back to its cooldown value.
   *
   * @return A valid PlayerProjectile instance if allowed to fire; null if still on cooldown
   */
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