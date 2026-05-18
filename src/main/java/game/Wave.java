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
    for (Enemy e: enemies) {
      if (e.isAlive()) {
        return false;
      }
    }
    return true;
  }

  public void update() {

    // check whether enemy is ready to spawn
    for (Enemy e: enemies) {
      if (e.getArrivalTime() <= frameCount && e.isAlive()) {
        e.draw();
        e.update();
      }
    }
    frameCount++;
  }

  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }
}
