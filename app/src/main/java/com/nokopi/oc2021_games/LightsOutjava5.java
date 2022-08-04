package com.nokopi.oc2021_games;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Random;

public class LightsOutjava5 extends AppCompatActivity {

    final int SIZE = 5;
    final int LIMIT = 0;

    private SoundPlayer soundPlayer;

    ImageView[][] lights = new ImageView[SIZE][SIZE];

    /* HashMap は ImageView の ID（R.id.）と，画像（off：0，on:1）のペア*/
    HashMap<Integer, Integer> status = new HashMap<Integer, Integer>();

    //任意の盤面を設定 0:off 1:on
//    int[] panel = {1,1,0,0,0,
//                   1,0,0,0,0,
//                   0,0,0,0,0,
//                   0,0,0,0,0,
//                   0,0,0,0,0};

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.lightsoutjava5);

        TableLayout tbl = findViewById(R.id.tablelayout);
        Button startReset = findViewById(R.id.startreset);
        Button returnButton = findViewById(R.id.returnButton);
        ImageView ruleImageView = findViewById(R.id.ruleImage);
        ImageButton hintButton = findViewById(R.id.hint);

        ruleImageView.setImageResource(R.drawable.rule);

        soundPlayer = new SoundPlayer(this);

        returnButton.setOnClickListener(v -> {
            finish();
        });

        hintButton.setOnClickListener(v -> {
            new AlertDialog.Builder(LightsOutjava5.this)
                    .setTitle("ブラウザを開いて解法を見ますか？")
                    .setMessage(R.string.警告)
                    .setPositiveButton(
                            "Yes",
                            (dialog, which) -> {
                                Uri uri = Uri.parse("http://katsubi.blog.fc2.com/blog-entry-8.html");
                                Intent hintWeb = new Intent(Intent.ACTION_VIEW,uri);
                                startActivity(hintWeb);
                            })
                    .setNegativeButton("No", null)
                    .show();
        });

        startReset.setOnClickListener(v -> {
            int noAnswer1 = 0;
            int noAnswer2 = 0;
            int checkAnswer = 1;

            soundPlayer.playStartSound();
            startReset.setText("RESET");
            tbl.setVisibility(View.VISIBLE);
            ruleImageView.setVisibility(View.GONE);

            while (checkAnswer > 0){
                makeLights();
                noAnswer1 = (status.get(lights[0][1].getId()) + status.get(lights[0][2].getId()) + status.get(lights[0][3].getId()) + status.get(lights[1][0].getId()) + status.get(lights[1][2].getId()) + status.get(lights[1][4].getId()) + status.get(lights[2][0].getId()) + status.get(lights[2][1].getId())
                        + status.get(lights[2][3].getId()) + status.get(lights[2][4].getId()) + status.get(lights[3][0].getId()) + status.get(lights[3][2].getId()) + status.get(lights[3][4].getId()) + status.get(lights[4][1].getId()) + status.get(lights[4][2].getId()) + status.get(lights[4][3].getId()))%2;
                noAnswer2 = (status.get(lights[0][0].getId()) + status.get(lights[0][2].getId()) + status.get(lights[0][4].getId()) + status.get(lights[1][0].getId()) + status.get(lights[1][2].getId()) + status.get(lights[1][4].getId()) + status.get(lights[3][0].getId()) + status.get(lights[3][2].getId())
                        + status.get(lights[3][4].getId()) + status.get(lights[4][0].getId()) + status.get(lights[4][2].getId()) + status.get(lights[4][4].getId()))%2;
                checkAnswer = noAnswer1 + noAnswer2;
            }


        });


        //検証用プログラム
//        startReset.setOnClickListener(v -> {
//            int a = 0;
//            tbl.setVisibility(View.VISIBLE);
//            for (int i = 0; i < SIZE; i++){
//                for (int j = 0; j < SIZE; j++){
//                    int Id = lights[i][j].getId();
//                    if (panel[a] == 0){
//                        lights[i][j].setImageResource(R.drawable.teston);
//                        status.put(Id, 0);
//                    }
//                    if (panel[a] == 1){
//                        lights[i][j].setImageResource(R.drawable.testoff);
//                        status.put(Id, -1);
//                    }
//                    a++;
//                }
//            }
//        });

        View.OnClickListener listener = v -> {
            ImageView touchImage = (ImageView) v;
            int touchImageId = touchImage.getId();
            int touchImageXY = SearchImagePlace(touchImage.getId());
            int x = touchImageXY/10;
            int y = touchImageXY%10;

            int right;
            int left;
            int top;
            int bottom;

            soundPlayer.playTouchLightSound();

            //その場のlightsを変える
            if (status.get(touchImageId) == 0){
                lights[x][y].setImageResource(R.drawable.testoff);
                status.put(lights[x][y].getId(), 1);
            } else {
                lights[x][y].setImageResource(R.drawable.teston);
                status.put(lights[x][y].getId(), 0);
            }

            //right
            right = y + 1;
            if (right < SIZE){
                if (status.get(lights[x][right].getId()) == 0){
                    lights[x][right].setImageResource(R.drawable.testoff);
                    status.put(lights[x][right].getId(), 1);
                } else {
                    lights[x][right].setImageResource(R.drawable.teston);
                    status.put(lights[x][right].getId(), 0);
                }
            }

            //left
            left = y - 1;
            if (left >= LIMIT){
                if (status.get(lights[x][left].getId()) == 0){
                    lights[x][left].setImageResource(R.drawable.testoff);
                    status.put(lights[x][left].getId(), 1);
                } else {
                    lights[x][left].setImageResource(R.drawable.teston);
                    status.put(lights[x][left].getId(), 0);
                }
            }

            //top
            top = x - 1;
            if (top >= LIMIT){
                if (status.get(lights[top][y].getId()) == 0){
                    lights[top][y].setImageResource(R.drawable.testoff);
                    status.put(lights[top][y].getId(), 1);
                } else {
                    lights[top][y].setImageResource(R.drawable.teston);
                    status.put(lights[top][y].getId(), 0);
                }
            }

            //bottom
            bottom = x + 1;
            if (bottom < SIZE){
                if (status.get(lights[bottom][y].getId()) == 0){
                    lights[bottom][y].setImageResource(R.drawable.testoff);
                    status.put(lights[bottom][y].getId(), 1);
                } else {
                    lights[bottom][y].setImageResource(R.drawable.teston);
                    status.put(lights[bottom][y].getId(), 0);
                }
            }

            if (AllOff()){
                soundPlayer.playGameClearSound();
                Intent success = new Intent(LightsOutjava5.this, Success.class);
                startActivity(success);
            }
        };

        lights[0][0] = findViewById(R.id.imageButton1);
        lights[0][1] = findViewById(R.id.imageButton2);
        lights[0][2] = findViewById(R.id.imageButton3);
        lights[0][3] = findViewById(R.id.imageButton4);
        lights[0][4] = findViewById(R.id.imageButton17);

        lights[1][0] = findViewById(R.id.imageButton5);
        lights[1][1] = findViewById(R.id.imageButton6);
        lights[1][2] = findViewById(R.id.imageButton7);
        lights[1][3] = findViewById(R.id.imageButton8);
        lights[1][4] = findViewById(R.id.imageButton18);

        lights[2][0] = findViewById(R.id.imageButton9);
        lights[2][1] = findViewById(R.id.imageButton10);
        lights[2][2] = findViewById(R.id.imageButton11);
        lights[2][3] = findViewById(R.id.imageButton12);
        lights[2][4] = findViewById(R.id.imageButton19);

        lights[3][0] = findViewById(R.id.imageButton13);
        lights[3][1] = findViewById(R.id.imageButton14);
        lights[3][2] = findViewById(R.id.imageButton15);
        lights[3][3] = findViewById(R.id.imageButton16);
        lights[3][4] = findViewById(R.id.imageButton20);

        lights[4][0] = findViewById(R.id.imageButton21);
        lights[4][1] = findViewById(R.id.imageButton22);
        lights[4][2] = findViewById(R.id.imageButton23);
        lights[4][3] = findViewById(R.id.imageButton24);
        lights[4][4] = findViewById(R.id.imageButton25);

        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++) {
                lights[i][j].setImageResource(R.drawable.empty);
                status.put(lights[i][j].getId(),-1);
                lights[i][j].setOnClickListener(listener);
            }
        }
    }

    public int SearchImagePlace(int id) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (lights[i][j].getId() == id){
                    return i*10+j;
                }
            }
        }
        return 0;
    }

    public Boolean AllOff() {
        int answer = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (status.get(lights[i][j].getId()) == 0) {
                    answer++;
                }
            }
        }
        return answer == SIZE*SIZE;
    }

    public void makeLights(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int Id = lights[i][j].getId();
                Random rand = new Random();
                int num = rand.nextInt(2) - 1;
                if (num == 0) {
                    lights[i][j].setImageResource(R.drawable.teston);
                    status.put(Id, 0);
                }
                if (num == -1) {
                    lights[i][j].setImageResource(R.drawable.testoff);
                    status.put(Id, 1);
                }
            }
        }
    }

}
