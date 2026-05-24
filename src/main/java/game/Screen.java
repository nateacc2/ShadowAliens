package game;

import bagel.Font;
import java.util.Properties;
import bagel.Input;
import bagel.util.Colour;

public abstract class Screen {
  protected Player player;
  protected Properties gameProps;
  protected double screenWidth;
  protected double screenHeight;
  protected Font generalText;
  protected int fontSize;
  protected Colour bgColour;
  protected final double CENTER;

  public Screen(Properties gameProps, double screenWidth,
                double screenHeight) {
    this.screenHeight = screenHeight;
    this.screenWidth = screenWidth;
    CENTER = screenWidth / 2;
    this.gameProps = gameProps;
    fontSize = Integer.parseInt(gameProps.getProperty("text.size"));
    this.generalText = new Font(gameProps.getProperty("text.font"), fontSize);

    // Load background colour
    String[] colorParts = gameProps.getProperty("background.colour").split(",");
    double r = Double.parseDouble(colorParts[0]);
    double g = Double.parseDouble(colorParts[1]);
    double b = Double.parseDouble(colorParts[2]);
    bgColour = new Colour(r, g, b);
  }

  public abstract void update(Input input);
}
