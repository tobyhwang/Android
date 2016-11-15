package project_4.cs478.hwang62.tictactoe;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Init restart button
    private Button mRestart;

    //init all the buttons for the game
    private TextView mSquareOne;
    private TextView mSquareTwo;
    private TextView mSquareThree;
    private TextView mSquareFour;
    private TextView mSquareFive;
    private TextView mSquareSix;
    private TextView mSquareSeven;
    private TextView mSquareEight;
    private TextView mSquareNine;

    //Create a list of buttons
    private TextView[][] squareArray;


    //Values for the handler
    private static final int UPDATE_X = 0;
    private static final int UPDATE_O = 1;
    private static final int NEXT_MOVE = 2;

    //Create the two threads
    HandlerThread t1;
    HandlerThread t2;

    CompPlayer1 player1;
    CompPlayer2 player2;

    //Check if the game is playing
    Boolean isPlaying = false;

    //Statues update
    TextView tv;

    GameAction game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the restart button
        mRestart = (Button) findViewById(R.id.restart);

        //Status updates
        tv = (TextView) findViewById(R.id.textView);

        //add each square to an array
        mSquareOne = (TextView)findViewById(R.id.txtOne);
        mSquareTwo = (TextView) findViewById(R.id.txtTwo);
        mSquareThree = (TextView) findViewById(R.id.txtThree);
        mSquareFour = (TextView) findViewById(R.id.txtFour);
        mSquareFive = (TextView) findViewById(R.id.txtFive);
        mSquareSix = (TextView) findViewById(R.id.txtSix);
        mSquareSeven = (TextView) findViewById(R.id.txtSeven);
        mSquareEight = (TextView) findViewById(R.id.txtEight);
        mSquareNine = (TextView) findViewById(R.id.txtNine);

        //set the 2d array with each text view
        squareArray = new TextView[][] {{mSquareOne, mSquareTwo, mSquareThree }, {mSquareFour, mSquareFive, mSquareSix }, {mSquareSeven, mSquareEight, mSquareNine}};

        //create a new instance of Game Action
        game = new GameAction(squareArray);

        //set click action
        mRestart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //reset the game
                Restart();

                //Start worker thread 1
                t1 = new HandlerThread("Player 1");
                t1.start();

                //Start worker thread 2
                t2 = new HandlerThread("Player 2");
                t2.start();

                //create and instance of each player
                //attaches looper to handler
                player1 = new CompPlayer1(t1.getLooper());
                player2 = new CompPlayer2(t2.getLooper());
                isPlaying = true;

                //start player 1
                player1.sendEmptyMessage(NEXT_MOVE);
            }
        });
    }


    //This player always goes first and uses only X's
    public class CompPlayer1 extends Handler{

        //init the position
        private int posX;
        private int posY;

        //Random number generator
        Random rand = new Random();

        //constructor
        public CompPlayer1(Looper loop){
            super(loop);
        }

        //Create the handle for player 1
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case NEXT_MOVE:
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                    Move();
            }
        }

        //perform a random move
        public void Move(){
            Message msg1 = mHandleUI.obtainMessage(UPDATE_X);

            //loop through until an empty spot is randomly found
            while(true)
            {
                posX = rand.nextInt(3);
                posY = rand.nextInt(3);

                if (squareArray[posX][posY].getText().equals("")) {
                    msg1.arg1 = posX;
                    msg1.arg2 = posY;
                    mHandleUI.sendMessage(msg1);
                    return;
                }
            }

        }

    }

    //This is the second player and it only plays O's
    public class CompPlayer2 extends Handler{

        private int posX;
        private int posY;
        Random rand = new Random();

        public CompPlayer2(Looper loop){
            super(loop);
        }

        //Create a handler for player 2
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case NEXT_MOVE:
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                    Move();
            }
        }

        //perform a random move
        public void Move(){

            Message msg2 = mHandleUI.obtainMessage(UPDATE_O);

            while(true) {
                posX = rand.nextInt(3);
                posY = rand.nextInt(3);

                //loop through until an empty spot is randomly found
                if (squareArray[posX][posY].getText().equals("")) {
                    msg2.what = UPDATE_O;
                    msg2.arg1 = posX;
                    msg2.arg2 = posY;
                    mHandleUI.sendMessage(msg2);
                    return;
                }
            }

        }
    }

    //this function clears the actual board after the restart button has been clicked
    public void Restart(){
        mRestart.setText("");
        mRestart.setText("Restart");
        game.ClearBoard();
        tv.setText("");
        if(isPlaying) {
            StopThreads();
        }

    }

    //function to handle stopping the threads and clearing the messages
    public void StopThreads(){
        try {
            t1.quit();
            t2.quit();
            t1.join();
            t2.join();
            player1.removeMessages(NEXT_MOVE);
            player2.removeMessages(NEXT_MOVE);
            mHandleUI.removeMessages(UPDATE_X);
            mHandleUI.removeMessages(UPDATE_O);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //show the winner
    public void ShowWinner(String winner){
        isPlaying = false;
        tv.setText(winner);
        StopThreads();
    }

    //Create UI handler
    public Handler mHandleUI = new Handler(){
        public synchronized void handleMessage(Message msg){
            int what = msg.what;
            int gameState;

            switch(what){
                //update for player 1
                case UPDATE_X:
                    gameState = game.UpdateBoard(msg.arg1, msg.arg2, "X");
                    if(gameState == 0){
                        player2.sendEmptyMessage(NEXT_MOVE);
                    }
                    else if(gameState == 1){
                        ShowWinner("Player 1 Wins!");
                    }
                    else{
                        ShowWinner("No Winner!");
                    }
                    break;

                //update for player 2
                case UPDATE_O:
                    gameState = game.UpdateBoard(msg.arg1, msg.arg2, "O");
                    if(gameState == 0) {
                        player1.sendEmptyMessage(NEXT_MOVE);
                    }
                    else if(gameState == 1) {
                    ShowWinner("Player 2 Wins!");
                    }
                    else {
                        ShowWinner("No Winner!");
                    }
                    break;

            }
        }
    };

}
