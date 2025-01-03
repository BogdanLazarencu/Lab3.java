import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Magazin {
    public static void main(String[] args) {
        List<Produs> produse = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader("src/produse.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String denumire = parts[0].trim();
                double pret = Double.parseDouble(parts[1].trim());
                int cantitate = Integer.parseInt(parts[2].trim());
                LocalDate dataExpirare = LocalDate.parse(parts[3].trim(), formatter);
                produse.add(new Produs(denumire, pret, cantitate, dataExpirare));
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMeniu:");
            System.out.println("1. Afișare produse");
            System.out.println("2. Afișare produse expirate");
            System.out.println("3. Vânzare produs");
            System.out.println("4. Afișare produse cu preț minim");
            System.out.println("5. Salvare produse cu cantitate mică în fișier");
            System.out.println("6. Ieșire");
            System.out.print("Alegeți o opțiune: ");

            int optiune = scanner.nextInt();
            scanner.nextLine(); // Consumă linia rămasă

            switch (optiune) {
                case 1:
                    produse.forEach(System.out::println);
                    break;
                case 2:
                    produse.stream()
                            .filter(p -> p.getDataExpirare().isBefore(LocalDate.now()))
                            .forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Introduceți denumirea produsului: ");
                    String denumire = scanner.nextLine();
                    System.out.print("Introduceți cantitatea de vândut: ");
                    int cantitateVanduta = scanner.nextInt();

                    Optional<Produs> produsOptional = produse.stream()
                            .filter(p -> p.getDenumire().equalsIgnoreCase(denumire))
                            .findFirst();

                    if (produsOptional.isPresent()) {
                        Produs produs = produsOptional.get();
                        if (produs.getCantitate() >= cantitateVanduta) {
                            produs.setCantitate(produs.getCantitate() - cantitateVanduta);
                            Produs.adaugaIncasari(produs.getPret() * cantitateVanduta);
                            System.out.println("Produs vândut cu succes!");
                            if (produs.getCantitate() == 0) {
                                produse.remove(produs);
                            }
                        } else {
                            System.out.println("Cantitate insuficientă pe stoc!");
                        }
                    } else {
                        System.out.println("Produsul nu există!");
                    }
                    break;
                case 4:
                    double pretMinim = produse.stream().mapToDouble(Produs::getPret).min().orElse(Double.NaN);
                    produse.stream().filter(p -> p.getPret() == pretMinim).forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Introduceți pragul de cantitate: ");
                    int prag = scanner.nextInt();
                    System.out.print("Introduceți numele fișierului de ieșire: ");
                    scanner.nextLine();
                    String fisierIesire = scanner.nextLine();

                    try (PrintWriter pw = new PrintWriter(new FileWriter(fisierIesire))) {
                        produse.stream().filter(p -> p.getCantitate() < prag).forEach(pw::println);
                        System.out.println("Produsele au fost salvate în fișier.");
                    } catch (IOException e) {
                        System.out.println("Eroare la scrierea fișierului: " + e.getMessage());
                    }
                    break;
                case 6:
                    running = false;
                    System.out.println("Program încheiat. Încasări totale: " + Produs.getIncasari());
                    break;
                default:
                    System.out.println("Opțiune invalidă!");
            }
        }

        scanner.close();
    }
}