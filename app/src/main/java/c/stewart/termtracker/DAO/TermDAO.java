package c.stewart.termtracker.DAO;

// Import statements.
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import c.stewart.termtracker.Entities.Term;

// Defining a DAO (Data Access Object) interface for working with a Room database.
@Dao
public interface TermDAO {
    // Adds an term to the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    // Modifies an existing term in the database.
    @Update
    void update(Term term);

    // Deletes and existing term in the database.
    @Delete
    void delete(Term term);

    // Queries allows user to extract data from the database.
    @Query("SELECT * FROM Terms ORDER BY termID ASC")
    List<Term> getAllTerms();

}
