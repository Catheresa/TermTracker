package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.R;

public class AssessmentList extends AppCompatActivity {
    private Repository repository;
    private AssessmentAdapter assessmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        RecyclerView recyclerView=findViewById(R.id.assessment_course_recycler_view);
        repository= new Repository(getApplication());
        List<Assessment> allAssessments= repository.getmAllAssessments();

        assessmentAdapter= new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);

        // When user clicks on the plus fab, user will be taken to the assessment details screen.
        FloatingActionButton fabAdd=findViewById(R.id.addAssessmentBTN);
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(AssessmentList.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });
    }

    // Makes menu show up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_list, menu);
        return true;
    }
    // Refresh the list of assessments after any changes to the database.
    @Override
    protected void onResume(){
        super.onResume();
        List<Assessment> allAssessments= repository.getmAllAssessments();
        assessmentAdapter.setAssessments(allAssessments);
    }

    // Actions when user clicks on menu items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_assessment_list_terms){
            Intent intent = new Intent (AssessmentList.this, TermList.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_assessment_list_courses){
            Intent intent = new Intent (AssessmentList.this, CourseList.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }

}