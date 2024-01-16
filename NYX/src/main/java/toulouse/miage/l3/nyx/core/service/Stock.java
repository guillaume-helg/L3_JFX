package toulouse.miage.l3.nyx.core.service;

import toulouse.miage.l3.nyx.core.model.Element;

import java.util.ArrayList;

public class Stock {
    private ArrayList<Element> elements = new ArrayList<>();

    public void enleverElement(Element e) {
        this.elements.remove(e);
    }

    public void ajouterElement(Element e) {
        this.elements.add(e);
    }
}
