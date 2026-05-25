package game;

import bagel.Drawing;
import bagel.Font;
import java.util.Properties;
import bagel.Input;
import bagel.util.Colour;

/**
 * Acts as the parent blueprint for all game screens
 * It handles shared screen settings like window sizing, finding the horizontal
 * center, loading fonts, and drawing background colors so each child class doesn't
 * have to load them separately.
 *
 * @author Nathan S.
 */
public abstract class Screen {
  protected Player player;
  protected Properties gameProps;
  protected double screenWidth;
  protected double screenHeight;
  protected Font generalText;
  protected int fontSize;
  protected Colour bgColour;
  protected final double CENTER;

  /**
   * Sets up a screen layout by saving window dimensions, calculating the exact center
   * point for text alignment, and converting comma-separated configuration text into
   * a usable background color.
   *
   * @param gameProps    The settings file containing color data and font choices.
   * @param screenWidth  The horizontal width of the game window.
   * @param screenHeight The vertical height of the game window.
   */
  public Screen(Properties gameProps, double screenWidth,
                double screenHeight) {
    this.screenHeight = screenHeight;
    this.screenWidth = screenWidth;
    this.gameProps = gameProps;
    CENTER = screenWidth / 2;

    fontSize = Integer.parseInt(gameProps.getProperty("text.size"));
    this.generalText = new Font(gameProps.getProperty("text.font"), fontSize);

    // Load background colour
    String[] colorParts = gameProps.getProperty("background.colour").split(",");
    double r = Double.parseDouble(colorParts[0]);
    double g = Double.parseDouble(colorParts[1]);
    double b = Double.parseDouble(colorParts[2]);
    bgColour = new Colour(r, g, b);
  }

  /**
   * Processes player inputs and renders images for each game screen.
   * Every specific screen type must fill this in to control its own gameplay or menu rules.
   *
   * @param input The current keyboard/mouse input.
   */
  public abstract void update(Input input);

  public void drawBG() {
    Drawing.drawRectangle(0,0, screenWidth,screenHeight, bgColour);
  }
}
