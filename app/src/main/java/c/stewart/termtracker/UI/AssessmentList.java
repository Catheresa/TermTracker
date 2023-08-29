package c.stewart.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import c.stewart.termtracker.R;

public class AssessmentList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        FloatingActionButton fabAdd=findViewById(R.id.addAssessmentBTN);
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(AssessmentList.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });

////        TODO: Build out term deletion with the floating action button
//
//        FloatingActionButton fabUpdate=findViewById(R.id.updateAssessmentBTN);
//        fabUpdate.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                // TODO: Pull in data from the selected list item
//                Intent intent=new Intent(AssessmentList.this, AssessmentDetails.class);
//                startActivity(intent);
//            }
//        });
    }

    // Makes menu show up.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_list, menu);
        return true;
    }
    // Actions when user clicks on menu items.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_assessment_list_terms){
            Intent intent = new Intent (AssessmentList.this, TermList.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId()==R.id.menu_assessment_list_courses){
            Intent intent = new Intent (AssessmentList.this, CourseList.class);
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