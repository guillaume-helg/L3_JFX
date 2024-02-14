package toulouse.miage.l3.nyx.core.utils;

import javafx.collections.ObservableList;
import toulouse.miage.l3.nyx.core.model.Element;
import toulouse.miage.l3.nyx.core.model.Unite;

import java.io.*;
import java.util.ArrayList;

public class UtilsElement {
    /**
     * Read every line of a file of element, parse these lines into Element,
     * and add them to an ArrayList
     *
     * @return a table of elements read from the file element.csv
     */
    public static ArrayList<Element> readElement() {
        String fileName = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/elements.csv";
        String line;
        ArrayList<Element> element = new ArrayList<>();

        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));

            while ((line = file.readLine()) != null) {
                element.add(parseElement(line));
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("Acces problem");
        }
        return element;
    }

    /**
     * Parse a line in parameter with the format : code, name, quantity, unity, buy price, sell price
     * into a new Element
     *
     * @param line : Here is an example of line : E001;Circuit principal;200;pieces;50;20
     * @return : Return a new Element create with content of this line
     */
    private static Element parseElement(String line) {
        String[] l = line.split(";");
        return new Element(l[0], l[1], Double.parseDouble(l[2]), Unite.valueOf(l[3]), Double.parseDouble(l[4]), Double.parseDouble(l[5]));
    }

    /**
     * Write on object Element into a file elements.csv
     *
     * @param e : table with every Element of the application
     */
    public static void writeElement(ObservableList<Element> e) {
        String fileName = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/elements.csv";
        try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));

            for (Element a : e) {
                file.println(a.getCode() + ";"
                        + a.getNom() + ";"
                        + a.getQuantite() + ";"
                        + a.getUniteMesure() + ";"
                        + a.getPrixAchat() + ";"
                        + a.getPrixVente());
            }

            file.close();
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
    }

    public static boolean checkFormatCode(String code){
        if (code.matches("E\\d{3}")) return true;
        return false;
    }

    public static boolean checkQuantite(Double qte){
        if (qte <= 0) return false;
        return true;
    }

    public static boolean checkPurchasePrice(Double pp){
        if (pp <= 0) return false;
        return true;
    }

    public static boolean checkSalePrice(Double sp){
        if (sp <= 0) return false;
        return true;
    }
}
