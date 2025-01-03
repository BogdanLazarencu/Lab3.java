import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Produs {
    private String denumire;
    private double pret;
    private int cantitate;
    private LocalDate dataExpirare;
    private static double incasari = 0;

    public Produs(String denumire, double pret, int cantitate, LocalDate dataExpirare) {
        this.denumire = denumire;
        this.pret = pret;
        this.cantitate = cantitate;
        this.dataExpirare = dataExpirare;
    }

    public String getDenumire() {
        return denumire;
    }

    public double getPret() {
        return pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public LocalDate getDataExpirare() {
        return dataExpirare;
    }

    public static double getIncasari() {
        return incasari;
    }

    public static void adaugaIncasari(double suma) {
        incasari += suma;
    }

    @Override
    public String toString() {
        return String.format("%s, %.2f, %d, %s", denumire, pret, cantitate, dataExpirare);
    }
}



