package c.stewart.termtracker.Database;

// Import statements.
import android.app.Application;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import c.stewart.termtracker.DAO.AssessmentDAO;
import c.stewart.termtracker.DAO.CourseDAO;
import c.stewart.termtracker.DAO.TermDAO;
import c.stewart.termtracker.Entities.Assessment;
import c.stewart.termtracker.Entities.Course;
import c.stewart.termtracker.Entities.Term;

// Class responsible for handling interactions with the database entities.
public class Repository {
    // Three DAO instances:
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;

    // Three lists:
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;

    private List<Course> mCourse;
    private List<Assessment> mAllAssessments;



    // Threads help with responsiveness and is essential to handle potential exceptions & errors.
    private static int NUMBER_OF_THREADS=4;
    // Used for asynchronous (not existing or happening at the same time) operations.
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Takes an app instance as a parameter used to obtain a reference to the database.
    public Repository(Application application){
        TermTrackerDatabase db=TermTrackerDatabase.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mAssessmentDAO=db.assessmentDAO();
    }


    // Thread.sleep: used to give the database operation time to complete before returning control to the caller.

    // Fetches a list of terms from the database.
    public List<Term>getmAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllTerms;
    }
    // Fetch a term by termID
    public Term getTermByID(int termID){
        return mTermDAO.getTermByID(termID);
    }
    // Term CRUD methods:
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Fetches a list of courses from the database.
    public List<Course>getmAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses=mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
    // Fetches a list of courses with a specific termID from the database.
    public List<Course>getAssociatedCourses(int termID){
        databaseExecutor.execute(()->{
            mAllCourses=mCourseDAO.getAssociatedCourses(termID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
    // Fetch a course by courseID
    public Course getCourseByID(int courseID){
        return mCourseDAO.getCourseByID(courseID);
    }

    // Course CRUD methods:
    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Fetches a list of assessments from the database.
    public List<Assessment>getmAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    // Fetches a list of assessments with a specific courseID from the database.
    public List<Assessment>getAssociatedAssessments(int courseID){
        databaseExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAssociatedAssessments(courseID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    // Assessment CRUD methods:
    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
