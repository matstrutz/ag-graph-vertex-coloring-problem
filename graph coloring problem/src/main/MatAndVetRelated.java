package main;

import java.util.ArrayList;

public class MatAndVetRelated {

    public static void printarMatrizPadrao(int matriz[][]){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(" | " + matriz[i][j]);
            }
            System.out.print(" | ");
            System.out.println("");
        }
    }

    public static void printarVetorPadrao(int vet[]) {
        for (int j = 0; j < vet.length; j++) {
            System.out.print(" | " + vet[j]);
        }
        System.out.print(" | ");
    }

    public static void printarVetorBonitinho(int vet[]) {
        for (int j = 0; j < vet.length; j++) {
            if(vet[j] >= 0 && vet[j] < 10){
                System.out.print(" | 00" + vet[j]);
            }
            if(vet[j] >= 10 && vet[j] < 100){
                System.out.print(" | 0" + vet[j]);
            }
            if(vet[j] >= 100){
                System.out.print(" | " + vet[j]);
            }
        }

        System.out.print(" | ");
    }

    public static void printarMatrizBonitinha(int matriz[][]){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if(matriz[i][j] >= 0 && matriz[i][j] < 10){
                    System.out.print(" | 000" + matriz[i][j]);
                }
                if(matriz[i][j] >= 10 && matriz[i][j] < 100){
                    System.out.print(" | 00" + matriz[i][j]);
                }
                if(matriz[i][j] >= 100 && matriz[i][j] < 1000){
                    System.out.print(" | 0" + matriz[i][j]);
                }
                if(matriz[i][j] >= 1000){
                    System.out.print(" | " + matriz[i][j]);
                }
            }
            System.out.print(" | ");
            System.out.println("");
        }
    }

    public static int[] criaVetorSequencial(int [] vetSequencial){
        int cont = 0;
        for (int i = 0; i < vetSequencial.length; i++) {
            vetSequencial[i] = ++cont;
        }
        return vetSequencial;
    }

    public static int[] popularCores(int [] vetColorido, int qtdCores){
        for (int i = 0; i < vetColorido.length; i++) {
            vetColorido[i] = (int)(Math.random() * qtdCores + 1);
        }
        return vetColorido;
    }

    public static int[][] montarMatrizAdjacente(int [][] matrizCarregada){

        int [][] matrizAdjavente = new int[matrizCarregada[0][0]][matrizCarregada[0][0]];

        for (int i = 1; i < matrizCarregada.length; i++) {
            int [] vet = new int[matrizCarregada[0].length];
            for (int j = 0; j < matrizCarregada[0].length; j++) {
                vet[j] = matrizCarregada[i][j];
            }

            matrizAdjavente[vet[0] - 1][vet[1] - 1] = 1;
            matrizAdjavente[vet[1] - 1][vet[0] - 1] = 1;
        }

        return matrizAdjavente;
    }

    public static int verificarColisaoAdjacente(int matriz[][], int vetor[]){
        int cont = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if(matriz[i][j] == 1 && vetor[i] == vetor[j] && (i != j) ){
                    cont++;
                }
            }
        }

        return cont / 2;
    }

    public static int[][] arraysCarregadosParaMatriz(ArrayList<Integer> ladoA, ArrayList<Integer> ladoB){
        int[][] matrizMontada = new int[ladoA.size()][2];

        for (int i = 0; i < ladoA.size(); i++) {
            matrizMontada[i][0] = ladoA.get(i);
            matrizMontada[i][1] = ladoB.get(i);
        }

        return verificaDuplicidade(matrizMontada);
    }

    public static int[][] verificaDuplicidade(int[][] matrizDuplicada){

        int valorA = 0, valorB = 0;

        for (int i = 1; i < matrizDuplicada.length; i++) {
            valorA = matrizDuplicada[i][0];
            valorB = matrizDuplicada[i][1];

            for (int j = 1; j < matrizDuplicada.length; j++) {
                if(i != j){
                    if((matrizDuplicada[j][0] == valorA && matrizDuplicada[j][1] == valorB) || (matrizDuplicada[j][1] == valorA && matrizDuplicada[j][0] == valorB)){
                        matrizDuplicada[j][0] = 0;
                        matrizDuplicada[j][1] = 0;
                    }
                }
            }
        }

        ArrayList<Integer> listaA = new ArrayList<>();
        ArrayList<Integer> listaB = new ArrayList<>();

        for (int i = 0; i < matrizDuplicada.length; i++) {
            if(!(matrizDuplicada[i][0] == 0 || matrizDuplicada[i][1] == 0)){
                listaA.add(matrizDuplicada[i][0]);
                listaB.add(matrizDuplicada[i][1]);
            }
        }

        int[][] novaMatriz = new int[listaA.size()][2];

        for (int i = 0; i < listaA.size(); i++) {
            novaMatriz[i][0] = listaA.get(i);
            novaMatriz[i][1] = listaB.get(i);
        }

        return novaMatriz;
    }
}
