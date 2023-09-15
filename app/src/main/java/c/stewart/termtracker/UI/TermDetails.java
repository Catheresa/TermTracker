package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import c.stewart.termtracker.Database.DateConverter;
import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;


// This activity will allow users to view, add, and manage details of a term and be displayed on screen.
public class TermDetails extends AppCompatActivity {
    // Member variables:
    int termID;

    String termName;
    EditText editTermName;

    DatePickerDialog.OnDateSetListener termStart;
    final Calendar myCalendarStart = Calendar.getInstance();
    TextView editTermStart;

    DatePickerDialog.OnDateSetListener termEnd;
    final Calendar myCalendarEnd = Calendar.getInstance();
    TextView editTermEnd;

    String termStartDate;
    String termEndDate;

    Repository repository;
    RecyclerView recyclerView;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    // Method that is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        // Data retrieval & management.
        repository = new Repository(getApplication());

        // Sets up the views.
        recyclerView = findViewById(R.id.term_course_recycler_view);
        editTermName = findViewById(R.id.termTitleInput);
        editTermStart = findViewById(R.id.termStartDateInput);
        editTermEnd = findViewById(R.id.termEndDateInput);
        FloatingActionButton fabAdd=findViewById(R.id.addTermDetailsBTN);
        FloatingActionButton fabUpdate=findViewById(R.id.updateTermDetailsBTN);
        FloatingActionButton fabDelete=findViewById(R.id.deleteTermDetailsBTN);

        // Obtains data from the database.
        termID = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        termStartDate = getIntent().getStringExtra("termStart");
        termEndDate = getIntent().getStringExtra("termEnd");

        // Sets the data in the appropriate input field.
        editTermName.setText(termName);

        // Actions taken when user clicks on this field.
        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info=editTermStart.getText().toString();
                if(info.equals (""))info="07/01/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                new DatePickerDialog(TermDetails.this, termStart, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        termStart =  new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                termStartDate = sdf.format(myCalendarStart.getTime());
                editTermStart.setText(sdf.format(myCalendarStart.getTime()));
            }
        };

        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info=editTermEnd.getText().toString();
                if(info.equals (""))info="07/01/23";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                new DatePickerDialog(TermDetails.this, termEnd, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termEnd =  new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                termEndDate = sdf.format(myCalendarEnd.getTime());
                editTermEnd.setText(sdf.format(myCalendarEnd.getTime()));
            }
        };

        // Add item to the Room Database.
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String start = editTermStart.getText().toString();
                String end = editTermEnd.getText().toString();

                try {
                    Date startDate = sdf.parse(start);
                    Date endDate = sdf.parse(end);

                    start = sdf.format(startDate);
                    end = sdf.format(endDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                //Retrieve data from input fields
                termName = editTermName.getText().toString();

                //Create a new Term object with the entered data
                Term newTerm = new Term(0, termName, start, end);

                // Insert the new Term into the database
                repository.insert(newTerm);

                // Return to the List of Terms screen
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
                }
        });

        // Update item in the Room Database.
        //THIS IS THE EVENT HANDLER FOR THE SAVE TERM BUTTON
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve updated data from input fields
                termName = editTermName.getText().toString();
                String termStartDateString = editTermStart.getText().toString();
                String termEndDateString = editTermEnd.getText().toString();

                try {
                    Date termStartDate = sdf.parse(termStartDateString);
                    Date termEndDate = sdf.parse(termEndDateString);

                    termStartDateString = sdf.format(termStartDate);
                    termEndDateString = sdf.format(termEndDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                // Create a new Term object with the updated data
                Term updatedTerm = new Term(termID, termName, termStartDateString, termEndDateString);

                // Update the Term in the database.
                repository.update(updatedTerm);

                // Return to the List of Terms screen
                Intent intent = new Intent(TermDetails.this, TermList.class);
                startActivity(intent);
            }
        });

        // Remove item from the Room Database.
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

        // Visibility of the floating action buttons will be dependent upon action taken.
        if (termID == -1) {
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

        // Assigns the view where the list will populate.
        RecyclerView recyclerView = findViewById(R.id.term_course_recycler_view);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termID = getIntent().getIntExtra("termID", -1);
        // If user is creating a new term, the list will not populate.
        // Otherwise, the list will be filtered based on the term selected.
        if (termID!=-1) {
            courseAdapter.setCourses(repository.getAssociatedCourses(termID));
            recyclerView.setAdapter(courseAdapter);
        }else{
            recyclerView.setAdapter(null);
        }

    }

    // Refreshes the list of courses with any changes to the database.
    @Override
    protected void onResume() {
        super.onResume();

        CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Refresh the list of terms
        List<Course>  allCourses = new ArrayList<>();
        for(Course course:repository.getmAllCourses() ){
            if(course.getTermID() == termID){
                allCourses.add(course);
            }
        }
        courseAdapter.setCourses(allCourses);

        editTermStart.setText(termStartDate);
        editTermEnd.setText(termEndDate);
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