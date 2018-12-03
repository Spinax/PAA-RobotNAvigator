import org.unige.aims.GridWorld;

public class Main {

	public static void main(String[] args) {
		//breve gestione degli errori che potrebbero essere presenti nell' input
		//dato che il programma deve prendere in input 3 dati se questo non avviene stampo un messaggio di errore ed esco
		if(args.length != 3) {
			System.out.println("Three arguments required.");
			System.exit(0);
		}
		//dato che il programma richiede che la grid_dim sia positiva se questo non avviene stampo un messaggio di errore ed esco
		if(Integer.parseInt(args[0]) <= 0) {
			System.out.println("Grid dimension must be a positive number.");
			System.exit(0);
		}
		//dato che il programma richiede che la obs_density sia compresa tra (0,1) se questo non avviene stampo un messaggio di errore ed esco
		if(Double.parseDouble(args[1]) <= 0 || Double.parseDouble(args[1]) >= 1 ) {
			System.out.println("Obstacle density must be a number in the (0,1) interval.");
			System.exit(0);
		}
		//i tre parametri del costruttore di GridWorld
		int grid_dim = Integer.parseInt(args[0]);  //dimensioni della griglia quadrata
		double obs_density = Double.parseDouble(args[1]);  //densita' degli ostacoli
		long seed = Long.parseLong(args[2]);  //seme per la generazione casuale
		//creazione strutture dati
		GridWorld Grid = new GridWorld(grid_dim,obs_density,seed);  //creo la griglia con i dati presi in input
		Cell CurrentCell = new Cell(Grid.getCurrentCell());  //salvo la posizione iniziale (0,0) in un cella
		CurrentCell.setH((grid_dim*2)-2);  //computeH in PathFinder spiega perche' sottraggo 2
		PathFinder alg = new PathFinder(Grid,grid_dim,CurrentCell);  //e li passo come argomento al costruttore della classe che trovera' il percorso
		do {
			alg.bestMove();  //ciclo l'algoritmo di pathfinding finche' arrivo nella cella target
			} while(!Grid.targetReached());
		alg.bestMove();
		alg.printPath(); 
		
		
		
		
		
		
		
		
		

	}

}
