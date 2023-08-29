package c.stewart.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;

public class TermList extends AppCompatActivity {
    private Repository repository;
    private Button viewListOfCourses;
    private Button viewListOfAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        FloatingActionButton fabAdd=findViewById(R.id.addTermBTN);
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });

////        TODO: Build out term deletion with the floating action button
//
//        FloatingActionButton fabUpdate=findViewById(R.id.updateTermBTN);
//        fabUpdate.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                // TODO: Pull in data from the selected list item
//                Intent intent=new Intent(TermList.this, TermDetails.class);
//                startActivity(intent);
//            }
//        });

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
        if(item.getItemId()==R.id.loadSampleData){
            repository= new Repository(getApplication());
            //Toast.makeText(TermList.this, "put in sample data", Toast.LENGTH_LONG).show();

            Term term = new Term(0,"Fall 2022","9/1/2022",
                    "12/1/2022");
            repository.insert(term);

            term = new Term(0,"Winter 2023","1/3/2023",
                    "4/3/2023");
            repository.insert(term);

            Course course = new Course(0, "Advanced Java Concepts",
                    "9/1/2022", "12/1/2022", "Completed",
                    "Sam Stern", "(599)892-4585",
                    "sam.stern@wgu.edu",
                    "Refines object-oriented programming expertise.", 1);
            repository.insert(course);

            course = new Course(0, "Business of IT",
                    "9/1/2022", "12/1/2022", "Completed",
                    "Kara Williams", "(599)224-8874",
                    "kara.williams@wgu.edu", "Examines IT instrastructure" +
                    "library(ITIL) terminology.", 1);
            repository.insert(course);

            course = new Course(0, "IT Applications",
                    "Winter 2023","1/3/2023", "Completed",
                    "Tyler Turner", "(599)234-8744",
                    "tyler.turner@wgu.edu", "Explores PC components",
                    2);
            repository.insert(course);

            Assessment assessment = new Assessment(0, "Fall 2022",
                    "ITIL Exam","Objective Assessment",
                    "9/1/2022", "9/1/2022","Completed",
                    "85% passing grade",1);
            repository.insert(assessment);

            assessment = new Assessment(0, "Winter 2023",
                    "CompTIA-A+ Exam","Objective Assessment",
                    "1/3/2023", "4/3/2023","Completed",
                    "87% passing grade",2);
            repository.insert(assessment);

            return true;
        }
        if(item.getItemId()==R.id.menu_term_list_courses){
            Intent intent = new Intent (TermList.this, CourseList.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_term_list_assessments){
            Intent intent = new Intent (TermList.this, AssessmentList.class);
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