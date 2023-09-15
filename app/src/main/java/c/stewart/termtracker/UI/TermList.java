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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;
import java.util.Date;

public class TermList extends AppCompatActivity {
    // Member variables.
    private Repository repository;
    private TermAdapter termAdapter;

    // Method called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        // Sets up the views.
        FloatingActionButton fabAdd=findViewById(R.id.addTermBTN);

        // Initialization of the view based on id, data retrieval and management, & fetching a lis of 'Term' objects.
        RecyclerView recyclerView=findViewById(R.id.term_recycler_view);
        repository= new Repository(getApplication());
        List<Term> allTerms= repository.getmAllTerms();

        // Creates a new instance of a 'TermAdapter' used to populate the RecyclerView.
        // Connects the data source (the adapter) to the 'RecyclerView' so it knows how to display data.
        // Arranges the list view vertically.
        // Passes the 'Term' objects to the 'TermAdapter' so data can be displayed in the 'RecyclerView'
        termAdapter= new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

        // When user clicks on the plus fab, user will be taken to the term details screen.
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });
    }

    // Refreshes the list of terms with any changes to the database.
    @Override
    protected void onResume(){
        super.onResume();

        // Refresh the list of terms
        List<Term> allTerms= repository.getmAllTerms();
        termAdapter.setTerms(allTerms);
    }

    // Makes menu show up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.term_list, menu);
        return true;
    }

    // Actions when user clicks on menu items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // The initial set of data that is loaded to the database on click.
        if(item.getItemId()==R.id.main_menu_term_screen){
            Intent intent = new Intent (TermList.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.loadSampleData){
            repository= new Repository(getApplication());

            String startDate1 = "09/01/22";
            String endDate1 = "12/01/22";
            String startDate2 = "01/03/23";
            String endDate2 = "04/03/23";
            Term term1 = new Term(0, "Fall 2022", startDate1, endDate1);
            repository.insert(term1);

            Term term2 = new Term(0, "Winter 2023", startDate2, endDate2);
            repository.insert(term2);

            Course course = new Course(0, "Advanced Java Concepts",
                    startDate1, endDate1, "Completed",
                    "Sam Stern", "(599)892-4585",
                    "sam.stern@wgu.edu",
                    "Refines object-oriented programming expertise.", 1);
            repository.insert(course);

            course = new Course(0, "Business of IT",
                    startDate1, endDate1, "Completed",
                    "Kara Williams", "(599)224-8874",
                    "kara.williams@wgu.edu", "Examines IT instrastructure" +
                    "library(ITIL) terminology.", 1);
            repository.insert(course);

            course = new Course(0, "IT Applications",
                    startDate2,endDate2, "Completed",
                    "Tyler Turner", "(599)234-8744",
                    "tyler.turner@wgu.edu", "Explores PC components",
                    2);
            repository.insert(course);

            Assessment assessment = new Assessment(0, "ITIL Exam",
                    "Objective Assessment", startDate1, endDate2,
                    "85% passing grade",1);
            repository.insert(assessment);

            assessment = new Assessment(0, "CompTIA-A+ Exam","Objective Assessment",
                    startDate2, endDate2, "87% passing grade",2);
            repository.insert(assessment);

            return true;
        }
        // Navigation to the List of Courses screen when this menu item is selected.
        if(item.getItemId()==R.id.menu_term_list_courses){
            Intent intent = new Intent (TermList.this, CourseList.class);
            startActivity(intent);
            return true;
        }
        // Navigation to the List of Assessments screen when this menu item is selected.
        if(item.getItemId()==R.id.menu_term_list_assessments){
            Intent intent = new Intent (TermList.this, AssessmentList.class);
            startActivity(intent);
            return true;
        }
        // Handles user's action when they press the 'Up' or 'Back' button.
        if(item.getItemId()==android.R.id.home){
            // Used to close the current activity and return to the prior activity or home screen.
            this.finish();
            // Indicates that the action has been handled successfully.
            return true;
        }
        return true;
    }

}