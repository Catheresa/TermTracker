package c.stewart.termtracker.Entities;

// Import statements.
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

// An instance of a class that will be stored in a database table.
@Entity(tableName = "Terms")
public class Term {
    // Attributes found in the database table.
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;
    private String termStart;
    private String termEnd;

    // Creates the instances of the term class which takes parameters for the various fields.
    public Term(int termID, String termName, String termStart, String termEnd) {
        this.termID = termID;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    @Override
    public String toString(){
        return String.valueOf(termID);
    }

}
