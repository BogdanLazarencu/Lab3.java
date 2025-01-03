import java.io.*;
import java.util.*;

class Parabola {
    private int a, b, c;
    public Parabola(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double[] getvarf() {
        double x = -b / (2.0 * a);
        double y = (a * x * x) + (b * x) + c;
        return new double[]{x, y};
    }
    @Override
    public String toString() {
        return "f(x) = " + a + "x^2 + " + b + "x + " + c;
    }

    public double[] getMidpoint(Parabola other) {
        double[] thisVertex = this.getvarf();
        double[] otherVertex = other.getvarf();
        double midX = (thisVertex[0] + otherVertex[0]) / 2;
        double midY = (thisVertex[1] + otherVertex[1]) / 2;
        return new double[]{midX, midY};
    }

    public static double[] getMidpoint(Parabola p1, Parabola p2) {
        double[] vertex1 = p1.getvarf();
        double[] vertex2 = p2.getvarf();
        double midX = (vertex1[0] + vertex2[0]) / 2;
        double midY = (vertex1[1] + vertex2[1]) / 2;
        return new double[]{midX, midY};
    }

    public double getSegmentLength(Parabola other) {
        double[] thisVertex = this.getvarf();
        double[] otherVertex = other.getvarf();
        return Math.hypot(thisVertex[0] - otherVertex[0], thisVertex[1] - otherVertex[1]);
    }


    public static double getSegmentLength(Parabola p1, Parabola p2) {
        double[] vertex1 = p1.getvarf();
        double[] vertex2 = p2.getvarf();
        return Math.hypot(vertex1[0] - vertex2[0], vertex1[1] - vertex2[1]);
    }

    public static void main(String[] args) {
        List<Parabola> parabolas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/in.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coefficients = line.split(" ");
                int a = Integer.parseInt(coefficients[0]);
                int b = Integer.parseInt(coefficients[1]);
                int c = Integer.parseInt(coefficients[2]);
                parabolas.add(new Parabola(a, b, c));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }


        for (Parabola p : parabolas) {
            System.out.println(p);
            double[] vertex = p.getvarf();
            System.out.printf("Varf: (%.2f, %.2f)%n", vertex[0], vertex[1]);
        }

        if (parabolas.size() >= 2) {
            Parabola p1 = parabolas.get(0);
            Parabola p2 = parabolas.get(1);

            double[] midpoint = Parabola.getMidpoint(p1, p2);
            System.out.printf("Midpoint: (%.2f, %.2f)%n", midpoint[0], midpoint[1]);

            double length = Parabola.getSegmentLength(p1, p2);
            System.out.printf("Length of segment: %.2f%n", length);
        }
    }
}
