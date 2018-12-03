import java.util.ArrayList;
import java.util.LinkedHashSet;
import org.unige.aims.GridWorld;
import org.unige.aims.GridWorld.Coordinate;
/*Classe che implementa un algoritmo di Pathfinding ispirato ad A*, il metodo bestMove() che viene iterato nel main fino a quando
* non si arriva alla cella target. Il percorso migliore viene scelto calcolando di volta in volta il costo H delle CLA e muovendosi nellla
* cella che piu' avvicina il robot alla fine del labirinto. Vengono escluse dalla scelta le celle gia' visitate con il metodo remCells.
* Nel caso non ci siano CLA dopo il metodo remCells il robot torna indietro e rieffettua il calcolo dei costi delle CLA.
* Se il robot torna in (0,0) l'algoritmo termina e non c'e' un percorso.
* CLA = celle libere adiacenti */
public class PathFinder {
	
	private GridWorld Grid;	//copia della griglia generata nel main su cui verra' effettuata la ricerca del percorso
	private Cell CurrentCell;  //posizione corrente del robot sulla griglia
	private int grid_size;	//dimensioni della griglia
	private ArrayList<Cell> closedList = new ArrayList<Cell>();  //lista delle celle escluse dal calcolo del percorso
	private LinkedHashSet<Cell> path = new LinkedHashSet<Cell>();  //percorso del robot da (0,0) a (grid_size-1,grid_size-1)
	
	//costruttore che copia la griglia nel parametro Grid di questa classe
	public PathFinder(GridWorld Grid, int grid_size, Cell CurrentCell) {
		this.Grid = Grid;
		this.grid_size = grid_size;
		this.CurrentCell = CurrentCell;
		closedList.add(CurrentCell);
	}
	//metodo per ottenere un oggetto ArrayList di CLA
	public ArrayList<Cell> getAdjacentFreeCells() {
		ArrayList<Cell> AdjacentFreeCells = new ArrayList<Cell>();	//creo l'arraylist che conterra' le CLA
		Iterable<Coordinate> AdjacentFreeCoordinates;  //creo l'iterable che conterra' le coordinate adiacenti e libere
		AdjacentFreeCoordinates = Grid.getAdjacentFreeCells();	//metto le coordinate adiacenti e libere in un oggetto iterable
		//per ogni oggetto Coordinate creo un oggetto Cell con gli stessi valori per row e col
		for ( Coordinate C : AdjacentFreeCoordinates ) { 
			Cell cl = new Cell(C,CurrentCell);  
			computeH(cl);  //calcolo il suo costo F
			AdjacentFreeCells.add(cl);  // e lo aggiungo all'arraylist delle CLA
		}
		return AdjacentFreeCells;
	}
	//metodo per calcolare i costi H delle CLA
	private void computeH(Cell c) {
		//sottraggo 2 per compensare il fatto che la griglia parte da 0 e arriva a grid_size-1 per due sottrazioni
		c.setH((grid_size - c.getRow()) + (grid_size - c.getCol()) - 2);  //manhattan distance method := (Xf-Xi)+(Yf-Yi) per stimare h
	}
	//metodo che trova la cella adiacente con il costo minore con un semplice algoritmo per trovare il valore minimo in una collezione di dati
	private Cell chooseBestCell(ArrayList<Cell> AdjacentFreeCells) {
		Cell BestCell = AdjacentFreeCells.get(0); 
		int minFCost = AdjacentFreeCells.get(0).getH();
		for (Cell c : AdjacentFreeCells ) {
			if (c.getH() < minFCost) {
				minFCost = c.getH();
				BestCell = c;
			}
		}
		return BestCell;	
	}
	//metodo per rimuovere dalla lista di CLA le celle della closed list
	private void remCells(ArrayList<Cell> cl, ArrayList<Cell> fac) {
		ArrayList<Cell> markedForDel = new ArrayList<Cell>();  //lista che conterra' le celle da eliminare dalla lista delle CLA
		for (Cell oc : fac) {  //prendo una per una le CLA 
			for (Cell cc : cl) {  //e le comparo con tutti gli elementi della closed list
				if (cc.getRow() == oc.getRow() && cc.getCol() == oc.getCol()) {  //se trovo una corrispondenza 
					markedForDel.add(oc);  //la aggiungo alla lista delle celle da eliminare
					break;  //e passo al controllo della prossima CLA
				}
			}
		}
	    fac.removeAll(markedForDel);  //CLA - closed list
	}
	//metodo per evitare di aggiungere alla closed list celle gia' presenti in essa
	private boolean isInClosedList(Cell c) {
		for (Cell cl : closedList) {
			if (c.getRow() == cl.getRow() && c.getCol() == cl.getCol())
				return false;
		}
		return true;
	}
	//metodo che torna indietro nel caso non ci siano CLA,rimuovendo l' ultima cella dal percorso
	private void getBack() {
		Movement dir = new Movement();
		path.remove(CurrentCell); 
		Grid.moveToAdjacentCell(dir.computeDirection(CurrentCell,CurrentCell.getPreviousCell()));
		CurrentCell = CurrentCell.getPreviousCell();		
	}
	//metodo per scegliere la cella migliore in cui fare il prossimo spostamento
	public void bestMove() {
		Movement dir = new Movement();  //creo l'oggetto che gestisce gli spostamenti
		path.add(CurrentCell); //aggiungo la cella corrente alla lista percorso
		ArrayList<Cell> tempCells = getAdjacentFreeCells();  //creo un arraylist di CLA
		remCells(closedList,tempCells);  //rimuovo le CLA che sono presenti nella closed list
		if((CurrentCell.getRow() == 0 && CurrentCell.getCol() == 0) && tempCells.size() == 0 ) { //se sono tornato in 0,0 e non ho CLA il labirinto non ha uscita
			System.out.println("Nessun percorso!");
			System.exit(0);
		}
		if (tempCells.size() != 0) {  //se ho spostamenti possibili mi muovo nella direzione migliore
			Grid.moveToAdjacentCell(dir.computeDirection(CurrentCell,chooseBestCell(tempCells)));  
			CurrentCell = chooseBestCell(tempCells); //aggiorno la posizione
			if(isInClosedList(CurrentCell)) //se non e' gia' nella closed list aggiungo la posizione alla lista
			closedList.add(CurrentCell);	
		}
		else
			getBack();  //altrimenti torno indietro	
	}
	//metodo che stampa il percorso trovato dall' algoritmo
	public void printPath() {
		for (Cell c : path)
			System.out.print("("+c.getRow()+", "+c.getCol()+") ");
	}	
}
	
	
	
	

	

