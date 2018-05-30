package skku.swprac3.modeola;

import skku.swprac3.modeola.flexiblecalendar.entity.Event;

/**
 * @author p-v
 */
public class CustomEvent implements Event {

    private int color;

    public CustomEvent(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
