package toulouse.miage.l3.nyx.core.model;

public class Element {
    private String code;
    private String nom;
    private char uniteMesure;
    private Double prixAchat;
    private Double prixVente;
    private int quantite;

    public Element(String code, Double prixAchat, Double prixVente, int quantite) {
        this.code = code;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.quantite = quantite;
    }

    public String getCode() {
        return code;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
