package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private TextView playeronescore,playertwoscore, playerStatus;
private Button [] buttons = new Button[9];
private Button resetGame;
private int playeronescorecount, playertwoscorecount, rountcount;
boolean activeplayer;
int[] gameState = {2,2,2,2,2,2,2,2,2};
int[][] winningposition = {
        {0,1,2},{3,4,5},{6,7,8},
        {0,3,6},{1,4,7},{2,5,8},
        {0,4,8},{2,4,6}
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playeronescore = (TextView)findViewById(R.id.playeronescore);
        playertwoscore = (TextView)findViewById(R.id.playertwoscore);
        playerStatus = (TextView)findViewById(R.id.playerStatus);
        resetGame = (Button)findViewById(R.id.resetGame);
        for(int i =0;i<buttons.length;i++) {
            String buttonID = "btn_"+i; //we get btn_2
            int resourseID = getResources().getIdentifier(buttonID,"id",getPackageName()); // here we get 2
            buttons[i] = (Button)findViewById(resourseID);
            buttons[i].setOnClickListener(this);
        }
        rountcount =0;
        playeronescorecount = 0;
        playertwoscorecount = 0;
        activeplayer = true;
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) { //checking if button is pressed before or not
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));
        if (activeplayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        rountcount++;
        if (checkWinner()) {
            if (activeplayer) {
                playeronescorecount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One has Won the Game !!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playertwoscorecount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two has Won the Game !!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (rountcount == 9) {
            playAgain();
            Toast.makeText(this, "No Winner !!", Toast.LENGTH_SHORT).show();
        } else {
            activeplayer = !activeplayer;
        }

        if(playeronescorecount > playertwoscorecount) {
            playerStatus.setText("Player One is Winning !");
        }else if(playertwoscorecount > playeronescorecount) {
            playerStatus.setText("Player Two is Winning !");
        }else {
            playerStatus.setText("Both player Scores are equal");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playeronescorecount = 0;
                playertwoscorecount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner() {
        boolean winnerResult = false;
        for (int[] winningPosition : winningposition) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore() {
        playeronescore.setText(Integer.toString(playeronescorecount));
        playertwoscore.setText(Integer.toString(playertwoscorecount));
    }
    public void playAgain() {
        rountcount = 0;
        activeplayer = true;
        for(int i = 0;i<buttons.length;i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }


    }
}