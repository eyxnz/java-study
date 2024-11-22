package prob05;

public class Rectangle extends Shape implements Resizable {
    public Rectangle(int width, int height) {
        super(width, height);
    }

    @Override
    public double getArea() {
        return super.getWidth() * super.getHeight();
    }

    @Override
    public double getPerimeter() {
        return (super.getWidth() + super.getHeight()) * 2;
    }

    @Override
    public void resize(double rate) {
        super.setWidth(getWidth() * rate);
        super.setHeight(getHeight() * rate);
    }
}
