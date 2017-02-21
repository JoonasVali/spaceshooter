package ee.joonasvali.spaceshooter.core.game.level;

/**
 * @author Joonas Vali February 2017
 */
public interface LevelDescriptor {
  String getName();
  String[] getLevel();
  int getWidth();
  int getHeight();
}
