package c.stewart.termtracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import c.stewart.termtracker.R;

public class TermDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        FloatingActionButton fabAdd=findViewById(R.id.addTermDetailsBTN);
//        TODO: Build out term addition with the floating action button

        FloatingActionButton fabDelete=findViewById(R.id.deleteTermDetailsBTN);
//        TODO: Build out term deletion with the floating action button

        FloatingActionButton fabUpdate=findViewById(R.id.updateTermDetailsBTN);
//        TODO: Build out term deletion with the floating action button

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