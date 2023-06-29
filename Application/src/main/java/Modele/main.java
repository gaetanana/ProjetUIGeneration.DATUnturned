package Modele;

import Modele.modeleApplication;

import java.io.File;

import static Modele.modeleApplication.*;

public class main {

    public static void main(String[] args) throws Exception {
        System.out.println("Voici le contenue du fichier DAT sans le BluePrint : " + readDataWithOutBluePrints("D:\\Winchester.dat"));
        System.out.println("Voici la partie BluePrint de l'item : " +readBlueprintsData( "D:\\Winchester.dat"));
        System.out.println("Nombre de craft disponible pour cet item : "+getNumberOfBlueprints("D:\\Winchester.dat"));
    }
}
