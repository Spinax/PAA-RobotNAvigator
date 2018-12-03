import org.unige.aims.GridWorld.Coordinate;
//Classe per definire l' unita' cella, di cui e' composto il gridWorld
//Contiene i parametri G e H per permettere di calcolare il percorso migliore usando l' algoritmo A*
public class Cell {
	
	private Cell PreviousCell; //cella precedente nel percorso,utilizzato nel calcolo di G e nel metodo getBack()
	private int row;  //numero della riga della cella
	private int col;  //numero della colonna della cella
	private int H;	//stima del numero di passi necessari ad arrivare alla posizione finale, secondo il Manhattan Distance Method

	//costruttore che crea una cella partendo da una coordinata sulla griglia,usato solo nel main per creare la cella d'origine
	public Cell(Coordinate other) {
		row = other.row;
		col = other.col;
	}
	//costruttore per le celle del percorso 
	public Cell(Coordinate other, Cell PreviousCell) {
		this.PreviousCell = PreviousCell;
		row = other.row;
		col = other.col;
	}
	//get&set necessari
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public int getH() {
		return H;
	}
	public void setH(int h) {
		H = h;
	}
	public Cell getPreviousCell() {
		return PreviousCell;
	}
}
	
	
	
