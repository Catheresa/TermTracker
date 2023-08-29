package c.stewart.termtracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM Courses ORDER BY courseID ASC")
    List<Course > getAllCourses();

    @Query("SELECT * FROM Courses WHERE termID=:termID ORDER BY courseID ASC")
    List<Course> getAssociatedCourses(int termID);
}
