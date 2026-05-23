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

  private Player player;
  private Wave currWave;
  private ArrayList<Projectile> projectiles = new ArrayList<>();
  private ArrayList<Explosion> explosions = new ArrayList<>();
  private ArrayList<Enemy> enemies = new ArrayList<>();

  private int scoreCount;
  private int waveLevel;
  private boolean isPaused = false;
  private boolean invincible;
  private double timeScale;
  private int frameCounter;

  // UI Related
  public Font generalText;
  private int fontSize;
  public Font pausedText;
  public final Colour bgColour;
  private Image playerLife;

  private Image largeExplosionImg;
  private int largeExplosionDur;

  // UI positions
  private double waveX;
  private double waveY;
  private double liveX;
  private double liveY;
  private int lifeGap;
  private double scoreX;
  private double scoreY;

  // pause menu
  private double pausedY;
  private double controlY;
  private double rowGap;
  private final String title;
  private final String timeScaleText;
  private final String[] controlList;
  private double timeX;
  private double timeY;
  private int pausedFontSize;

  // scores related
  private int regEnemyScore;
  private int strafeEnemyScore;
  private int shootEnemyScore;
  private int gotHitDeduction;
  private int powerupScore;
  private int hitProjScore;
  private int waveCompScore;

  // Magic numbers
  private static final int ORIGIN = 0;
  private static final int NO_REMAINDER = 0;
  private static final int NULL = 0;
  private static final int NORMAL_TIME = 1;
  private static final int FIRST_COUNT = 1;
  private static final int HALF_SPEED = -2;

  private static Properties gameProps;

  public static double screenWidth;
  public static double screenHeight;

  private final double center;

  /**
   * Shadow Aliens Class used to initalises game variables and load game data
   * */
  public ShadowAliens(Properties gameProps) {
    super(Integer.parseInt(gameProps.getProperty("window.width")),
            Integer.parseInt(gameProps.getProperty("window.height")),
            "Shadow Aliens");

    ShadowAliens.gameProps = gameProps;

    screenWidth = Integer.parseInt(gameProps.getProperty("window.width"));
    screenHeight = Integer.parseInt(gameProps.getProperty("window.height"));
    center = screenWidth / 2;

    // Load images, lives position and live count
    playerLife = new Image(gameProps.getProperty("playerLives.image"));
    lifeGap = Integer.parseInt(gameProps.getProperty("playerLives.gap"));
    String[] lifePos = gameProps.getProperty("playerLives.startPosition").split(",");
    this.liveX = Integer.parseInt(lifePos[0]);
    this.liveY = Integer.parseInt(lifePos[1]);
    timeScale = NORMAL_TIME;

    // Load font settings
    fontSize = Integer.parseInt(gameProps.getProperty("text.size"));
    pausedFontSize = Integer.parseInt(gameProps.getProperty("pausedTitle.size"));
    pausedText = new Font(gameProps.getProperty("text.font"), pausedFontSize);
    generalText = new Font(gameProps.getProperty("text.font"), fontSize);

    // Load wave and score positions
    String[] wavePos = gameProps.getProperty("wave.pos").split(",");
    waveX = Integer.parseInt(wavePos[0]);
    waveY = Integer.parseInt(wavePos[1]);
    String[] scorePos = gameProps.getProperty("score.pos").split(",");
    scoreX = Integer.parseInt(scorePos[0]);
    scoreY = Integer.parseInt(scorePos[1]);
    waveLevel = FIRST_COUNT;

    // Load background colour
    String[] colorParts = gameProps.getProperty("background.colour").split(",");
    double r = Double.parseDouble(colorParts[0]);
    double g = Double.parseDouble(colorParts[1]);
    double b = Double.parseDouble(colorParts[2]);
    bgColour = new Colour(r, g, b);

    // Load player specs
    Image spaceshipImg = new Image(gameProps.getProperty("player.image"));
    Image shieldImg = new Image(gameProps.getProperty("invincibility.image"));
    int playerLives = Integer.parseInt(gameProps.getProperty("player.initialLives"));
    int playerSpeed = Integer.parseInt(gameProps.getProperty("player.speed"));

    int projCooldown = Integer.parseInt(gameProps.getProperty("player.shootCooldown"));
    int projSpeed = Integer.parseInt(gameProps.getProperty("projectile.movementSpeed"));
    Image projImage = new Image(gameProps.getProperty("projectile.image"));

    double startX = center;
    double startY = Integer.parseInt(gameProps.getProperty("player.posY"));

    this.player = new Player(startX, startY, spaceshipImg, shieldImg, playerLives, playerSpeed,
            projCooldown, projSpeed, projImage);


    largeExplosionImg = new Image(gameProps.getProperty("explosion.large.image"));
    largeExplosionDur = Integer.parseInt(gameProps.getProperty("explosion.large.duration"));

    // Load pause menu specs
    title = gameProps.getProperty("pausedTitle.text");
    timeScaleText = gameProps.getProperty("timescale.text");
    String[] timePos = gameProps.getProperty("timescale.pos").split(",");
    timeX = Integer.parseInt(timePos[0]);
    timeY = Integer.parseInt(timePos[1]);
    pausedY = Double.parseDouble(gameProps.getProperty("pausedTitle.posY"));
    controlY = Double.parseDouble(gameProps.getProperty("controlsList.startPosY"));
    controlList = gameProps.getProperty("controlsList.text").split(",");
    rowGap = Double.parseDouble(gameProps.getProperty("controlsList.rowGap"));

    // Load scores details
    regEnemyScore = Integer.parseInt(gameProps.getProperty("score.destroyedEnemy.regular"));
    strafeEnemyScore = Integer.parseInt(gameProps.getProperty("score.destroyedEnemy.strafing"));
    shootEnemyScore = Integer.parseInt(gameProps.getProperty("score.destroyedEnemy.shooting"));
    gotHitDeduction = Integer.parseInt(gameProps.getProperty("score.gotHit"));
    powerupScore = Integer.parseInt(gameProps.getProperty("score.gotPowerup"));
    hitProjScore =  Integer.parseInt(gameProps.getProperty("score.hitProjectile"));
    waveCompScore = Integer.parseInt(gameProps.getProperty("score.waveCompleted"));

    loadWave(waveLevel);
  }

  /**
   * Run and render the next frame.
   * @param input The current mouse/keyboard input.
   */
  @Override
  protected void update(Input input) {

    Drawing.drawRectangle(ORIGIN,ORIGIN,screenWidth, screenHeight, bgColour);

    // navigation between pause and game screen
    if (input.wasPressed(Keys.ESCAPE)) {
      // check if game is already paused
      if (isPaused) {
        // back to battle screen
        isPaused = false;
      } else {
        isPaused = true;
      }
    }

    /* Speeding up or slowing down logic
     * for e.g. if timescale is -2 (half speed), this means we have
     * to call update method every 2 frames.
     * Modulo operator calculates when the game should run every X frames.
     * */
    if (!isPaused) {
      // Check speed factor if positive
      if (timeScale >= NORMAL_TIME) {
        // Run the update game logic, i times in one frame
        for (int i = 0; i < timeScale; i++){
          updateGameLogic(input);
        }
      } else {
        /* Calculates how often X frames need to past
         * before calling update method
         * */
        int skipFrame = (int) Math.abs(timeScale);
        if (frameCounter % skipFrame == NO_REMAINDER) {
          updateGameLogic(input);
        }
      }
      frameCounter++;
    }

    player.draw();
    currWave.draw();
    for (Projectile p: projectiles) {
      p.draw();
    }
    for (Explosion e: explosions) {
      e.draw();
    }

    generalText.drawString(gameProps.getProperty("wave.text") + " " + waveLevel, waveX, waveY);
    generalText.drawString(gameProps.getProperty("score.text") + " " + scoreCount, scoreX, scoreY);

    for (int i = 0; i < player.getLivesCount() ; i++) {
      playerLife.draw(liveX + (lifeGap*i),liveY);
    }

    if (isPaused) {
      // Find the X center value for the title given the title width
      double titleX = center - (pausedText.getWidth(title) / 2.0);
      pausedText.drawString(title, titleX, pausedY);

      for (int i = 0; i < controlList.length; i++) {
        String currText = controlList[i];
        double controlX = center - (generalText.getWidth(currText) /
                2.0);
        generalText.drawString(currText, controlX, controlY + (rowGap * i));
      }

      // Implementing timescale display in pause menu
      String displayScale = "";
      if (timeScale >= NORMAL_TIME) {
        displayScale = "" +  (int) Math.abs(timeScale);
      } else if (timeScale <= HALF_SPEED) {
        // add logic to write speed as fraction
        int denominator = (int) Math.abs(timeScale);
        displayScale = "1/" + denominator;
      }

      if (!displayScale.equals("")) {
        generalText.drawString(timeScaleText + " " + displayScale, timeX, timeY);
      }
    }

    /* implementing speeding up logic skipping values 0 and -1
     * as it 0 does not make sense unless the game is frozen and the
     * logic for slowing down is by a factor of 1/2 (timescale = -2)
     * */
    if (input.wasPressed(Keys.G)){
      if (timeScale == HALF_SPEED) {
        timeScale = NORMAL_TIME; // Jump from half-speed to normal
      } else if (timeScale < HALF_SPEED) {
        // Move from -3 (1/3 speed) to -2 (1/2 speed)
        timeScale++;
      } else {
        // Move from 1 (normal) to 2 (double speed)
        timeScale++;
      }
    }

    // implementing slowing down logic
    if (input.wasPressed(Keys.F)) {
      if (timeScale == NORMAL_TIME) {
        timeScale = HALF_SPEED; // Jump from normal speed to half speed
      } else {
        timeScale--;
      }
    }

    // implementing invincibility
    if (input.wasPressed(Keys.I)) {
      if (invincible) {
        invincible = false;
      } else {
        invincible = true;
      }
    }

    if (input.wasPressed(Keys.R)) {
      restartGame();
    }

    if (input.wasPressed(Keys.N)) {
      waveLevel++;
      projectiles.clear();
      scoreCount+= waveCompScore;
      loadWave(waveLevel);

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

  /**
   * Method to call for updating game logic such as
   * updating player speed, checking collision logic and
   * tracking cooldowns.
   * */
  private void updateGameLogic(Input input){

    player.update(input);

    // Player shooting
    if (input.wasPressed(Keys.SPACE)) {
      PlayerProjectile p = player.shoot();
      if (p != null) {
        projectiles.add(p);
      }
    }

      // Update projectiles
    for (int i = projectiles.size() - 1; i >= 0; i--) {
      Projectile p = projectiles.get(i);
      p.update();
      if (!p.isProjExist()) {
        projectiles.remove(i);
      }
    }

    // Update wave (handles enemy movement)
    currWave.update();

    // Collision detection
    checkCollisions();

    // Update explosions
    for (int i = explosions.size() - 1; i >= 0; i--) {
      Explosion e = explosions.get(i);
      e.update();
      if (!e.isActive()) {
        explosions.remove(i);
      }
    }

    // Check game over
    if (player.getLivesCount() <= 0) {
      System.exit(0);
    }

    if (currWave.isCompleted()) {
      waveLevel++;
      scoreCount += waveCompScore;
      // check if another wave exists
      if (gameProps.getProperty("wave." + waveLevel + ".enemy.0.posX") != null) {
        loadWave(waveLevel);
      } else {
        System.exit(0);
      }
    }
  }

  /** Method to re-initialise game to its initial state */
  private void restartGame() {
    scoreCount = NULL;
    timeScale = NORMAL_TIME;
    frameCounter = NULL;
    invincible = false;
    projectiles.clear();
    explosions.clear();
    waveLevel = FIRST_COUNT;

    Image spaceshipImg = new Image(gameProps.getProperty("player.image"));
    Image shieldImg = new Image(gameProps.getProperty("invincibility.image"));
    int playerLives = Integer.parseInt(gameProps.getProperty("player.initialLives"));
    int playerSpeed = Integer.parseInt(gameProps.getProperty("player.speed"));
    int projCooldown = Integer.parseInt(gameProps.getProperty("player.shootCooldown"));
    int projSpeed = Integer.parseInt(gameProps.getProperty("projectile.movementSpeed"));
    Image projImage = new Image(gameProps.getProperty("projectile.image"));
    double startY = Integer.parseInt(gameProps.getProperty("player.posY"));

    this.player = new Player(center, startY, spaceshipImg, shieldImg,
            playerLives, playerSpeed, projCooldown, projSpeed, projImage);
    loadWave(waveLevel);
  }

  private void loadWave(int waveLevel) {
    // reset enemies array for each wave
    enemies.clear();

    Image regEnemyImg = new Image(gameProps.getProperty("enemy.regular.image"));
    Image strafeEnemyImg = new Image(gameProps.getProperty("enemy.strafing.image"));
    Image shootEnemyImg = new Image(gameProps.getProperty("enemy.shooting.image"));

    int index = 0;
    while (gameProps.getProperty("wave." + waveLevel + ".enemy." + index + ".posX") != null) {

      // Parse the data for the current wave and enemy index
      int arrivalTime = Integer.parseInt(gameProps.getProperty("wave." + waveLevel + ".enemy." + index + ".arrivalTime"));
      double movementSpeed = Double.parseDouble(gameProps.getProperty("wave." + waveLevel + ".enemy." + index + ".movementSpeed"));
      double enemyX = Integer.parseInt(gameProps.getProperty("wave." + waveLevel + ".enemy." + index + ".posX"));
      String enemyType = gameProps.getProperty("wave." + waveLevel + ".enemy." + index + ".type");

      if (enemyType.equals("regular")) {
        enemies.add(new RegularEnemy(enemyX,0, regEnemyImg, arrivalTime, movementSpeed));
      } else if (enemyType.equals("strafing")) {
        enemies.add(new StrafingEnemy(enemyX, 0, strafeEnemyImg, arrivalTime, movementSpeed, screenWidth));
      } else if (enemyType.equals("shooting")) {
        enemies.add(new ShootingEnemy(enemyX, 0, shootEnemyImg, arrivalTime, movementSpeed,
                0, 0, 1,shootEnemyImg));
      }
      // Move to the next enemy in this specific wave
      index++;
    }
    currWave = new Wave(enemies, waveLevel);
  }

  private void checkCollisions() {

    for (Projectile p : projectiles) {
      if (!p.isProjExist()) {
        continue;
      }

    }

    for (Enemy e : currWave.getEnemies()) {
      if (!e.isAlive()) {
        continue;
      }

      // Player projectile hits enemy
      for (int i = projectiles.size() - 1; i >= 0; i--) {
        if (e.collidesWith(projectiles.get(i))) {
          projectiles.remove(i);
          e.setAlive(false);
          explosions.add(new Explosion(e.getX(), e.getY(),
                  largeExplosionImg, largeExplosionDur, true));
          if (e instanceof RegularEnemy) {
            scoreCount += regEnemyScore;
          } else if (e instanceof StrafingEnemy) {
            scoreCount += strafeEnemyScore;
          } else if (e instanceof ShootingEnemy) {
            scoreCount += shootEnemyScore;
          }
          break;
        }
      }

      // Enemy hits player
      if (e.isAlive()) {
        if (e.collidesWith(player)) {
          e.setAlive(false);
          explosions.add(new Explosion(e.getX(), e.getY(),
                  largeExplosionImg, largeExplosionDur, true));
          if (!invincible && !player.isInvincible()) {
            player.loseLife();
            scoreCount = Math.max(0, scoreCount - gotHitDeduction);
          }
        }
      }

      // Enemy goes off screen
      if (e.getY() > screenHeight) {
        e.setAlive(false);
      }
    }
  }
}

