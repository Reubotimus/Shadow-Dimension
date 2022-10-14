import bagel.Font;
import bagel.Input;

/**
 * represents all screens in application
 */
public abstract class Screen {
    // font is used to display text in game
    protected final int STANDARD_FONT_SIZE = 75;
    protected final int SUBTITLE_FONT_SIZE = 40;
    protected final Font STANDARD_FONT = new Font("res/frostbite.ttf", STANDARD_FONT_SIZE);
    protected final Font SUBTITLE_FONT = new Font("res/frostbite.ttf", SUBTITLE_FONT_SIZE);
    public abstract Screen update(Input input);
}
