package models;

public class BoundingBox {
    public Double height;
    public Double width;
    public Double left;
    public Double top;

    public BoundingBox(Double height, Double width, Double left, Double top) {
        this.height = height;
        this.width = width;
        this.left = left;
        this.top = top;
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "height=" + height +
                ", width=" + width +
                ", left=" + left +
                ", top=" + top +
                '}';
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public BoundingBox() {

    }

}
