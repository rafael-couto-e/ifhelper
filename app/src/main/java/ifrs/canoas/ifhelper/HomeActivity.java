package ifrs.canoas.ifhelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import ifrs.canoas.model.Course;
import ifrs.canoas.model.Courses;

public class HomeActivity extends AppCompatActivity {
    private Response<Courses> response = new Response<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String token = getIntent().getStringExtra("token");

        if (token == null) return;

        loadUserData(token);
    }

    private void loadUserData(final String token) {
        String uri = "http://moodle.canoas.ifrs.edu.br/webservice/rest/server.php";
        uri += "?wstoken="+token;
        uri += "&wsfunction=core_webservice_get_site_info";
        uri += "&moodlewsrestformat=json";

        WebServiceConsumer consumer = new WebServiceConsumer() {
            @Override
            protected void onPostExecute(String s) {
                setupWelcomeMessage(token, s);
            }
        };

        consumer.execute(uri);
    }

    private void setupWelcomeMessage(String token, String json) {
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        response.setData(new Gson().fromJson(json, Courses.class));

        tvWelcome.setText(
                String.format(
                        Locale.getDefault(),
                        "Bem-vindo, %s!",
                        response.getData().getFirstname()
                )
        );

        loadCourses(token, String.valueOf(response.getData().getUserId()));
    }

    private void loadCourses(String token, String userId) {
        String uri = "http://moodle.canoas.ifrs.edu.br/webservice/rest/server.php";
        uri += "?wstoken="+token;
        uri += "&wsfunction=core_enrol_get_users_courses";
        uri += "&moodlewsrestformat=json";
        uri += "&userid="+userId;

        WebServiceConsumer consumer = new WebServiceConsumer() {
            @Override
            protected void onPostExecute(String s) {
                setupList(s);
            }
        };

        consumer.execute(uri);
    }

    public void setupList(String json) {
        Type listType = new TypeToken<List<Course>>(){}.getType();

        List<Course> courses = new Gson().fromJson(json, listType);

        response.getData().setCourses(courses);

        displayCourses();
    }

    private void displayCourses() {
        LinearLayout llCourses = (LinearLayout) findViewById(R.id.llCourses);

        for(Course course: response.getData().getCourses()) {
            TextView tv = new TextView(this);
            tv.setText(course.getFullname());
            llCourses.addView(tv);
        }
    }
}
