package game;

import java.util.Properties;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Font;

public class StartScreen extends Screen {

  private String startText;
  private String instructionList[];
  private Font titleText;
  private int titleSize;
  private double startY;
  private double instructionY;
  private double rowGap;

  public StartScreen(Properties gameProps, double screenWidth, double screenHeight) {
    super(gameProps, screenWidth, screenHeight);

    startText = gameProps.getProperty("start.title.text");
    instructionList = gameProps.getProperty("start.instructionsList.text").split(",");
    titleSize = Integer.parseInt(gameProps.getProperty("start.title.size"));
    startY = Double.parseDouble(gameProps.getProperty("start.title.posY"));
    instructionY = Double.parseDouble(gameProps.getProperty("start.instructionsList.startPosY"));
    rowGap = Double.parseDouble(gameProps.getProperty("start.instructionsList.rowGap"));

    titleText = new Font(gameProps.getProperty("text.font"), titleSize);

    double startX = CENTER;
    double playerY = Integer.parseInt(gameProps.getProperty("player.posY"));

    Image spaceshipImg = new Image(gameProps.getProperty("player.image"));
    Image shieldImg = new Image(gameProps.getProperty("invincibility.image"));
    int playerLives = Integer.parseInt(gameProps.getProperty("player.initialLives"));
    int playerSpeed = Integer.parseInt(gameProps.getProperty("player.speed"));

    int projCooldown = Integer.parseInt(gameProps.getProperty("player.shootCooldown"));
    int projSpeed = Integer.parseInt(gameProps.getProperty("projectile.movementSpeed"));
    Image projImage = new Image(gameProps.getProperty("projectile.image"));

    this.player = new Player(startX, playerY, spaceshipImg, shieldImg, playerLives, playerSpeed,
            projCooldown, projSpeed, projImage, gameProps);
  }

  public void update(Input input) {

    player.update(input);
    player.draw(player.isInvincible() || player.isHitInvincible());

    double titleX = CENTER - (titleText.getWidth(startText) / 2.0 );
    titleText.drawString(startText, titleX, startY);

    for (int i = 0; i < instructionList.length; i++) {
      String currText = instructionList[i];
      double instructionX = CENTER - (generalText.getWidth(currText) /
              2.0);
      generalText.drawString(currText, instructionX, instructionY + (rowGap * i));
    }

    if (input.wasPressed(Keys.SPACE)) {
      Screen gameplayScreen = new BattleScreen(this.gameProps, this.screenWidth,
              this.screenHeight);
      ShadowAliens.changeScreen(gameplayScreen);
    }


  }
}
