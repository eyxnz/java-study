package prob05;

public class RectTriangle extends Shape implements Resizable {
    public RectTriangle(int width, int height) {
        super(width, height);
    }

    @Override
    public double getArea() {
        return super.getWidth() * super.getHeight() * 0.5;
    }

    @Override
    public double getPerimeter() {
        return super.getWidth() + super.getHeight() + Math.sqrt(super.getWidth() * super.getWidth() + super.getHeight() * super.getHeight());
    }

    @Override
    public void resize(double rate) {

    }
}
