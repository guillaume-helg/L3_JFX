package toulouse.miage.l3.nyx.core.utils;

import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Commande;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static toulouse.miage.l3.nyx.core.model.Usine.*;

public class UtilsCommande {
    /**
     * Create new file, in which you can read every chaine in the arrayList listesCommande
     * The file is in the repertory export
     * It contains : the date of the commande, the content of the command and his quantity
     * and the total price of the command with is percentage of result.
     */
    public static boolean writeResultInAFile() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDate = dateTime.format(formatter);

        String nomFichier = Paths.get("NYX", "src", "main", "resources", "toulouse", "miage", "l3", "nyx", "save", "commande", "commande_" + formattedDate + ".txt").toString();

        String separator = "\n--------------------------------------------------------------------\n";
        try {
            PrintWriter fichier = new PrintWriter(new FileWriter(nomFichier));

            fichier.println("Date de la commande -> " + LocalDateTime.now());
            fichier.println(separator);
            fichier.println("L'incateur de valeur est égal à -> " + calculRentabiliteProduction() + "€");
            fichier.println(separator);
            fichier.println("La liste des commandes \n");

            for (Commande c : getCommandes()) {
                if (c.getFeasible()) {
                    fichier.println("\tChaîne : " + c.getChaine().getCode() + " - " + c.getChaine().getNom());
                    fichier.println("\tQuantité : " + c.getQuantity());
                    fichier.println("\tListe d'éléments d'entrée : " + c.getChaine().getFormattedListeEntree());
                    fichier.println("\tListe d'éléments de sortie : " + c.getChaine().getFormattedListeSortie());
                    fichier.println("\n");
                } else {
                    fichier.println("############ ! Pas Faisable ! ############");
                    fichier.println("\tChaîne : " + c.getChaine().getCode() + " - " + c.getChaine().getNom());
                    fichier.println("\tQuantité : " + c.getQuantity());
                    fichier.println("\tListe d'éléments d'entrée : " + c.getChaine().getFormattedListeEntree());
                    fichier.println("\tListe d'éléments de sortie : " + c.getChaine().getFormattedListeSortie());
                    fichier.println("############ ! Pas Faisable ! ############");
                    fichier.println("\n");
                }
            }

            fichier.println(separator);
            fichier.close();
        } catch (IOException ex) {
            System.err.println("File access problem");
            return false;
        }
        return true;
    }

    /**
     * Parse the Map in parameter into a new object Commande in the Usine listeCommande
     * @param listeCommande, Hashmap of llll
     */
    public static void parseHashmapToCommand(Map<Chaine, Integer> listeCommande) {
        for (Map.Entry<Chaine, Integer> entry : listeCommande.entrySet()) {
            addToCommandes(new Commande(entry.getKey(), entry.getValue()));
        }
    }


    /**
     * Compute the price of the command
     * @return total price of the command
     */
    public static double calculRentabiliteProduction() {
        double prixTotal = 0;
        for(Commande c : getCommandes()) {
            for (Map.Entry<Element, Double> element : c.getChaine().getListeElementEntree().entrySet()) {
                prixTotal -= element.getKey().getPrixVente() * element.getValue() * c.getQuantity();
            }
            for (Map.Entry<Element, Double> element : c.getChaine().getListeElementSortie().entrySet()) {
                prixTotal += element.getKey().getPrixVente() * element.getValue() * c.getQuantity();
            }
        }
        return prixTotal;
    }

    /**
     * if the user finish is command the export it, this function modify the current stock of Element
     * used to build the command
     */
    public static void placeOrder() {
        for(Commande c : getCommandes()) {
            if (c.getFeasible()) {
                for (Map.Entry<Element, Double> currentElement : c.getChaine().getListeElementEntree().entrySet()) {
                    Element element = currentElement.getKey();
                    if (getElements().contains(element)) {
                        int index = getElements().indexOf(element);
                        getElements().get(index).setQuantite(element.getQuantite()-(currentElement.getValue() * c.getQuantity()));
                    }
                }
                for (Map.Entry<Element, Double> currentElement : c.getChaine().getListeElementSortie().entrySet()) {
                    Element element = currentElement.getKey();
                    if (getElements().contains(element)) {
                        int index = getElements().indexOf(element);
                        getElements().get(index).setQuantite(element.getQuantite()+(currentElement.getValue() * c.getQuantity()));
                    }
                }
            }
        }
    }

    /**
     * Count the number of command feasible
     * @return string of a fraction of valid command on the total of command
     */
    public static String getNbOrder() {
        int countFeasible = 0;
        int countInfeasible = 0;
        for (Commande c : getCommandes()) {
            if (c.getFeasible()) {
                countFeasible += c.getQuantity();
            } else {
                countInfeasible += c.getQuantity();
            }
        }
        return countFeasible + "/" + countInfeasible;
    }


    /**
     * Create a string of the element used to produce the command
     * TODO : si j'ai le temps
     * @return a list of element used to build the command with their quantity
     */
    public static String getUsedElement() {
        HashMap<Element, Integer> d = new HashMap<>();

        String str = "";

        for (Commande c : getCommandes()) {
            if (c.getFeasible()) {
               // d. c.getChaine().getListeElementEntree();
                       c.getQuantity();
            }
        }

        System.out.println(str);
        return str;
    }

    /**
     * Estimation of the time to produce a command
     */
    public static void costTimeProduction() {

    }
}
