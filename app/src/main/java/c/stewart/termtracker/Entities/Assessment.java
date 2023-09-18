package c.stewart.termtracker.Entities;

// Import statements.
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

// An instance of a class that will be stored in a database table.
@Entity(tableName = "Assessments")
public class Assessment {
    // Attributes found in the database table.
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentName;
    private String assessmentType;
    private String assessmentStart;
    private String assessmentEnd;

    // Foreign key
    private int courseID;

    // Creates the instances of the assessment class which takes parameters for the various fields.
    public Assessment(int assessmentID, String assessmentName, String assessmentType,
                      String assessmentStart, String assessmentEnd, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString(){
        return String.valueOf(assessmentID);
    }
}
