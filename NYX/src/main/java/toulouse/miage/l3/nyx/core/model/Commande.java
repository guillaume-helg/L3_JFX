package toulouse.miage.l3.nyx.core.model;

/**
 * Represents an order (Commande)
 * @author Guillaume Helg
 * @version 1.0
 */
public class Commande {
    private Chaine chaine;
    private int quantity;


    public Commande(Chaine chaine, int quantity) {
        this.chaine = chaine;
        this.quantity = quantity;
    }

    public Chaine getChaine() {
        return this.chaine;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public boolean getFeasible() {
        return this.chaine.isFeasible(quantity);
    }
    
    
    public void setQuantity(int i) {
        if (this.quantity - i >= 0) {
            this.quantity -= i;
        }
    }
    
    public String toString() {
        return "";
    }
}