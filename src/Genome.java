import java.util.Random;

/*
 * Creates a Genome object capable of asexual and sexual reproduction
 * Used to model evolution of strings to a target string goal
 * @author Kevin Kulman
 * @version 4/16/22
 *
 */
public class Genome implements Comparable<Genome> {

    //genes - A list of characters representing the string encoded by the genome. This list is
    //either copied from another genome (using the copy constructor) or initialized to the
    //empty list.
    protected MyLinkedList<Character> genes;

    //target - The target string. This variable is initialized in the constructor and never
    //modified. Initialize this to your name to test it.
    private MyLinkedList<Character> target;

    //mutationRate - The mutation rate for the population. For this experiment we will
    //use a mutation rate of 0.05.
    private double mutationRate = 0.05;

    //Default constructor takes no arguments and initializes genes to the empty list
    public Genome() {
        genes = new MyLinkedList<>();
        setTarget();
    }

    // The copy constructor takes a single genome as an argument copies genes
    //from the genome.
    public Genome(Genome copyGenome){
        //genes = copyGenome.genes;
        genes = new MyLinkedList<>();
        copyGenome.genes.first();
        //COPY
        for (int i = 0; i < copyGenome.genes.size(); i++){
            genes.addToEnd(copyGenome.genes.current());
            copyGenome.genes.next();
        }
        setTarget();
    }

    //Helper method for constructors - set target name
    private void setTarget(){
        target = new MyLinkedList<Character>();
        String s = "KEVIN KULMAN";
        char[] c = s.toCharArray();
        for(int i = 0; i < s.length(); i++){
            target.addToEnd(c[i]);
        }
    }

    //mutate - This method is called to mutate the genes.
        //1) With mutationRate chance add a randomly selected character to a
            //randomly selected position in genes.
        //2) With mutationRate chance delete a single character from a randomly
            //selected position of genes but do this only if its length is at least 1.
        //3) For each character in genes:
            //with mutationRate chance the character is replaced by a randomly
            //selected character.
    public void mutate() {
        Random rand = new Random();
        //add a randomly selected character to a randomly selected position in genes.
        if (rand.nextInt(100) + 1 <= 17) {
            getRandomGene(rand);
            genes.addBefore(getRandomLetter(rand));
        }
        //delete a single character from a random position of genes only if length > 0
        if (rand.nextInt(100) + 1 <= mutationRate*100 && genes.size() > 0) {
            getRandomGene(rand);
            genes.remove();
        }

        //FOR ALL CHARACTERS chance the character is replaced by a randomly selected character.
        genes.first();
        for(int i = 0; i < genes.size(); i++){
            if (rand.nextInt(100) + 1 <= mutationRate*100){
                genes.addBefore(getRandomLetter(rand));
                genes.remove(); //this moves current over one index this no need for next()
                //genes.next();
            } else {
                genes.next();
            }
        }
    }

    //mutate helper method
    private void getRandomGene(Random rand){
        int index  = rand.nextInt(genes.size()+1);
        //index = genes.size() == 0 ? 0 : rand.nextInt(genes.size());
        genes.first();
        for (int i = 0; i < index; i++) { //move current to index
            genes.next();
        }
    }

    //mutate helper method
    private Character getRandomLetter(Random rand){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ -'";
        Character c = letters.charAt(rand.nextInt(29));
        return c;
    }

    //crossover - This function will update its genes by crossing it over with the parent.
    //Create the new genes list by following these steps for each position in the list:
        //1) Randomly choose one of the two parent lists.
        //2) If the parent list has a character at this position (i.e. it is long enough)
            //copy that character into the new list. Otherwise end the new list here.
    public void crossover(Genome parent){
        Random rand = new Random();
        int maxGenomeLength = Math.max(genes.size(), parent.genes.size());
        MyLinkedList<Character> newGenes = new MyLinkedList<>();
        boolean inheritSelf = rand.nextBoolean(); //decides which parent passes gene
        genes.first();
        parent.genes.first();
        for(int i = 0; i < maxGenomeLength; i++){
//            if(inheritSelf){
//                if(i < genes.size()){
//                    newGenes.addToEnd(genes.current());
//                } else {
//                    genes = newGenes; //UPDATE OWN GENES
//                    return;
//                }
//            } else {
//                if(i < parent.genes.size()){
//                    newGenes.addToEnd(parent.genes.current());
//                } else {
//                    genes = newGenes; //UPDATE OWN GENES
//                    return;
//                }
//            }

            if(inheritSelf && i < genes.size()){ //pass down own genes
                newGenes.addToEnd(genes.current());
            } else if (!inheritSelf && i < parent.genes.size()){ //pass down other parents genes
                newGenes.addToEnd(parent.genes.current());
            } else {
                genes = newGenes; //UPDATE OWN GENES
                return;
            }
            genes.next();
            parent.genes.next();
        }
    }

    //fitness - Returns the fitness calculated using the following formula:
    //where l is the difference in length between genes and the target
    //d is the number of characters that are incorrect
    // (a missing character or an extra character are also considered mismatches).
    public int fitness(){
        int l = Math.abs(target.size() - genes.size());
        int d = checkIncorrectCharacters();
        int fitness = -1 * (l + d);
        return fitness;
    }

    //Helper method for fitness()
    //Find d - d is the number of characters that are incorrect
    private int checkIncorrectCharacters(){
        int d = 0;
        int lengthDifference = Math.abs(target.size()-genes.size());
        genes.first();
        target.first();
        for(int i = 0; i < genes.size(); i++){
            if (genes.current().compareTo(target.current()) == 0) { //if a match
                //nothing
            } else {
                d++;
            }
            genes.next();
            target.next();
        }
        return d + lengthDifference;
    }

    @Override
    public int compareTo(Genome g) {
        if(fitness() > g.fitness()) return 1;
        if(fitness() < g.fitness()) return -1;
        return 0;
    }

    public String toString(){
        return genes.toString();
    }
}
