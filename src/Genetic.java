/**
 * Created by njruntuwene on 9/30/16.
 */

import java.util.ArrayList;
import java.util.Random;

public class Genetic {
    private ArrayList<CSP> population;
    private int populationSize;
    private int steps;
    private int maximumViolation;

    public Genetic(int populationSize,int steps, ArrayList<Activity> activities, ArrayList<Classroom> classrooms) {
        this.populationSize = populationSize;
        this.population = new ArrayList<>();
        this.steps = steps;

        int activitiesSize = activities.size();
        this.maximumViolation = (activitiesSize * (activitiesSize - 1)) >> 1;

        for (int i = 0; i < populationSize; i++) {
            CSP csp = new CSP(activities,classrooms);
            csp.setRandomAllActivity();
            population.add(csp);
        }
    }

    public int countTotalFitness() {
        int total = 0;
        for (CSP populace : population) {
            total += (maximumViolation - populace.countViolation());
        }
        return total;
    }

    public CSP selection(int totalFitness) {
        //select random number
        Random rand = new Random();
        int random = rand.nextInt(totalFitness);

        //find populace
        int i = 0;
        while (random >= 0 && i < populationSize) {
            random -= (maximumViolation - population.get(i).countViolation());
            i++;
        }
        return population.get(i-1);
    }

    public ArrayList<Activity> crossover(ArrayList<Activity> activities1, ArrayList<Activity> activities2) {
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

    public void mutate(CSP csp) {
        //choose activity to mutate and then use setRandomActivity
        Random rand = new Random();
        int indexMutation = rand.nextInt(csp.getArrayActivity().size());
        csp.setRandomActivity(csp.getArrayActivity().get(indexMutation));
    }

    public CSP breed() {
        //select two from population using roulette
        int totalFitness = countTotalFitness();
        CSP parent1 = selection(totalFitness);
        CSP parent2 = selection(totalFitness);

        //cross them over to get new populace
        ArrayList<Activity> activities = crossover(parent1.getArrayActivity(),parent2.getArrayActivity());

        //mutate the new populace
        CSP newPopulace = new CSP(activities,parent1.getArrayClassroom(),parent1.getUnplaceables());
        mutate(newPopulace);

        //check violation of new populace
        newPopulace.checkViolation();

        return newPopulace;
    }

    public int newGeneration() {
        ArrayList<CSP> newPopulation = new ArrayList<>();
        int minimumViolation = 10000;
        for (int i = 0; i < populationSize; i++) {
            newPopulation.add(breed());
            minimumViolation = Math.min(newPopulation.get(i).countViolation(), minimumViolation);
        }
        this.population = newPopulation;
        return minimumViolation;
    }

    public CSP findBestCSP() {
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
        CSP best = findBestCSP();
        while (newGeneration() > 0 && i < steps) {
            CSP temp = findBestCSP();
            best = temp.countViolation() > best.countViolation() ? temp : best;
            i++;
        }
        CSP temp = findBestCSP();
        best = temp.countViolation() > best.countViolation() ? temp : best;
        return best;
    }

    public static void main(String args[]) {
        try{
			int step = Integer.parseInt(args[1]);
			int popSize = Integer.parseInt(args[2]);
			FileReader fr = new FileReader(args[0]);
			ArrayList<Classroom> classrooms = fr.parseArrayClassroom();
			ArrayList<Activity> activities = fr.parseArrayActivity();
	
			Genetic genetic = new Genetic(popSize,step,activities,classrooms);
			CSP answer = genetic.run();
			answer.printAllActivity();
			System.out.println("Jumlah Bentrok: " + answer.countViolation());
			System.out.println("Persentasi keefektifan " + answer.percentage() );
            System.out.println("Unplaceables : ");
            answer.printAllUnplaceable();

		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Input not complete");
    		System.out.println("Algorithm Terminated");
    		System.exit(0);
		}
    }
}
