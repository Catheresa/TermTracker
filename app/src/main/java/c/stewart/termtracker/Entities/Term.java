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
    private Date termStart;
    private Date termEnd;

    // Creates the instances of the term class which takes parameters for the various fields.

    public Term(int termID, String termName, Date termStart, Date termEnd) {
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

    public Date getTermStart() {
        return termStart;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }
    @Override
    public String toString(){
        return String.valueOf(termID);
    }
}
