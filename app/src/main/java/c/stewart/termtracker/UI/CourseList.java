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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;

public class CourseList extends AppCompatActivity {
    private Repository repository;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        RecyclerView recyclerView=findViewById(R.id.course_recycler_view);
        repository = new Repository(getApplication());
        List<Course> allCourses=repository.getmAllCourses();

        courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        courseAdapter.setCourses(allCourses);

        // When user clicks on the plus fab, user will be taken to the course details screen.
        FloatingActionButton fabAdd=findViewById(R.id.addCourseBTN);
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                List<Term> allTerms = repository.getmAllTerms();
                if(allTerms.isEmpty()){
                    // Display a message indicating that a term must be added first.
                    Toast.makeText(CourseList.this, "A term must be added before a " +
                            "course can be added.", Toast.LENGTH_SHORT).show();
                    // Navigate back to the term list screen.
                    Intent intent = new Intent(CourseList.this, TermList.class);
                    startActivity(intent);
                }else{
                    // If there are terms, navigate to the course details screen.
                    Intent intent=new Intent(CourseList.this, CourseDetails.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Makes menu show up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.course_list, menu);
        return true;
    }

    // Refresh the list of courses after any changes to the database.
    @Override
    protected  void onResume(){
        super.onResume();
        List<Course> allCourses = repository.getmAllCourses();
        courseAdapter.setCourses(allCourses);
    }
    // Actions when user clicks on menu items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.main_menu_course_screen){
            Intent intent = new Intent (CourseList.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_course_list_terms){
            Intent intent = new Intent (CourseList.this, TermList.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_course_list_assessments){
            Intent intent = new Intent (CourseList.this, AssessmentList.class);
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