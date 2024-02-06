package toulouse.miage.l3.nyx.core.model;
public class Element {
    private String code;
    private String nom;
    private Double quantite;
    private String uniteMesure;
    private Double prixAchat;
    private Double prixVente;

    public Element(String code, String nom, Double quantite, String uniteMesure, Double prixAchat, Double prixVente) {
        this.code = code;
        this.uniteMesure = uniteMesure;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.quantite = quantite;
        this.nom = nom;
    }

    public Element() {

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

}
