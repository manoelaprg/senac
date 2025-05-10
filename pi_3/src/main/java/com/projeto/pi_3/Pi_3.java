package com.projeto.pi_3;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Pi_3 {

    public static void main(String[] args) {
        try {
            File htmlFile = new File("web/index.html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}