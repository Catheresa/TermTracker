package c.stewart.termtracker.DAO;

// Import statements.
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import c.stewart.termtracker.Entities.Assessment;


// Defining a DAO (Data Access Object) interface for working with a Room database.
@Dao
public interface AssessmentDAO {

    // Adds an assessment to the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    // Modifies an existing assessment in the database.
    @Update
    void update(Assessment assessment);

    // Deletes and existing assessment in the database.
    @Delete
    void delete(Assessment assessment);

    // Queries allows user to extract data from the database.
    @Query("SELECT * FROM Assessments ORDER BY courseID ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM Assessments WHERE courseID=:courseID ORDER BY courseID ASC")
    List<Assessment> getAssociatedAssessments(int courseID);

    @Query("SELECT COUNT(*) FROM Assessments WHERE courseID=:courseID")
    int getAssessmentCountForCourse(int courseID);

}
