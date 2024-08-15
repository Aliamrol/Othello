import java.io.IOException;
import java.util.Scanner;


/**
 * The Main class of the game
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.2
 */
public class Run
{


    // for get the players inputs
    private static Scanner inputs = new Scanner(System.in);



    // this method check the player input
    private static boolean isValid(String choosenBlock)
    {
        return ((int)choosenBlock.charAt(0) < 58 && 48 < (int)choosenBlock.charAt(0))
                && 
                ((int)choosenBlock.charAt(1) < 73 && 64 < (int)choosenBlock.charAt(1));
    }


    public static void main(String[] args) throws IOException 
    {
        // calibrater the font size of the terminal screen
        Printer.calibrate(inputs);



        // initialization classes and needed variables
        Rules.initialization();
        Board gameBoard = new Board();
        Player player1, player2;
        Bot bot;
        int operand, scoreDelta = 0;
        String firstName, choosenBlock = "";

        
        while(true)
        {
            while (true)
            {
                // show the game menu
                Printer.printMenu();

                operand = inputs.nextInt();
                inputs.nextLine();

                // check the player input
                if (operand < 4 && 0 < operand)
                    break;
            }

            switch (operand)
            {
                case 1: // new Two Player game

                    // creat a new player for player 1
                    Printer.getPlayerName(1);
                    firstName = inputs.nextLine();
                    player1 = new Player(firstName, "NEVERMIND", 0, 1, 'X', "NEVERMIND");
                    player1.setScore(2);
                    
                    // creat a new player for player 2
                    Printer.getPlayerName(2);
                    firstName = inputs.nextLine();
                    player2 = new Player(firstName, "NEVERMIND", 0, -1, 'O', "NEVERMIND");
                    player2.setScore(2);


                    while (!Rules.isGameEnded(gameBoard))
                    {
                        // player 1 turn
                        while (true)
                        {
                            // show the board to player 1  
                            Rules.findSelectableBlocks(gameBoard, player1.getPlayerID());
                            Printer.printVisualBoard(gameBoard.getVisualBoard(), gameBoard.getVisualY(), gameBoard.getVisualX());
                            Printer.printPlayersScores(player1, player2);

                            // check that the player can choose any block or not
                            if (Rules.isPassed(player1))
                            {
                                Printer.passedPlayer(inputs);
                                break;
                            }

                            // get player choose
                            Printer.printTurn(player1);
                            choosenBlock = inputs.nextLine();

                            // check the player input
                            if (choosenBlock.length() > 2 || choosenBlock.length() < 2 || !isValid(choosenBlock))
                            {
                                Printer.inValidInputError(inputs);

                                Rules.reset(player1.getPlayerID());
                                gameBoard.reset();

                                continue;
                            }

                            // check the player choosen move
                            if (!Rules.isPossible(player1.getPlayerID(), choosenBlock))
                            {
                                Printer.wrongChooseError(inputs);

                                Rules.reset(player1.getPlayerID());
                                gameBoard.reset();

                                continue;
                            }


                            break;
                        }
                        // apply the player choosen move and calculate the player gained score
                        scoreDelta = Rules.applyChoose(gameBoard, player1.getPlayerID(), choosenBlock);

                        // set the players new scores
                        player1.setScore(scoreDelta + player1.getScore());
                        player2.setScore(player2.getScore() - scoreDelta + 1);

                        // reset the map
                        Rules.reset(player1.getPlayerID());
                        gameBoard.reset();


                        // player 2 turn
                        while (true)
                        {
                            // show the board to player 2
                            Rules.findSelectableBlocks(gameBoard, player2.getPlayerID());
                            Printer.printVisualBoard(gameBoard.getVisualBoard(), gameBoard.getVisualY(), gameBoard.getVisualX());
                            Printer.printPlayersScores(player1, player2);

                            // check that the player can choose any block or not
                            if (Rules.isPassed(player2))
                            {
                                Printer.passedPlayer(inputs);
                                break;
                            }
                            
                            // get player choose
                            Printer.printTurn(player2);
                            choosenBlock = inputs.nextLine();

                            // check the player input
                            if (choosenBlock.length() > 3 || choosenBlock.length() == 0 || !isValid(choosenBlock))
                            {
                                Printer.inValidInputError(inputs);

                                Rules.reset(player2.getPlayerID());
                                gameBoard.reset();

                                continue;
                            }

                            // check the player choosen move
                            if (!Rules.isPossible(player2.getPlayerID(), choosenBlock))
                            {
                                Printer.wrongChooseError(inputs);

                                Rules.reset(player2.getPlayerID());
                                gameBoard.reset();

                                continue;
                            }


                            break;
                        }
                        // apply the player choosen move and calculate the player gained score
                        scoreDelta = Rules.applyChoose(gameBoard, player2.getPlayerID(), choosenBlock);

                        // set the players new scores
                        player2.setScore(scoreDelta + player2.getScore());
                        player1.setScore(player1.getScore() - scoreDelta + 1);

                        /// reset the map
                        Rules.reset(player2.getPlayerID());
                        gameBoard.reset();
                    }

                    // show the winner
                    Printer.printVisualBoard(gameBoard.getVisualBoard(), gameBoard.getVisualY(), gameBoard.getVisualX());
                    Printer.printPlayersScores(player1, player2);
                    Rules.winner(player1, player2, inputs);

                break;
                
                case 2: // new Single player game

                    // creat a new player as player ond
                    Printer.getPlayerName(1);
                    firstName = inputs.nextLine();
                    player1 = new Player(firstName, "NEVERMIND", 0, 1, 'X', "NEVERMIND");
                    player1.setScore(2);

                    // creat a bot 
                    player2 = new Bot();
                    player2.setScore(2);
                    bot = (Bot)player2;

                    while (!Rules.isGameEnded(gameBoard))
                    {
                        // player turn
                        while (true)
                        {
                            // show the board to player 
                            Rules.findSelectableBlocks(gameBoard, player1.getPlayerID());
                            Printer.printVisualBoard(gameBoard.getVisualBoard(), gameBoard.getVisualY(), gameBoard.getVisualX());
                            Printer.printPlayersScores(player1, player2);

                            // check that the player can choose any block or not
                            if (Rules.isPassed(player1))
                            {
                                Printer.passedPlayer(inputs);
                                break;
                            }

                            // get player choose
                            Printer.printTurn(player1);
                            choosenBlock = inputs.nextLine();

                            // check the player input
                            if (choosenBlock.length() > 2 || choosenBlock.length() < 2 || !isValid(choosenBlock))
                            {
                                Printer.inValidInputError(inputs);

                                Rules.reset(player1.getPlayerID());
                                gameBoard.reset();

                                continue;
                            }

                            // check the player choosen move
                            if (!Rules.isPossible(player1.getPlayerID(), choosenBlock))
                            {
                                Printer.wrongChooseError(inputs);

                                Rules.reset(player1.getPlayerID());
                                gameBoard.reset();

                                continue;
                            }


                            break;
                        }
                        // apply the player choosen move and calculate the player gained score
                        scoreDelta = Rules.applyChoose(gameBoard, player1.getPlayerID(), choosenBlock);

                        // set the player and bot new scores
                        player1.setScore(scoreDelta + player1.getScore());
                        player2.setScore(player2.getScore() - scoreDelta + 1);

                        // reset the map
                        Rules.reset(player1.getPlayerID());
                        gameBoard.reset();

                        // bot move
                        scoreDelta = bot.botChoose(gameBoard);

                        // set the player and bot new scores
                        player2.setScore(scoreDelta + player2.getScore());
                        player1.setScore(player1.getScore() - scoreDelta + 1);
                    }

                    // show the winner
                    Printer.printVisualBoard(gameBoard.getVisualBoard(), gameBoard.getVisualY(), gameBoard.getVisualX());
                    Printer.printPlayersScores(player1, player2);
                    Rules.winner(player1, player2, inputs);

                break;

                case 3: // exit
                    return;
            }

            gameBoard = new Board();
            Rules.reset(1);
            Rules.reset(-1);
        }
    }

}
