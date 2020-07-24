package com.company;

public class Main {

    public static void main(String[] args) {
        Game connectFour = new Game();

        connectFour.switchTurn();
        connectFour.runAi(50);
    }
}