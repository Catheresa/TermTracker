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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
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
public class CourseDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    // Member variables
    int termID;

    int courseID;

    String courseTitle;
    EditText editCourseTitle;

    DatePickerDialog.OnDateSetListener courseStart;
    final Calendar myCalendarCourseStart = Calendar.getInstance();
    TextView editCourseStart;
    String courseStartDate;

    DatePickerDialog.OnDateSetListener courseEnd;
    final Calendar myCalendarCourseEnd = Calendar.getInstance();
    TextView editCourseEnd;
    String courseEndDate;

    String courseStatus;

    String courseInstructor;
    EditText editCourseInstructor;

    String courseInstructorPhone;
    EditText editCourseInstructorPhone;

    String courseInstructorEmail;
    EditText editCourseInstructorEmail;

    String courseNotes;
    EditText editCourseNotes;

    Repository repository;
    RecyclerView recyclerView;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    // Method that is called when the activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        // Link to the repository that links to the database.
        repository = new Repository(getApplication());

        List<String> courseStatusList = new ArrayList<>();
        courseStatusList.add("In progress");
        courseStatusList.add("Completed");
        courseStatusList.add("Dropped");
        courseStatusList.add("Plan to take");

        // Initializes the views.
        recyclerView = findViewById(R.id.course_assessment_recycler_view);
        editCourseTitle = findViewById(R.id.courseTitleInput);
        editCourseInstructor = findViewById(R.id.instructorInput);
        editCourseInstructorPhone = findViewById(R.id.instructorPhoneInput);
        editCourseInstructorEmail = findViewById(R.id.instructorEmailInput);
        editCourseNotes = findViewById(R.id.courseNotesInput);
        editCourseStart = findViewById(R.id.courseStartDateInput);
        editCourseEnd = findViewById(R.id.courseEndDateInput);
        FloatingActionButton fabAdd = findViewById(R.id.addCourseDetailsBTN);
        FloatingActionButton fabUpdate = findViewById(R.id.updateCourseDetailsBTN);
        FloatingActionButton fabDelete = findViewById(R.id.deleteCourseDetailsBTN);

        // Obtains data from the database.
        termID = getIntent().getIntExtra("termID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");
        courseStartDate = getIntent().getStringExtra("courseStart");
        courseEndDate = getIntent().getStringExtra("courseEnd");
        courseStatus = getIntent().getStringExtra("courseStatus");
        courseInstructor = getIntent().getStringExtra("courseInstructor");
        courseInstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
        courseInstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
        courseNotes = getIntent().getStringExtra("courseNotes");

        // Sets the data in the appropriate input field.
        editCourseTitle.setText(courseTitle);
        editCourseInstructor.setText(courseInstructor);
        editCourseInstructorPhone.setText(courseInstructorPhone);
        editCourseInstructorEmail.setText(courseInstructorEmail);
        editCourseNotes.setText(courseNotes);

        // Populates data in the corresponding spinners.
        Spinner termSpinner = findViewById(R.id.termSpinner);
        ArrayList<Term> termArrayList = new ArrayList<>();
        termArrayList.addAll(repository.getmAllTerms());
        ArrayAdapter<Term> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termArrayList);
        termSpinner.setAdapter(termAdapter);
        int termIndex = 0;
        for(int i=0; i<termArrayList.size(); i++){
            if(termArrayList.get(i).getTermID() == termID){termIndex = i;}
        }
        termSpinner.setSelection(termIndex);

        Spinner courseStatusSpinner = findViewById(R.id.courseStatusSpinner);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courseStatusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);
        int statusIndex = courseStatusList.indexOf(courseStatus);
        courseStatusSpinner.setSelection(statusIndex);
        courseStatusSpinner.setOnItemSelectedListener(this);

        // Editing and setting the dates
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info= editCourseStart.getText().toString();
                if(info.equals(" "))info = "07/01/23";
                try{
                    myCalendarCourseStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
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
                String info= editCourseEnd.getText().toString();
                if(info.equals(" "))info = "07/01/23";
                try{
                    myCalendarCourseEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
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
//                editCourseEnd.setText(courseEndDate);
                editCourseEnd.setText(sdf.format(myCalendarCourseEnd.getTime()));
            }
        };
        // Add an object.
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = editCourseStart.getText().toString();
                String end = editCourseEnd.getText().toString();

                try {
                    Date startDate = sdf.parse(start);
                    Date endDate = sdf.parse(end);

                    start = sdf.format(startDate);
                    end = sdf.format(endDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                // Retrieve data from input fields.
                courseTitle = editCourseTitle.getText().toString();
                courseInstructor = editCourseInstructor.getText().toString();
                courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                courseNotes = editCourseNotes.getText().toString();
                Term selectedTerm = (Term) termSpinner.getSelectedItem();
                termID = selectedTerm.getTermID();

                String selectedStatusString = (String ) courseStatusSpinner.getSelectedItem();
                courseStatus = selectedStatusString;

                // Create a new Course object with the entered data.
                Course newCourse = new Course(0, courseTitle, start, end, courseStatus,
                        courseInstructor, courseInstructorPhone, courseInstructorEmail, courseNotes,
                        termID);

                // Insert the new Course into the database.
                repository.insert(newCourse);

                // Return to the List of Courses screen.
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                startActivity(intent);
            }
        });
        // Update the selected object when fab is clicked.
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve updated data from input fields
                courseTitle = editCourseTitle.getText().toString();
                String courseStartDateString = editCourseStart.getText().toString();
                String courseEndDateString = editCourseEnd.getText().toString();

                try {
                    Date courseStartDate = sdf.parse(courseStartDateString);
                    Date courseEndDate = sdf.parse(courseEndDateString);

                    courseStartDateString = sdf.format(courseStartDate);
                    courseEndDateString = sdf.format(courseEndDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                courseInstructor = editCourseInstructor.getText().toString();
                courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                courseNotes = editCourseNotes.getText().toString();
                Term selectedTerm = (Term) termSpinner.getSelectedItem();
                termID = selectedTerm.getTermID();
                String selectedStatus = (String) courseStatusSpinner.getSelectedItem();

                // Create a new Course object with the updated data
                Course updatedCourse = new Course(courseID, courseTitle, courseStartDateString,
                        courseEndDateString, selectedStatus, courseInstructor,
                        courseInstructorPhone, courseInstructorEmail, courseNotes, termID);

                // Update the Course in the database.
                repository.update(updatedCourse);

                // Return to the List of Courses screen
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                startActivity(intent);
            }
        });

        // Delete the selected object when a button is clicked.
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
                        Course courseToDelete = new Course(courseID, " ", " ", " ",
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
        /* If user is creating a new course, the list will not populate. Otherwise, the list will be
         filtered based on the course selected.*/
        if (courseID != -1) {
            assessmentAdapter.setAssessments(repository.getAssociatedAssessments(courseID));
            recyclerView.setAdapter(assessmentAdapter);
        } else {
            recyclerView.setAdapter(null);
        }

        // Visibility of the floating action buttons will be dependent upon add, delete, or update modes.
        if (courseID == -1) {
            // User is in add mode, hide the deleteCourseDetailsBTN.
            fabDelete = findViewById(R.id.deleteCourseDetailsBTN);
            fabDelete.setVisibility(View.GONE);
            // User is in add mode, hide the updateCourseDetailsBTN.
            fabUpdate = findViewById(R.id.updateCourseDetailsBTN);
            fabUpdate.setVisibility(View.GONE);
            // User is in add mode and the fab should be visible.
            fabAdd = findViewById(R.id.addCourseDetailsBTN);
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            // User is in add mode, hide the fab.
            fabDelete = findViewById(R.id.deleteCourseDetailsBTN);
            fabDelete.setVisibility(View.VISIBLE);
            // User is in add mode, hide the fab.
            fabUpdate = findViewById(R.id.updateCourseDetailsBTN);
            fabUpdate.setVisibility(View.VISIBLE);
            // User is not in add mode and addCourseDetailsBTN should not be visible
            fabAdd = findViewById(R.id.addCourseDetailsBTN);
            fabAdd.setVisibility(View.GONE);
        }
    }
    // Refreshes the list of assessments with any changes to the database.
    @Override
    protected void onResume() {
        super.onResume();

        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Refresh the list of terms
        List<Assessment>  allAssessments = new ArrayList<>();
        for(Assessment assessment:repository.getmAllAssessments() ){
            if(assessment.getCourseID() == courseID){
                allAssessments.add(assessment);
            }
        }
        assessmentAdapter.setAssessments(allAssessments);

        editCourseStart.setText(courseStartDate);
        editCourseEnd.setText(courseEndDate);
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
        if(item.getItemId()==R.id.menu_course_detail_main){
            Intent intent = new Intent (CourseDetails.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_course_detail_course){
            Intent intent = new Intent (CourseDetails.this, CourseList.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_course_alert){
            String dateFromScreen = editCourseStart.getText().toString();
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
            sentIntent.putExtra(Intent.EXTRA_TEXT, editCourseNotes.getText().toString()+ " SHARE_SUCCESS");
            sentIntent.putExtra(Intent.EXTRA_TITLE, editCourseNotes.getText().toString()+ " SHARE_SUCCESS");
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

    @Override
    public void onItemSelected(AdapterView<?> parentView, View view, int position, long l) {
        String selectedStatus = parentView.getItemAtPosition(position).toString();
        courseStatus = selectedStatus;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {

    }
}