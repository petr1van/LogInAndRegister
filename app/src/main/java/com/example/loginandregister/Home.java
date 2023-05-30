package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandregister.Models.Day;
import com.example.loginandregister.Models.UserAccount;
import com.example.loginandregister.Utils.Database;

import java.time.LocalTime;
import java.util.List;

public class Home extends AppCompatActivity {
    private Database db;
    private Day day;
    private TextView txtName, txtDeleteAllData;
    private int userId;
    private Button btnGoUpdate, btnGoUpdate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtName = findViewById(R.id.txtName);
        txtDeleteAllData = findViewById(R.id.txtDeleteAllData);
        btnGoUpdate = findViewById(R.id.btnGoUpdate);
        btnGoUpdate2 = findViewById(R.id.btnGoUpdate2);
        db = new Database(this);
        userId = getIntent().getIntExtra("userId", 0);
        btnGoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, UpdateName.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        btnGoUpdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, UpdatePassword.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        txtDeleteAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllAccounts();
                Toast.makeText(Home.this, "Данные успешно удалены.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        });
        displayUserName();
    }

    @SuppressLint("SetTextI18n")
    private void displayUserName() {
        UserAccount user = db.getUserById(userId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            day = new Day(LocalTime.now().getHour());
        }
        if (user != null) {
            if (day.isMorning()) {
                txtName.setText("Здравствуйте " + user.getUsername() + ", Доброе утро");
            } else if (day.isAfternoon()) {
                txtName.setText("Здравствуйте " + user.getUsername() + ", Добрый день");
            } else if (day.isEvening()) {
                txtName.setText("Здравствуйте " + user.getUsername() + ", Добрый вечер");
            }
        } else {
            // Handle the case where the user is not found in the database
            txtName.setText("Пользователь не найден");
        }
    }
}