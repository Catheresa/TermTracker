package c.stewart.termtracker.UI;

// Import statements.
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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView courseItemView2;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView=itemView.findViewById(R.id.course_list_item_assessment);
            courseItemView2=itemView.findViewById(R.id.course_list_item_term);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course current= mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
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
                }
            });
        }
    }
    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current=mCourses.get(position);
            String courseTitle=current.getCourseTitle();
            int termID= current.getTermID();
            holder.courseItemView.setText(courseTitle);
            holder.courseItemView2.setText("Term ID: " + Integer.toString(termID));
        }else{
            holder.courseItemView.setText("No course name");
            holder.courseItemView2.setText("No term id");
        }
    }

    public void setCourses(List<Course> courses){
        mCourses= courses;
        notifyDataSetChanged();
    }

    public  int getItemCount(){
        if(mCourses!=null) return mCourses.size();
        else return 0;
    }
}
