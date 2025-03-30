package in.edu.visitingapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class ApplyVisitActivity extends AppCompatActivity {

    TextInputEditText etName, etEmail, etPhone, etPurpose, etDate, etTime;
    TextInputLayout dateInputLayout, timeInputLayout;
    Button btnApplyVisit;
    DatabaseHelper databaseHelper;
    private Calendar calendar;
    private static final String TAG = "ApplyVisitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_visit);

        // Set up toolbar with navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        databaseHelper = new DatabaseHelper(this);
        calendar = Calendar.getInstance();

        // Initialize form fields
        etName = findViewById(R.id.apply_name);
        etEmail = findViewById(R.id.apply_email);
        etPhone = findViewById(R.id.apply_phone);
        etPurpose = findViewById(R.id.apply_purpose);
        etDate = findViewById(R.id.apply_date);
        etTime = findViewById(R.id.apply_time);
        dateInputLayout = findViewById(R.id.date_input_layout);
        timeInputLayout = findViewById(R.id.time_input_layout);
        btnApplyVisit = findViewById(R.id.apply_visit_button);

        // Set up date & time pickers
        setupDateTimePickers();

        // Retrieve the primary key (logged-in user's email) from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String userId = sharedPreferences.getString("user_email", "");
        if (userId.isEmpty()) {
            Toast.makeText(ApplyVisitActivity.this, "User not logged in. Please login again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ApplyVisitActivity.this, Login.class));
            finish();
            return;
        }

        btnApplyVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user inputs
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String purpose = etPurpose.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String time = etTime.getText().toString().trim();

                // Basic validation
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                        purpose.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    Toast.makeText(ApplyVisitActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Log the values for debugging
                Log.d(TAG, "UserID: " + userId);
                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "Email: " + email);
                Log.d(TAG, "Phone: " + phone);
                Log.d(TAG, "Purpose: " + purpose);
                Log.d(TAG, "Date: " + date);
                Log.d(TAG, "Time: " + time);

                // Generate a unique key (10 characters, all uppercase letters and digits)
                String uniqueKey = generateRandomKey();
                Log.d(TAG, "Unique Key: " + uniqueKey);

                // Insert the request into the database
                Boolean isInserted = databaseHelper.insertUserRequest(userId, name, email, phone, purpose, date, time, uniqueKey);
                if (isInserted) {
                    Toast.makeText(ApplyVisitActivity.this, "Visit Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the form
                    etName.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                    etPurpose.setText("");
                    etDate.setText("");
                    etTime.setText("");
                } else {
                    Toast.makeText(ApplyVisitActivity.this, "Failed to submit request", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Database insertion failed.");
                }
            }
        });
    }

    private void setupDateTimePickers() {
        // Date picker setup
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateField();
            }
        };

        // Time picker setup
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateTimeField();
            }
        };

        // Set click listeners for date field and icon
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(dateSetListener);
            }
        });

        dateInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(dateSetListener);
            }
        });

        // Set click listeners for time field and icon
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(timeSetListener);
            }
        });

        timeInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(timeSetListener);
            }
        });
    }

    private void showDatePicker(DatePickerDialog.OnDateSetListener dateSetListener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ApplyVisitActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set minimum date to today (optional)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        
        datePickerDialog.show();
    }

    private void showTimePicker(TimePickerDialog.OnTimeSetListener timeSetListener) {
        new TimePickerDialog(
                ApplyVisitActivity.this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false // 12-hour format
        ).show();
    }

    private void updateDateField() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        etDate.setText(sdf.format(calendar.getTime()));
    }

    private void updateTimeField() {
        String timeFormat = "hh:mm a"; // e.g. "03:30 PM"
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        etTime.setText(sdf.format(calendar.getTime()));
    }

    // Generates a random 10-character key with uppercase letters and digits
    private String generateRandomKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
