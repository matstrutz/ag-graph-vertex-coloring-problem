package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class Algoritmo {
    private static DecimalFormat casasDecimais = new DecimalFormat("0.000");
    private static final long NANOSEC_PER_SEC = 1000l*1000*1000;

    public static class  Cromossomo {

        private int[] vet;

        Cromossomo(int numGenes) {
            this.vet = new int[numGenes];
        }

        private int generator;
        private int posVet;
        private double fitness;
        private String label;

        Cromossomo(main.Cromossomo cromossomo) {
            this.fitness = cromossomo.getFitness();
            this.vet = cromossomo.getCromossomo();
        }

//        void calc(int[][] matCarregada, int[] vetCor) {
//            double cont = 0;
//            for (int[] mat : matCarregada) {
//                int[] vetAux = new int[2];
//                for (int j = 0; j < matCarregada[0].length; j++) {
//                    vetAux[j] = mat[j];
//                }
//
//                if (vetCor[vetAux[0] - 1] == vetCor[vetAux[1] - 1]) {
//                    cont = cont + 1;
//                }
//            }
//
//
//            this.fitness = cont;
//        }

        String ResultadoFinal() {
            String fitness = casasDecimais.format(this.fitness);
            StringBuilder legend = new StringBuilder("Melhor Fitness: " + fitness);
            legend.append("\nMelhor Mediana: ");
            for (int value : this.vet) {
                legend.append(" - ").append(value);
            }
            legend.append(" - ");
            return legend.toString();
        }

        void heranca(Cromossomo[] pais, Cromossomo piorPai) {
            if (pais[0].getGenerator() > pais[1].getGenerator()) {
                this.generator = pais[0].getGenerator() + 1;
            } else {
                this.generator = pais[1].getGenerator() + 1;
            }
            this.posVet = piorPai.getPosVet();
            this.label = piorPai.getLabel();
        }

        String getLabel() {
            return label;
        }

        double getFitness() {
            return fitness;
        }

        int getNumGenes() {
            return this.vet.length;
        }

        void setPosVetGene(int gene, int pos) {
            this.vet[pos] = gene;
        }

        int getPosVetGene(int pos) {
            return this.vet[pos];
        }

        int getPosVet() {
            return posVet;
        }

        int getGenerator() {
            return generator;
        }
    }

    public static class Cruzamento {

        static Cromossomo[] Cruz(Cromossomo[] pais) {
            return gen(pais);
        }

        static Cromossomo[] gen(Cromossomo[] pais) {
            Cromossomo[] filhos = new Cromossomo[2];
            int genInt;
            genInt = (int) (Math.random() * 2);
            if (genInt == 0) {
                filhos[0] = pais[0];
                filhos[1] = pais[1];
            } else {
                filhos[0] = pais[1];
                filhos[1] = pais[0];
            }
            return filhos;
        }
    }

    public static class Insercao {

        static boolean Ins(Populacao pop, Cromossomo[] pais, Cromossomo[] filhos, int[][] mat, Resultado A, int[] vetCor) {
            boolean cond;
            cond = piorPai(pop, pais, filhos, mat, A, vetCor);
            return cond;
        }

        static boolean piorPai(Populacao pop, Cromossomo[] pais, Cromossomo[] filhos, int[][] mat, Resultado A, int[] vetCor) {
//            filhos[0].calc(mat, vetCor);
//            filhos[1].calc(mat, vetCor);
            Cromossomo melhorFilho;
            boolean cond = false;
            if (filhos[0].getFitness() < filhos[1].getFitness()) {
                melhorFilho = filhos[0];
            } else {
                melhorFilho = filhos[1];
            }
            Cromossomo piorPai = pais[0].getFitness() > pais[1].getFitness() ? pais[0] : pais[1];
            if (melhorFilho.getFitness() < piorPai.getFitness()) {
                if (cloneCheck(pop, melhorFilho)) {
//                    melhorFilho.heranca(pais, piorPai);
                    if(melhorFilho.fitness < pop.vetC[piorPai.posVet].fitness){
                        pop.setPosVetC(piorPai.getPosVet(), melhorFilho);
                        cond = true;
                    }
                }
            }
            if (pop.getTop1().getFitness() > melhorFilho.getFitness()) {
                pop.setTop1(melhorFilho);
                A.addContTop1(melhorFilho);
            }
            return cond;
        }

        static boolean cloneCheck(Populacao pop, Cromossomo melhorFilho) {
            int i = 0;
            while (i < pop.tam()) {
                if (MatAndVetRelated.verificarIdentico(pop.vetC[i].vet, melhorFilho.vet)) {
                    return true;
                }
                i++;
            }
            return false;
        }
    }

    public static class Mutacao {

        static Cromossomo[] Mut(Cromossomo[] filhos, int porcentGene, boolean fazerOuNao, int variacao) {
            return mutacao(filhos, porcentGene, fazerOuNao, variacao);
        }

        static Cromossomo[] mutacao(Cromossomo[] filhos, int porcentGene, boolean fazerOuNao, int variacao) {
            if (!fazerOuNao) {
                return filhos;
            }
            int numGenes = (filhos[0].getNumGenes() * porcentGene) / 100;
            if (numGenes == 0) {
                numGenes = 1;
            }
            int[] vetPos = new int[filhos[0].getNumGenes()];
            for (int i = 0; i < vetPos.length; i++) {
                vetPos[i] = i;
            }
            int aux, p1, p2;
            for (int i = 0; i < vetPos.length; i++) {
                p1 = (int) ((Math.random() * (variacao)) + 1 );
                p2 = (int) ((Math.random() * (variacao)) + 1 );
                aux = vetPos[p1];
                vetPos[p1] = vetPos[p2];
                vetPos[p2] = aux;
            }
            for (int i = 0; i < numGenes; i++) {
                int novoGen = (int) (Math.random() * (variacao + 1));
                filhos[0].setPosVetGene(novoGen, vetPos[i]);
                filhos[1].setPosVetGene(novoGen, vetPos[i]);
            }
            return filhos;
        }
    }

    public static class Populacao {
        private Cromossomo[] vetC;
        private Cromossomo top1;

        Populacao(main.Cromossomo[] cromossomos) {
            Cromossomo[] vetAuxCromossomo = new Cromossomo[cromossomos.length];

            for (int i = 0; i < cromossomos.length; i++) {
                vetAuxCromossomo[i] = new Cromossomo(cromossomos[i]);
            }

            this.vetC = vetAuxCromossomo;
            boolean primeiro = true;

            for (int i = 0; i < vetC.length; i++) {
                this.vetC[i] = new Cromossomo(cromossomos[i]);
                this.vetC[i].posVet = i;
                if (primeiro) {
                    this.top1 = this.vetC[i];
                    primeiro = false;
                } else if (this.top1.getFitness() > this.vetC[i].getFitness()) {
                    this.top1 = this.vetC[i];
                }
            }
        }

        Cromossomo getVetC(int pos) {
            return vetC[pos];
        }

        int tam() {
            return vetC.length;
        }

        void setPosVetC(int pos, Cromossomo novo) {
            this.vetC[pos] = novo;
        }

        Cromossomo getTop1() {
            return top1;
        }

        void setTop1(Cromossomo top1) {
            this.top1 = top1;
        }

    }

    public static class Selecao {

        static Cromossomo[] setPaisPorTorneio(Populacao pop, int numPart) {
            Cromossomo[] pais = new Cromossomo[2];

            pais[0] = torneio(pop, numPart);

            do {
                pais[1] = torneio(pop, numPart);
            } while (pais[0].getFitness() == pais[1].getFitness());

            for (int i = 0; i < pais.length; i++) {
                pais[1] = torneio(pop, numPart);
            }

            return pais;
        }

        static Cromossomo torneio(Populacao pop, int numPart) {
            int pos = (int) (Math.random() * pop.tam());
            Cromossomo vencedor = pop.getVetC(pos);
            for (int i = 0; i < numPart - 1; i++) {
                pos = (int) (Math.random() * pop.tam());
                if (vencedor.getFitness() > pop.getVetC(pos).getFitness()) {
                    vencedor = pop.getVetC(pos);
                }
            }
            return vencedor;
        }

    }

    public static class Resultado {
        Cromossomo top1;

        void addContTop1(Cromossomo top1) {
            this.top1 = top1;
        }

        void setTop1(Cromossomo top1) {
            this.top1 = top1;
        }

        public String toString() {
            return "\n" + this.top1.ResultadoFinal();
        }
    }

    public static class Coisas {

        static int[][] carrega(String caminho) {
            FileReader arq;
            BufferedReader buff;
            String saida;
            StringBuilder temp = new StringBuilder();
            int pos = 0, cont;
            try {
                arq = new FileReader(caminho);
                buff = new BufferedReader(arq);
                int[][] mat = new int[Integer.parseInt(buff.readLine())][2];
                while (buff.ready()) {
                    saida = buff.readLine();
                    cont = 0;
                    for (int i = 0; i < saida.length(); i++) {
                        if (saida.charAt(i) != ' ') {
                            temp.append(saida.charAt(i));
                        }
                        if (saida.charAt(i) == ' ' || i == saida.length() - 1) {
                            mat[pos][cont] = Integer.parseInt(temp.toString());
                            temp = new StringBuilder();
                            cont++;
                        }
                    }
                    pos++;
                }
                buff.close();
                arq.close();
                return mat;
            } catch (IOException e) {
                System.out.println("Erro na abertura do arquivo!");
                return null;
            }
        }

        public static double verificaColisao(int[][] matCarregada, int[] vetCor){
            double cont = 0;
            for (int[] mat : matCarregada) {
                int[] vetAux = new int[2];
                for (int j = 0; j < matCarregada[0].length; j++) {
                    vetAux[j] = mat[j];
                }

                if (vetCor[vetAux[0] - 1] == vetCor[vetAux[1] - 1]) {
                    cont = cont + 1;
                }
            }

            return cont;
        }

        static double distancia(int[][] mat, int c1, int c2) {
            double distancia;
            int c = mat[c1][0] - mat[c2][0];
            int b = mat[c1][1] - mat[c2][1];
            distancia = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));
            return distancia;
        }
    }

    public static void loopAg(int[][] mat, Resultado[] vetResultado, int[] vetCor,
                              main.Cromossomo[] cromossomos, int qtdCor, long startTime,
                              int duracaoLoop){

        for (int j = 0; j < 1; j++) {
//            Populacao pop = new Populacao(100, mat, 5, vetCor);
            Populacao populacao = new Populacao(cromossomos);
            int cont = 0;
            int countMut = 0;
            int countIns = 0;
            boolean flagMutacao = true;

            Cromossomo[] pais, filhos, filhosAux;
            Resultado result = new Resultado();
            result.setTop1(populacao.getTop1());

            do {
                pais = Selecao.setPaisPorTorneio(populacao, 3);
                filhos = Cruzamento.Cruz(pais);

                if(1 >= (int) (Math.random() * 100)){
                    flagMutacao = true;
                    countMut++;
                } else {
                    flagMutacao = false;
                }

                filhosAux = Mutacao.Mut(filhos, 1, flagMutacao, 3);

                filhosAux[0].fitness = Coisas.verificaColisao(mat, filhosAux[0].vet);
                filhosAux[1].fitness = Coisas.verificaColisao(mat, filhosAux[1].vet);

                boolean condIns = Insercao.Ins(populacao, pais, filhosAux, mat, result, vetCor);

//                if(populacao.top1.fitness < filhosAux[0].fitness){
////                    populacao.top1 = filhosAux[0];
////                }
////                if(populacao.top1.fitness < filhosAux[1].fitness){
////                    populacao.top1 = filhosAux[1];
////                }

                if(condIns){
                    countIns++;
                }

                cont++;
            } while ((System.nanoTime()-startTime)< duracaoLoop*60*NANOSEC_PER_SEC);

            vetResultado[j] = result;
            System.out.println("\nQuantidade de loops feitos: " + cont);
            System.out.println("\nNumero de Mutaçoes: " + countMut + "\nNumero de Inserções: " + countIns);
            System.out.println(result);
        }
    }
}
	