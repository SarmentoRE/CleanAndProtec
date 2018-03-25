package models;


import java.util.List;

public class DetectedFace {
    public BoundingBox boundingBox;
    public Double confidence;
    public List<Landmark> landmarks = null;
    public Pose pose;
    public Quality quality;

    public DetectedFace(BoundingBox boundingBox, Double confidence, List<Landmark> landmarks, Pose pose, Quality quality) {
        this.boundingBox = boundingBox;
        this.confidence = confidence;
        this.landmarks = landmarks;
        this.pose = pose;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "DetectedFace{" +
                "boundingBox=" + boundingBox +
                ", confidence=" + confidence +
                ", landmarks=" + landmarks +
                ", pose=" + pose +
                ", quality=" + quality +
                '}';
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(List<Landmark> landmarks) {
        this.landmarks = landmarks;
    }

    public Pose getPose() {
        return pose;
    }

    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public DetectedFace() {

    }
}
