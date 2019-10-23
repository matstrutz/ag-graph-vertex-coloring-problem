package main;

public class Main {
    final static int qtdCores = 3; //MAIOR QUE 3 PARA QUE FUNCIONE
    final static int tamPopulacao = 100;

    public static void main(String[] args) {
        int[][] matrizCarregada = MatAndVetRelated.matrizTeste();

        Cromossomo[] cromossomos = new Cromossomo[tamPopulacao];

        loopPopulacao(matrizCarregada, cromossomos);
    }

    public static Cromossomo resultado(int[][] matrizCarregada){
        int[] vetCorPar = new int[matrizCarregada[matrizCarregada.length-1][matrizCarregada[0].length-1]];

        Cromossomo cromossomo = new Cromossomo();

        vetCorPar = MatAndVetRelated.popularCores(vetCorPar, qtdCores);

        int colisao = verificaColisao(matrizCarregada, vetCorPar);

        MatAndVetRelated.printarVetorPadrao(vetCorPar);
        System.out.print("- " + colisao);
        System.out.println("");

        cromossomo.setCromossomo(vetCorPar);
        cromossomo.setFitness(colisao);

        return cromossomo;
    }

    public static int verificaColisao(int[][] matCarregada, int[] vetCor){
        int cont = 0;
        for (int i = 0; i < matCarregada.length; i++) {
            int [] vetAux = new int[2];
            for (int j = 0; j < matCarregada[0].length; j++) {
                vetAux[j] = matCarregada[i][j];
            }

            if(vetCor[vetAux[0] - 1] == vetCor[vetAux[1] - 1]){
                cont++;
            }
        }

        return cont;
    }

    public static void loopPopulacao(int[][] matrizCarregada, Cromossomo[] cromossomos){
        for (int i = 0; i < cromossomos.length; i++) {
            System.out.print(CoisasBobas.inteiroFormatado(i + 1) + " -");
            cromossomos[i] = resultado(matrizCarregada);
        }
    }
}
