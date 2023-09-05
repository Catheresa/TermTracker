package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;

// This activity will allow users to view, add, and manage details of a course and be displayed on screen.
public class CourseDetails extends AppCompatActivity {
    // Member variables
    int courseID;
    int termID;
    String courseTitle;
    DatePickerDialog.OnDateSetListener courseStart;
    final Calendar myCalendarCourseStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener courseEnd;
    final Calendar myCalendarCourseEnd = Calendar.getInstance();
    String courseStatus;
    String courseInstructor;
    String courseInstructorPhone;
    String courseInstructorEmail;
    String courseNotes;


    EditText editCourseID;
    EditText editCourseTitle;
    TextView editCourseStart;
    TextView editCourseEnd;
    EditText editCourseStatus;
    EditText editCourseInstructor;
    EditText editCourseInstructorPhone;
    EditText editCourseInstructorEmail;
    EditText editCourseNotes;
    EditText editTermID;
    Repository repository;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);



    // Method that is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        repository = new Repository(getApplication());

        // Initializing fields
        editCourseTitle = findViewById(R.id.courseTitleInput);
        courseTitle = getIntent().getStringExtra("courseTitle");
        editCourseTitle.setText(courseTitle);

        editCourseStatus = findViewById(R.id.courseStatusInput);
        courseStatus = getIntent().getStringExtra("courseStatus");
        editCourseStatus.setText(courseStatus);

        editCourseInstructor = findViewById(R.id.instructorInput);
        courseInstructor = getIntent().getStringExtra("courseInstructor");
        editCourseInstructor.setText(courseInstructor);

        editCourseInstructorPhone = findViewById(R.id.instructorPhoneInput);
        courseInstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
        editCourseInstructorPhone.setText(courseInstructorPhone);

        editCourseInstructorEmail = findViewById(R.id.instructorEmailInput);
        courseInstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
        editCourseInstructorEmail.setText(courseInstructorEmail);

        editCourseNotes = findViewById(R.id.courseNotesInput);
        courseNotes = getIntent().getStringExtra("courseNotes");
        editCourseNotes.setText(courseNotes);

        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);

        editCourseStart = findViewById(R.id.courseStartDateInput);
        editCourseStart.setText(getIntent().getStringExtra("courseStart"));

        editCourseEnd = findViewById(R.id.courseEndDateInput);
        editCourseEnd.setText(getIntent().getStringExtra("courseEnd"));

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generate method stub???
                Date date;
                // TODO: get value from other screen, but it's hard coded here????
//                String info= editCourseStart.getText().toString();
//                if(info.equals(" "))info = "07/01/23";
//                try{
//                    myCalendarCourseStart.setTime(sdf.parse(info));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                String courseStartDate = getIntent().getStringExtra("courseStart");
                if (courseStartDate != null && !courseStartDate.isEmpty()) {
                    try {
                        myCalendarCourseStart.setTime(sdf.parse(courseStartDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                new DatePickerDialog(CourseDetails.this, courseStart, myCalendarCourseStart
                        .get(Calendar.YEAR), myCalendarCourseStart.get(Calendar.MONTH),
                        myCalendarCourseStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarCourseStart.set(Calendar.YEAR, year);
                myCalendarCourseStart.set(Calendar.MONTH, month);
                myCalendarCourseStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editCourseStart.setText(sdf.format(myCalendarCourseStart.getTime()));
            }

        };

        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date2;
//                String info2= editCourseEnd.getText().toString();
//                if(info2.equals(" "))info2 = "07/01/23";
//                try{
//                    myCalendarCourseEnd.setTime(sdf.parse(info2));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                String courseEndDate = getIntent().getStringExtra("courseEnd");
                if (courseEndDate != null && !courseEndDate.isEmpty()) {
                    try {
                        myCalendarCourseStart.setTime(sdf.parse(courseEndDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }



                new DatePickerDialog(CourseDetails.this, courseEnd, myCalendarCourseEnd
                        .get(Calendar.YEAR), myCalendarCourseEnd.get(Calendar.MONTH),
                        myCalendarCourseEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarCourseEnd.set(Calendar.YEAR, year);
                myCalendarCourseEnd.set(Calendar.MONTH, month);
                myCalendarCourseEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editCourseEnd.setText(sdf.format(myCalendarCourseEnd.getTime()));
            }
        };
        // Initialize repository and courseDetailAdapter here.
        Spinner spinner = findViewById(R.id.termSpinner);
        ArrayList<Term> termArrayList = new ArrayList<>();

        termArrayList.addAll(repository.getmAllTerms());

        ArrayAdapter<Term> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termArrayList);
        spinner.setAdapter(termAdapter);

        // Find the Term object with matching termID
        int selectedTermPosition = -1;
        for (int i = 0; i < termArrayList.size(); i++) {
            if (termArrayList.get(i).getTermID() == termID) {
                selectedTermPosition = i;
                break;
            }
        }

        // Set the spinner's selection based on the found Term object
        if (selectedTermPosition != -1) {
            spinner.setSelection(selectedTermPosition);
        } else {
            // Handle the case where the termID doesn't match any of the available terms.
            // You can choose to set a default selection or handle it as needed.
        }

        // Add an object.
        FloatingActionButton fabAdd = findViewById(R.id.addCourseDetailsBTN);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from input fields.
                courseTitle = editCourseTitle.getText().toString();
                courseStatus = editCourseStatus.getText().toString();
                courseInstructor = editCourseInstructor.getText().toString();
                courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                courseNotes = editCourseNotes.getText().toString();
                Term selectedTerm = (Term) spinner.getSelectedItem();
                termID = selectedTerm.getTermID();

                // Parse data strings to Date objects
                Date startDate = null;

                // Create a new Course object with the entered data.
                Course newCourse = new Course(0, courseTitle, myCalendarCourseStart.getTime(),
                        myCalendarCourseEnd.getTime(), courseStatus, courseInstructor,
                        courseInstructorPhone, courseInstructorEmail, courseNotes, 0);

                // Insert the new Course into the database.
                repository.insert(newCourse);

                // Return to the List of Courses screen.
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                startActivity(intent);
            }
        });

        // Update an object.
        FloatingActionButton fabUpdate = findViewById(R.id.updateCourseDetailsBTN);

        // Task to be performed when the update button is clicked.
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve updated data from input fields
                courseTitle = editCourseTitle.getText().toString();
                courseStatus = editCourseStatus.getText().toString();
                courseInstructor = editCourseInstructor.getText().toString();
                courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                courseNotes = editCourseNotes.getText().toString();
                String updatedCourseStart = editCourseStart.getText().toString();
                String updatedCourseEnd = editCourseEnd.getText().toString();
                Term selectedTerm = (Term) spinner.getSelectedItem();
                termID = selectedTerm.getTermID();

                // Create a new Course object with the updated data
                Course updatedCourse = new Course(0, courseTitle, myCalendarCourseStart.getTime(),
                        myCalendarCourseEnd.getTime(), courseStatus, courseInstructor,
                        courseInstructorPhone, courseInstructorEmail, courseNotes, 0);

                // Update the Course in the database.
                repository.update(updatedCourse);

                // Return to the List of Courses screen
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                startActivity(intent);
            }
        });

        // Delete an object.
        FloatingActionButton fabDelete = findViewById(R.id.deleteCourseDetailsBTN);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }

            private void deleteCourse() {
                int courseID = getIntent().getIntExtra("courseID", -1);
                int termID = getIntent().getIntExtra("termID", -1);
                if (courseID != -1) {
                    // Check if there are associated courses
                    List<Assessment> associatedAssessments = repository.getAssociatedAssessments(courseID);

                    if (associatedAssessments.isEmpty()) {
                        // Allow deletion if there are no associated courses.
                        Course courseToDelete = new Course(courseID, " ", new Date(), new Date(),
                                " ", "", "", "",
                                " ", termID);
                        repository.delete(courseToDelete);
                        // Closes activity after deletion.
                        finish();
                    } else {
                        // Display an error message.
                        Toast.makeText(getApplicationContext(), "Cannot delete course with " +
                                "associated assessments.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Assigns the view where the list will populate.
        RecyclerView recyclerView = findViewById(R.id.course_assessment_recycler_view);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int courseID = getIntent().getIntExtra("courseID", -1);
        // If user is creating a new course, the list will not populate.
        // Otherwise, the list will be filtered based on the course selected.
        if (courseID != -1) {
            assessmentAdapter.setAssessments(repository.getAssociatedAssessments(courseID));
            recyclerView.setAdapter(assessmentAdapter);
        } else {
            recyclerView.setAdapter(null);
        }

        // Visibility of the floating action buttons will be dependent upon add, delete, or update modes.
        if (courseTitle == null && courseStart == null && courseEnd == null && courseStatus == null
                && courseInstructor == null && courseInstructorPhone == null && courseInstructorEmail == null
                && courseNotes == null) {
            // User is in add mode, hide the deleteCourseDetailsBTN.
            fabDelete = findViewById(R.id.deleteCourseDetailsBTN);
            fabDelete.setVisibility(View.GONE);
            // User is in add mode, hide the updateCourseDetailsBTN.
            fabUpdate = findViewById(R.id.updateCourseDetailsBTN);
            fabUpdate.setVisibility(View.GONE);
        } else {
            // User is not in add mode and addCourseDetailsBTN should not be visible
            fabAdd = findViewById(R.id.addCourseDetailsBTN);
            fabAdd.setVisibility(View.GONE);
        }
    }

    // Makes menu show up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.course_detail_list, menu);
        return true;
    }
    // Actions when user clicks on menu items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_course_add_assessment){
            Intent intent = new Intent (CourseDetails.this, AssessmentDetails.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_course_update_assessment){
            Intent intent = new Intent (CourseDetails.this, AssessmentDetails.class);
            startActivity(intent);
            // TODO: Add code to auto-populate based on selected course.  DO I NEED THIS?
            return true;
        }
        if(item.getItemId()==R.id.menu_course_alert){
            String dateFromScreen = editCourseStart.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate1 = null;
            try {
                myDate1 = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate1.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", "message I want to see");
            PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this,++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            return true;
        }
        if(item.getItemId()==R.id.menu_course_share_note){
            Intent sentIntent= new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editCourseNotes.getText().toString()+ "SHARE_SUCCESS");
            sentIntent.putExtra(Intent.EXTRA_TITLE, editCourseNotes.getText().toString()+ "SHARE_SUCCESS");
            sentIntent.setType("text/plain");
            Intent shareIntent=Intent.createChooser(sentIntent,null);
            startActivity(shareIntent);
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }
}