package game;

import bagel.AbstractGame;
import bagel.Input;
import java.awt.*;
import java.util.Properties;

/**
 * Main game class that manages initialization of game window and objects,
 * global properties and screen transition
 * * @author Nathan S.
 */
public class ShadowAliens extends AbstractGame {

  private static ShadowAliens gameInstance;
  private Screen currScreen;
  private static Properties gameProps;
  public static double screenWidth;
  public static double screenHeight;

  /**
   * Constructs the game instance, configures window dimensions using properties file,
   * initializes the starting interface screen
   * @param gameProps Configuration properties file containing setup and layout
   *                  variables specific to the Shadow Aliens game
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

    this.currScreen = new StartScreen(gameProps, screenWidth, screenHeight);
  }

  /**
   * Run and render the next frame.
   * @param input The current mouse/keyboard input.
   */
  @Override
  protected void update(Input input) {

    if (currScreen != null) {
      currScreen.update(input);
    }
  }

  /** Entry point for the game that checks whether configuration file provided
   * is valid and boots the main game.
   * @param args The command line arguments
   */
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

  /** Method to transition game interfaces between different active screens
   * @param newScreen next Screen instance passed in to render and update
   */
  public static void changeScreen(Screen newScreen) {
    gameInstance.currScreen = newScreen;
  }
}
