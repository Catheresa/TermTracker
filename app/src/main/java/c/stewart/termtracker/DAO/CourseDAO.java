package c.stewart.termtracker.DAO;

// Import statements.
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import c.stewart.termtracker.Entities.Course;


// Defining a DAO (Data Access Object) interface for working with a Room database.
@Dao
public interface CourseDAO {

    // Adds a course to the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    // Modifies an existing course in the database.
    @Update
    void update(Course course);

    // Deletes and existing course in the database.
    @Delete
    void delete(Course course);

    // Queries allows user to extract data from the database.
    @Query("SELECT * FROM Courses ORDER BY courseID ASC")
    List<Course > getAllCourses();

    @Query("SELECT * FROM Courses WHERE termID=:termID ORDER BY courseID ASC")
    List<Course> getAssociatedCourses(int termID);
}
