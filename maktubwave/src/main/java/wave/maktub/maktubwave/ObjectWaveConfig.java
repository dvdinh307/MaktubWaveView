package wave.maktub.maktubwave;

/**
 * Created by Maktub on 3/21/2017.
 */

public class ObjectWaveConfig {
    private float radius;
    private float corner;
    private float direction;
    // Height of wave when up.
    private float up;
    // Height of wave when down.
    private float down;

    // Values need change when view show on large screen.
    private float upLarge;
    private float downLarge;
    private int color;
    // Alpha of wave.
    private int alpha;
    // Using this values when calculator corner with method Sin(values).
    private int valuesSin;

    public ObjectWaveConfig(float radius, float corner, float direction, float up, float down, float upLarge, float downLarge, int color, int alpha, int valuesSin) {
        this.radius = radius;
        this.corner = corner;
        this.direction = direction;
        this.up = up;
        this.down = down;
        this.upLarge = upLarge;
        this.downLarge = downLarge;
        this.color = color;
        this.alpha = alpha;
        this.valuesSin = valuesSin;
    }

    public ObjectWaveConfig() {
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getCorner() {
        return corner;
    }

    public void setCorner(float corner) {
        this.corner = corner;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getUp() {
        return up;
    }

    public void setUp(float up) {
        this.up = up;
    }

    public float getDown() {
        return down;
    }

    public void setDown(float down) {
        this.down = down;
    }

    public float getUpLarge() {
        return upLarge;
    }

    public void setUpLarge(float upLarge) {
        this.upLarge = upLarge;
    }

    public float getDownLarge() {
        return downLarge;
    }

    public void setDownLarge(float downLarge) {
        this.downLarge = downLarge;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getValuesSin() {
        return valuesSin;
    }

    public void setValuesSin(int valuesSin) {
        this.valuesSin = valuesSin;
    }
}
