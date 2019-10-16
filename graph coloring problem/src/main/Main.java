package main;

public class Main {
    final static int qtdCores = 3; //MAIOR QUE 3 PARA QUE FUNCIONE E MENOR QUE O NUMERO DE VERTICES

    public static void main(String[] args) {
        int[][] matrizCarregada = MatAndVetRelated.matrizTeste();

        int[][] matAdjacente = new int[matrizCarregada[matrizCarregada.length-1][matrizCarregada[0].length-1]][matrizCarregada[matrizCarregada.length-1][matrizCarregada[0].length-1]];

        matAdjacente = montarMatrizAdjacente(matAdjacente, matrizCarregada);

        int[] vetCorPar = new int[matrizCarregada[matrizCarregada.length-1][matrizCarregada[0].length-1]];

        vetCorPar = MatAndVetRelated.popularCores(vetCorPar, qtdCores);

        int colisao = verificarColisao(matAdjacente, vetCorPar);
        MatAndVetRelated.printarVetorPadrao(vetCorPar);
        System.out.println("");
        MatAndVetRelated.printarMatrizPadrao(matAdjacente);
        System.out.println("");
        System.out.println(colisao);
    }

    public static int[][] montarMatrizAdjacente(int [][] matrizAdjavente, int [][] matrizCarregada){
        for (int i = 0; i < matrizCarregada.length; i++) {
            int [] vet = new int[matrizCarregada[0].length];
            for (int j = 0; j < matrizCarregada[0].length; j++) {
                vet[j] = matrizCarregada[i][j];
            }

            matrizAdjavente[vet[0] - 1][vet[1] - 1] = 1;
            matrizAdjavente[vet[1] - 1][vet[0] - 1] = 1;
        }

        return matrizAdjavente;
    }

    public static int verificarColisao(int matriz[][], int vetor[]){
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
