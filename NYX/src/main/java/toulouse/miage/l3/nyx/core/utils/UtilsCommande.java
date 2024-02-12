package toulouse.miage.l3.nyx.core.utils;

import toulouse.miage.l3.nyx.core.model.Chaine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static toulouse.miage.l3.nyx.core.model.Usine.getChainesCommandes;

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
            System.err.println("File access problem");
            return false;
        }
        return true;
    }
}
