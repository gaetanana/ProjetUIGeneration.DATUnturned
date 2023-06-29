package Modele;

import Modele.modeleApplication;

import java.io.File;

public class main {

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\Winchester.dat");
        System.out.println(modeleApplication.readDATFile(file));
    }
}
