/**
 * Created by njruntuwene on 9/30/16.
 */

import java.util.ArrayList;
import java.util.Random;

public class Genetic {
    private ArrayList<CSP> population;
    private int populationSize;
    private int steps;

    public Genetic(int populationSize,int steps) {
        this.populationSize = populationSize;
        this.steps = steps;
        for (int i = 0; i < populationSize; i++) {
            CSP csp = new CSP();
            csp.setRandomAllActivity();
            population.add(csp);
        }
    }

    private int countTotalFitness() {
        int total = 0;
        for (CSP populace : population) {
            total += populace.countViolation();
        }
        return total;
    }

    private CSP selection(int totalFitness) {
        //select random number
        Random rand = new Random();
        int random = rand.nextInt(totalFitness);

        //find populace
        int i = 0;
        while (random > 0 && i < populationSize) {
            random -= population.get(i).countViolation();
            i++;
        }
        return population.get(i-1);
    }

    private ArrayList<Activity> crossover(ArrayList<Activity> activities1, ArrayList<Activity> activities2) {
        Random rand = new Random();
        ArrayList<Activity> activities = new ArrayList<>(activities1.size());
        int indexCrossover = rand.nextInt(activities1.size());
        for (int i = 0; i < indexCrossover; i++) {
            activities.add(activities1.get(i));
        }
        for (int i = indexCrossover; i < activities2.size(); i++) {
            activities.add(activities2.get(i));
        }
        return activities;
    }

    private void mutate(CSP csp) {
        //choose activity to mutate and then use setRandomActivity
        Random rand = new Random();
        int indexMutation = rand.nextInt(csp.getArrayActivity().size());
        csp.setRandomActivity(csp.getArrayActivity().get(indexMutation));
    }

    private CSP breed() {
        //select two from population using roulette
        int totalFitness = countTotalFitness();
        CSP parent1 = selection(totalFitness);
        CSP parent2 = selection(totalFitness);

        //cross them over to get new populace
        ArrayList<Activity> activities = crossover(parent1.getArrayActivity(),parent2.getArrayActivity());

        //mutate the new populace
        CSP newPopulace = new CSP(activities,parent1.getArrayClassroom());
        mutate(newPopulace);

        //check violation of new populace
        newPopulace.checkViolation();

        return newPopulace;
    }

    private int newGeneration() {
        ArrayList<CSP> newPopulation = new ArrayList<>();
        int minimumViolation = 10000;
        for (int i = 0; i < populationSize; i++) {
            newPopulation.add(breed());
            minimumViolation = Math.min(newPopulation.get(i).countViolation(), minimumViolation);
        }
        population = newPopulation;
        return minimumViolation;
    }

    private CSP findBestCSP() {
        int minimumIndex = 0;
        for (int i = 1; i < populationSize; i++) {
            if (population.get(i).countViolation() < population.get(minimumIndex).countViolation()) {
                minimumIndex = i;
            }
        }
        return population.get(minimumIndex);
    }

    public CSP run() {
        int i = 0;
        while (newGeneration() > 0 && i < steps) i++;
        return findBestCSP();
    }

    public static void main(String args[]) {
        int step = Integer.parseInt(args[0]);
        int popSize = Integer.parseInt(args[1]);
        Genetic genetic = new Genetic(popSize,step);
        CSP answer = genetic.run();
        answer.printAllActivity();
    }
}
