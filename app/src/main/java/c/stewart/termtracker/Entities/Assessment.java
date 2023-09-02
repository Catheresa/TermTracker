package c.stewart.termtracker.Entities;

// Import statements.
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// An instance of a class that will be stored in a database table.
@Entity(tableName = "Assessments")
public class Assessment {
    // Attributes found in the database table.
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String termName;
    private String assessmentName;
    private String assessmentType;
    private String assessmentStart;
    private String assessmentEnd;
    private String assessmentStatus;
    private String assessmentNotes;

    // Foreign key
    private int courseID;

    // Creates the instances of the assessment class which takes parameters for the various fields.
    public Assessment(int assessmentID, String termName, String assessmentName,
                      String assessmentType, String assessmentStart, String assessmentEnd,
                      String assessmentStatus, String assessmentNotes, int courseID) {
        this.assessmentID = assessmentID;
        this.termName = termName;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.assessmentStatus = assessmentStatus;
        this.assessmentNotes = assessmentNotes;
        this.courseID = courseID;
    }

    // Getters and setters used to get and set values of the private fields.
    public int getAssessmentID() {
        return assessmentID;
    }
    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getTermName() {
        return termName;
    }
    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getAssessmentName() {
        return assessmentName;
    }
    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }
    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentStart() {
        return assessmentStart;
    }
    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public String getAssessmentEnd() {
        return assessmentEnd;
    }
    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public String getAssessmentStatus() {
        return assessmentStatus;
    }
    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public String getAssessmentNotes() {
        return assessmentNotes;
    }
    public void setAssessmentNotes(String assessmentNotes) {
        this.assessmentNotes = assessmentNotes;
    }

    public int getCourseID() {
        return courseID;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
