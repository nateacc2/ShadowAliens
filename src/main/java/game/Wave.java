package game;

import java.util.ArrayList;

public class Wave {
  ArrayList<Enemy> enemies = new ArrayList<>();
  // ArrayList<Powerup> powerups = new ArrayList<>();
  private int stage;
  private int frameCount;

  public Wave(ArrayList<Enemy> enemies, int stage) {
    this.enemies = enemies;
    this.stage = stage;
    this.frameCount = 0;
  }

  // check if enemy are still alive
  public boolean isCompleted() {
   return enemies.isEmpty();
  }

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
