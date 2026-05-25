package game;

import bagel.Font;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

/**
 * Manages the final screen when the game ends.
 * Displays either a winning message or a losing message to the player,
 * lists instructions for what to do next, and watches for input to restart.
 *
 * @author Nathan S.
 */
public class EndScreen extends Screen {
  private boolean hasWon;
  private String instructionList[];
  private double instructionListY;
  private double rowGap;
  private String winString;
  private String loseString;
  private int winSize;
  private int loseSize;
  private double winPosY;
  private double losePosY;
  private Font winText;
  private Font loseText;

  /**
   * Creates the end screen object and loads all message configurations, font sizes,
   * positions, and instructions from the properties file.
   *
   * @param gameProps    The file properties containing text and display layout metrics.
   * @param screenWidth  The width of the game window.
   * @param screenHeight The height of the game window.
   * @param hasWon       True if the player cleared the game, false if have no more lives.
   * @param player       The current player instance to finish updating and drawing.
   */
  public EndScreen(Properties gameProps, double screenWidth, double screenHeight,
                   boolean hasWon, Player player) {
    super(gameProps, screenWidth, screenHeight);

    this.hasWon = hasWon;
    this.player = player;

    instructionList = gameProps.getProperty("end.instructionsList.text").split(",");
    instructionListY = Double.parseDouble(gameProps.getProperty("end.instructionsList.startPosY"));
    rowGap = Double.parseDouble(gameProps.getProperty("end.instructionsList.rowGap"));

    winString = gameProps.getProperty("end.win.text");
    winSize = Integer.parseInt(gameProps.getProperty("end.win.size"));
    winText = new Font(gameProps.getProperty("text.font"), winSize);
    winPosY = Double.parseDouble(gameProps.getProperty("end.win.posY"));

    loseString = gameProps.getProperty("end.lose.text");
    loseSize = Integer.parseInt(gameProps.getProperty("end.lose.size"));
    loseText = new Font(gameProps.getProperty("text.font"), loseSize);
    losePosY = Double.parseDouble(gameProps.getProperty("end.lose.posY"));
  }

  /**
   * Draws the background, the player ship, the win/lose status message,
   * and the options menu text. Checks if the space bar is pressed to restart
   * a fresh game session.
   *
   * @param input The current keyboard/mouse input.
   */
  @Override
  public void update(Input input) {

    drawBG();
    player.update(input);
    player.draw(player.isInvincible() || player.isHitInvincible());

    if (hasWon) {
      double winX = CENTER - (winText.getWidth(winString) / 2.0 );
      winText.drawString(winString, winX, winPosY);
    } else {
      double loseX = CENTER - (loseText.getWidth(loseString) / 2.0);
      loseText.drawString(loseString, loseX, losePosY);
    }

    for (int i = 0; i < instructionList.length; i++) {
      String currText = instructionList[i];
      double instructionX = CENTER - (generalText.getWidth(currText) /
              2.0);
      generalText.drawString(currText, instructionX, instructionListY + (rowGap * i));
    }

    if (input.wasPressed(Keys.SPACE)) {
      Screen gameplayScreen = new BattleScreen(this.gameProps, this.screenWidth,
              this.screenHeight);
      ShadowAliens.changeScreen(gameplayScreen);
    }

  }
}

