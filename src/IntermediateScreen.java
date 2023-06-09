import bagel.Input;
import bagel.Keys;
import bagel.Window;

public class IntermediateScreen extends Screen {
    private long intialisationTime;

    public IntermediateScreen() {
        this.intialisationTime = System.currentTimeMillis();
    }

    private static final String[] lines = {"PRESS SPACE TO START",
            "PRESS A TO ATTACK",
            "DEFEAT NAVEC TO WIN"};

    @Override
    public Screen update(Input input) {
        if (System.currentTimeMillis() - this.intialisationTime > 3000) {
            SUBTITLE_FONT.drawString(lines[0], 350, 350);
            SUBTITLE_FONT.drawString(lines[1],
                    (double) (Window.getWidth() / 2) - SUBTITLE_FONT.getWidth(lines[1]) / 2.0,
                    350 + 45);
            SUBTITLE_FONT.drawString(lines[2],
                    (double) (Window.getWidth() / 2) - SUBTITLE_FONT.getWidth(lines[2]) / 2.0,
                    350 + 2 * 45);
        } else {
        if (input.wasPressed(Keys.SPACE)) {
            return new Level(1);
        }
        return this;
    }
}
