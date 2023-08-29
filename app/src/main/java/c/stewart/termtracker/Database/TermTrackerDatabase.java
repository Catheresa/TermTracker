package c.stewart.termtracker.Database;

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

@Database(entities = {Assessment.class, Course.class, Term.class}, version = 3, exportSchema = false)
public abstract class TermTrackerDatabase extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();
    private static volatile TermTrackerDatabase INSTANCE;

    static TermTrackerDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (TermTrackerDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TermTrackerDatabase.class, "MyTermTrackerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
