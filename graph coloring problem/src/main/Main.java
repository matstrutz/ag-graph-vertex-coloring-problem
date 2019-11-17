package main;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final static int qtdCores = 3; //MAIOR QUE 3 PARA QUE FUNCIONE
    final static int tamPopulacao = 100;
    final static int participToeneio = 4; //Se não for um numero par sera arrendodado para cima
    final static int chanceMutacao = 1; //Se não for um numero par sera arrendodado para cima


    public static void main(String[] args) {
        int[][] matrizCarregada = MatAndVetRelated.matrizTeste();

        Cromossomo[] cromossomos = new Cromossomo[tamPopulacao];

        cromossomos = loopPopulacao(matrizCarregada, cromossomos);

        Cromossomo melhorCromossomo = verificarMelhor(cromossomos);

        LoopAg(cromossomos, melhorCromossomo, participToeneio, matrizCarregada);

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
            cromossomos[i].setPosicao(i);
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

    public static Cromossomo mascara(Cromossomo paiA, Cromossomo paiB){
        Cromossomo filho = new Cromossomo();

        int[] vetAux = new int[paiA.getCromossomo().length];

        int[] paiAgenes = paiA.getCromossomo();
        int[] paiBgenes = paiB.getCromossomo();

        for (int i = 0; i < vetAux.length; i++) {
            if((int)(Math.random()*2) == 0){
                vetAux[i] = paiAgenes[i];
            } else {
                vetAux[i] = paiBgenes[i];
            }
        }

        filho.setCromossomo(vetAux);

        return filho;
    }

    public static boolean ocorrerMutacao(int chance){
        if(chance>(int)(Math.random()*100)) {
            return true;
        }
        return false;
    }

    public static Cromossomo mutacao(Cromossomo filho){
        int[] listaGene = new int[filho.getCromossomo().length];

        listaGene[(int)(Math.random()*listaGene.length)] = ((int)(Math.random()*qtdCores) + 1);

        filho.setCromossomo(listaGene);

        return filho;
    }

    public static Cromossomo[] insercao(Cromossomo[] populacao, Cromossomo paiA, Cromossomo paiB, Cromossomo filhoA, Cromossomo filhoB){
        Cromossomo piorPai;
        Cromossomo melhorFilho;

        if(paiA.getFitness() >= paiB.getFitness()){
            piorPai = paiA;
        } else {
            piorPai = paiB;
        }

        if(filhoA.getFitness() >= filhoB.getFitness()){
            melhorFilho = filhoA;
        } else {
            melhorFilho = filhoB;
        }

        if(piorPai.getFitness() <= melhorFilho.getFitness()){
            return populacao;
        } else if(piorPai.getCromossomo().equals(melhorFilho.getCromossomo())){
            return populacao;
        } else {
            melhorFilho.setPosicao(piorPai.getPosicao());

            populacao[melhorFilho.getPosicao()] = melhorFilho;
        }

        return populacao;
    }

    public static void LoopAg(Cromossomo[] populacao, Cromossomo melhorCromossomo, int numParticipantes, int[][] matriz) {

        int contMutacao = 0;
    	
    	do {
    	    Cromossomo paiA = torneio(populacao, numParticipantes);
            Cromossomo paiB = torneio(populacao, numParticipantes);

            Cromossomo filhoA = mascara(paiA, paiB);
            filhoA.setFitness(verificaColisao(matriz, filhoA.getCromossomo()));
            Cromossomo filhoB = mascara(paiA, paiB);
            filhoB.setFitness(verificaColisao(matriz, filhoB.getCromossomo()));


            if(ocorrerMutacao(chanceMutacao)){
                filhoA = mutacao(filhoA);
                filhoA.setFitness(verificaColisao(matriz, filhoA.getCromossomo()));
                contMutacao++;
            }

            if(ocorrerMutacao(chanceMutacao)){
                filhoB = mutacao(filhoB);
                filhoB.setFitness(verificaColisao(matriz, filhoB.getCromossomo()));
                contMutacao++;
            }

            populacao = insercao(populacao, paiA, paiB, filhoA, filhoB);

            Cromossomo melhorAtual = verificarMelhor(populacao);

            if(melhorAtual.getFitness() < melhorCromossomo.getFitness()){
                melhorCromossomo = melhorAtual;
            }
    		
    	} while ((melhorCromossomo.getFitness() != 0));

    	System.out.println("");
        System.out.println("Melhor Cromossomo");
        System.out.println("");

    	System.out.print(CoisasBobas.inteiroFormatado(melhorCromossomo.getPosicao() + 1) + " -");
    	MatAndVetRelated.printarVetorPadrao(melhorCromossomo.getCromossomo());
        System.out.print("- " + CoisasBobas.inteiroFormatado(melhorCromossomo.getFitness()));
    }
}
