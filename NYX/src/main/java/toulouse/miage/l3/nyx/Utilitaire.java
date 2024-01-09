package toulouse.miage.l3.nyx;

import java.io.*;
import java.util.ArrayList;

public class Utilitaire {

    private static ArrayList<Element> elements;
    private static ArrayList<Chaine> chaines;

    public static int compterElements(Element e) {
        return elements.size();
    }

    public static void lireElements() {
        String nomFichier = "./elements.csv";
        String ligne;

        try {
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier));

            while ((ligne = fichier.readLine()) != null) {
                elements.add(convertirLigneEnElement(ligne));
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès fichier");
        }
    }

    private static Element convertirLigneEnElement(String ligne) {
        String[] l = ligne.split(";");
        for(String s : l) s = s.replaceAll("[^\\w+]", "");
        return new Element(l[0], Double.parseDouble(l[1]), Double.parseDouble(l[2]), Integer.parseInt(l[3]));
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

    public static void lireChaine() {
        String nomFichier = "./elements.csv";
        String ligne;

        try {
            BufferedReader fichier = new BufferedReader(new FileReader(nomFichier));

            while ((ligne = fichier.readLine()) != null) {
                chaines.add(convertirLigneEnChaine(ligne));
            }

            fichier.close();
        } catch (IOException ex) {
            System.out.println("Problème d'accès fichier");
        }
    }

    private static Chaine convertirLigneEnChaine(String ligne) {
        return null;
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
