package toulouse.miage.l3.nyx.core.utils;

import javafx.scene.control.Alert;
import toulouse.miage.l3.nyx.core.model.Chaine;
import toulouse.miage.l3.nyx.core.model.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static toulouse.miage.l3.nyx.core.model.Usine.getChaine;
import static toulouse.miage.l3.nyx.core.model.Usine.getElements;

public class UtilsChaine {

    /**
     * static constant for error messages / Format
     */
    private static final String CHAINES_FILE_PATH = "NYX/src/main/resources/toulouse/miage/l3/nyx/save/chaines.csv";
    private static final String CHAINE_CODE_FORMAT = "C\\d{3}";
    private static final String CHAINE_CODE_ERROR_MESSAGE = "Code pas au bon format\nFormat : 'C000' - 'C999'";
    private static final String CHAINE_CODE_EXISTS_ERROR_MESSAGE = "Code Chaine déja existant";
    private static final String CHAINE_NAME_EXISTS_ERROR_MESSAGE = "Nom Chaine déja existant";

    /**
     * Read line of a file named chaines.csv, and transform these line into an object Chaine,
     * add it to an Arraylist and return it
     * @return : ArrayList with Chaine read from the file chaine.csv
     */
    public static ArrayList<Chaine> readChaine() {
        ArrayList<Chaine> chaines = new ArrayList<>();
        try (BufferedReader fichier = new BufferedReader(new FileReader(CHAINES_FILE_PATH))) {
            String ligne;
            while ((ligne = fichier.readLine()) != null) {
                chaines.add(parseChaine(ligne));
            }
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
        return chaines;
    }

    /**
     * Parse a line in parameter with the format : Code;Nom;Entrée (code,qte);Sortie (code,qte)
     * into a new Chaine
     *
     * @param line : Here is an example of line : C001;Propulsion;(E004,1),(E002,0.5),(E003,0.1);(E005,1)
     * @return : Return a new Chaine create with content of this line
     */
    public static Chaine parseChaine(String line) {
        String[] parts = line.split(";");

        String code = parts[0];
        String name = parts[1];

        HashMap<Element, Double> inputElementList = parseElementList(parts[2]);
        HashMap<Element, Double> outputElementList = parseElementList(parts[3]);

        int time = Integer.parseInt(parts[4]);

        return new Chaine(code, name, inputElementList, outputElementList, time);
    }

    /**
     * Parse an input in parameter with the format : (E004,1),(E002,0.5),(E003,0.1)
     * into a Hashmap<Element, Double> and return it
     *
     * Before putting Element into the Hashmap, the function check if the Element exist in
     * the list of Element, if not it print an error
     *
     * @param input : Here is an example of line : (E004,1),(E002,0.5),(E003,0.1)
     * @return Hashmap which contains a list of Element and quantity attributed
     */
    public static HashMap<Element, Double> parseElementList(String input) {
        HashMap<Element, Double> elementMap = new HashMap<>();
        String[] elements = input.split(",");

        for (int i = 0; i < elements.length; i += 2) {
            String code = elements[i].replaceAll("[(]", "");
            Double value = Double.parseDouble(elements[i + 1].replaceAll("[)]", ""));

            Element existingElement = findElementByCode(getElements(), code);

            if (existingElement != null && getElements().contains(existingElement)) {
                elementMap.put(existingElement, value);
            } else {
                System.err.println("Your Element does not exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Un des éléments entrées n'existe pas");
                alert.showAndWait();
            }
        }
        return elementMap;
    }

    /**
     * Research, from a list of Element passed in parameter, an element which have the same code
     * also passed in parameter. The function return the Element with the code.
     * @param elements : list of element, in which you can retrieve every Element of the app
     * @param code : unique code used like a primary key for the object Element
     * @return : return the Element which contain this code/primary key
     */
    private static Element findElementByCode(List<Element> elements, String code) {
        for (Element element : elements) {
            if (element.getCode().equals(code)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Write the object Element contains in a table chaines into a file chaine.csv
     * @param chaines : table with every chaine of use by the application
     */
    public static void writeChaine(Chaine[] chaines) {
        try (PrintWriter file = new PrintWriter(new FileWriter(CHAINES_FILE_PATH))) {
            for (Chaine chaine : chaines) {
                file.println(chaine.getCode() + ";"
                        + chaine.getNom() + ";"
                        + chaine.getListeEntreeCSVType() + ";"
                        + chaine.getListeSortieCSVType() + ";"
                        + chaine.getTime()
                );
            }
        } catch (IOException ex) {
            System.out.println("File access problem");
        }
    }

    /* ===========================================
     * CHECK VALIDITY OF INPUT
     * =========================================== */

    /**
     * check the validity of the chaine code
     * @param code : code of the chaine in input
     */

    public static boolean checkCodeChaine(String code) {
        if (!code.matches(CHAINE_CODE_FORMAT)) {
            showErrorAlert(CHAINE_CODE_ERROR_MESSAGE);
            return false;
        }
        for (Chaine chaine : getChaine()) {
            if (code.equals(chaine.getCode())) {
                showErrorAlert(CHAINE_CODE_EXISTS_ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * check the validity of the chaine name
     * @param nom : name of the chaine in input
     */

    public static boolean checkNomChaine(String nom) {
        for (Chaine chaine : getChaine()) {
            if (nom.equals(chaine.getNom())) {
                showErrorAlert(CHAINE_NAME_EXISTS_ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the quantity is strictly positive
     *
     * @param qte : quantities of an element
     * @return a boolean
     */
    public static boolean checkQuantite(Double qte) {
        return qte > 0;
    }

    private static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }


}




