package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArchiveManipulator {

    public static int[][] carrega(String caminho){
        FileReader arq;
        BufferedReader buff;
        String saida="", tempA="", tempB="";
        boolean linhaDeVertice = false;
        boolean linhaDeArestas = false;
        boolean trocandoLado = false;
        ArrayList<Integer> ladoA = new ArrayList();
        ArrayList<Integer> ladoB = new ArrayList();
        try{
            arq = new FileReader(caminho);
            buff = new BufferedReader(arq);
            while (buff.ready()) {
                saida = buff.readLine();
                for (int i = 0; i < saida.length(); i++) {
                    if (saida.charAt(i) == 'p') {
                        if (saida.charAt(i + 1) == ' ') {
                            if (saida.charAt(i + 2) == 'e') {
                                linhaDeVertice = true;
                            }
                        }
                    }
                    if (linhaDeVertice) {
                        if (i > 6) {
                            if (trocandoLado) {
                                tempB += saida.charAt(i);
                            }
                            if (saida.charAt(i) != ' '){
                                tempA += saida.charAt(i);
                            }
                            if (saida.charAt(i) == ' ') {
                                ladoA.add(Integer.parseInt(tempA));
                                trocandoLado = true;
                            }
                            if (i == saida.length() - 1) {
                                ladoB.add(Integer.parseInt(tempB));
                                linhaDeVertice = false;
                                trocandoLado = false;
                                tempA = "";
                                tempB = "";
                            }
                        }
                    }

                    if (saida.charAt(i) == 'e') {
                        if (saida.charAt(i + 1) == ' ') {
                            if (saida.charAt(i + 2) == '1') {
                                if (saida.charAt(i + 3) == ' ') {
                                    linhaDeArestas = true;
                                }
                            }
                        }
                    }
                    if(linhaDeArestas){
                        if (i > 1) {
                            if (trocandoLado) {
                                tempB += saida.charAt(i);
                            }
                            if (saida.charAt(i) == ' ') {
                                ladoA.add(Integer.parseInt(tempA));
                                trocandoLado = true;
                            }
                            if (saida.charAt(i) != ' ' && !trocandoLado){
                                tempA += saida.charAt(i);
                            }
                            if (i == saida.length() - 1) {
                                ladoB.add(Integer.parseInt(tempB));
                                linhaDeVertice = false;
                                trocandoLado = false;
                                tempA = "";
                                tempB = "";
                            }
                        }
                    }
                }
            }
            buff.close();
            arq.close();
            return MatAndVetRelated.arraysCarregadosParaMatriz(ladoA, ladoB);
        }catch(IOException e){
            System.out.println("Erro na abertura do arquivo!");
            return null;
        }
    }

}
