import bagel.*;
import bagel.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Represents the interactive game part of application
 * includes a list of entities read in from a csv file
 */
public class Level extends Screen {
    public Player player;
    public Navec navec = null;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public static Image background;

    /**
     * creates a new level using a csv file
     * @param levelNum an int representing the level number,
     *                 number must match the name of the csv file for the level
     */
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

                if (cells[TYPE_INDEX].equals("Demon")) {
                    this.entities.add(new Demon(
                            Integer.parseInt(cells[X_INDEX]),
                            Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("Tree")) {
                    this.entities.add(new Tree(
                            Integer.parseInt(cells[X_INDEX]),
                            Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("Navec")) {
                    this.navec = new Navec(
                            Integer.parseInt(cells[X_INDEX]),
                            Integer.parseInt(cells[Y_INDEX]));
                    this.entities.add(this.navec);
                    continue;
                }


                if (cells[TYPE_INDEX].equals("TopLeft")) {
                    Entity.setTopLeft(new Point(Integer.parseInt(cells[X_INDEX]), Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("BottomRight")) {
                    Entity.setBottomRight(new Point(Integer.parseInt(cells[X_INDEX]), Integer.parseInt(cells[Y_INDEX])));
                    continue;
                }

                if (cells[TYPE_INDEX].equals("Fae")) {
                    this.player = new Player(Integer.parseInt(cells[X_INDEX]),
                            Integer.parseInt(cells[Y_INDEX]));
                    this.entities.add(0, this.player);
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * updates the level by checking for level commands and updating all entities
     * @param input the user input
     * @return returns the screen for the next frame
     */
    public Screen update(Input input) {

        if (input.wasPressed(Keys.W)) {
            return new Level(1);
        }

        if (input.wasPressed(Keys.L)) {
            Entity.increaseTimescale();
        }

        if (input.wasPressed(Keys.K)) {
            Entity.decreaseTimescale();
        }

        // if the player no longer has health, loses game
        if (player.getHealthBar().getHealth() <= 0) {
            return new TextScreen("GAME OVER");
        }

        // if the player is within bounds of exit portals, wins game
        if (this.navec == null && player.getRectangle().left() >= 950 && player.getRectangle().top() >= 670) {
            return new IntermediateScreen();
        }

        if (this.navec != null && navec.healthBar.getHealth() == 0) {
            return new TextScreen("Congratulations!");
        }

        // displays background, all entities and player
        background.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) != null) {
                entities.get(i).update(input, entities);
            }
        }
        return this;
    }
}
