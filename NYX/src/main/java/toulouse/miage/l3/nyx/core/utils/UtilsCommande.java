package toulouse.miage.l3.nyx.core.utils;

import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Commande;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static toulouse.miage.l3.nyx.core.model.Usine.*;
import static toulouse.miage.l3.nyx.core.utils.UtilsChaine.getChaineByName;

/**
 * Util that enable function to write, read or compute data related to Command
 * @author Guillaume Helg
 * @version 1.0
 */
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

        String separator = "\n--------------------------------------------------------------------\n";
        try {
            // Creating destination directory in the user's Downloads directory
            Path destinationDir = Paths.get(System.getProperty("user.home"), "Downloads", "CommandesNYX");
            Files.createDirectories(destinationDir);

            // Creating destination file path
            Path destinationPath = destinationDir.resolve("commande_" + formattedDate + ".txt");

            // Creating file object
            File fichier = destinationPath.toFile();

            // Writing to the file
            PrintWriter writer = new PrintWriter(new FileWriter(fichier));

            writer.println("Date de la commande -> " + dateTime);
            writer.println(separator);
            writer.println("L'incateur de valeur est égal à -> " + calculRentabiliteProduction() + "€");
            writer.println(separator);
            writer.println("L'indication du temps de production : " + timeEstimation());
            writer.println(separator);
            writer.println("La liste des commandes \n");

            for (Commande c : getCommandes()) {
                writer.println(getCommandeDetails(c));
                writer.println("\n");
            }

            writer.println(separator);
            writer.close();

        } catch (IOException ex) {
            System.err.println("File access problem");
            return false;
        }
        return true;
    }

    private static String getCommandeDetails(Commande c) {
        StringBuilder sb = new StringBuilder();
        if (c.getFeasible()) {
            sb.append("\tChaîne : ").append(c.getChaine().getCode()).append(" - ").append(c.getChaine().getNom()).append("\n");
            sb.append("\tQuantité : ").append(c.getQuantity()).append("\n");
            sb.append("\tListe d'éléments d'entrée : ").append(c.getChaine().getFormattedListeEntree()).append("\n");
            sb.append("\tListe d'éléments de sortie : ").append(c.getChaine().getFormattedListeSortie()).append("\n");
        } else {
            sb.append("############ ! Pas Faisable ! ############\n");
            sb.append("\tChaîne : ").append(c.getChaine().getCode()).append(" - ").append(c.getChaine().getNom()).append("\n");
            sb.append("\tQuantité : ").append(c.getQuantity()).append("\n");
            sb.append("\tListe d'éléments d'entrée : ").append(c.getChaine().getFormattedListeEntree()).append("\n");
            sb.append("\tListe d'éléments de sortie : ").append(c.getChaine().getFormattedListeSortie()).append("\n");
            sb.append("############ ! Pas Faisable ! ############\n");
        }
        return sb.toString();
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
     * @return a list of element used to build the command with their quantity
     */
    public static String getUsedElement() {
        HashMap<Element, Integer> d = new HashMap<>();

        String str = "";

        for (Commande c : getCommandes()) {
            if (c.getFeasible()) {
               // d. c.getChaine().getListeElementEntree();
                       c.getQuantity();
            } else {

            }
        }

        System.out.println(str);
        return str;
    }

    /**
     * Estimation of the time to produce a command
     * @return Estimation of time
     */
    public static int timeEstimation() {
        int totalTime = 0;
        for(Commande c : getCommandes()) {
            if (c.getFeasible()) {
                totalTime += c.getChaine().getTime() * c.getQuantity();
            } else {
                /* Chaine chaine = c.getChaine();
                HashMap<Element, Double> elementsEntree = chaine.getListeElementEntree();
                for (Map.Entry<Element, Double> entry : elementsEntree.entrySet()) {
                    Element element = entry.getKey();
                    double quantityNeeded = entry.getValue();

                    if (quantityNeeded * c.getQuantity() < element.getQuantite()) {
                        Chaine ch = getChaineByName(getChaine(), element.getNom());
                        if(ch.getTime() == 0) {
                            totalTime += ch.getTime();
                        }
                    }
                }

                 */
                System.out.println("Un peu long");
            }
        }
        return totalTime;
    }
}
