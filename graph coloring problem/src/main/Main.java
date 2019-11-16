package main;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final static int qtdCores = 3; //MAIOR QUE 3 PARA QUE FUNCIONE
    final static int tamPopulacao = 100;
    final static int participToeneio = 4; //Se não for um numero par sera arrendodado para cima 

    public static void main(String[] args) {
        int[][] matrizCarregada = MatAndVetRelated.matrizTeste();

        Cromossomo[] cromossomos = new Cromossomo[tamPopulacao];

        cromossomos = loopPopulacao(matrizCarregada, cromossomos);

        Cromossomo melhorCromossomo = verificarMelhor(cromossomos);

        LoopAg(cromossomos, melhorCromossomo, participToeneio);

    }

    public static Cromossomo criarCromossomo(int[][] matrizCarregada){
        int[] vetCorPar = new int[matrizCarregada[0][0]];

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
        for (int i = 1; i < matCarregada.length; i++) {
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

    public static Cromossomo[] loopPopulacao(int[][] matrizCarregada, Cromossomo[] cromossomos){
        for (int i = 0; i < cromossomos.length; i++) {
            System.out.print(CoisasBobas.inteiroFormatado(i + 1) + " -");
            cromossomos[i] = criarCromossomo(matrizCarregada);
        }
        
        return cromossomos;
    }
    
    public static Cromossomo verificarMelhor(Cromossomo[] populacao) {
    	Cromossomo melhorCromossomo = new Cromossomo();
    	
    	for (int i = 0; i < populacao.length; i++) {
			if(i == 0) {
				melhorCromossomo = populacao[i];
			}
			if(populacao[i].getFitness() < melhorCromossomo.getFitness()) {
				melhorCromossomo = populacao[i];
			}
		}
    	
    	return melhorCromossomo;
    }
    
    public static Cromossomo torneio (Cromossomo[] populacao, int participantes) {
    	Cromossomo[] pais = new Cromossomo[CoisasBobas.arredondarParaCima(participantes)];

        ArrayList<Integer> list = new ArrayList<>();
        for (int i=1; i<populacao.length; i++) {
            list.add(i - 1);
        }
        Collections.shuffle(list);
        for (int i=0; i<pais.length; i++) {
            pais[i] = populacao[list.get(i)];
        }

    	return verificarMelhor(pais);
    }
    
    public static void LoopAg(Cromossomo[] populacao, Cromossomo melhorCromossomo, int numParticipantes) {

//    	TODO Parado enquanto não tenho todo o loop pronto, fazendo um loop de 10 iterações apenas para testes
//    	do {
//    		
//    	} while (melhorCromossomo.getFitness() != 0);
    	
    	int count = 0;
    	
    	do {
    	    Cromossomo paiA = torneio(populacao, numParticipantes);
            Cromossomo paiB = torneio(populacao, numParticipantes);
    		
    		count++;
    	} while (count < 11);
    	
    }
}
