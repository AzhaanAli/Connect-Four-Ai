package com.company;

import java.util.Scanner;

public class Game {
    //The game board in which the game is played.
    /*
      - 0 represents an unfilled space.
      - 1 represents filled by player 1 coin.
      - 2 represents filled by player 2 coin.
     */
    int[][]board;

    //The current turn.
    /*
      - 1 represents player 1 turn.
      - 2 represents player 2 turn.
      - Turn will swap after every move.
     */
    int turn;

    //Colored text codes for print methods.
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_YELLOW = "\u001b[33;1m";
    private String ANSI_RESET = "\u001B[0m";

    /***********
     Constructors
     **********/
    //No param constructor.
    // - starts a new game with an unfilled board.
    public Game(){
        this.board = new int[6][7];
        this.turn = 1;
    }

    //One param constructor.
    // - starts a game with a board the copy of another preexisting game.
    public Game(Game other){
        this.board = other.board;
        this.turn = other.turn;
    }

    /***********
     Accessor Method
     **********/
    public int getTurn(){
        return this.turn;
    }

    /***********
     Helper Methods
     **********/
    //Helper method to return colored text for the coins on board.
    private String returnColored(int player){
        if(player == 1){
            return (ANSI_RED + "O" + ANSI_RESET);
        }else if(player == 2){
            return (ANSI_YELLOW + "O" + ANSI_RESET);
        }else{
            return("O");
        }
    }

    //Helper method returns true if column in available, otherwise returns false.
    public boolean isOpen(int column){
        //Return false if told a column that doesn't exist.
        if(column < 0){
            return false;
        }else if(column > 6){
            return false;
        }
        //Loop through to find the lowest available space a coin can be placed.
        for(int i = 0; i < this.board.length; i++){
            //If space is empty, return true.
            if(this.board[i][column] == 0){
                return true;
            }
        }
        return false;
    }

    //Helper method to switch player turns.
    public void switchTurn(){
        if(this.turn == 1){
            this.turn = 2;
        }else{
            this.turn = 1;
        }
    }

    //Helper method to get the opposite players turn.
    public int getOppTurn(){
        if(this.turn == 1){
            return 2;
        }else{
            return 1;
        }
    }

    //Helper method to set a game to an already existing one.
    public void setGame(Game other){
        this.turn = other.turn;
        for(int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board[0].length; y++) {
                this.board[x][y] = other.board[x][y];
            }
        }
    }

    //Helper method to reset a game to a new one.
    public void resetGame(){
        this.setGame(new Game());
    }



    //Helper method to show whether the game has been won or not.
    public boolean hasWon(int player){
        //Check to see if any methods of winning return true.
        //If they do, then return true as well.
        if(this.hasWonHorizontal(player)){
            return true;
        }else if(hasWonVertical(player)){
            return true;
        }else if(hasWonRightDiagonal(player)){
            return true;
        }else{
            return hasWonLeftDiagonal(player);
        }
    }

    //Checks to see if any vertical wins have been won.
    private boolean hasWonVertical(int player){
        //Loop through all possible places a vertical win could be be held.
        for(int row = 3; row < this.board.length; row++){
            for(int col = 0; col < this.board[0].length; col++){
                //If there is a win, return true.
                if(player == this.board[row][col]){
                    if(player == this.board[row - 1][col]){
                        if(player == this.board[row - 2][col]){
                            if(player == this.board[row - 3][col]){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //Checks to see if any horizontal wins have been won.
    private boolean hasWonHorizontal(int player){
        //Loop through all possible places a horizontal win could be be held.
        for(int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < 4; col++) {
                //If there is a win, return true.
                if(player == this.board[row][col]){
                    if(player == this.board[row][col + 1]){
                        if(player == this.board[row][col + 2]){
                            if(player == this.board[row][col + 3]){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //Checks to see if any right diagonal wins have been won.
    private boolean hasWonRightDiagonal(int player){
        //Loop through all possible places a right diagonal win could be be held.
        for(int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                //If there is a win, return true.
                if(player == this.board[row][col]){
                    if(player == this.board[row + 1][col + 1]){
                        if(player == this.board[row + 2][col + 2]){
                            if(player == this.board[row + 3][col + 3]){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //Checks to see if any right diagonal wins have been won.
    private boolean hasWonLeftDiagonal(int player){
        //Loop through all possible places a left diagonal win could be be held.
        for(int row = 0; row < 3; row++) {
            for (int col = 3; col < 7; col++) {
                //If there is a win, return true.
                if(player == this.board[row][col]){
                    if(player == this.board[row + 1][col - 1]){
                        if(player == this.board[row + 2][col - 2]){
                            if(player == this.board[row + 3][col - 3]){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //Checks to see if any right diagonal wins have been won.
    private boolean hasDraw(){
        //Loop through all possible places to see if nothing is open.
        for(int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if(this.board[row][col] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /***********
     Print Methods
     **********/
    //Prints the current state of the game board.
    public void print(){
        //Print the header with position numbers.
        System.out.print("   ");
        for(int i = 0; i < this.board[0].length; i++){
            System.out.print(i + "  ");
        }
        System.out.println();
        //Print the boards contents within a border.
        System.out.println("_________________________");
        for(int x = this.board.length - 1; x >= 0; x--){
            System.out.print("|  ");
            for(int y = 0; y < this.board[0].length; y++){
                System.out.print(this.returnColored(this.board[x][y]) + "  ");
            }
            System.out.println("|");
        }
        System.out.println("_________________________");
    }

    /***********
     General Methods
     **********/
    //Places a coin at a specified column.
    public void placeCoin(int column){
        //Place a coin at the lowest available space.
        for(int i = 0; i < this.board.length; i++){
            if(this.board[i][column] == 0){
                this.board[i][column] = this.turn;
                //Stop once you hav found the lowest.
                return;
            }
        }
    }

    //Simulates a turn.
    public void makeTurn(int column){
        //Check to see if you are allowed to place a coin in the specified slot.
        /*
          - If so, place a coin and change turn.
          - If not, then don't and print to pick a different spot.
         */
        if(this.isOpen(column)){
            placeCoin(column);
            this.switchTurn();
        }else{
            //Tell the player to make a valid turn.
            if(this.turn == 1){
                System.out.println(ANSI_RED + "Player 1" + ANSI_RESET + ", please choose a valid column.");
            }else{
                System.out.println(ANSI_YELLOW + "Player 2" + ANSI_RESET + ", please choose a valid column.");
            }
        }
    }

    /***********
     Run Methods
     **********/

    //Runs a game of connect four until it ends.
    //Returns the player who won. If its a draw, returns 0.
    public int run(){
        Scanner console = new Scanner(System.in);
        System.out.print("is " + ANSI_RED + "Player 1" + ANSI_RESET + " going first? (y/n): ");
        if(!new Scanner(System.in).next().equals("y")){
            this.switchTurn();
        }
        //While the game is going, keep going until someone looses.
        while(!this.hasWon(this.getOppTurn())){
            //Print the game board.
            this.print();

            //Ask the current player where they want to go.
            if(this.turn == 1){
                System.out.print(ANSI_RED + "Player 1" + ANSI_RESET + ", which column would you like to play?: ");
            }else{
                System.out.print(ANSI_YELLOW + "Player 2" + ANSI_RESET + ", which column would you like to play?: ");
            }
            //Store the players choice column as a variable
            int choice = console.nextInt();
            // Make SURE the player chooses a valid space.
            while(!this.isOpen(choice)){
                //Tell current player to make a proper choice
                if(this.turn == 1){
                    System.out.print(ANSI_RED + "Player 1" + ANSI_RESET + ", please choose a valid column.");
                }else{
                    System.out.print(ANSI_YELLOW + "Player 2" + ANSI_RESET + ", please choose a valid column.");
                }
                choice = console.nextInt();
            }
            makeTurn(choice);

            //Check if the game has ended in a draw.
            if(this.hasDraw()){
                this.print();
                System.out.println("The game has ended in a draw.");
                return 0;
            }
        }
        //Now that the game has ended, print the winning board.
        this.print();
        if(this.turn == 2){
            System.out.println(ANSI_RED + "Player 1 has won!" + ANSI_RESET);
            return 1;
        }else{
            System.out.println(ANSI_YELLOW + "Player 2 has won!" + ANSI_RESET);
            return 2;
        }
    }
    //Repeats a specified number of games and then prints the stats at the end.
    public void runBestTo(int games){
        int player1wins = 0;
        int player2wins = 0;
        int draws = 0;
        while(player1wins + player2wins < games){
            int win = this.run();
            this.resetGame();
            if(win == 1){
                player1wins++;
            }else if(win == 2){
                player2wins++;
            }else{
                draws++;
            }
        }
        System.out.println("There have been:");
        System.out.println("  - " + games + " games in total,");
        if(player1wins == 1){
            System.out.println("  - " + player1wins + " win by player 1,");
        }else{
            System.out.println("  - " + player1wins + " wins by player 1,");
        }
        if(player2wins == 1){
            System.out.println("  - " + player2wins + " win by player 2,");
        }else{
            System.out.println("  - " + player2wins + " wins by player 2,");
        }
        if(draws == 1){
            System.out.println("  - and " + draws + " draw.");
        }else{
            System.out.println("  - and " + draws + " draws.");
        }
    }

    //Runs a game of connect four until it ends.
    //Returns the player who won. If its a draw, returns 0.
    public int runAi(){
        AI ai = new AI();
        Scanner console = new Scanner(System.in);

        //While the game is going, keep going until someone looses.
        while(!this.hasWon(this.getOppTurn())){
            //Ask the current player where they want to go.
            if(this.turn == 1){
                //Print the game board.
                this.print();
                System.out.print(ANSI_RED + "Player 1" + ANSI_RESET + ", which column would you like to play?: ");
                //Store the players choice column as a variable
                int choice = console.nextInt();
                // Make SURE the player chooses a valid space.
                while(!this.isOpen(choice)){
                    //Tell current player to make a proper choice
                    System.out.print(ANSI_RED + "Player 1" + ANSI_RESET + ", please choose a valid column.");
                    choice = console.nextInt();
                }
                makeTurn(choice);

            }else{
                //Ai Time.
                ai.sim.setGame(this);
                ai.updatePriority();
                makeTurn(ai.getMove());
                //ai.printPriority();
            }


            //Check if the game has ended in a draw.
            if(this.hasDraw()){
                this.print();
                System.out.println("The game has ended in a draw.");
                return 0;
            }
        }
        //Now that the game has ended, print the winning board.
        this.print();
        if(this.turn == 2){
            System.out.println(ANSI_RED + "Player 1 has won!" + ANSI_RESET);
            return 1;
        }else{
            System.out.println(ANSI_YELLOW + "AI has won!" + ANSI_RESET);
            return 2;
        }
    }
    public int runAi(int vision){
        AI ai = new AI(vision);
        Scanner console = new Scanner(System.in);

        //While the game is going, keep going until someone looses.
        while(!this.hasWon(this.getOppTurn())){
            //Ask the current player where they want to go.
            if(this.turn == 1){
                //Print the game board.
                this.print();
                System.out.print(ANSI_RED + "Player 1" + ANSI_RESET + ", which column would you like to play?: ");
                //Store the players choice column as a variable
                int choice = console.nextInt();
                // Make SURE the player chooses a valid space.
                while(!this.isOpen(choice)){
                    //Tell current player to make a proper choice
                    System.out.print(ANSI_RED + "Player 1" + ANSI_RESET + ", please choose a valid column.");
                    choice = console.nextInt();
                }
                makeTurn(choice);

            }else{
                //Ai Time.
                ai.sim.setGame(this);
                ai.updatePriority();
                makeTurn(ai.getMove());
                //ai.printPriority();
            }


            //Check if the game has ended in a draw.
            if(this.hasDraw()){
                this.print();
                System.out.println("The game has ended in a draw.");
                return 0;
            }
        }
        //Now that the game has ended, print the winning board.
        this.print();
        if(this.turn == 2){
            System.out.println(ANSI_RED + "Player 1 has won!" + ANSI_RESET);
            return 1;
        }else{
            System.out.println(ANSI_YELLOW + "AI has won!" + ANSI_RESET);
            return 2;
        }
    }
    //Asks you who's turn it is to start.
    public int runAiwChoice(){
        System.out.print("Are you going first? (y/n): ");
        if(!new Scanner(System.in).next().equals("y")){
            this.switchTurn();
        }
        return this.runAi();
    }
    public int runAiwChoice(int vision){
        System.out.print("Are you going first? (y/n): ");
        if(!new Scanner(System.in).next().equals("y")){
            this.switchTurn();
        }
        return this.runAi(vision);
    }
    //Repeats a specified number of games and then prints the stats at the end.
    public void runAiBestTo(int games){
        int player1wins = 0;
        int player2wins = 0;
        int draws = 0;
        while(player1wins + player2wins < games){
            int win = this.runAi();
            this.resetGame();
            if(win == 1){
                player1wins++;
            }else if(win == 2){
                player2wins++;
            }else{
                draws++;
            }
        }
        System.out.println("There have been:");
        System.out.println("  - " + games + " games in total,");
        if(player1wins == 1){
            System.out.println("  - " + player1wins + " win by player 1,");
        }else{
            System.out.println("  - " + player1wins + " wins by player 1,");
        }
        if(player2wins == 1){
            System.out.println("  - " + player2wins + " win by player 2,");
        }else{
            System.out.println("  - " + player2wins + " wins by player 2,");
        }
        if(draws == 1){
            System.out.println("  - and " + draws + " draw.");
        }else{
            System.out.println("  - and " + draws + " draws.");
        }
    }
    public void runAiBestTo(int games, int vision){
        int player1wins = 0;
        int player2wins = 0;
        int draws = 0;
        while(player1wins + player2wins < games){
            int win = this.runAi(vision);
            this.resetGame();
            if(win == 1){
                player1wins++;
            }else if(win == 2){
                player2wins++;
            }else{
                draws++;
            }
        }
        System.out.println("There have been:");
        System.out.println("  - " + games + " games in total,");
        if(player1wins == 1){
            System.out.println("  - " + player1wins + " win by player 1,");
        }else{
            System.out.println("  - " + player1wins + " wins by player 1,");
        }
        if(player2wins == 1){
            System.out.println("  - " + player2wins + " win by player 2,");
        }else{
            System.out.println("  - " + player2wins + " wins by player 2,");
        }
        if(draws == 1){
            System.out.println("  - and " + draws + " draw.");
        }else{
            System.out.println("  - and " + draws + " draws.");
        }
    }

    //Runs a game of connect four until it ends.
    //Returns the player who won. If its a draw, returns 0.
    public int runAIvAI(int vision1, int vision2){
        AI ai = new AI(vision1);
        AI ai2 = new AI(vision2);


        //While the game is going, keep going until someone looses.
        while(!this.hasWon(this.getOppTurn())){
            //Ask the current player where they want to go.
            if(this.turn == 1){
                //Print the game board.
                this.print();

                //Ai Time.
                ai.sim.setGame(this);
                ai.updatePriority();
                makeTurn(ai.getMove());
                //ai.printPriority();

            }else{
                //Ai Time 2.
                ai2.sim.setGame(this);
                ai2.updatePriority();
                makeTurn(ai2.getMove());
                //ai2.printPriority();
            }


            //Check if the game has ended in a draw.
            if(this.hasDraw()){
                this.print();
                System.out.println("The game has ended in a draw.");
                return 0;
            }
        }
        //Now that the game has ended, print the winning board.
        this.print();
        if(this.turn == 2){
            System.out.println(ANSI_RED + "Player 1 has won!" + ANSI_RESET);
            return 1;
        }else{
            System.out.println(ANSI_YELLOW + "Player 2 has won!" + ANSI_RESET);
            return 2;
        }
    }
}
