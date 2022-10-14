import bagel.*;
import bagel.util.*;

/**
 * The first screen when the app is launched
 * Shows title and basic instructions
 */
public class StartScreen extends Screen {
    final String[] lines = {"SHADOW DIMENSION", "PRESS SPACE TO START", "USE ARROW KEYS TO FIND GATE"};
    final double X_0_POSITION = 260;
    final double Y_0_POSITION = 250;
    final double X_1_POSITION = X_0_POSITION + 90;
    final double Y_1_POSITION = Y_0_POSITION + 190;
    final double X_2_POSITION = X_1_POSITION + (SUBTITLE_FONT.getWidth(lines[1]) - SUBTITLE_FONT.getWidth(lines[2])) / 2;
    final double Y_2_POSITION = Y_1_POSITION + SUBTITLE_FONT_SIZE + 10;


    /**
     * draws the titles and checks if the player starts game
     * @param input input from the used
     * @return returns either itself or the first level of game
     */
    public Screen update(Input input) {
        STANDARD_FONT.drawString(lines[0], X_0_POSITION, Y_0_POSITION);
        SUBTITLE_FONT.drawString(lines[1], X_1_POSITION, Y_1_POSITION);
        SUBTITLE_FONT.drawString(lines[2], X_2_POSITION, Y_2_POSITION);
        if (input.wasPressed(Keys.SPACE)) {
            return new Level(0);
        }
        return this;
    }
}
