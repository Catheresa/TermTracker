package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;

public class MainActivity extends AppCompatActivity {
    private Button termScreen;
    private Button courseScreen;
    private Button assessmentScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termScreen = findViewById(R.id.termScreen);

        termScreen.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        }));

        courseScreen = findViewById(R.id.courseScreen);

        courseScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CourseList.class);
                startActivity(intent);
            }
        });

        assessmentScreen = findViewById(R.id.assessmentScreen);

        assessmentScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AssessmentList.class);
                startActivity(intent);
            }
        });
    }
}