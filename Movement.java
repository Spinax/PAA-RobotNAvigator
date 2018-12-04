import org.unige.aims.GridWorld.Direction;
//Classe per gestire le direzioni di movimento del robot
public class Movement {
	Cell CurrentCell;  //cella in cui si trova il robot attualmente
	Cell TargetCell;  //cella in cui il robot vuole andare
	
	//costruttore per la classe
	public Movement() {}
	
	/* metodo che in base alla differenza tra le coordinate delle due posizioni
	*  riconosce se il movimento e' verso NORD,SUD,EST o OVEST, le uniche
	*  direzioni riconosciute dal robot  */
 	public Direction computeDirection(Cell CurrentCell, Cell TargetCell) {
		if ((CurrentCell.getRow() - 1 == TargetCell.getRow()) && (CurrentCell.getCol() == TargetCell.getCol()))  //stessa colonna, sale di una riga
			return Direction.NORTH;  //direzione nord
		if ((CurrentCell.getRow() + 1 == TargetCell.getRow()) && (CurrentCell.getCol() == TargetCell.getCol()))  //stessa colonna, scende di una riga
			return Direction.SOUTH;  //direzione sud
		if ((CurrentCell.getRow() == TargetCell.getRow()) && (CurrentCell.getCol() + 1 == TargetCell.getCol()))  //stessa riga, si muove a destra di una colonna
			return Direction.EAST;  //direzione est
		if ((CurrentCell.getRow() == TargetCell.getRow()) && (CurrentCell.getCol() - 1 == TargetCell.getCol()))  //stessa riga, si muove a sinistra di una colonna
			return Direction.WEST;  //direzione ovest
		else
			return null;  //nel caso le due celle non siano adiacenti
	}
}
