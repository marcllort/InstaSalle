package Json;


public class Point {

    private double x;
    private double y;


    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x1) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double calcularDistanciaDesde(Point point) {
        double catet1 = x - point.getX();
        double catet2 = y - point.getY();
        double hipotenusa = Math.sqrt(catet1 * catet1 + catet2 * catet2);

        return hipotenusa;
    }

    public String toString() {
        return ("x = " + getX() + ", y= " + getY());
    }

}