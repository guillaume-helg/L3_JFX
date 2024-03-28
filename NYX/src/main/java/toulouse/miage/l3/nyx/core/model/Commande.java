package toulouse.miage.l3.nyx.core.model;

/**
 * Represents a command (Commande)
 * @author Guillaume Helg
 * @version 1.0
 */
public class Commande {

    /**
     * This is the chain ordered
     */
    private Chaine chaine;

    /**
     * Quantity of the same chaine ordered
     */
    private int quantity;

    /**
     * Boolean if this chaine is feasible
     */
    private boolean feasible;


    /**
     * Constructor of Commande
     * @param chaine chaine ordered
     * @param quantity quantity of the chaine ordered
     */
    public Commande(Chaine chaine, int quantity) {
        this.chaine = chaine;
        this.quantity = quantity;
        this.feasible = chaine.isFeasible(quantity);
    }

    /**
     * Get the chaine that produce the command
     * @return chaine
     */
    public Chaine getChaine() {
        return this.chaine;
    }

    /**
     * get the quantity of the command
     * @return quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Compute if we have enough resources to pursuit the command
     * @return if the command is feasible or not
     */
    public boolean getFeasible() {
        return this.feasible;
    }

    /**
     * Set the field boolean feasible
     * @param b : true of false
     */
    public void setFeasible(boolean b) {
        this.feasible = b;
    }

    /**
     * set the quantity
     * @param i : integer that corresponds with the quantity
     */
    public void setQuantity(int i) {
        if (this.quantity - i >= 0) {
            this.quantity -= i;
        }
    }

    /**
     * Command in String
     * @return the command parse in String
     */
    public String toString() {
        return "" + chaine.toString() + " " + this.quantity + this.feasible;
    }
}