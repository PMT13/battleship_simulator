// Written by Peng Mong Thao (thao0577)

import java.util.Scanner;

public class BattleBoatsBoard{
	private String[][] board;
	private String[][] displayedBoard;
	private String mode;
	private int totalShots=0;
	private int turns = 0;
	private int shipsAlive;
	private int boat1; private int boat2; private int boat3; private int boat4; private int boat5;
	private int boat6; private int boat7; private int boat8; private int boat9; private int boat10;

	public static void main(String[] args) {
		int whatToDo = 0;											// just a variable to keep the game going until the user quits or wins the game
		int missileUsesExpert = 2;									// Sets the number of uses of a missile to 2 for expert mode
		int missileUsesStandard = 1;
		int scanUsesExpert = 2;										// Sets the number of uses of scan power to 2 for expert mode
		int scanUsesStandard = 1;
		BattleBoatsBoard newBoard = new BattleBoatsBoard();
		newBoard.boardSetup();
		newBoard.placeBoats();
		while (whatToDo == 0) {
			System.out.println("\nTurns: "+newBoard.turns+"\nTotal Shots : "+newBoard.totalShots+"\nShips Left: "+newBoard.shipsAlive+"\n");
			System.out.println(newBoard.display());
			System.out.println("Type the corresponding number of what you want to do next:\n1. Fire \n2. Missile (1 use for Standard and 2 for Expert)" +
					"\n3. Drone (1 use for Standard and 2 for Expert)\n4. Show ships\n5. Quit\n");
			Scanner choiceScanner = new Scanner(System.in);
			int choice = choiceScanner.nextInt();
			if(choice == 1) {
				int row = 0;
				int column = 0;
				boolean withinBounds = false;										    // this is the variable used to keep track of
				while (withinBounds == false) {
				System.out.println("Type the row number you would like to attack.");
				Scanner rowScanner = new Scanner(System.in);
				row = rowScanner.nextInt();
				System.out.println("Type the column number you would like to attack.");
				Scanner columnScanner = new Scanner(System.in);
				column = columnScanner.nextInt();
					if (newBoard.mode.equals("Standard")) {
						if (row >= 8 || column >= 8 || row < 0 || column < 0) {
							System.out.println("Invalid row or column given");
							withinBounds = false;
						} else {
							withinBounds = true;
						}
					}
					if (newBoard.mode.equals("Expert")) {
						if (row >= 12 || column >= 12 || row < 0 || column < 0) {
							System.out.println("Invalid row or column given");
							withinBounds = false;
						} else {
							withinBounds = true;
						}
					}
				}
				newBoard.fire(row,column);
				} 															//choice 1 bracket
			if(choice == 2) {
				if (newBoard.mode.equals("Standard")) {
					if (missileUsesStandard > 0) {
						newBoard.missile();
						missileUsesStandard--;
					}else{
						System.out.println("You don't have anymore missiles!");
					}
				}
				if (newBoard.mode.equals("Expert")) {
					if (missileUsesExpert > 0) {
						newBoard.missile();
						missileUsesExpert--;
					}else{
						System.out.println("You don't have anymore missiles!");
					}
				}
			} 																// choice 2 bracket
			if(choice == 3){
				if (newBoard.mode.equals("Standard")) {
					if (scanUsesStandard > 0) {
						System.out.println("There are "+newBoard.drone()+" targets in the area.");
						scanUsesStandard--;
					}else{
						System.out.println("You don't have anymore drones!");
					}
				}
				if (newBoard.mode.equals("Expert")) {
					if (scanUsesExpert > 0) {
						System.out.println("There are "+newBoard.drone()+" targets in the area.");
						scanUsesExpert--;
					}else{
						System.out.println("You don't have anymore drones!");
					}
				}
			}
			if(choice == 4){
				System.out.println(newBoard.print());
			} 																// choice 4 bracket
			if(choice == 5){
				whatToDo = 1;
			} 																// choice 5 bracket (Quits by getting out of the "if whatToDo == 0" loop)
			if(newBoard.shipsAlive == 0){			// if you win the game
				System.out.println(newBoard.display());
				System.out.println("\nCongratulations, You've won the game!\nIt took you "+newBoard.turns+" turns and "+newBoard.totalShots+" shots.");
				whatToDo = 1;
			}
		}
	} //main bracket

	public String[][] boardSetup(){
		int correctInput = 0;
		String boardPrint = "";
		while(correctInput == 0){		// loop will run until user gives a valid input
			System.out.println("Type the corresponding number of what mode you would like to play in:\n1. Standard\n2. Expert\n");
			Scanner scannerOne = new Scanner(System.in);
			int modeScan = scannerOne.nextInt();
			if(modeScan == 1){
				board = new String[8][8];
				displayedBoard = new String[8][8];
				mode = "Standard";
				boat1 = 2; boat2 = 3; boat3 = 3; boat4 = 4; boat5 = 5;			// each one represents the length of a boat
				shipsAlive = 5;
				correctInput = 1;
			}
			if(modeScan == 2){
				board = new String[12][12];
				displayedBoard = new String[12][12];
				mode = "Expert";
				boat1 = 2; boat2 = 2; boat3 = 3; boat4 = 3; boat5 = 3;
				boat6 = 3; boat7 = 4; boat8 = 4; boat9 = 5; boat10 = 5;
				shipsAlive = 10;
				correctInput = 2;
			}
		}
		for(int row = 0; row < board.length;row++){			// this loop just prints out starting-empty board
			for(int column = 0; column < board[row].length;column++){
				board[row][column] = " - ";
				displayedBoard[row][column] = " - ";
				if (column == board[row].length - 1){
					boardPrint = boardPrint + " -\n";	// goes to the next line on the board if it reaches length 8 or 12 depending on mode game is in
				}else{
					boardPrint = boardPrint + " - ";
				}
			}
		}
		return board;
	} //boardSetup bracket

	public void placeBoats(){
		int shipsLeft = 0;
		int[] boats = {};
		int i = 0;													// i is just a variable used to keep track of an array index
		int boatNum = 0;
		if(mode.equals("Standard")){
			shipsLeft = 5;											// this represents how many more boats need to be placed
			boats = new int[]{2,3,3,4,5};							// "boats" is an array of the length of the boats needed to be placed
		}else{
			if(mode.equals("Expert")){
				shipsLeft = 10;
				boats = new int[]{2, 2, 3, 3, 3, 3, 4, 4, 5, 5};
			}
		}
			while(shipsLeft > 0) {
				int openSlotR = 0;										// keeps track of open slots to the right and below the randomly chosen point
				int openSlotL = 0;										// keeps track of open slots to the left and above the randomly chosen point
				int row = (int) Math.floor(Math.random()*10)%8;
				int column = (int) Math.floor(Math.random()*10)%8;
				int o = 1;												// o is used to check on the slots around the randomly chosen point
				int layout = (int) Math.floor(Math.random()*10)%2;		// 0 = vertically placed boat  &  1 = horizontally placed boat
				Boolean openSlotBool = true;
				Boolean placedBoat = false;								// my loop places one boat at a time so this boolean placedBoat makes it so that when a boat is placed at the
				if(board[row][column].equals(" - ")) {					// 	random slot, the loop runs again to the next boat it needs to set.
					if (layout == 1) {
						while (openSlotBool == true && column + o < board[row].length) {	// used to loop through consecutive slots of a row or column until a non-empty one is reached
							if (board[row][column + o].equals(" - ")) {						// checks to see if slot is empty
								openSlotR++;
								o++;										// "o" represents the # of open slots there are in that row or column
							} else {
								openSlotBool = false;
							}
						} //while loop
						openSlotBool = true;
						o = 1;
						while (openSlotBool == true && column - o >= 0) {
							if (board[row][column - o].equals(" - ")) {
								openSlotL++;
								o++;
							} else {
								openSlotBool = false;
							}
						} // while loop
						while (placedBoat == false) {
							if (openSlotR >= boats[i]) {			// if there are enough slots to the right of the random slot, the boat will be placed starting from the random slot
								for (int x = 0; x < boats[i]; x++) {		// to the right until the right length of the boat is reached
									if (board[row][column + x].equals(" - ")) {
										board[row][column + x] = " " + boatNum + " ";
									}
								}
								shipsLeft = shipsLeft - 1;
								i++;
								boatNum++;
								placedBoat = true;				// restarts loop
							} else {
								if (openSlotL >= boats[i]) {		// if there are enough slots to the left of the random slot, the boat will be placed starting from the random slot
									for (int x = 0; x < boats[i]; x++) {			// to the left until the right length of the boat is reached
										if (board[row][column - x].equals(" - ")) {
											board[row][column - x] = " " + boatNum + " ";
										}
									}
									shipsLeft = shipsLeft - 1;
									i++;
									boatNum++;
									placedBoat = true;
								} else {
									if (openSlotR + openSlotL >= (boats[i])) {			// if there's neither enough slots open to the right or to the left of the randomly chosen slot but
										for (int x = 1; x <= boats[i]-openSlotR; x++) {		// there is enough if you combine the open slots on the left with the right, this function runs
											if (board[row][column - x].equals(" - ")) {
												board[row][column - x] = " " + boatNum + " ";  //changes the empty slots on the left to the identifier of the boat
											}
										}
										for (int x = 0; x < openSlotR; x++) {					//changes the empty slots on the right to the identifier of the boat
											if (board[row][column + x].equals(" - ")) {
												board[row][column + x] = " " + boatNum + " ";
											}
										}
										//boats[i] = 0;
										shipsLeft = shipsLeft - 1;
										i++;
										boatNum++;
										placedBoat = true;
									} else {
										placedBoat = true;			// this happens if none of the previous 'if' conditions were met (no open slots to place boat around)
									}								//  	Makes the loop restart and try again until it finds a viable slot
								}
							}
						}
					} //if layout == 1 bracket
					if (layout == 0) {								// this if statement and everything below it are essentially the same thing as above but instead of placing the
						while (openSlotBool == true && row + o < board.length) {		// boat horizontally, we're doing everything vertically
							if (board[row + o][column].equals(" - ")) {
								openSlotR++;
								o++;
							} else {
								openSlotBool = false;
							}
						} //while loop
						openSlotBool = true;
						o = 1;
						while (openSlotBool == true && row - o >= 0) {
							if (board[row - o][column].equals(" - ")) {
								openSlotL++;
								o++;
							} else {
								openSlotBool = false;
							}
						} // while loop
						while (placedBoat == false) {
							if (openSlotR >= boats[i]) {
								for (int x = 0; x < boats[i]; x++) {
									if (board[row + x][column].equals(" - ")) {
										board[row + x][column] = " " + boatNum + " ";
									}
								}
								//boats[i] = 0;
								shipsLeft = shipsLeft - 1;
								i++;
								boatNum++;
								placedBoat = true;

							} else {
								if (openSlotL >= boats[i]) {
									for (int x = 0; x < boats[i]; x++) {
										if (board[row - x][column].equals(" - ")) {
											board[row - x][column] = " " + boatNum + " ";
										}
									}
									//boats[i] = 0;
									shipsLeft = shipsLeft - 1;
									i++;
									boatNum++;
									placedBoat = true;
								} else {
									if (openSlotR + openSlotL >= (boats[i])) {
										for (int x = 1; x <= boats[i]-openSlotR; x++) {
											if (board[row - x][column].equals(" - ")) {
												board[row - x][column] = " " + boatNum + " ";
											}
										}
										for (int x = 0; x < openSlotR; x++) {
											if (board[row + x][column].equals(" - ")) {
												board[row + x][column] = " " + boatNum + " ";
											}
										}
										//boats[i] = 0;
										shipsLeft = shipsLeft - 1;
										i++;
										boatNum++;
										placedBoat = true;
									} else {
										placedBoat = true;
									}
								}
							}
						}
					}
				}
			} // while shipsLeft != 0 bracket
	} //placeBoats method bracket

	public void fire(int row, int column) {
			if (board[row][column].equals(" - ") && displayedBoard[row][column].equals(" - ")) { 		// confirms that no boats are here and that user hasn't already fired at this spot
				System.out.println("Miss!");
				displayedBoard[row][column] = " X ";
				turns++;
				totalShots++;
			} else {
				if (displayedBoard[row][column].equals(" X ") || displayedBoard[row][column].equals(" O ")) {
					System.out.println("You've already fired here...Penalty (unless using missile)");
					turns = turns + 2;
					totalShots++;
				} else {
					if (!board[row][column].equals(" - ")) {			// if a slot != " - " then it equals a number which means there is a boat there
						System.out.println("Hit!");
						if (board[row][column].equals(" 0 ")) {					// all these similar looking "if" statements check to see if boat has been sunk
							boat1--;
							if (boat1 == 0) {
								System.out.println("You have sunk a boat!");
								shipsAlive--;
							}
						}
						if (board[row][column].equals( " 1 ")) {
							boat2--;
							if (boat2 == 0) {
								System.out.println("You have sunk a boat!");
								shipsAlive--;
							}
						}
						if (board[row][column].equals( " 2 ")) {
							boat3--;
							if (boat3 == 0) {
								System.out.println("You have sunk a boat!");
								shipsAlive--;
							}
						}
						if (board[row][column].equals( " 3 ")) {
							boat4--;
							if (boat4 == 0) {
								System.out.println("You have sunk a boat!");
								shipsAlive--;
							}
						}
						if (board[row][column].equals( " 4 ")) {
							boat5--;
							if (boat5 == 0) {
								System.out.println("You have sunk a boat!");
								shipsAlive--;
							}
						}
						if (mode.equals("Expert")) {
							if (board[row][column].equals( " 5 ")) {
								boat6--;
								if (boat6 == 0) {
									System.out.println("You have sunk a boat!");
									shipsAlive--;
								}
							}
							if (board[row][column].equals( " 6 ")) {
								boat7--;
								if (boat7 == 0) {
									System.out.println("You have sunk a boat!");
									shipsAlive--;
								}
							}
							if (board[row][column].equals( " 7 ")) {
								boat8--;
								if (boat8 == 0) {
									System.out.println("You have sunk a boat!");
									shipsAlive--;
								}
							}
							if (board[row][column].equals( " 8 ")) {
								boat9--;
								if (boat9 == 0) {
									System.out.println("You have sunk a boat!");
									shipsAlive--;
								}
							}
							if (board[row][column].equals( " 9 ")) {
								boat10--;
								if (boat10 == 0) {
									System.out.println("You have sunk a boat!");
									shipsAlive--;
								}
							}
						}
						displayedBoard[row][column] = " O ";
						turns++;
						totalShots++;
						}	// "if the user hit a boat" bracket
					}
				}
	} // fire() bracket

	public void missile(){
		boolean withinBounds = false;
		int row=0;
		int column=0;
		int turnsMinus = 0;					// had to create a "turnsMinus" and "shotsMinus" variable because, since I"m calling my fire() method to help with this function, the turns
		int shotsMinus = 0;					//  and shots were being incremented each time I called the fire() method and we only wanted to increment turns by 1 for the powers.
		int base = 0;
		if(mode.equals("Standard")){
			base = 8;
		}else{
			if(mode.equals("Expert")){
				base = 12;
			}
		}
		while (withinBounds == false) {
			System.out.println("Type the row number you would like to launch the missile at. (First row is Row 0)");
			Scanner rowScanner = new Scanner(System.in);
			row = rowScanner.nextInt();
			System.out.println("Type the column number you would like to launch the missile at. (First column is Column 0)");
			Scanner columnScanner = new Scanner(System.in);
			column = columnScanner.nextInt();
				if (row >= base || column >= base || row < 0 || column < 0) {
					System.out.println("Invalid row or column given");
					withinBounds = false;
				} else {
					if (displayedBoard[row][column].equals(" X ") || displayedBoard[row][column].equals(" O ")) {
						turns = turns - 1;   		// had to do this because the user shouldn't receive a +1 turn penalty for firing in the same spots when using the missile
					}
					fire(row,column);
					if (row - 1 >= 0 && column + 1 < base) {
						if (displayedBoard[row-1][column+1].equals(" X ") || displayedBoard[row-1][column+1].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row - 1, column + 1);
						turnsMinus++;
						shotsMinus++;
					}
					if (column + 1 < base) {
						if (displayedBoard[row][column+1].equals(" X ") || displayedBoard[row][column+1].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row, column + 1);
						turnsMinus++;
						shotsMinus++;
					}
					if(row + 1 < base && column + 1 < base) {
						if (displayedBoard[row+1][column+1].equals(" X ") || displayedBoard[row+1][column+1].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row + 1, column+1);
						turnsMinus++;
						shotsMinus++;
					}
					if(row - 1 >= 0) {
						if (displayedBoard[row-1][column].equals(" X ") || displayedBoard[row-1][column].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row - 1, column);
						turnsMinus++;
						shotsMinus++;
					}
					if(row  + 1 < base) {
						if (displayedBoard[row+1][column].equals(" X ") || displayedBoard[row+1][column].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row + 1, column);
						turnsMinus++;
						shotsMinus++;
					}
					if(row - 1 >= 0 && column-1 >= 0) {
						if (displayedBoard[row-1][column-1].equals(" X ") || displayedBoard[row-1][column-1].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row - 1, column - 1);
						turnsMinus++;
						shotsMinus++;
					}
					if (column - 1 >= 0) {
						if (displayedBoard[row][column-1].equals(" X ") || displayedBoard[row][column-1].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row, column - 1);
						turnsMinus++;
						shotsMinus++;
					}
					if(row + 1 < base && column - 1 >= 0) {
						if (displayedBoard[row+1][column-1].equals(" X ") || displayedBoard[row+1][column-1].equals(" O ")) {
							turns = turns - 1;
						}
						fire(row + 1, column - 1);
						turnsMinus++;
						shotsMinus++;
					}
					withinBounds = true;
					turns = turns - (turnsMinus);
					totalShots = totalShots - (shotsMinus+1);
				}
		} //withinBounds == false bracket
	} //missile() bracket
	public int drone() {
		int scannedBoats = 0;
		boolean withinBounds = false;
		int base = 0;
		if(mode.equals("Standard")){
			base = 8;
		}else{
			if(mode.equals("Expert")){
				base = 12;
			}
		}
		while (withinBounds == false) {
			System.out.println("Would you like to scan a row or column? Type the corresponding number.\n1. Row\n2. Column");
			Scanner rowOrColumnScanner = new Scanner(System.in);
			int scanChoice = rowOrColumnScanner.nextInt();
			if (scanChoice > 2 || scanChoice < 0) {
				System.out.println("Invalid input given");
			}else {
				withinBounds = true;
				if (scanChoice == 1) {
					System.out.println("Type the row number you would like to scan. (First row is Row 0)");
					Scanner rowScanScanner = new Scanner(System.in);
					int rowScanChoice = rowScanScanner.nextInt();
					if (rowScanChoice > base || rowScanChoice < 0) {
						System.out.println("Invalid row given");
					}else{
						for(int i = 0; i < board.length;i++){
							if(!board[rowScanChoice][i].equals(" - ")){					// checks for boats
								if(!displayedBoard[rowScanChoice][i].equals(" O ") && !displayedBoard[rowScanChoice][i].equals(" X ")) {    // checks that player hasn't already fired here
									displayedBoard[rowScanChoice][i] = " B ";
								}
								scannedBoats++;
							}
						}
						turns++;
						return scannedBoats;
					}
				}else {
					if (scanChoice == 2) {
						System.out.println("Type the column number you would like to scan. (First column is Column 0)");
						Scanner columnScanScanner = new Scanner(System.in);
						int columnScanChoice = columnScanScanner.nextInt();
						if (columnScanChoice > base || columnScanChoice < 0) {
							System.out.println("Invalid row given");
						}else{
							for(int i = 0; i < board.length;i++){
								if(!board[i][columnScanChoice].equals(" - ")){
									if(!displayedBoard[i][columnScanChoice].equals(" O ") && !displayedBoard[i][columnScanChoice].equals(" X ") ) {
										displayedBoard[i][columnScanChoice] = " B ";
									}
									scannedBoats++;
								}
							}
							turns++;
							return scannedBoats;
						}
					} 									// if(scanChoice == 2) bracket
				}
			}
		} 												// while(withinBounds == false) bracket
		return drone();
	} // drone() bracket
	public String print() {
		String boardPrint = "Board with Ships Shown:\n";
		if (mode.equals("Standard")) {
			boardPrint = "     \n     0  1  2  3  4  5  6  7\n   -------------------------\n";
		} else {
			if (mode.equals("Expert")) {
				boardPrint = "     \n     0  1  2  3  4  5  6  7  8  9  10 11\n   -------------------------------------\n";
			}
		}
		for (int row = 0; row < board.length; row++) {
			String rowLabel = row + "| ";
			if (row < 10) {
				rowLabel = row + " | ";
			}
			for (int column = 0; column < board[row].length; column++) {
				if (column == 0) {
					boardPrint += rowLabel + board[row][column];
				} else {
					if (column == board[row].length - 1) {
						boardPrint = boardPrint + board[row][column] + "\n";            // goes to the next line on the board
					} else {
						boardPrint = boardPrint + board[row][column];
					}
				}
			}
		}
		return boardPrint;
	}

	public String display() {
		String displayedBoardPrint = "";
		if (mode.equals("Standard")) {
			displayedBoardPrint += "     0  1  2  3  4  5  6  7\n   -------------------------\n";
		} else {
			if (mode.equals("Expert")) {
				displayedBoardPrint += "     0  1  2  3  4  5  6  7  8  9  10 11\n   -------------------------------------\n";
			}
		}
		for (int row = 0; row < displayedBoard.length; row++) {
			String rowLabel = row + "| ";
			if(row < 10){
				rowLabel = row + " | ";
			}
			for (int column = 0; column < displayedBoard[row].length; column++) {
				if (column == 0) {
					displayedBoardPrint += rowLabel + displayedBoard[row][column];
				} else {
					if (column == board[row].length - 1) {
						displayedBoardPrint += displayedBoard[row][column] + "\n";
					} else {
						displayedBoardPrint += displayedBoard[row][column];
					}
				}
			}
		}
		return displayedBoardPrint;
	}
} //class bracket