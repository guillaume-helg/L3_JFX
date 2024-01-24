package toulouse.miage.l3.nyx.core.service;

import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Utilitaire {

    private static ArrayList<Element> elements;


    public static int compterElements(Element e) {
        return elements.size();
    }

    /**
     * @return
     */
    public static ArrayList<Element> lireElements() {
        String nomFichier = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/elements.csv";
        String ligne;
        ArrayList<Element> element = new ArrayList<>();

        try {
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier));

            while ((ligne = fichier.readLine()) != null) {
                element.add(convertirLigneEnElement(ligne));
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès fichier");
        }
        return element;
    }

    private static Element convertirLigneEnElement(String ligne) {
        String[] l = ligne.split(";");
        return new Element(l[0], l[1], Double.parseDouble(l[2]), l[3], Double.parseDouble(l[4]), Double.parseDouble(l[5]));
    }

    /**
     *
     */
    public static void ecritureElement(Element[] e) {
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

    public static ArrayList<Chaine> lireChaine() {
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

    //Code;Nom;Entree (code,qte);Sortie (code,qte)
    //
    public static Chaine parseChaine(String input) {
        String[] parts = input.split(";");

        String code = parts[0];
        String nom = parts[1];

        HashMap<Element, Double> listeElementEntree = parseElementList(parts[2]);
        HashMap<Element, Double> listeElementSortie = parseElementList(parts[3]);

        return new Chaine(code, nom, listeElementEntree, listeElementSortie);
    }

    private static HashMap<Element, Double> parseElementList(String input) {
        HashMap<Element, Double> elementMap = new HashMap<>();
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i+=2) {
            Element e = new Element(elements[i].replaceAll("[(]", ""));
            Double value = Double.parseDouble(elements[i+1].replaceAll("[)]", ""));
            elementMap.put(e, value);
        }
        return elementMap;
    }


    public static void ecrireChaine(Chaine[] c) {
        String nomFichier = "element.csv";
        try {
            PrintWriter fichier = new PrintWriter(new FileWriter(nomFichier));

            for (Chaine a : c) {
                fichier.println(a);
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès au fichier");
        }
    }
}
