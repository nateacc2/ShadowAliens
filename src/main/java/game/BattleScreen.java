package game;

import bagel.*;
import bagel.Font;
import bagel.Image;
import java.util.Properties;
import java.util.ArrayList;

public class BattleScreen extends Screen {

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
  private int fontSize;
  public Font pausedText;
  private Image playerLife;

  private Image largeExplosionImg;
  private int largeExplosionDur;
  private Image smallExplosionImg;
  private int smallExplosionDur;

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
  private static final int NO_REMAINDER = 0;
  private static final int NULL = 0;
  private static final int NORMAL_TIME = 1;
  private static final int FIRST_COUNT = 1;
  private static final int HALF_SPEED = -2;

  // Power up related
  private ArrayList<Powerup> fallingPowerups = new ArrayList<>();
  private Powerup activePowerup;

  /**
   * BattleScreen Constructor initialized from ShadowAliens data properties
   */
  public BattleScreen(Properties gameProps, double screenWidth, double screenHeight) {
    super(gameProps, screenWidth, screenHeight);

    restartGame();

    // Assign the player instance created on the start screen
    this.player = player;

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

    // Load wave and score positions
    String[] wavePos = gameProps.getProperty("wave.pos").split(",");
    waveX = Integer.parseInt(wavePos[0]);
    waveY = Integer.parseInt(wavePos[1]);
    String[] scorePos = gameProps.getProperty("score.pos").split(",");
    scoreX = Integer.parseInt(scorePos[0]);
    scoreY = Integer.parseInt(scorePos[1]);
    waveLevel = FIRST_COUNT;

    largeExplosionImg = new Image(gameProps.getProperty("explosion.large.image"));
    largeExplosionDur = Integer.parseInt(gameProps.getProperty("explosion.large.duration"));
    smallExplosionImg = new Image(gameProps.getProperty("explosion.small.image"));
    smallExplosionDur = Integer.parseInt(gameProps.getProperty("explosion.small.duration"));

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

  @Override
  public void update(Input input) {

    drawBG();

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

    for (Explosion e: explosions) {
      e.draw();
    }
    for (Powerup p: fallingPowerups) {
      p.draw();
    }
    player.draw(invincible);
    currWave.draw();

    for (Projectile p: projectiles) {
      p.draw();
    }

    generalText.drawString(gameProps.getProperty("wave.text") + " " + waveLevel, waveX, waveY);
    generalText.drawString(gameProps.getProperty("score.text") + " " + scoreCount, scoreX, scoreY);

    for (int i = 0; i < player.getLivesCount() ; i++) {
      playerLife.draw(liveX + (lifeGap*i),liveY);
    }

    // PAUSE MENU FUNCTIONS
    if (isPaused) {
      // Find the X center value for the title given the title width
      double titleX = CENTER - (pausedText.getWidth(title) / 2.0);
      pausedText.drawString(title, titleX, pausedY);

      for (int i = 0; i < controlList.length; i++) {
        String currText = controlList[i];
        double controlX = CENTER - (generalText.getWidth(currText) /
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
      Screen startScreen = new StartScreen(gameProps, screenWidth, screenHeight);
      ShadowAliens.changeScreen(startScreen);
    }

    if (input.wasPressed(Keys.N)) {
      waveLevel++;
      projectiles.clear();
      scoreCount+= waveCompScore;
      loadWave(waveLevel);
    }
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

    // Enemy shooting
    for (Enemy e: currWave.getEnemies()) {
      if (e instanceof ShootingEnemy) {
        // Downcast needed as normal Enemy does not have a shoot function
        ShootingEnemy shooter = (ShootingEnemy) e;
        EnemyProjectile proj = shooter.shoot();
        if (proj != null) {
          projectiles.add(proj);
        }
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

    for (Powerup p : fallingPowerups) {
      p.update();
    }

    // Collision detection
    checkCollisions();

    if (activePowerup != null) {
      activePowerup.update();

      // if active power up timer runs out, deactivate
      if (!activePowerup.isActive()) {
        activePowerup.deactivate(player);
        activePowerup = null;
      }
    }

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
      Screen endState = new EndScreen(gameProps, screenWidth, screenHeight, false, player);
      ShadowAliens.changeScreen(endState);
    }

    if (currWave.isCompleted()) {
      waveLevel++;
      scoreCount += waveCompScore;
      // check if another wave exists
      if (gameProps.getProperty("wave." + waveLevel + ".enemy.0.posX") != null) {
        loadWave(waveLevel);
      } else {
        Screen endState = new EndScreen(gameProps, screenWidth, screenHeight,
                true, player);
        ShadowAliens.changeScreen(endState);
      }
    }
  }

  /** Method to re-initialise game to its initial state */
  protected void restartGame() {
    scoreCount = NULL;
    timeScale = NORMAL_TIME;
    frameCounter = NULL;
    invincible = false;
    projectiles.clear();
    explosions.clear();
    fallingPowerups.clear();
    waveLevel = FIRST_COUNT;

    if (activePowerup != null) {
      activePowerup.deactivate(player);
    }
    activePowerup = null;

    Image spaceshipImg = new Image(gameProps.getProperty("player.image"));
    Image shieldImg = new Image(gameProps.getProperty("invincibility.image"));
    int playerLives = Integer.parseInt(gameProps.getProperty("player.initialLives"));
    int playerSpeed = Integer.parseInt(gameProps.getProperty("player.speed"));
    int projCooldown = Integer.parseInt(gameProps.getProperty("player.shootCooldown"));
    int projSpeed = Integer.parseInt(gameProps.getProperty("projectile.movementSpeed"));
    Image projImage = new Image(gameProps.getProperty("projectile.image"));
    double startY = Integer.parseInt(gameProps.getProperty("player.posY"));

    this.player = new Player(CENTER, startY, spaceshipImg, shieldImg,
            playerLives, playerSpeed, projCooldown, projSpeed, projImage, gameProps);
    loadWave(waveLevel);
  }

  private void loadWave(int waveLevel) {
    // reset enemies and powerups array for each wave
    enemies.clear();
    fallingPowerups.clear();

    Image regEnemyImg = new Image(gameProps.getProperty("enemy.regular.image"));
    Image strafeEnemyImg = new Image(gameProps.getProperty("enemy.strafing.image"));
    Image shootEnemyImg = new Image(gameProps.getProperty("enemy.shooting.image"));
    Image enemyProj = new Image(gameProps.getProperty("enemyProjectile.image"));
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
        int firingRate = Integer.parseInt(gameProps.getProperty("enemy.shooting.firingRate"));
        int projSpeed = Integer.parseInt(gameProps.getProperty("enemyProjectile.movementSpeed"));
        enemies.add(new ShootingEnemy(enemyX, 0, shootEnemyImg, arrivalTime, movementSpeed,
                firingRate, 0, projSpeed,enemyProj));
      }
      // Move to the next enemy in this specific wave
      index++;
    }
    currWave = new Wave(enemies, waveLevel);

    Image lifePowerupImg = new Image(gameProps.getProperty("powerup.life.image"));
    Image cdPowerupImg = new Image(gameProps.getProperty("powerup.cooldown.image"));
    Image engineImg = new Image(gameProps.getProperty("powerup.engine.image"));
    Image shieldImg = new Image(gameProps.getProperty("powerup.shield.image"));
    int powerIndex = 0;
    while (gameProps.getProperty("wave." + waveLevel + ".powerup." + powerIndex + ".posX") != null) {

      // Parse the data for the current wave and power up index
      int arrivalTime = Integer.parseInt(gameProps.getProperty("wave." + waveLevel + ".powerup." + powerIndex + ".arrivalTime"));
      double powerX = Integer.parseInt(gameProps.getProperty("wave." + waveLevel + ".powerup." + powerIndex + ".posX"));
      String powerType = gameProps.getProperty("wave." + waveLevel + ".powerup." + powerIndex + ".type");

      if (powerType.equals("engine")) {
        fallingPowerups.add(new Engine(powerX,0, engineImg, arrivalTime, gameProps));
      } else if (powerType.equals("life")) {
        fallingPowerups.add(new Life(powerX, 0, lifePowerupImg, arrivalTime, gameProps));
      } else if (powerType.equals("cooldown")) {
        fallingPowerups.add(new Cooldown(powerX, 0, cdPowerupImg, arrivalTime, gameProps));
      } else if (powerType.equals("shield")) {
        fallingPowerups.add(new Shield(powerX, 0, shieldImg, arrivalTime, gameProps));
      }
      powerIndex++;
    }
  }

  private void checkCollisions() {

    for (int i = projectiles.size() - 1; i >= 0; i--) {
      // ensures array index is not out of bounds after being modified
      if (i >= projectiles.size()) {
        continue;
      }
      Projectile p = projectiles.get(i);

      // Enemy projectile vs Player projectile collision logic
      if (p instanceof EnemyProjectile) {
        for (int j = projectiles.size() - 1; j >= 0; j--) {

          if (j >= projectiles.size()) {
            continue;
          }
          // don't compare projectile to itself
          if (j == i) {
            continue;
          }

          Projectile other = projectiles.get(j);
          if (other instanceof PlayerProjectile && p.collidesWith(other)) {
            explosions.add(new Explosion(p.getX(), p.getY(),
                    smallExplosionImg, smallExplosionDur));
            projectiles.remove(p);
            projectiles.remove(other);
            scoreCount += hitProjScore;
            break;
          }
        }
      }

      // If Enemy projectiles hits player
      if (p instanceof EnemyProjectile && p.collidesWith(player)) {
        if (!invincible && !player.isInvincible() && !player.isHitInvincible()) {
          player.triggerHitInvincibility();
          scoreCount = Math.max(0, scoreCount - gotHitDeduction);
        }
        explosions.add(new Explosion(p.getX(), p.getY(),
                smallExplosionImg, smallExplosionDur));
        projectiles.remove(i);
        break;
      }
    }

    // Enemy loop
    for (Enemy e : currWave.getEnemies()) {
      if (!e.isAlive()) {
        continue;
      }

      for (int i = projectiles.size() - 1; i >= 0; i--) {
        Projectile p = projectiles.get(i);
        // player projectile collides with enemy
        if (p instanceof PlayerProjectile && e.collidesWith(p)) {
          projectiles.remove(i);
          e.setAlive(false);
          explosions.add(new Explosion(e.getX(), e.getY(),
                  largeExplosionImg, largeExplosionDur));

          // check for the enemy type killed
          if (e instanceof ShootingEnemy) {
            scoreCount += shootEnemyScore;
          }
          else if (e instanceof StrafingEnemy) {
            scoreCount += strafeEnemyScore;
          }
          else if (e instanceof RegularEnemy) {
            scoreCount += regEnemyScore;
          }
          break;
        }
      }

      // Enemy spaceship hits player spaceship
      if (e.isAlive() && e.collidesWith(player)) {
        e.setAlive(false);
        explosions.add(new Explosion(e.getX(), e.getY(),
                largeExplosionImg, largeExplosionDur));

        // checks if player has any invulnerability status
        if (!invincible && !player.isInvincible() && !player.isHitInvincible()) {
          player.triggerHitInvincibility();
          scoreCount = Math.max(0, scoreCount - gotHitDeduction);
        }
      }

      // Enemy goes off screen
      if (e.getY() > screenHeight) {
        e.setAlive(false);
      }
    }

    // Checks collision between powerups and player
    for (int i = 0; i < fallingPowerups.size(); i++) {
      Powerup p = fallingPowerups.get(i);

      if (p.collidesWith(player)) {
        // shut down buff if player obtains new powerup
        if (activePowerup != null) {
          activePowerup.deactivate(player);
        }

        p.collect();
        activePowerup = p;
        activePowerup.activate(player);

        scoreCount += powerupScore;
        fallingPowerups.remove(i);
        i--;
      }
    }

  }
}