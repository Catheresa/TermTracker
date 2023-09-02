package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;

// This activity will all users to view, add, and manage details of a term and
// be displayed on an Android screen.
public class TermDetails extends AppCompatActivity {
    // Member variables:
    int termID;
    String termName;
    String termStart;
    String termEnd;
    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;
    Repository repository;

    // Method that is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Initialize the Room Database.
        repository = new Repository(getApplication());

        // Add item to the Room Database.
        FloatingActionButton fabAdd=findViewById(R.id.addTermDetailsBTN);

        // Tasks to be performed when floating action button is clicked.
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Retrieve data from input fields
                termName = editTermName.getText().toString();
                termStart = editTermStart.getText().toString();
                termEnd = editTermEnd.getText().toString();

            //Create a new Term object with the entered data
            Term newTerm = new Term(0, termName, termStart, termEnd);

            // Insert the new Term into the database
            repository.insert(newTerm);

            // Return to the List of Terms screen
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fabDelete=findViewById(R.id.deleteTermDetailsBTN);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTerm();
            }

            private void deleteTerm() {
                int termID = getIntent().getIntExtra("termID", -1);

                if (termID != -1){
                    // Check if there are associated courses
                    List<Course> associatedCourses = repository.getAssociatedCourses(termID);

                    if(associatedCourses.isEmpty()){
                        // Allow deletion if there are no associated courses.
                        Term termToDelete = new Term(termID, " ", " ", " ");
                        repository.delete(termToDelete);
                        // Closes activity after deletion.
                        finish();
                    } else{
                        // Display an error message.
                        Toast.makeText(getApplicationContext(), "Cannot delete term with " +
                                "associated courses.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Initializes the input fields with data received from the previous activity.
        FloatingActionButton fabUpdate=findViewById(R.id.updateTermDetailsBTN);
        editTermName= findViewById(R.id.termTitleInput);
        editTermStart = findViewById(R.id.termStartDateInput);
        editTermEnd = findViewById(R.id.termEndDateInput);
        termID = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        termStart = getIntent().getStringExtra("termStart");
        termEnd = getIntent().getStringExtra("termEnd");
        editTermName.setText(termName);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);


        // Visibility of the floating action buttons will be dependent upon add, delete,
        // or update modes.
        if (termName == null && termStart == null && termEnd == null) {
            // User is in add mode, hide the deleteTermDetailsBTN
            fabDelete = findViewById(R.id.deleteTermDetailsBTN);
            fabDelete.setVisibility(View.GONE);
            // User is in add mode, hide the updateTermDetailsBTN
            fabUpdate = findViewById(R.id.updateTermDetailsBTN);
            fabUpdate.setVisibility(View.GONE);
            // User is in add mode and addTermDetailsBTN should be visible
            fabAdd = findViewById(R.id.addTermDetailsBTN);
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            // User is not in add mode, keep the deleteTermDetailsBTN visible
            fabDelete = findViewById(R.id.deleteTermDetailsBTN);
            fabDelete.setVisibility(View.VISIBLE);
            // User is not in add mode, keep the updateTermDetailsBTN visible
            fabUpdate = findViewById(R.id.updateTermDetailsBTN);
            fabUpdate.setVisibility(View.VISIBLE);
            // User is not in add mode and addTermDetailsBTN should not be visible
            fabAdd = findViewById(R.id.addTermDetailsBTN);
            fabAdd.setVisibility(View.GONE);
        }

        // Task to be performed when the update button is clicked.
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve updated data from input fields
                termName = editTermName.getText().toString();
                termStart = editTermStart.getText().toString();
                termEnd = editTermEnd.getText().toString();

                // Create a new Term object with the updated data
                Term updatedTerm = new Term(termID, termName, termStart, termEnd);

                // Update the Term in the database.
                repository.update(updatedTerm);

                // Return to the List of Terms screen
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
            }
        });
    }

    // Makes menu show up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.term_details_list, menu);
        return true;
    }
    // Actions when user clicks on menu items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_term_add_course){
            Intent intent = new Intent (TermDetails.this, CourseDetails.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_term_update_course){
            Intent intent = new Intent (TermDetails.this, CourseDetails.class);
            startActivity(intent);
            // TODO: Add code to auto-populate based on selected course.
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }
}