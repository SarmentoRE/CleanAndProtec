package models;

public class Quality {
    public Double brightness;

    @Override
    public String toString() {
        return "Quality{" +
                "brightness=" + brightness +
                ", sharpness=" + sharpness +
                '}';
    }

    public Double getBrightness() {
        return brightness;
    }

    public void setBrightness(Double brightness) {
        this.brightness = brightness;
    }

    public Double getSharpness() {
        return sharpness;
    }

    public void setSharpness(Double sharpness) {
        this.sharpness = sharpness;
    }

    public Quality() {

    }

    public Quality(Double brightness, Double sharpness) {

        this.brightness = brightness;
        this.sharpness = sharpness;
    }

    public Double sharpness;

}
