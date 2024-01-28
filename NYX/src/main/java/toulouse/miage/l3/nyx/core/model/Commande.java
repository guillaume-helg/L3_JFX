package toulouse.miage.l3.nyx.core.model;

public class Commande {
    private String code;
    private double prixAchat;
    private double prixVente;
    private double quantite;

    public String qtt;

    public Commande(String code, double prixAchat, double prixVente, double quantite) {
        this.code = code;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.quantite = quantite;
    }

    public Commande(String code, double quantite) {
        this(code, 0, 0, quantite);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
}
