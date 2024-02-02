package toulouse.miage.l3.nyx.core.service;

import toulouse.miage.l3.nyx.core.model.Chaine;

import java.util.Map;

import static toulouse.miage.l3.nyx.core.model.Usine.listeCommande;

public class LoggerTmp {

    public static void afficherListeCommande() {
        for (Map.Entry<Chaine, Integer> entry : listeCommande.entrySet()) {
            System.out.println("Clé : " + entry.getKey() + "\nValeur : " + entry.getValue());
        }
    }
}
