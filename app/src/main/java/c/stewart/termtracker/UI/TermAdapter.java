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
import c.stewart.termtracker.Entities.Term;
import c.stewart.termtracker.R;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    // Member variables.
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    // An adapter connects the UI and the data source (a bridge to the data).
    public TermAdapter(Context context){
        // Inflater reads the XML file that describes a layout & creates objects that
        // correspond to it making the object visible within the app.
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }

    // A ViewHolder is a wrapper around a View that contains the layout for an individual
    // item in the list.
    public class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        // Constructor for the ViewHolder class.
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sets up the view.
            termItemView=itemView.findViewById(R.id.term_list_item);
            // Action to take on an on click event.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Term current= mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("termStart", current.getTermStart());
                    intent.putExtra("termEnd", current.getTermEnd());
                    context.startActivity(intent);
                }
            });
        }
    }

    // Creates instances of a ViewHolder, including setting up widgets.
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder((itemView));
    }

    // Fetches the appropriate data to display on the RecyclerView.
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null){
            Term current=mTerms.get(position);
            String termName=current.getTermName();
            holder.termItemView.setText(termName);
        }else{
            holder.termItemView.setText("No term name");
        }
    }

    // Method used to obtain the size of the dataset.
    @Override
    public int getItemCount() {
        if(mTerms!=null) {
            return mTerms.size();
        }else return 0;
    }

    // Sets the list of terms that will be visible in the UI.
    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }
}
