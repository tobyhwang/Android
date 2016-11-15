package project_4.cs478.hwang62.tictactoe;

import android.widget.TextView;

/**
 * Created by Toby on 11/14/16.
 */
public class GameAction {

    //spots filled
    int spotsFilled = 0;

    //the game board
    TextView[][] squareArray;

    //constructor
    public GameAction(TextView[][] board)
    {
        this.squareArray = board;
    }


    //function to actually update the board
    //return 0 for no win, 1 for win, and 2 for draw
    public synchronized int UpdateBoard(int posX, int posY, String letter){
        int won = 0;

        if(letter.equals("X")) {
            squareArray[posX][posY].setText(letter);
            spotsFilled++;
            won = CheckWin(letter);
            return won;
        }
        else if(letter.equals("O"))
        {
            squareArray[posX][posY].setText(letter);
            spotsFilled++;
            won = CheckWin(letter);
            return won;
        }

        return 0;

    }

    //clear the board
    public void ClearBoard() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                squareArray[i][j].setText("");
            }
        }
        spotsFilled = 0;
    }


    //function to check to see who won the game
    //return 1 if someone won, 0 if no one has one yet, and 2 if it's a draw
    public int CheckWin(String player){

        //check rows
        int row = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++)
            {
                if(squareArray[i][j].getText().equals(player)){
                    if(row == 2){
                        return 1;
                    }
                    row++;
                }
            }

            row = 0;
        }


        //check columns
        int columns = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++)
            {
                if(squareArray[j][i].getText().equals(player)){
                    if(columns == 2){
                        return 1;
                    }
                    columns++;
                }
            }

            columns = 0;
        }

        //check left to right diagonal
        int rDiag = 0;
        for(int i = 0; i < 3; i++)
        {
            if(squareArray[i][i].getText().equals(player)){
                if(rDiag == 2){
                    return 1;
                }
                rDiag++;
            }

        }

        //check right to left diagonal
        int lDiag = 0;
        int j = 0;
        for(int i = 2; i > -1; i--)
        {
            if(squareArray[i][j].getText().equals(player)){
                if(lDiag == 2){
                    return 1;
                }
                lDiag++;
            }

            j++;
        }

        //check draw
        if(spotsFilled == 9){
            return 2;
        }

        return 0;

    }

}
