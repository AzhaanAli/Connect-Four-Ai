package com.company;

import java.util.ArrayList;

public class AI {
    //The highest value index is the column the AI will choose.
    /*
      - each indices value is determined by the possible amount of wins - the possible amount of losses that column has if played there.
     */
    int[] priority;

    //An imaginary game within the AIs head to do its simulations.
    Game sim;

    //The amount of moves the AI looks into the future.
    int vision;

    /***********
     Constructors
     **********/
    public AI(){
        this.priority = new int[7];
        this.sim = new Game();
        this.vision = 10;
    }

    public AI(int vision){
        this.priority = new int[7];
        this.sim = new Game();
        this.vision = vision;
    }

    /***********
     Methods
     **********/
    //Sets the sim to the most recent play of the game.
    public void setSim(Game other){
        sim.setGame(other);
    }

    //Gets the priority of a column on the board.
    public int getPriority(int column, Game original, int depth){
        int num = 0;
        if(depth >= 0) {
            //For every open slot, place a coin and see if you win.
            if (sim.isOpen(column)) {
                sim.setGame(original);
                //Place a coin at i and see if you won, if you didn't then go deeper.
                sim.placeCoin(column);
                sim.switchTurn();
                if (sim.hasWon(1)) {
                    sim.setGame(original);
                    //Slightly higher priority for moves coming up soon.
                    return (this.vision - depth) * -1;
                } else if (sim.hasWon(2)) {
                    sim.setGame(original);
                    //Slightly higher priority for moves coming up soon.
                    return (this.vision - depth);
                }else{
                    for (int i = 0; i < 7; i++) {
                        num += this.getPriority(i, sim, depth - 1);
                    }
                }
            }
        }
        sim.setGame(original);
        return num;
    }

    //Prints the ais probability array.
    public void printPriority(){
        for(int i = 0; i  < this.priority.length; i++){
            System.out.print(this.priority[i] + "   ");
        }
        System.out.println();
    }

    //Updates the priority of all possible moves.
    public void updatePriority(){
        Game original = new Game();
        original.setGame(sim);
        //Call get priority on all possible slots.
        for(int i = 0; i  < this.priority.length; i++){
            this.priority[i] = this.getPriority(i, sim, vision);
            //Revert sim back to the game board so it doesn't screw anything up.
            sim.setGame(original);
        }

        //Check whether you can win or block a win next turn, if you can then go there.
        for(int i = 0; i  < this.priority.length; i++){
            sim.setGame(original);
            if (sim.isOpen(i)) {
                //Check if player will win on next turn.
                sim.switchTurn();
                sim.placeCoin(i);
                if (sim.hasWon(1)) {
                    this.priority[i] = 9999999;
                }else{
                    //Check if ai will win on next turn.
                    sim.setGame(original);
                    sim.placeCoin(i);
                    if (sim.hasWon(2)) {
                        this.priority[i] = 99999999;
                    }
                }
            }else{
                //Its not open, so make it really really low priority.
                this.priority[i] = -9999999;
            }
        }
        sim.setGame(original);
    }

    //Returns where it wants to go next.
    public int getMove(){
        int max = this.priority[0];
        //Set max variable.
        for(int i = 0; i  < this.priority.length; i++){
            if(max < this.priority[i]){
                max = this.priority[i];
            }
        }

        //Holds the possible best moves.
        ArrayList<Integer> bestMoves = new ArrayList<>();

        //Return index of max variable.
        for(int i = 0; i  < this.priority.length; i++){
            if(max == this.priority[i]){
                while(!sim.isOpen(i)) {
                    i++;
                }
                bestMoves.add(i);
            }
        }
        //Return a random place in bestMove
        return bestMoves.get((int)(Math.random() * bestMoves.size()));
    }
}
