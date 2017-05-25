package android.myapplicationdev.com.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {

        Button btnAdd;
        ListView lvTask;
        ArrayAdapter aa;
        ArrayList<String> al;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            DBHelper dbh = new DBHelper(MainActivity.this);

            btnAdd = (Button) findViewById(R.id.btnAdd);
            lvTask = (ListView) findViewById(R.id.lvTask);

            al = new ArrayList<String>();
            al.addAll(dbh.getAllTask());
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
            lvTask.setAdapter(aa);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, AddActivity.class);
                    startActivityForResult(i, 9);
                }
            });

        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode,
                                        Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && requestCode == 9){
                DBHelper  dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllTask());
                lvTask.setAdapter(aa);
                aa.notifyDataSetChanged();
            }

        }
    }

