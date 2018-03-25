package models;

import java.util.List;

public class FaceSearchResponse {
    public DetectedFace detectedFace;
    public List<Object> matchedFaces = null;

    public FaceSearchResponse(DetectedFace detectedFace, List<Object> matchedFaces) {
        this.detectedFace = detectedFace;
        this.matchedFaces = matchedFaces;
    }

    @Override
    public String toString() {
        return "FaceSearchResponse{" +
                "detectedFace=" + detectedFace +
                ", matchedFaces=" + matchedFaces +
                '}';
    }

    public FaceSearchResponse() {
    }

    public DetectedFace getDetectedFace() {

        return detectedFace;
    }

    public void setDetectedFace(DetectedFace detectedFace) {
        this.detectedFace = detectedFace;
    }

    public List<Object> getMatchedFaces() {
        return matchedFaces;
    }

    public void setMatchedFaces(List<Object> matchedFaces) {
        this.matchedFaces = matchedFaces;
    }

}
