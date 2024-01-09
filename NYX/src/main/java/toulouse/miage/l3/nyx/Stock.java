package toulouse.miage.l3.nyx;

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
