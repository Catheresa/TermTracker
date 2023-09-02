package c.stewart.termtracker.UI;

// Import statements.
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import c.stewart.termtracker.R;

public class AssessmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        FloatingActionButton fabAdd=findViewById(R.id.addAssessmentDetailsBTN);
//        TODO: Build out course addition with the floating action button

        FloatingActionButton fabDelete=findViewById(R.id.deleteAssessmentDetailsBTN);
//        TODO: Build out course deletion with the floating action button

        FloatingActionButton fabUpdate=findViewById(R.id.updateAssessmentDetailsBTN);
//        TODO: Build out course update with the floating action button
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
        if(item.getItemId()==R.id.menu_assessment_alert){
            // TODO: Add code to send alert
            return true;
        }
        if(item.getItemId()==R.id.menu_assessment_share_note){
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