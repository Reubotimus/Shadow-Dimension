import bagel.*;

public class TextScreen extends Screen{
    private final String message;
    public TextScreen(String message) {
        this.message = message;
    }
    public Screen update(Input input) {
        STANDARD_FONT.drawString(message,
                (double)(Window.getWidth() / 2) - STANDARD_FONT.getWidth(message) / 2.0,
                (double)(Window.getHeight() / 2) + STANDARD_FONT_SIZE / 2.0);
        return this;
    }
}
