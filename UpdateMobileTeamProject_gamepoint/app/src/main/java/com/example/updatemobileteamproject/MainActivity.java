package com.example.updatemobileteamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {



    ImageView profileImage;

    Button btn1,btn2;
    TextView text1,text2;
    Switch switch1;
    DatePicker datePicker;
    View dialogView;
    EditText dlgEdt1, dlgEdt2, edit1;
    Calendar cal = Calendar.getInstance();
    int cYear = cal.get(Calendar.YEAR);
    int cMonth = cal.get(Calendar.MONTH);
    int cDay = cal.get(Calendar.DAY_OF_MONTH);



    ArrayList<ToDoModel> taskList;
    ToDoAdapter adapter;
    RecyclerView recyclerView;

    ToDoDB db;
    //할일 ID
    int gId;

    int todopoint;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("todolist");




        //DB 연결
        db = new ToDoDB(this);
        ///초기화
        taskList = new ArrayList<>();

        //recyclerView 설정
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adapter 설정
        adapter = new ToDoAdapter(db);
        adapter.setTasks(taskList);

        //adapter 적용
        recyclerView.setAdapter(adapter);

        //조회
        selectData();

        String nametext1;
        String nametext2;




        profileImage = (ImageView) findViewById(R.id.profile);

        int point, ex = 0;
        int arrayCount[] = {0,0,0,0,0,0,0,0};
        boolean pandaspecial = true,arrayAchievement[] = {true,true,true,true,true,true,true,true,true};


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

        if (ex < 50) {
            profileImage.setImageResource(R.drawable.game_panda1);
        } else if (ex < 100) {
            profileImage.setImageResource(R.drawable.game_panda2);
        } else if (ex < 200) {
            profileImage.setImageResource(R.drawable.game_panda3);
        } else if (ex < 300) {
            profileImage.setImageResource(R.drawable.game_panda4);
        } else if (ex < 400) {
            profileImage.setImageResource(R.drawable.game_panda5);
        } else if (ex < 500) {
            profileImage.setImageResource(R.drawable.game_panda6);
        } else if (ex < 600) {
            profileImage.setImageResource(R.drawable.game_panda7);
        } else if (ex < 700) {
            profileImage.setImageResource(R.drawable.game_panda8);
        } else {
            profileImage.setImageResource(R.drawable.game_panda8);
        }






        final String[] fileName = new String[1];
        btn1 =  findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        switch1 = (Switch) findViewById(R.id.switch1);
        datePicker = (DatePicker) findViewById(R.id.datePicker) ;
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        edit1 = (EditText) findViewById(R.id.edit1);


        SharedPreferences pref;
        SharedPreferences.Editor editor;
        SharedPreferences.Editor profile;


        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        todopoint = pref.getInt("todopoint", 0);
        nametext1 = pref.getString("name1","");
        nametext2 = pref.getString("name2","");

        profile = pref.edit();


        text1.setText(nametext1);
        text2.setText(nametext2);





        //--------------------------------------------------------------------------------------
        //Toast.makeText(getApplicationContext(), "point "+todopoint,Toast.LENGTH_SHORT).show();
        //--------------------------------------------------------------------------------------


        //프로필 수정 버튼
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (View) View.inflate(MainActivity.this,R.layout.dialog1,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("사용자 정보 입력");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dlgEdt1 = (EditText) dialogView.findViewById(R.id.dlgEdt1);
                        dlgEdt2 = (EditText) dialogView.findViewById(R.id.dlgEdt2);

                        editor.putString("name1", dlgEdt1.getText().toString());
                        editor.apply();
                        editor.putString("name2", dlgEdt2.getText().toString());
                        editor.apply();
                        text1.setText(dlgEdt1.getText().toString());
                        text2.setText(dlgEdt2.getText().toString());

                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }
        });

        //추가버튼
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //point 증가
                int a = todopoint+10;
                editor.putInt("todopoint", a);
                editor.apply();
                SharedPreferences pref;
                SharedPreferences.Editor editor;
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();
                todopoint =  pref.getInt("todopoint", 100);
                Toast.makeText(getApplicationContext(), "추가 되었습니다",Toast.LENGTH_SHORT).show();

                String text = edit1.getText().toString();
                if(btn2.getText().toString().equals("추가")){

                    //데이터 담기
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    task.setPickerdate( datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth());

                    //할일 추가
                    db.addTask(task);

                    //조회 및 리셋
                    selectReset("추가");
                    Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();

                }else{ //수정

                    //할일 수정
                    db.updateTask(gId, text);

                    //조회 및 리셋
                    selectReset("수정");
                }
            }
        });

        //스와이프 기능(수정, 삭제)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                switch(direction){

                    //삭제 기능
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:

                        dialogView = (View) View.inflate(MainActivity.this,R.layout.dialog2,null);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                        dlg.setTitle("경고");
                        dlg.setView(dialogView);
                        dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //할일 ID 변수에 담기
                                int id = taskList.get(position).getId();

                                //아이템 삭제
                                adapter.removeItem(position);

                                //DB에서 삭제
                                db.deleteTask(id);

                            }
                        });
                        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),"취소되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dlg.show();
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.RED)      //왼쪽 스와이프 배경색 설정
                        //  .addSwipeLeftActionIcon(R.drawable.ic_delete)//왼쪽 스와이프 아이콘 설정
                        .addSwipeLeftLabel("삭제")                    //왼쪽 스와이프 라벨 설정
                        .setSwipeLeftLabelColor(Color.WHITE)         //왼쪽 스와이프 라벨 색상
                        .addSwipeRightBackgroundColor(Color.RED)    //오른쪽 스와이프 배경색 설정
                        .addSwipeRightLabel("삭제")                   //오른쪽 스와이프 라벨 설정
                        .setSwipeRightLabelColor(Color.WHITE)        //오른쪽 스와이프 라벨 색상
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        //스위치를 눌렀을때 데이터피커 상태변경
        CheckState();

        datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                //날짜를 변경했을때 조회
                selectData();
            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckState();
            }

        });
    }

    /**
     * 조회
     */
    public void selectData() {

        if(datePicker == null) {
            taskList = db.getTask( cYear+"-"+(cMonth+1)+"-"+cDay);
        } else {
            taskList = db.getTask( datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth());
        }

        Collections.reverse(taskList); //역순 정렬
        adapter.setTasks(taskList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 조회및 리셋
     * @param type
     */
    public void selectReset(String type){

        //조회
        selectData();

        //초기화
        edit1.setText("");

        //등록이 아니면 등록으로 변경
        if(!type.equals("추가")){
            btn2.setText("추가");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);
        return true;
    }

    //스위치를 눌렀을때 데이터피커 상태변경
    private void CheckState(){
        if(switch1.isChecked()) {
            datePicker.setVisibility(View.VISIBLE);
        }
        else{
            datePicker.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1,menu);
    }

    //game모드 선택하면 MainGame.class로 이동
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.item1:
                taskList = db.getAllTasks();
                adapter.setTasks(taskList);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.item2:

                Intent intent = new Intent(getApplicationContext(), MainGame.class);
                intent.putExtra("toDoPoint", todopoint);
                todopoint = 0;
                SharedPreferences pref;
                SharedPreferences.Editor editor;
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();
                editor.putInt("todopoint", 0);
                editor.apply();
                startActivity(intent);

                return true;
        }return false;
    }
}