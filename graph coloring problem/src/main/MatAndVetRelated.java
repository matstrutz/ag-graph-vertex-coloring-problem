package main;

public class MatAndVetRelated {

    public static int[][] matrizTeste(){
        return new int[][]{
        		{ 11, 20 },
                { 1, 2 },
                { 1, 4 },
                { 1, 7 },
                { 1, 9 },
                { 2, 3 },
                { 2, 6 },
                { 2, 8 },
                { 3, 5 },
                { 3, 7 },
                { 3, 10 },
                { 4, 5 },
                { 4, 6 },
                { 4, 10 },
                { 5, 8 },
                { 5, 9 },
                { 6, 11 },
                { 7, 11 },
                { 8, 11 },
                { 9, 11 },
                { 10, 11 },
        };
    }

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
}
