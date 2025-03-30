package in.edu.visitingapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserValidationActivity extends AppCompatActivity {

    private EditText etUniqueCode;
    private Button btnValidate;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_validation);

        etUniqueCode = findViewById(R.id.etUniqueCode);
        btnValidate = findViewById(R.id.btnValidate);
        databaseHelper = new DatabaseHelper(this);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredCode = etUniqueCode.getText().toString().trim();
                if (enteredCode.isEmpty()) {
                    Toast.makeText(UserValidationActivity.this, "Please enter the unique code", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Get current date in "yyyy-MM-dd" format
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                Cursor cursor = databaseHelper.getRequestByUniqueKeyAndDate(enteredCode, currentDate);
                if (cursor != null && cursor.moveToFirst()) {
                    int requestId = cursor.getInt(cursor.getColumnIndex("id"));
                    String userId = cursor.getString(cursor.getColumnIndex("user_id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    String phone = cursor.getString(cursor.getColumnIndex("phone"));
                    String purpose = cursor.getString(cursor.getColumnIndex("purpose"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    String uniqueKey = cursor.getString(cursor.getColumnIndex("unique_key"));
                    String status = cursor.getString(cursor.getColumnIndex("status"));
                    cursor.close();

                    Intent intent = new Intent(UserValidationActivity.this, VisitPassDetailsActivity.class);
                    intent.putExtra("id", requestId);
                    intent.putExtra("user_id", userId);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("phone", phone);
                    intent.putExtra("purpose", purpose);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("unique_key", uniqueKey);
                    intent.putExtra("status", status);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserValidationActivity.this, "Invalid code or no valid request for today", Toast.LENGTH_SHORT).show();
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        });
    }
}
