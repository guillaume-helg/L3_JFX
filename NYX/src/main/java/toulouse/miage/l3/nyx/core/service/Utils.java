package toulouse.miage.l3.nyx.core.service;

import javafx.scene.control.IndexRange;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static toulouse.miage.l3.nyx.core.model.Usine.listesChainesCommandes;
import static toulouse.miage.l3.nyx.core.model.Usine.listesElements;

public class Utils {

    /**
     * Read every line of a file of element, parse these lines into Element,
     * and add them to an ArrayList
     * @return a table of elements
     */
    public static ArrayList<Element> readElement() {
        String fileName = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/elements.csv";
        String line;
        ArrayList<Element> element = new ArrayList<>();

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));

            while ((line = file.readLine()) != null) {
                element.add(parseElement(line));
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("Acces problem");
        }
        return element;
    }

    /**
     *
     * @param line
     * @return
     */
    private static Element parseElement(String line) {
        String[] l = line.split(";");
        return new Element(l[0], l[1], Double.parseDouble(l[2]), l[3], Double.parseDouble(l[4]), Double.parseDouble(l[5]));
    }

    /**
     *
     */
    public static void writeElement(Element[] e) {
        String nomFichier = "element.csv";
        try {
            PrintWriter fichier = new PrintWriter(new FileWriter(nomFichier));

            for (Element a : e) {
                fichier.println(a);
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès au fichier");
        }
    }

    /**
     *
     * @return
     */
    public static ArrayList<Chaine> readChaine() {
        String nomFichier = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/chaines.csv";
        String ligne;
        ArrayList<Chaine> chaines = new ArrayList<>();

        try {
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier));

            while ((ligne = fichier.readLine()) != null) {
                chaines.add(parseChaine(ligne));
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès fichier");
        }
        return chaines;
    }

    /**
     * Code;Nom;Entree (code,qte);Sortie (code,qte)
     * @param input
     * @return
     */
    public static Chaine parseChaine(String input) {
        String[] parts = input.split(";");

        String code = parts[0];
        String nom = parts[1];

        HashMap<Element, Double> listeElementEntree = parseElementList(parts[2]);
        HashMap<Element, Double> listeElementSortie = parseElementList(parts[3]);

        return new Chaine(code, nom, listeElementEntree, listeElementSortie);
    }

    /**
     *
     * @param input
     * @return
     */
    private static HashMap<Element, Double> parseElementList(String input) {
        HashMap<Element, Double> elementMap = new HashMap<>();
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i += 2) {
            String code = elements[i].replaceAll("[(]", "");
            Double value = Double.parseDouble(elements[i + 1].replaceAll("[)]", ""));

            Element existingElement = findElementByCode(listesElements, code);

            if (existingElement != null) {
                elementMap.put(existingElement, value);
            } else {
                System.err.println("erreur element pas existant");
            }
        }
        return elementMap;
    }

    /**
     *
     * @param elements
     * @param code
     * @return
     */
    private static Element findElementByCode(List<Element> elements, String code) {
        for (Element element : elements) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        return null;
    }

    /**
     *
     * @param c
     */
    public static void writeChaine(Chaine[] c) {
        String fileName = "element.csv";
        try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));

            for (Chaine a : c) {
                file.println(a);
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
    }

    public static void writeResultInAFile() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = dateTime.format(formatter);

        String nomFichier = "NYX/src/main/resources/toulouse/miage/l3/nyx/export/Commandetest_" + formattedDate + ".txt";

        try {
            PrintWriter fichier = new PrintWriter(new FileWriter(nomFichier));

            fichier.println("Date de la commande : " + LocalDateTime.now());

            fichier.println("Le resultat des commandes est de : " ); // mettre la valeur du résultat des commandes

            fichier.println("La liste des commandes : \n");

            for (Map.Entry<Chaine, Integer> entry : listesChainesCommandes) {
                fichier.println("Chaîne : " + entry.getKey().getCode() + " - " + entry.getKey().getNom());
                fichier.println("Quantité : " + entry.getValue());
                fichier.println("Liste d'éléments d'entrée : " + entry.getKey().getListeElementEntree());
                fichier.println("Liste d'éléments de sortie : " + entry.getKey().getListeElementSortie());
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès au fichier");
        }
    }
}