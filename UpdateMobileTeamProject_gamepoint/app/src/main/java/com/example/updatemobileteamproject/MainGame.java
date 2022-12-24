package com.example.updatemobileteamproject;

import static android.view.View.inflate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainGame extends MainActivity {

    TextView inGamePoint, toastText1;
    ImageButton inGamePandaVersion;
    ImageView achievementA, game_achievementA;
    Button inGameItem1, inGameItem2, inGameItem3, inGameItem4, inGameItem5, inGameItem6, inGameItem7, inGameItem8;
    View toastView, achievementView, restartView;
    int point;
    int ex;
    int arrayCount[] = {0,0,0,0,0,0,0,0};
    boolean pandaspecial = true,arrayAchievement[] = {true,true,true,true,true,true,true,true,true};
    int count = 0;
    int itemPoint[] = {1, 2, 3, 4, 5, 10, 15, 20};
    int itemEx[] = {1, 2, 3, 4, 5, 10, 15, 20};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        setTitle("판다 키우기");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        inGamePoint = (TextView) findViewById(R.id.inGamePoint);
        inGamePandaVersion = (ImageButton) findViewById(R.id.inGamePandaVersion);

        Button inGameItem[] = new Button[8];
        Integer inGameItemId[] = {R.id.inGameItem1, R.id.inGameItem2, R.id.inGameItem3, R.id.inGameItem4, R.id.inGameItem5, R.id.inGameItem6, R.id.inGameItem7, R.id.inGameItem8};


        //inGamePoint.setText(point+"");


        Intent gameIntent = getIntent();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try{
            FileInputStream inFs = openFileInput("Data.txt");
            DataInputStream data = new DataInputStream(inFs);
            point = data.readInt();
            ex = data.readInt();

            for(int i = 0 ; i < arrayCount.length; i++){
                arrayCount[i]= data.read();
            }
            for(int i = 0; i <arrayAchievement.length; i++){
                arrayAchievement[i] = data.readBoolean();
            }
            pandaspecial = data.readBoolean();


            data.close();
            inFs.close();

        }catch (IOException e){
            point = 0;
            ex = 0;
            for(int i = 0 ; i < arrayCount.length; i++){
                arrayCount[i]= 0;
            }
            for(int i = 0; i <arrayAchievement.length; i++){
                arrayAchievement[i] = true;
            }

            System.out.println("NOPE DATA");
        }

        int tpoint = gameIntent.getIntExtra("toDoPoint",0);
        point = point + tpoint;

        inGamePoint.setText(point+ "");
        changePandaVersion();

        System.out.println(point);
        System.out.println(ex);

        for(int i = 0 ; i < arrayCount.length; i++){
            System.out.println(arrayCount[i]);
        }
        for(int i = 0; i <arrayAchievement.length; i++){
            System.out.println(arrayAchievement[i]);
        }
        if(pandaspecial == true){
            if (ex >= 700) {
                inGamePandaVersion.setImageResource(R.drawable.game_panda8);
            }
        }else if(pandaspecial == false){
            inGamePandaVersion.setImageResource(R.drawable.game_panda_spe1);
        }




        for (int i = 0; i < inGameItem.length; i++) {
            final int index;
            index = i;
            inGameItem[index] = (Button) findViewById(inGameItemId[index]);
            inGameItem[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (point >= itemPoint[index]) {
                        point = point - itemPoint[index];
                        ex = ex + itemEx[index];
                        arrayCount[index]++;
                        System.out.println("Ex: " + ex + ", Point: " + point);
                        changePandaVersion();
                        inGamePoint.setText(point+ "");
                        achievementToast(index);
                    } else {
                        showToast("포인트가 부족합니다");
                    }

                }
            });
        }

        inGamePandaVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                point ++;
                inGamePoint.setText(point+ "");

            }
        });




    }


    public void achievementToast(int index){
        if(arrayAchievement[index] == true){
            if(arrayCount[index] > 10){
                showToast("업적이 달성되었습니다");
                arrayAchievement[index] = false;
            }
        }
        if(arrayAchievement[8] == true){
            if(ex > 700){
                showToast("업적이 달성되었습니다");
                arrayAchievement[8] = false;
            }
        }



    }



    Integer gridText[] = {R.id.inGameItem1,R.id.inGameItem2, R.id.inGameItem3,R.id.inGameItem4,R.id.inGameItem5,R.id.inGameItem6,R.id.inGameItem7,R.id.inGameItem8 };
    public void changePandaVersion() {
        if (ex < 50) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda1);
        } else if (ex < 100) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda2);
        } else if (ex < 200) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda3);
        } else if (ex < 300) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda4);
        } else if (ex < 400) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda5);
        } else if (ex < 500) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda6);
        } else if (ex < 600) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda7);
        } else if (ex < 700) {
            inGamePandaVersion.setImageResource(R.drawable.game_panda8);
        }else{
            inGamePandaVersion.setImageResource(R.drawable.game_panda8);
        }

        if(ex>300){
            int arr[] = {5,6,7,8,10,15, 20, 25};
            for(int i = 0; i < 8; i++){
                Button buttonA = (Button) findViewById(gridText[i]);
                itemPoint[i] = arr[i];
                buttonA.setText(String.valueOf(arr[i]));
            }
        }

    }


    public void showToast(String data) {
        Toast toast = new Toast(MainGame.this);
        toastView = (View) inflate(MainGame.this, R.layout.toast, null);
        toastText1 = (TextView) toastView.findViewById(R.id.toastText1);
        toastText1.setText(data);
        toast.setView(toastView);
        toast.show();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작

                try{
                    FileOutputStream outFs = openFileOutput("Data.txt", Context.MODE_PRIVATE);
                    DataOutputStream data = new DataOutputStream(outFs);
                    data.writeInt(point);
                    data.writeInt(ex);

                    for(int i = 0 ; i < arrayCount.length; i++){
                        data.write(arrayCount[i]);
                    }
                    for(int i = 0; i <arrayAchievement.length; i++){
                        data.writeBoolean(arrayAchievement[i]);
                    }
                    data.writeBoolean(pandaspecial);

                    data.flush();
                    data.close();
                    outFs.close();
                    System.out.println("STORE");

                }catch (IOException e){
                    showToast("애러 발생");
                }

                finish();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);


                return true;
            }
            case R.id.item1:
                View v;
                achievementView = (View) inflate(MainGame.this, R.layout.achievement,null);
                AlertDialog.Builder achi = new AlertDialog.Builder(MainGame.this);
                achi.setView(achievementView);
                achi.setTitle("업적");

                Integer achievement[] = {R.id.achievement1, R.id.achievement2, R.id.achievement3, R.id.achievement4,R.id.achievement5, R.id.achievement6, R.id.achievement7, R.id.achievement8};
                Integer gride[] = {R.drawable.game_gride_1bamboo, R.drawable.game_gride_2apple, R.drawable.game_gride_3mango, R.drawable.game_gride_4blueberry, R.drawable.game_gride_5banana, R.drawable.game_gride_6grape, R.drawable.game_gride_7pear, R.drawable.game_gride_8cherries};



                for(int i = 0; i < achievement.length; i++ ){
                    if(arrayAchievement[i] == true){
                        if(arrayCount[i] > 10){
                            achievementA = (ImageView) achievementView.findViewById(achievement[i]);
                            achievementA.setImageResource(gride[i]);
                        }
                    }
                    else {
                        achievementA = (ImageView) achievementView.findViewById(achievement[i]);
                        achievementA.setImageResource(gride[i]);
                    }
                }
                if(arrayAchievement[8] == true){
                    if(ex>700){
                        achievementA = (ImageView) achievementView.findViewById(R.id.achievement9);
                        achievementA.setImageResource(R.drawable.game_party);
                    }
                }else{
                    achievementA = (ImageView) achievementView.findViewById(R.id.achievement9);
                    achievementA.setImageResource(R.drawable.game_party);
                }

                achi.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                achi.show();

                return true;
            case R.id.item2:
                if(ex > 700){
                    if(pandaspecial == true){
                        inGamePandaVersion.setImageResource(R.drawable.game_panda_spe1);
                        pandaspecial = false;
                    }else if(pandaspecial == false){
                        inGamePandaVersion.setImageResource(R.drawable.game_panda8);
                        pandaspecial = true;
                    }
                }else{
                    showToast("레벨이 부족합니다");
                }
                return true;

            case R.id.item3:
                restartView = (View) inflate(MainGame.this, R.layout.restart,null);
                AlertDialog.Builder restart = new AlertDialog.Builder(MainGame.this);
                restart.setView(restartView);
                restart.setTitle("!!주의!!");

                restart.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            //즉시 데이터 수정 후 새로고침 필요
                            FileOutputStream outFs = openFileOutput("Data.txt", Context.MODE_PRIVATE);
                            DataOutputStream data = new DataOutputStream(outFs);
                            data.writeInt(0);
                            data.writeInt(0);
                            for(int j = 0 ; j < arrayCount.length; j++){
                                data.write(0);
                            }
                            for(int j = 0; j <arrayAchievement.length; j++){
                                data.writeBoolean(true);
                            }


                            data.flush();
                            data.close();
                            outFs.close();
                            System.out.println("STORE");


                        }catch (IOException e){

                        }

                        finish();
                    }
                });

                restart.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                restart.show();
                return true;

        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

}