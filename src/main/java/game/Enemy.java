package game;

import java.util.Properties;

public class Enemy {
  private double enemyX;
  private double enemyY = 0;
  private double arrivalTime;
  private double movementSpeed;
  private boolean isAlive;

  public Enemy(double enemyX, double arrivalTime, double movementSpeed) {
    this.enemyX = enemyX;
    // enemy always spawn at the top
    this.arrivalTime = arrivalTime;
    this.movementSpeed = movementSpeed;
    this.isAlive = true;
  }

  public double getEnemyX() {
    return enemyX;
  }

  public double getEnemyY() {
    return enemyY;
  }
  public double getArrivalTime() {
    return arrivalTime;
  }

  public double getMovementSpeed() {
    return movementSpeed;
  }

  public void move() {
    enemyY += movementSpeed;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    this.isAlive = alive;
  }
}


