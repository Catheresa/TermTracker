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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;

// This activity will allow users to view, add, and manage details of a term and be displayed on screen.
public class TermDetails extends AppCompatActivity {
    // Member variables:
    int termID;
    String termName;
    Date termStart;
    Date termEnd;
    EditText editTermName;
    TextView editTermStart;
    TextView editTermEnd;
    Repository repository;

    private TermDetailsAdapter  termDetailsAdapter;

    // Method that is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Add item to the Room Database.
        FloatingActionButton fabAdd=findViewById(R.id.addTermDetailsBTN);

        // Tasks to be performed when floating action button is clicked.
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Retrieve data from input fields
                termName = editTermName.getText().toString();
                String termStartString = editTermStart.getText().toString();
                String termEndString = editTermEnd.getText().toString();

                try {
                    // Parse the date strings to Date objects
                    termStart = new SimpleDateFormat("yyyy-MM-dd").parse(termStartString);
                    termEnd = new SimpleDateFormat("yyyy-MM-dd").parse(termEndString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                    return;
                }

            //Create a new Term object with the entered data
            Term newTerm = new Term(0, termName, termStart, termEnd);

            // Insert the new Term into the database
            repository.insert(newTerm);

            // Return to the List of Terms screen
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
            }
        });

        // Remove item from the Room Database.
        FloatingActionButton fabDelete=findViewById(R.id.deleteTermDetailsBTN);
        // Tasks to be performed when floating action button is clicked.
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
                        Term termToDelete = new Term(termID, " ", new Date(), new Date());
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

        // Update item in the Room Database.
        FloatingActionButton fabUpdate=findViewById(R.id.updateTermDetailsBTN);
        // Initializes the input fields with data received from the previous activity.
        editTermName= findViewById(R.id.termTitleInput);
        editTermStart = findViewById(R.id.termStartDateInput);
        editTermEnd = findViewById(R.id.termEndDateInput);
        termID = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        termStart = (Date) getIntent().getSerializableExtra("termStart");
        termEnd = (Date) getIntent().getSerializableExtra("termEnd");
        editTermName.setText(termName);

        if (termStart != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            editTermStart.setText(dateFormat.format(termStart));
        }

        if (termEnd != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            editTermEnd.setText(dateFormat.format(termEnd));
        }

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
                String termStartString = editTermStart.getText().toString();
                String termEndString = editTermEnd.getText().toString();

                try {
                    // Parse the date strings to Date objects
                    termStart = new SimpleDateFormat("yyyy-MM-dd").parse(termStartString);
                    termEnd = new SimpleDateFormat("yyyy-MM-dd").parse(termEndString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new Term object with the updated data
                Term updatedTerm = new Term(termID, termName, termStart, termEnd);

                // Update the Term in the database.
                repository.update(updatedTerm);

                // Return to the List of Terms screen
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
            }
        });
        // Assigns the view where the list will populate.
        RecyclerView recyclerView = findViewById(R.id.term_course_recycler_view);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int termID = getIntent().getIntExtra("termID", -1);
        // If user is creating a new term, the list will not populate.
        // Otherwise, the list will be filtered based on the term selected.
        if (termID!=-1) {
            courseAdapter.setCourses(repository.getAssociatedCourses(termID));
            recyclerView.setAdapter(courseAdapter);
        }else{
            recyclerView.setAdapter(null);
        }
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
//        if(item.getItemId()==R.id.menu_term_add_course){
//            Intent intent = new Intent (TermDetails.this, CourseDetails.class);
//            startActivity(intent);
//            return true;
//        }
//        if(item.getItemId()==R.id.menu_term_update_course){
//            Intent intent = new Intent (TermDetails.this, CourseDetails.class);
//            startActivity(intent);
//            return true;
//        }
        if(item.getItemId()==R.id.menu_term_update_main){
            Intent intent = new Intent (TermDetails.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_term_update_termList){
            Intent intent = new Intent (TermDetails.this, TermList.class);
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