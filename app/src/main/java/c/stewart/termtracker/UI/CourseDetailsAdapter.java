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

import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.R;

// This adapter will be used in conjunction with a RecyclerView to display a filtered list of assessments.
public class CourseDetailsAdapter extends RecyclerView.Adapter<CourseDetailsAdapter.CourseDetailsViewHolder> {
    // A private field that holds a list of 'Assessment' objects that will be displayed.
    private List<Assessment> mAssessments;
    // When you see the keyword final used before a variable declaration, it means that the
    // variable is a constant and cannot be reassigned or modified once it has been initialized.
    // This private field holds a reference to the Android app's context.
    private final Context context;
    // This private field is used for inflating the layout for each item in the RecyclerView.
    private final LayoutInflater mInflater;

    // Constructor for the adapter class.
    public CourseDetailsAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    // This is an inner class representing the ViewHolder for each item in the RecyclerView extending
    // RecyclerView.ViewHolder and holds references to the views within each item.
    public class CourseDetailsViewHolder extends RecyclerView.ViewHolder {
        // Member variables.
        private final TextView courseDetailsItemView;
        private final TextView courseDetailsItemView2;

        // Constructor for the ViewHolder class.
        public CourseDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sets up the views.
            courseDetailsItemView = itemView.findViewById(R.id.course_details_list_item_assessment);
            courseDetailsItemView2 = itemView.findViewById(R.id.course_details_list_item_courseID);
            // Action to take on an on click event.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Assessment current= mAssessments.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentStart", current.getAssessmentStart());
                    intent.putExtra("assessmentEnd", current.getAssessmentEnd());
                    intent.putExtra("assessmentNotes", current.getAssessmentNotes());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    // Creates instances of a ViewHolder, including setting up widgets.
    @NonNull
    @Override
    public CourseDetailsAdapter.CourseDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_details_list_item,parent,false);
        return new CourseDetailsAdapter.CourseDetailsViewHolder((itemView));
    }
    // This method used to bind data to a ViewHolder setting the data for each item based on the
    // position in the list. Fetches the appropriate data to display on the RecyclerView.
    @Override
    public void onBindViewHolder(@NonNull CourseDetailsAdapter.CourseDetailsViewHolder holder, int position) {
        if(mAssessments!=null){
            Assessment current=mAssessments.get(position);
            String assessmentName=current.getAssessmentName();
            int courseID = current.getCourseID();
            holder.courseDetailsItemView.setText(assessmentName + ": ");
            holder.courseDetailsItemView2.setText("Course ID " + Integer.toString(courseID));
        }else{
            holder.courseDetailsItemView.setText("No assessment name");
            holder.courseDetailsItemView2.setText("No courseID");
        }
    }
    // Method used to obtain the size of the dataset.
    @Override
    public int getItemCount() {
        if(mAssessments!=null) {
            return mAssessments.size();
        }else return 0;
    }
    public void setCourses(List<Assessment> assessments){
        mAssessments= assessments;
        notifyDataSetChanged();
    }
}
