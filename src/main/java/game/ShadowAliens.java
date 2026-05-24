package game;

import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import java.awt.*;
import java.util.Properties;
import java.util.ArrayList;

/**
 * Main game class that manages initialization of game window and objects
 */
public class ShadowAliens extends AbstractGame {

  private static ShadowAliens gameInstance;
  private Screen currScreen;
  private static Properties gameProps;

  public static double screenWidth;
  public static double screenHeight;
  public final Colour bgColour;

  /**
   * Shadow Aliens Class used to initalises game variables and load game data
   * */
  public ShadowAliens(Properties gameProps) {
    super(
        Integer.parseInt(gameProps.getProperty("window.width")),
        Integer.parseInt(gameProps.getProperty("window.height")),
        "Shadow Aliens");

    ShadowAliens.gameProps = gameProps;
    screenWidth = Integer.parseInt(gameProps.getProperty("window.width"));
    screenHeight = Integer.parseInt(gameProps.getProperty("window.height"));

    gameInstance = this;

    // Load background colour
    String[] colorParts = gameProps.getProperty("background.colour").split(",");
    double r = Double.parseDouble(colorParts[0]);
    double g = Double.parseDouble(colorParts[1]);
    double b = Double.parseDouble(colorParts[2]);
    bgColour = new Colour(r, g, b);

    this.currScreen = new StartScreen(gameProps, screenWidth, screenHeight);
  }

  /**
   * Run and render the next frame.
   * @param input The current mouse/keyboard input.
   */
  @Override
  protected void update(Input input) {

    Drawing.drawRectangle(0, 0, screenWidth, screenHeight, bgColour);

    if (currScreen != null) {
      currScreen.update(input);
    }
  }

  /** Entry point for the game */
  public static void main(String[] args) {
    String path = System.getProperty("gameData");

    if (path == null) {
      path = "gameData.properties";
    }

    Properties gameProps = null;
    try {
      gameProps = IOUtils.readPropertiesFile(path);
    } catch (Exception e) {
      System.out.println("Error with " + path);
      System.exit(-1);
    }

    if (gameProps == null) {
      System.out.println("Error with " + path);
      System.exit(-1);
    }

    ShadowAliens game = new ShadowAliens(gameProps);
    game.run();

  }

  public static void changeScreen(Screen newScreen) {
    gameInstance.currScreen = newScreen;
  }
}
