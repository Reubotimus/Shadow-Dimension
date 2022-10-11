import bagel.*;
import bagel.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Level extends Screen {
    public Player player;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public static Image background;

    public Level(int levelNum) {
        int TYPE_INDEX = 0, X_INDEX = 1, Y_INDEX = 2;
        background = new Image("res/background" + levelNum + ".png");

        try (BufferedReader br = new BufferedReader(new FileReader("res/level" + levelNum + ".csv"))) {
            String line;
            // reads in each line adding creating game objects and adding them to the list
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");

                if (cells[TYPE_INDEX].equals("Wall") ) {
                    this.entities.add(new Wall(Integer.parseInt(cells[X_INDEX]),
                            Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("Sinkhole")) {
                    this.entities.add(new Sinkhole(
                            Integer.parseInt(cells[X_INDEX]),
                            Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("TopLeft")) {
                    //player.setTopLeft(new Point(Integer.parseInt(cells[X_INDEX]), Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("BottomRight")) {
                    //player.setBottomRight(new Point(Integer.parseInt(cells[X_INDEX]), Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("Fae")) {
                    this.player = new Player(Integer.parseInt(cells[X_INDEX]),
                            Integer.parseInt(cells[Y_INDEX]));
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Screen update(Input input) {
        // if the player no longer has health, loses game
        if (player.getHealthBar().getHealth() <= 0) {
            return new TextScreen("GAME OVER");
        }

        // if the player is within bounds of exit portals, wins game
        if (player.getRectangle().left() >= 950 && player.getRectangle().bottom() >= 670) {
            return new TextScreen("CONGRATULATIONS");
        }

        // displays background, all entities and player
        background.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        for (Entity entity: this.entities) {
            entity.update(input, entities);
        }
        player.update(input, this.entities);
        player.draw();
        return this;
    }
}
