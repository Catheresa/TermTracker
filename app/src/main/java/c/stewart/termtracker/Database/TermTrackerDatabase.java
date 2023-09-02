package c.stewart.termtracker.Database;

// Import statements.
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import c.stewart.termtracker.DAO.AssessmentDAO;
import c.stewart.termtracker.DAO.CourseDAO;
import c.stewart.termtracker.DAO.TermDAO;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;

// Code represents the Room Database
@Database(entities = {Assessment.class, Course.class, Term.class}, version = 3, exportSchema = false)
public abstract class TermTrackerDatabase extends RoomDatabase {
    // Three abstract methods declared to access its corresponding DAO for that entity.
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();

    // Variable used to create a single instance of the database.
    private static volatile TermTrackerDatabase INSTANCE;

    // Method used to obtain an instance of the database taking context parameters.
    static TermTrackerDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (TermTrackerDatabase.class){
                if(INSTANCE==null){
                    // Initializes the database with the apps context.
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                                    TermTrackerDatabase.class, "MyTermTrackerDatabase.db")
                            // Method used to handle database schema migrations by destroying
                            // and recreating the db when the schema changes.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
