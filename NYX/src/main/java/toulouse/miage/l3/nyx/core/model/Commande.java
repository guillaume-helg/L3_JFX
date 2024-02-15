package toulouse.miage.l3.nyx.core.model;

public class Commande {
    private Chaine chaine;
    private int quantity;
    private boolean feasible;

    public Commande(Chaine chaine, int quantity) {
        this.chaine = chaine;
        this.quantity = quantity;
        this.feasible = chaine.isFeasible(quantity);
    }

    public Chaine getChaine() {
        return this.chaine;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public boolean getFeasible() {
        return this.feasible;
    }
    
    public void setFeasible(boolean b) {
        this.feasible = b;
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