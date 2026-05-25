package game;

import java.util.ArrayList;

/**
 * Manages the timeline and spawning sequence for a group of enemies. Keeps tracks of enemies
 * that are in the current wave and removes them after they have left the screen
 *
 * @author Nathan S.
 */
public class Wave {
  ArrayList<Enemy> enemies = new ArrayList<>();
  // ArrayList<Powerup> powerups = new ArrayList<>();
  private int stage;
  private int frameCount;

  /**
   * Links a list enemies to a specific wave level.
   * This allows the game to specifically track and differentiate the number and types of enemies
   * to each wave level.
   *
   * @param enemies The list of enemy ships scheduled to appear.
   * @param stage   The current wave level.
   */
  public Wave(ArrayList<Enemy> enemies, int stage) {
    this.enemies = enemies;
    this.stage = stage;
    this.frameCount = 0;
  }

  // check if enemy are still alive
  public boolean isCompleted() {
    for (Enemy e : enemies) {
      if (e.isAlive()) {
        return false; // Found an enemy that is still alive, wave is not done
      }
    }
    return true; // All enemies are dead!
  }

  /**
   * Steps through the enemies array and updates position if they are due to spawn.
   * Checks for enemies that are out of bounds to ensure they stopped being drawn.
   */
  public void update() {

    // check whether enemy is ready to spawn
    for (Enemy e: enemies) {
      if (e.getArrivalTime() <= frameCount && e.isAlive()) {
        e.update();
      }
    }


    for (int i = enemies.size() - 1; i >= 0; i--) {
      Enemy e = enemies.get(i);
      // Check if enemies is out of screen
      if (e.isOutOfScreen(ShadowAliens.screenHeight)) {
        // safely remove enemy if they are no longer on screen
        enemies.remove(i);
      }
    }

    frameCount++;
  }

  /**
   * Renders enemy spaceships in user's screen according to the wave level
   */
  public void draw() {
    for (Enemy e: enemies) {
      if (e.getArrivalTime() <= frameCount && e.isAlive()) {
        e.draw();
      }
    }
  }

  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }
}
