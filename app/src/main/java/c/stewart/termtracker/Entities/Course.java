package c.stewart.termtracker.Entities;

// Import statements.
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

// An instance of a class that will be stored in a database table.
@Entity(tableName = "Courses")
public class Course {
    // Attributes found in the database table.
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseTitle;
    private Date courseStart;
    private Date courseEnd;
    private String courseStatus;
    private String courseInstructor;
    private String courseInstructorPhone;
    private String courseInstructorEmail;
    private String courseNotes;

    // Foreign key
    private int termID;

    // Creates the instances of the course class which takes parameters for the various fields.

    public Course(int courseID, String courseTitle, Date courseStart, Date courseEnd,
                  String courseStatus, String courseInstructor, String courseInstructorPhone,
                  String courseInstructorEmail, String courseNotes, int termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseInstructor = courseInstructor;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public String getCourseInstructorPhone() {
        return courseInstructorPhone;
    }

    public void setCourseInstructorPhone(String courseInstructorPhone) {
        this.courseInstructorPhone = courseInstructorPhone;
    }

    public String getCourseInstructorEmail() {
        return courseInstructorEmail;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorEmail = courseInstructorEmail;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
