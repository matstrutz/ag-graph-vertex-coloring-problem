package main;

import java.util.ArrayList;

public class Cromossomo {
    private ArrayList cromossomo;

    private int fitness;

    private int posicao;

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getPosicao() {
        return posicao;
    }

    public int[] getCromossomo() {
        int vet[] = new int[cromossomo.size()];
        for (int i = 0; i < cromossomo.size(); i++) {
            vet[i] = (int) cromossomo.get(i);
        }

        return vet;
    }

    public void setCromossomo(int[] cromossomo) {
        ArrayList aux = new ArrayList();
        for (int i = 0; i < cromossomo.length; i++) {
            aux.add(cromossomo[i]);
        }
        this.cromossomo = aux;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
