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
                    fichier.println("\tChaîne : " + c.getChaine().getCode() + " - " + c.getChaine().getNom());
                    fichier.println("\tQuantité : " + c.getQuantity());
                    fichier.println("\tListe d'éléments d'entrée : " + c.getChaine().getListeElementEntree());
                    fichier.println("\tListe d'éléments de sortie : " + c.getChaine().getListeElementSortie());
                    fichier.println("\t" + c.getFeasible());
                    fichier.println("\n");
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
     *
     */
    public static void parseHashmapToCommand(Map<Chaine, Integer> listeCommande) {
        for (Map.Entry<Chaine, Integer> entry : listeCommande.entrySet()) {
            addToCommandes(new Commande(entry.getKey(), entry.getValue()));
        }
    }


    /**
     *
     */
    public static double calculRentabiliteProduction() {
        double prixTotal = 0;
        for(Commande c : getCommandes()) {
            for (Map.Entry<Element, Double> element : c.getChaine().getListeElementEntreeH().entrySet()) {
                prixTotal -= element.getKey().getPrixVente() * element.getValue() * c.getQuantity();
            }
            for (Map.Entry<Element, Double> element : c.getChaine().getListeElementSortieH().entrySet()) {
                prixTotal += element.getKey().getPrixVente() * element.getValue() * c.getQuantity();
            }
        }
        return prixTotal;
    }

    /**
     *
     */
    public static void placeOrder() {
        for(Commande c : getCommandes()) {
            if (c.getFeasible()) {
                for (Map.Entry<Element, Double> currentElement : c.getChaine().getListeElementEntreeH().entrySet()) {
                    Element element = currentElement.getKey();
                    if (getElements().contains(element)) {
                        int index = getElements().indexOf(element);
                        getElements().get(index).setQuantite(element.getQuantite()-(currentElement.getValue() * c.getQuantity()));
                    }
                }
                for (Map.Entry<Element, Double> currentElement : c.getChaine().getListeElementSortieH().entrySet()) {
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
     *
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
     * Compute the time to produce the order
     */
    public static double timeProduction() {
        double timeProduction = 0;

        // On récupère tout les éléments nécéssaires à produire la commande dans une Hashmap
        Map<Element, Double> elementNeeded = new HashMap<>();
        for (Commande c : getCommandes()) {
            timeProduction += c.getChaine().getProductionTime();
            Map<Element, Double> elementsFromCommande = c.getChaine().getListeElementEntreeH();
            for (Map.Entry<Element, Double> entry : elementsFromCommande.entrySet()) {
                elementNeeded.merge(entry.getKey(), entry.getValue() * c.getQuantity(), Double::sum);
            }
        }

        // On fait l'algo de calcul de temps
        for(Map.Entry<Element, Double> ele : elementNeeded.entrySet()) {
            if (ele.getKey().getQuantite() < ele.getValue()) {
                timeProduction += (ele.getValue() - ele.getKey().getQuantite()) * ele.getKey().getDeliveryDuration();
            }
        }

        System.out.println("Production time : " + timeProduction);

        return timeProduction;
    }
}