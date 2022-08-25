/*
 * Generates populations of Genomes of Genome object
 * @author Kevin
 * @version 4/16/22
 *
 */

import java.util.Collections;
import java.util.Random;

public class Population {

    //This list stores the Genomes in the population.
    //The initial population of size is created in the constructor.
    //The population is updated every time nextGeneration is called.
    public MyLinkedList<Genome> population;

    //This stores the most fit genome
    //After sorting the population by fitness in nextGeneration this value is extracted
    //from the list.
    public Genome mostFit;

    //The number of times nextGeneration has been called.
    public int generation;

    //size - The number of genomes in the population.
    //For this experiment, use a population size of 100.
    private int size;

    //Population constructor
    public Population(){
        population = new MyLinkedList<>();
        generation = 1;
        size = 100;
        //Create 100 Genomes, put inside population list
        for(int i = 0; i < size; i++){
            population.addBefore(new Genome());
        }
        mostFit = population.first();
    }

    //NextGeneration - updates the population to the next generation.
    // 1) Delete the half of the population with the lowest fitness. The remaining members
        //of the population will serve as possible parents of new members.
    // 2) Create new genomes from the remaining population until the population is
        //restored to its original size by doing either of the following with equal chance:
            //Pick a remaining genome at random and clone it (with the copy
            //constructor). Then mutate the clone.
            //■ Pick a remaining genome at random and clone it. Then crossover the
            //clone with another remaining genome (selected at random). Then mutate
            //the result.
    // 3) Sort the population by fitness.
    // 4) Update the mostFit variable to the genome with the highest fitness in the
        //population.
    public void nextGeneration(){
        killBottomHalf();
        repopulate();
        population.sort();
        getMostFit();
        generation++;
    }

    //Update most fit, helper for nextGeneration()
    private void getMostFit(){
        population.first();
        for(int i = 0; i < population.size(); i++){
            population.next();
        }
        mostFit = population.current();
    }
    //Helper method for nextGeneration()
    private void repopulate(){
        Random rand = new Random();
        boolean crossover;
        Genome clonedGenome;

        //CREATE REMAINING 50 LIST BY COPYING
       // MyLinkedList<Genome> remainingHalf = population;

        MyLinkedList<Genome> remainingHalf = new MyLinkedList<>();
        population.first();
        for (int i = 0; i < population.size(); i++){
            remainingHalf.addToEnd(population.current());
            population.next();
        }
        while(size < 100) {
            clonedGenome = cloneGenomeAtRandom(rand, remainingHalf);
            crossover = rand.nextBoolean();
            if (crossover) { //Crossover
                clonedGenome.crossover(cloneGenomeAtRandom(rand, remainingHalf));
            }
            clonedGenome.mutate();
            population.addBefore(clonedGenome);
            size++;
        }
    }

    private Genome cloneGenomeAtRandom(Random rand, MyLinkedList<Genome> remainingHalf){
        remainingHalf.first();
        int genomeToClone = rand.nextInt(remainingHalf.size());
        for(int i = 0; i < genomeToClone; i++){
            remainingHalf.next();
        }
        Genome clone = new Genome(remainingHalf.current());
        return clone;

    }
    //Helper method for nextGeneration()
    private void killBottomHalf(){
        population.first();
        for(int i = 0; i < 50; i++){ //KILL BOTTOM 50%
            population.remove();
            size--;
        }
    }

    //toString
    public String toString(){
        return population.toString();
    }
}

