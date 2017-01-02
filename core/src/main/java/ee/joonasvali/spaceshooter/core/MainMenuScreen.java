package ee.joonasvali.spaceshooter.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * @author Joonas Vali January 2017
 */
public class MainMenuScreen implements Screen {

  public final FileHandle CAMBRIA_FONT = Gdx.files.internal("fonts/cambria.ttc");
  private final SpaceShooterGame game;
  private final BitmapFont titlefont;

  private final OrthographicCamera camera;
  private OrthographicCamera textCamera;
  private float textCameraWidth;
  private float textCameraHeight;
  GlyphLayout glyphLayout = new GlyphLayout();

  public MainMenuScreen(SpaceShooterGame spaceShooterGame, float viewportWidth, float viewportHeight) {
    this.game = spaceShooterGame;

    camera = new OrthographicCamera();
    textCamera = new OrthographicCamera();
    camera.setToOrtho(false, viewportWidth, viewportHeight);
    textCameraWidth = 1000;
    textCameraHeight = 1000 * (viewportHeight / viewportWidth);
    textCamera.setToOrtho(false, textCameraWidth, textCameraHeight);

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(CAMBRIA_FONT);
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 30;
    parameter.shadowOffsetX = 3;
    parameter.shadowOffsetY = 3;
    parameter.shadowColor = Color.BLACK;
    parameter.color = Color.YELLOW;
    titlefont = generator.generateFont(parameter);
    generator.dispose();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.getBatch().setProjectionMatrix(camera.combined);
    // render behind text here

    textCamera.update();
    game.getBatch().setProjectionMatrix(textCamera.combined);

    game.getBatch().begin();
    game.getFont16().setColor(Color.WHITE);

    glyphLayout.setText(titlefont, "Space Shooter");
    float fwidth = glyphLayout.width;
    System.out.println(fwidth);
    titlefont.draw(game.getBatch(), "Space Shooter", textCameraWidth / 2  - fwidth / 2, textCameraHeight / 1.4f);
    game.getFont16().draw(game.getBatch(), "Tap anywhere to begin!", 5, 35);
    game.getBatch().end();

    if (Gdx.input.isTouched()) {
      game.setScreen(new GameScreen(game));
      dispose();
    }
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void show() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {
    titlefont.dispose();
  }
}
