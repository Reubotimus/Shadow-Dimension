import bagel.*;

/**
 * represents any screens with a single centered line of text
 */
public class TextScreen extends Screen{
    private final String message;

    /**
     * creates a new textscreen
     * @param message the single centered line of text that will appear
     */
    public TextScreen(String message) {
        this.message = message;
    }

    /**
     * updates the screen by drawing the text
     * @param input the used input
     * @return returns an instance of itself
     */
    public Screen update(Input input) {
        STANDARD_FONT.drawString(message,
                (double)(Window.getWidth() / 2) - STANDARD_FONT.getWidth(message) / 2.0,
                (double)(Window.getHeight() / 2) + STANDARD_FONT_SIZE / 2.0);
        return this;
    }
}
