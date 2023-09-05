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
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.R;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private final TextView assessmentItemView2;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.assessment_list_item);
            assessmentItemView2=itemView.findViewById(R.id.assessment_list_item_course);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Assessment current= mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentStart", current.getAssessmentStart());
                    intent.putExtra("assessmentEnd", current.getAssessmentEnd());
                    intent.putExtra("assessmentStatus", current.getAssessmentStatus());
                    intent.putExtra("assessmentNotes", current.getAssessmentNotes());
                    intent.putExtra("courseID", current.getCourseID());
                }
            });
        }
    }
    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments!=null){
            Assessment current=mAssessments.get(position);
            String assessmentName=current.getAssessmentName();
            int courseID= current.getCourseID();
            holder.assessmentItemView.setText(assessmentName + ": ");
            holder.assessmentItemView2.setText("Course ID " + Integer.toString(courseID));
        }else{
            holder.assessmentItemView.setText("No assessment name");
            holder.assessmentItemView2.setText("No course id");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments= assessments;
        notifyDataSetChanged();
    }

    public  int getItemCount(){
        if(mAssessments!=null) return mAssessments.size();
        else return 0;
    }
}
