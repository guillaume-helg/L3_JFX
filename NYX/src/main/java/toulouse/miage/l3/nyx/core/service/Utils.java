package toulouse.miage.l3.nyx.core.service;

import javafx.collections.ObservableList;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.model.Usine;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static toulouse.miage.l3.nyx.core.model.Usine.*;

public class Utils {

    /**
     * Read every line of a file of element, parse these lines into Element,
     * and add them to an ArrayList
     * @return a table of elements read from the file element.csv
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
     * Parse a line in parameter with the format : code, name, quantity, unity, buy price, sell price
     * into a new Element
     *
     * @param line : Here is an example of line : E001;Circuit principal;200;pieces;50;20
     * @return : Return a new Element create with content of this line
     */
    private static Element parseElement(String line) {
        String[] l = line.split(";");
        return new Element(l[0], l[1], Double.parseDouble(l[2]), l[3], Double.parseDouble(l[4]), Double.parseDouble(l[5]));
    }

    /**
     * Write on object Element into a file elements.csv
     * @param e : table with every Element of the application
     */
    public static void writeElement(ObservableList<Element> e) {
        String fileName = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/elements.csv";
        try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));

            for (Element a : e) {
                file.println(a.getCode() + ";"
                              + a.getNom() + ";"
                              + a.getQuantite() + ";"
                              + a.getUniteMesure() + ";"
                              + a.getPrixAchat() + ";"
                              + a.getPrixVente());
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
    }

    /**
     * Checks to see if the item's code is in the elements array
     *
     * @param e
     * @return
     */
    public static boolean isTheCodeInArray(Element e){
        return getElements().contains(e);
    }

    /**
     * Read line of a file named chaines.csv, and transform these line into an object Chaine,
     * add it to an Arraylist and return it
     * @return : ArrayList with Chaine read from the file chaine.csv
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
     * Parse a line in parameter with the format : Code;Nom;Entrée (code,qte);Sortie (code,qte)
     * into a new Chaine
     *
     * @param line : Here is an example of line : C001;Propulsion;(E004,1),(E002,0.5),(E003,0.1);(E005,1)
     * @return : Return a new Chaine create with content of this line
     */
    public static Chaine parseChaine(String line) {
        String[] parts = line.split(";");

        String code = parts[0];
        String name = parts[1];

        HashMap<Element, Double> inputElementList = parseElementList(parts[2]);
        HashMap<Element, Double> outputElementList = parseElementList(parts[3]);

        return new Chaine(code, name, inputElementList, outputElementList);
    }

    /**
     * Parse an input in parameter with the format : (E004,1),(E002,0.5),(E003,0.1)
     * into a Hashmap<Element, Double> and return it
     *
     * Before putting Element into the Hashmap, the function check if the Element exist in
     * the list of Element, if not it print an error
     *
     * @param input : Here is an example of line : (E004,1),(E002,0.5),(E003,0.1)
     * @return Hashmap which contains a list of Element and quantity attributed
     */
    private static HashMap<Element, Double> parseElementList(String input) {
        HashMap<Element, Double> elementMap = new HashMap<>();
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i += 2) {
            String code = elements[i].replaceAll("[(]", "");
            Double value = Double.parseDouble(elements[i + 1].replaceAll("[)]", ""));

            Element existingElement = findElementByCode(Usine.getElements(), code);

            if (existingElement != null) {
                elementMap.put(existingElement, value);
            } else {
                System.err.println("Your Element does not exist");
            }
        }
        return elementMap;
    }

    /**
     * Research, from a list of Element passed in parameter, an element which have the same code
     * also passed in parameter. The function return the Element with the code.
     * @param elements : list of element, in which you can retrieve every Element of the app
     * @param code : unique code used like a primary key for the object Element
     * @return : return the Element which contain this code/primary key
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
     * Write the object Element contains in a table chaines into a file chaine.csv
     * @param chaines : table with every chaine of use by the application
     */
    public static void writeChaine(Chaine[] chaines) {
        String fileName = "chaine.csv";
        try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));

            for (Chaine chaine : chaines) {
                file.println(chaine.getCode() + ";"
                           + chaine.getNom() + ";"
//                           + parseElementListIntoText(chaine.getFormattedListeEntree() + ";"
//                           + parseElementListIntoText(chaine.getFormattedListeSortie() + ";"
                );
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
    }

    /**
     * Create new file, in which you can read every chaine in the arrayList listesCommande
     * The file is in the repertory export
     * It contains : the date of the commande, the content of the command and his quantity
     * and the total price of the command with is percentage of result.
     */
    public static void writeResultInAFile() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = dateTime.format(formatter);

        String nomFichier = Paths.get("NYX", "src", "main", "resources", "toulouse", "miage", "l3", "nyx", "save", "commande", "commande_" + formattedDate + ".txt").toString();


        try {
            PrintWriter fichier = new PrintWriter(new FileWriter(nomFichier));

            fichier.println("Date de la commande : " + LocalDateTime.now());
            fichier.println("Le resultat des commandes est de : " ); // mettre la valeur du résultat des commandes
            fichier.println("La liste des commandes : \n");

            for (Map.Entry<Chaine, Integer> entry : getChainesCommandes()) {
                fichier.println("Chaîne : " + entry.getKey().getCode() + " - " + entry.getKey().getNom());
                fichier.println("Quantité : " + entry.getValue());
                fichier.println("Liste d'éléments d'entrée : " + entry.getKey().getListeElementEntree());
                fichier.println("Liste d'éléments de sortie : " + entry.getKey().getListeElementSortie());
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
    }
}