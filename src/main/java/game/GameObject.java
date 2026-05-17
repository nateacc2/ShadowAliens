package game;
import bagel.Image;

public abstract class GameObject {
  protected double x;
  protected double y;
  protected Image image;

  public GameObject(double x, double y, Image image) {
    this.x = x;
    this.y = y;
    this.image = image;
  }

  public void move(){
  }
  public void update(){
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
}
