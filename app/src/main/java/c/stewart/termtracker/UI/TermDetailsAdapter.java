package c.stewart.termtracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.R;

// This adapter will be used in conjunction with a RecyclerView to display a filtered list of courses.
public class TermDetailsAdapter extends RecyclerView.Adapter<TermDetailsAdapter.TermDetailsViewHolder> {
    // Member variables.
    // A private field that holds a list of 'Course' objects that will be displayed.
    private List<Course> mCourses;
    // This private field holds a reference to the Android app's context.
    private final Context context;
    // This private field is used for inflating the layout for each item in the RecyclerView.
    private final LayoutInflater mInflater;

    // Constructor for the adapter class.
    public TermDetailsAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }

    // This is an inner class representing the ViewHolder for each item in the RecyclerView extending
    // RecyclerView.ViewHolder and holds references to the views within each item.
    public class TermDetailsViewHolder extends RecyclerView.ViewHolder {
        // Member variables:
        private final TextView termDetailsItemView;
        private final TextView termDetailsItemView2;

        // Constructor for the ViewHolder class.
        public TermDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sets up the views.
            termDetailsItemView = itemView.findViewById(R.id.term_details_list_item_course);
            termDetailsItemView2 = itemView.findViewById(R.id.term_details_list_item_termID);
            // Action to take on an on click event.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course current= mCourses.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStart", current.getCourseStart());
                    intent.putExtra("courseEnd", current.getCourseEnd());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseInstructor", current.getCourseInstructor());
                    intent.putExtra("courseInstructorPhone", current.getCourseInstructorPhone());
                    intent.putExtra("courseInstructorEmail", current.getCourseInstructorEmail());
                    intent.putExtra("courseNotes", current.getCourseNotes());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }

    // Creates instances of a ViewHolder, including setting up widgets.
    @NonNull
    @Override
    public TermDetailsAdapter.TermDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.term_details_list_item,parent,false);
        return new TermDetailsAdapter.TermDetailsViewHolder((itemView));
    }

    // This method used to bind data to a ViewHolder setting the data for each item based on the
    // position in the list. Fetches the appropriate data to display on the RecyclerView.
    @Override
    public void onBindViewHolder(@NonNull TermDetailsAdapter.TermDetailsViewHolder holder, int position) {
        if(mCourses!=null){
            Course current=mCourses.get(position);
            String courseTitle=current.getCourseTitle();
            int termID = current.getTermID();
            holder.termDetailsItemView.setText(courseTitle + ": ");
            holder.termDetailsItemView2.setText("Term ID " + Integer.toString(termID));
        }else{
            holder.termDetailsItemView.setText("No course name");
            holder.termDetailsItemView2.setText("No termID");
        }
    }

    // Method used to obtain the size of the dataset.
    @Override
    public int getItemCount() {
        if(mCourses!=null) {
            return mCourses.size();
        }else return 0;
    }

    // Sets the list of courses that will be visible in the UI of this term details view.
    public void setTerms(List<Course> courses){
        mCourses= courses;
        notifyDataSetChanged();
    }
}
