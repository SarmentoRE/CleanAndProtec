package models;

public class Pose {
    public Double pitch;
    public Double roll;

    public Pose(Double pitch, Double roll, Double yaw) {
        this.pitch = pitch;
        this.roll = roll;
        this.yaw = yaw;
    }

    @Override
    public String toString() {
        return "Pose{" +
                "pitch=" + pitch +
                ", roll=" + roll +
                ", yaw=" + yaw +
                '}';
    }

    public Pose() {
    }

    public Double getPitch() {

        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

    public Double getYaw() {
        return yaw;
    }

    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

    public Double yaw;
}
