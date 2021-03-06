package main;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final static int qtdCores = 4; //MAIOR QUE 3 PARA QUE FUNCIONE
    final static int tamPopulacao = 100;
    final static int participToeneio = 4; //Se n�o for um numero par sera arrendodado para cima
    final static int chanceMutacao = 1;
    final static double startTime = System.nanoTime();

    /*

    NUMERO DE CORES PARA CADA ARQUIVO

    myciel3.col ----- 4
    myciel4.col ----- 5
    myciel5.col ----- 6
    huck.col -------- 11
    jean.col -------- 10
    david.col ------- 11
    games120.col ---- 9
    miles250.col ---- 8
    miles1000.col --- 42
    anna.col -------- 11
    fpsol2.i.1.col -- 65
    homer.col ------- 13

    */


    public static void main(String[] args) {

        int[][] matrizCarregada = ArchiveManipulator.carrega("myciel3.col.txt");

        Cromossomo[] cromossomos = new Cromossomo[tamPopulacao];

        cromossomos = loopPopulacao(matrizCarregada, cromossomos);

        Cromossomo melhorCromossomo = verificarMelhor(cromossomos);

        int [][] matrizAdjacente = MatAndVetRelated.montarMatrizAdjacente(matrizCarregada);

        LoopAg(cromossomos, melhorCromossomo, participToeneio, matrizCarregada, matrizAdjacente);

    }

    public static Cromossomo[] loopPopulacao(int[][] matrizCarregada, Cromossomo[] cromossomos){
        for (int i = 0; i < cromossomos.length; i++) {
            System.out.print(CoisasBobas.inteiroFormatado(i + 1) + " -");
            cromossomos[i] = criarCromossomo(matrizCarregada);
            cromossomos[i].setPosicao(i);
        }

        return cromossomos;
    }

    public static Cromossomo criarCromossomo(int[][] matrizCarregada){
        int[] vetCorPar = new int[matrizCarregada[0][0]];

        Cromossomo cromossomo = new Cromossomo();

        vetCorPar = MatAndVetRelated.popularCores(vetCorPar, qtdCores);

        int colisao = verificaColisao(matrizCarregada, vetCorPar);

        MatAndVetRelated.printarVetorBonitinho(vetCorPar);
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
        return chance > (int) (Math.random() * 100);
    }

    public static Cromossomo mutacao(Cromossomo filho, int[][] matrizAdjacente){
        int[] listaGene = filho.getCromossomo();

        int posicao = 0, valorAux = 0, valorMutacao = 0;

        for (int i = 0; i < matrizAdjacente.length; i++) {
            for (int j = 0; j < matrizAdjacente[0].length; j++) {
                if(matrizAdjacente[i][j] == 1){
                    if(listaGene[i] == listaGene[j]){
                        posicao = j;
                        valorAux = listaGene[j];
                        i = matrizAdjacente.length;
                        j = matrizAdjacente[0].length;
                    }
                }
            }
        }

        do{
            valorMutacao = ((int)(Math.random()*qtdCores) + 1);
        }while(valorAux == valorMutacao);

        listaGene[posicao] = valorMutacao;

        filho.setCromossomo(listaGene);

        return filho;
    }

    public static Cromossomo[] insercao(Cromossomo[] populacao, Cromossomo paiA, Cromossomo paiB, Cromossomo filhoA, Cromossomo filhoB){
        Cromossomo piorPai;
        Cromossomo melhorPai;
        Cromossomo melhorFilho;

        if(paiA.getFitness() >= paiB.getFitness()){
            piorPai = paiA;
            melhorPai = paiB;
        } else {
            piorPai = paiB;
            melhorPai = paiA;
        }

        if(filhoA.getFitness() >= filhoB.getFitness()){
            melhorFilho = filhoA;
        } else {
            melhorFilho = filhoB;
        }

        if(melhorPai.getFitness() > melhorFilho.getFitness()){
            if(piorPai.getCromossomo().equals(melhorFilho.getCromossomo())){
                return populacao;
            } else {
                melhorFilho.setPosicao(piorPai.getPosicao());

                populacao[melhorFilho.getPosicao()] = melhorFilho;
            }
        }

        return populacao;
    }

    public static void LoopAg(Cromossomo[] populacao, Cromossomo melhorCromossomo, int numParticipantes, int[][] matrizCarregada, int[][] matrizAdjacente) {

        int contMutacao = 0;

    	do {
    	    Cromossomo paiA = torneio(populacao, numParticipantes);

            Cromossomo paiB;

            do{
                paiB = torneio(populacao, numParticipantes);
            }while(paiA.equals(paiB));

            Cromossomo filhoA = mascara(paiA, paiB);
            filhoA.setFitness(verificaColisao(matrizCarregada, filhoA.getCromossomo()));
            Cromossomo filhoB = mascara(paiA, paiB);
            filhoB.setFitness(verificaColisao(matrizCarregada, filhoB.getCromossomo()));

            if(ocorrerMutacao(chanceMutacao)){
                filhoA = mutacao(filhoA, matrizAdjacente);
                filhoA.setFitness(verificaColisao(matrizCarregada, filhoA.getCromossomo()));
                contMutacao++;
            }

            if(ocorrerMutacao(chanceMutacao)){
                filhoB = mutacao(filhoB, matrizAdjacente);
                filhoB.setFitness(verificaColisao(matrizCarregada, filhoB.getCromossomo()));
                contMutacao++;
            }

            populacao = insercao(populacao, paiA, paiB, filhoA, filhoB);

            Cromossomo melhorAtual = verificarMelhor(populacao);

            if(melhorAtual.getFitness() < melhorCromossomo.getFitness()){
                melhorCromossomo = melhorAtual;
            }

//            DESCOMENTAR PARA VERIFICAR ANDAMENTO DO MELHOR FITNESS
//            System.out.println(melhorCromossomo.getFitness());

    	} while ((melhorCromossomo.getFitness() != 0));

        double endTime = System.nanoTime();
        double totalTime = endTime - startTime;

        System.out.println("");
        System.out.println("Dura��o: " + totalTime/1000000000);

        System.out.println("");
        System.out.println("Quantidade de muta��es: " + contMutacao);

    	System.out.println("");
        System.out.println("Melhor Cromossomo");
        System.out.println("");

    	System.out.print(CoisasBobas.inteiroFormatado(melhorCromossomo.getPosicao() + 1) + " -");
    	MatAndVetRelated.printarVetorPadrao(melhorCromossomo.getCromossomo());
        System.out.print("- " + CoisasBobas.inteiroFormatado(melhorCromossomo.getFitness()));
    }
}
