package ee.joonasvali.spaceshooter.core.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Joonas Vali January 2017
 */
public class GaussGunBulletProvider implements ProjectileProvider<GaussGunBullet>, Disposable {
  private static final float START_SPEED = 3f;
  private static final float SIZE = 0.3f;
  private static final float ACCELERATION = 0f;

  private static final int TEXTURE_X = 12;
  private static final int TEXTURE_Y = 2;
  private static final int TEXTURE_WIDTH = 8;
  private static final int TEXTURE_HEIGHT = 26;

  private final Texture texture;
  private final Sprite sprite;

  public GaussGunBulletProvider() {
    this.texture = new Texture(Gdx.files.internal("missile.png"));
    this.sprite = new Sprite(texture, TEXTURE_X, TEXTURE_Y, TEXTURE_WIDTH, TEXTURE_HEIGHT);
  }


  private Pool<GaussGunBullet> missilePool = new Pool<GaussGunBullet>() {
    @Override
    protected GaussGunBullet newObject() {
      return new GaussGunBullet(GaussGunBulletProvider.this);
    }
  };

  @Override
  public void draw(SpriteBatch batch, WeaponProjectile m) {
    sprite.setOrigin(m.getWidth() / 2, m.getHeight() / 2);
    sprite.setSize(m.getWidth(), m.getHeight());
    sprite.setRotation(-m.getAngle()); // why?
    sprite.setX(m.getX());
    sprite.setY(m.getY());
    sprite.draw(batch);
  }

  @Override
  public GaussGunBullet obtain() {
    GaussGunBullet projectile = missilePool.obtain();
    projectile.setSpeed(START_SPEED);
    projectile.setAcceleration(ACCELERATION);
    float height = SIZE * sprite.getHeight() / sprite.getWidth();
    projectile.setWidth(SIZE);
    projectile.setHeight(height);
    projectile.setDamage(1000);
    return projectile;
  }

  @Override
  public void free(WeaponProjectile projectile) {
    missilePool.free((GaussGunBullet) projectile);
  }

  @Override
  public void dispose() {
    texture.dispose();
  }
}
