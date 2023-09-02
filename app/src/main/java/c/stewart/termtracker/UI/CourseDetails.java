package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import c.stewart.termtracker.R;

public class CourseDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        FloatingActionButton fabAdd=findViewById(R.id.addCourseDetailsBTN);
//        TODO: Build out course addition with the floating action button

        FloatingActionButton fabDelete=findViewById(R.id.deleteCourseDetailsBTN);
//        TODO: Build out course deletion with the floating action button

        FloatingActionButton fabUpdate=findViewById(R.id.updateCourseDetailsBTN);
//        TODO: Build out course update with the floating action button
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
            // TODO: Add code to auto-populate based on selected course.
            return true;
        }
        if(item.getItemId()==R.id.menu_course_alert){

            // TODO: Add code to send alert
            return true;
        }
        if(item.getItemId()==R.id.menu_course_share_note){

            // TODO: Add code to share note
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }
}