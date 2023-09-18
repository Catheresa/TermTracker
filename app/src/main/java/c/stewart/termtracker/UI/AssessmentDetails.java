package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Locale;

import c.stewart.termtracker.Database.Repository;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.R;

public class AssessmentDetails extends AppCompatActivity {
    int assessmentID;
    int courseID;

    String assessmentName;
    EditText editAssessmentName;

    String assessmentType;

    DatePickerDialog.OnDateSetListener assessmentStart;
    final Calendar myCalendarAssessmentStart = Calendar.getInstance();
    TextView editAssessmentStart;
    String assessmentStartDate;

    DatePickerDialog.OnDateSetListener assessmentEnd;
    final Calendar myCalendarAssessmentEnd = Calendar.getInstance();
    TextView editAssessmentEnd;
    String assessmentEndDate;

    Repository repository;
    ScrollView scrollView;

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        // Link to the repository that links to the database.
        repository = new Repository(getApplication());

        List<String> assessmentTypeList = new ArrayList<>();
        assessmentTypeList.add("Performance Assessment");
        assessmentTypeList.add("Objective Assessment");

        // Initialize the views.
        scrollView = findViewById(R.id.assessment_detail_scrollView);
        editAssessmentName = findViewById(R.id.assessmentNameInput);
        editAssessmentStart = findViewById(R.id.assessmentStartDateInput);
        editAssessmentEnd = findViewById(R.id.assessmentEndDateInput);
        FloatingActionButton fabAdd=findViewById(R.id.addAssessmentDetailsBTN);
        FloatingActionButton fabDelete=findViewById(R.id.deleteAssessmentDetailsBTN);
        FloatingActionButton fabUpdate=findViewById(R.id.updateAssessmentDetailsBTN);

        // Obtains data from the database.
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentType = getIntent().getStringExtra("assessmentType");
        assessmentStartDate = getIntent().getStringExtra("assessmentStart");
        assessmentEndDate = getIntent().getStringExtra("assessmentEnd");

        // Sets the data in the appropriate input fields.
        editAssessmentName.setText(assessmentName);

        // Populates data in the corresponding spinners.
        Spinner courseSpinner = findViewById(R.id.assessmentCourseSpinner);
        ArrayList<Course> courseArrayList = new ArrayList<>();
        courseArrayList.addAll(repository.getmAllCourses());
        ArrayAdapter<Course> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseArrayList);
        courseSpinner.setAdapter(courseAdapter);
        int courseIndex = 0;
        for(int i=0; i<courseArrayList.size(); i++){
            if(courseArrayList.get(i).getCourseID() == courseID){courseIndex = i;}
        }
        courseSpinner.setSelection(courseIndex);

        Spinner assessmentTypeSpinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,assessmentTypeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(typeAdapter);
        int typeIndex = assessmentTypeList.indexOf(assessmentType);
        assessmentTypeSpinner.setSelection(typeIndex);

        // Editing and setting the dates.
        editAssessmentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info= editAssessmentStart.getText().toString();
                if(info.equals(" "))info = "07/01/23";
                try{
                    myCalendarAssessmentStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AssessmentDetails.this, assessmentStart, myCalendarAssessmentStart
                        .get(Calendar.YEAR), myCalendarAssessmentStart.get(Calendar.MONTH),
                        myCalendarAssessmentStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        assessmentStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarAssessmentStart.set(Calendar.YEAR, year);
                myCalendarAssessmentStart.set(Calendar.MONTH, month);
                myCalendarAssessmentStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editAssessmentStart.setText(sdf.format(myCalendarAssessmentStart.getTime()));
            }
        };

        editAssessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info= editAssessmentEnd.getText().toString();
                if(info.equals(" "))info = "07/01/23";
                try{
                    myCalendarAssessmentEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(AssessmentDetails.this, assessmentEnd, myCalendarAssessmentEnd
                        .get(Calendar.YEAR), myCalendarAssessmentEnd.get(Calendar.MONTH),
                        myCalendarAssessmentEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        assessmentEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarAssessmentEnd.set(Calendar.YEAR, year);
                myCalendarAssessmentEnd.set(Calendar.MONTH, month);
                myCalendarAssessmentEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editAssessmentEnd.setText(sdf.format(myCalendarAssessmentEnd.getTime()));
            }
        };
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from input fields.
                assessmentName = editAssessmentName.getText().toString();
                String start = editAssessmentStart.getText().toString();
                String end = editAssessmentEnd.getText().toString();

                try {
                    Date startDate = sdf.parse(start);
                    Date endDate = sdf.parse(end);

                    if(!startDate.after(endDate)){
                        start = sdf.format(startDate);
                        end = sdf.format(endDate);

                        Course selectedCourse = (Course) courseSpinner.getSelectedItem();
                        courseID = selectedCourse.getCourseID();

                        String selectedType = (String) assessmentTypeSpinner.getSelectedItem();
                        assessmentType = selectedType;

                        // Create a new Assessment object with the entered data.  Maximum of 5 assessments.
                        if(repository.getAssessmentsCountForCourse(courseID) < 5){
                            Assessment newAssessment = new Assessment(0, assessmentName,assessmentType,
                                    start, end,courseID);

                            // Insert the new Assessment into the database.
                            repository.insert(newAssessment);

                            // Return to the List of Assessments screen.
                            Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(AssessmentDetails.this, "You cannot add more than " +
                                    "5 assessments for this course.", Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "Start date cannot be after "+
                                "end date.", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Update the selected object when fab is clicked.
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve updated data from input fields.
                assessmentName = editAssessmentName.getText().toString();
                String assessmentStartDateString = editAssessmentStart.getText().toString();
                String assessmentEndDateString = editAssessmentEnd.getText().toString();

                try {
                    Date assessmentStartDate = sdf.parse(assessmentStartDateString);
                    Date assessmentEndDate = sdf.parse(assessmentEndDateString);

                    if(!assessmentStartDate.after(assessmentEndDate)){
                        assessmentStartDateString = sdf.format(assessmentStartDate);
                        assessmentEndDateString = sdf.format(assessmentEndDate);

                        Course selectedCourse = (Course) courseSpinner.getSelectedItem();
                        courseID = selectedCourse.getCourseID();

                        String selectedType = (String) assessmentTypeSpinner.getSelectedItem();
                        assessmentType = selectedType;

                        // Create a new Assessment object with the entered data. Maximum of 5 assessments.
                        if(repository.getAssessmentsCountForCourse(courseID) < 5){
                            Assessment updatedAssessment = new Assessment(assessmentID, assessmentName,assessmentType,
                                    assessmentStartDateString, assessmentEndDateString,courseID);

                            // Insert the new Assessment into the database.
                            repository.update(updatedAssessment);

                            // Return to the List of Assessments screen.
                            Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(AssessmentDetails.this, "You cannot have more than " +
                                    "5 assessments for the course selected.", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Start date cannot be after "+
                                "end date.", Toast.LENGTH_LONG).show();
                    }

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Delete the selected object when a button is clicked.
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAssessment();
            }

            private void deleteAssessment() {
                int assessmentID = getIntent().getIntExtra("assessmentID", -1);
                int courseID = getIntent().getIntExtra("courseID", -1);

                Assessment assessmentToDelete = new Assessment(assessmentID, " ",
                        " ", " "," ", courseID);
                repository.delete(assessmentToDelete);
                // Closes activity after deletion.
                finish();
            }
        });
        // Visibility of the floating action buttons will be dependent upon add, delete, or update modes.
        if (assessmentID == -1) {
            // User is in add mode, hide the deleteCourseDetailsBTN.
            fabDelete = findViewById(R.id.deleteAssessmentDetailsBTN);
            fabDelete.setVisibility(View.GONE);
            // User is in add mode, hide the updateCourseDetailsBTN.
            fabUpdate = findViewById(R.id.updateAssessmentDetailsBTN);
            fabUpdate.setVisibility(View.GONE);
            // User is in add mode and the fab should be visible.
            fabAdd = findViewById(R.id.addAssessmentDetailsBTN);
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            // User is in add mode, hide the fab.
            fabDelete = findViewById(R.id.deleteAssessmentDetailsBTN);
            fabDelete.setVisibility(View.VISIBLE);
            // User is in add mode, hide the fab.
            fabUpdate = findViewById(R.id.updateAssessmentDetailsBTN);
            fabUpdate.setVisibility(View.VISIBLE);
            // User is not in add mode and addCourseDetailsBTN should not be visible
            fabAdd = findViewById(R.id.addAssessmentDetailsBTN);
            fabAdd.setVisibility(View.GONE);
        }
    }

    private String today(){
        return sdf.format(new Date());
    }

    @Override
    protected void onResume(){
        super.onResume();

        editAssessmentStart.setText(assessmentStartDate);
        editAssessmentEnd.setText(assessmentEndDate);
    }

    // Makes menu show up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_detail_list, menu);
        return true;
    }
    // Actions when user clicks on menu items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_assessment_detail_main){
            Intent intent = new Intent (AssessmentDetails.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_assessment_detail_assessment){
            Intent intent = new Intent (AssessmentDetails.this, AssessmentList.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_assessment_alert_start){
            String startDateFromScreen = editAssessmentStart.getText().toString();
            Date myDate1 = null;
            if (startDateFromScreen.equals(today())) {
                try{
                    myDate1 = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = myDate1.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Assessment ID: " + assessmentID + " " + assessmentName + "  starts today");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this,
                        ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);
                return true;
            }
        }
        if(item.getItemId()==R.id.menu_assessment_alert_end){
            String endDateFromScreen = editAssessmentEnd.getText().toString();
            Date myDate2 = null;

            if (endDateFromScreen.equals(today())) {
                try{
                    myDate2 = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = myDate2.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Assessment ID: " + assessmentID + " " + assessmentName + " ends today");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this,
                        ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);
                return true;
            } else {
                return false;
            }
        }
        if(item.getItemId()==R.id.menu_assessment_alert_start_end){
            String startDateFromScreen = editAssessmentStart.getText().toString();
            String endDateFromScreen = editAssessmentEnd.getText().toString();
            Date myDate1 = null;
            Date myDate2 = null;

            if(startDateFromScreen.equals(today()) && endDateFromScreen.equals(today())){
                try{
                    myDate1 = sdf.parse(startDateFromScreen);
                    myDate2 = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = myDate1.getTime();
                Long endTrigger = myDate2.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Assessment ID: "+ assessmentID + assessmentName + " starts & ends today");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this,
                        ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);
                return true;
            } else if (startDateFromScreen.equals(today())) {
                try{
                    myDate1 = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = myDate1.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Assessment ID: " + assessmentID + " " + assessmentName + "  starts today");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this,
                        ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender);
                return true;
            } else if (endDateFromScreen.equals(today())) {
                try{
                    myDate2 = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = myDate2.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Assessment ID: " + assessmentID + " " + assessmentName + "  ends today");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this,
                        ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);
                return true;
            } else {
                return false;
            }
        }

        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }
}