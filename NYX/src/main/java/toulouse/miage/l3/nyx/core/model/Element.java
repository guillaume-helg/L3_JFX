package toulouse.miage.l3.nyx.core.model;

import java.util.*;

/**
 * Class Element
 *
 * @author Lucas Godard
 * @version 1.0
 */
public class Element {
    private String code;
    /** name of the element */
    private String nom;
    /** quantity of the element */
    private Double quantite;
    /** unit of the element */
    private String uniteMesure;
    /** purchase price of element */
    private Double prixAchat;
    /** selling price of element */
    private Double prixVente;

    /**
     * Constructeur de l'element
     * @param code
     * @param nom
     * @param quantite
     * @param uniteMesure
     * @param prixAchat
     * @param prixVente
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(code, element.code);
    }

    /**
     * Get the element's code
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the element's purchase price
     * @return prixAchat
     */
    public Double getPrixAchat() {
        return prixAchat;
    }

    /**
     * Set the element's purchase price
     * @param prixAchat
     */
    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    /**
     * Get the element's selling price
     * @return prixVente
     */
    public Double getPrixVente() {
        return prixVente;
    }

    /**
     * Set the element's selling price
     * @param prixVente
     */
    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    /**
     * Get the element's name
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Get the element's quantity
     * @return quantite
     */
    public Double getQuantite() {
        return quantite;
    }

    /**
     * Get the element's unit
     * @return uniteMesure
     */
    public String getUniteMesure() {
        return uniteMesure;
    }

    /**
     * Set the element's unit
     * @param quantite
     */
    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }
}
