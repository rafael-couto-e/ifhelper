package ifrs.canoas.ifhelper;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import ifrs.canoas.model.Course;
import ifrs.canoas.model.Courses;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {
    private Response<Courses> response = new Response<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String token = Session.init(this).getToken();

        if (token == null) onBackPressed();

        loadUserData(token);
    }

    @Override
    public void onBackPressed() {
        Session.init(this).clear();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }

        return false;
    }

    private void loadUserData(final String token) {
        dialog = ProgressDialog.show(this, "Home", "Carregando...");
        dialog.setCancelable(false);

        String uri = "http://moodle.canoas.ifrs.edu.br/webservice/rest/server.php";
        uri += "?wstoken="+token;
        uri += "&wsfunction=core_webservice_get_site_info";
        uri += "&moodlewsrestformat=json";

        WebServiceConsumer consumer = new WebServiceConsumer(
            s -> setupWelcomeMessage(token, s)
        );

        consumer.execute(uri);
    }

    private void setupWelcomeMessage(String token, String json) {
        if(json.contains("invalidtoken")) {
            Toast.makeText(
                    this,
                    "A sessão expirou, efetue login.",
                    Toast.LENGTH_SHORT
            ).show();

            this.onBackPressed();
            return;
        }

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        Courses courses = new Courses();

        try {
            response.setData(new Gson().fromJson(json, Courses.class));

            tvWelcome.setText(
                    String.format(
                            Locale.getDefault(),
                            "Bem-vindo, %s!",
                            response.getData().getFirstname()
                    )
            );

            loadCourses(token, String.valueOf(response.getData().getUserId()));
        }catch (Exception e) {
            dialog.dismiss();
            Toast.makeText(
                    this,
                    "Ocorreu algum problema, tente novamente.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void loadCourses(String token, String userId) {
        String uri = "http://moodle.canoas.ifrs.edu.br/webservice/rest/server.php";
        uri += "?wstoken="+token;
        uri += "&wsfunction=core_enrol_get_users_courses";
        uri += "&moodlewsrestformat=json";
        uri += "&userid="+userId;

        WebServiceConsumer consumer = new WebServiceConsumer(this::setupList);

        consumer.execute(uri);
    }

    public void setupList(String json) {
        Type listType = new TypeToken<List<Course>>(){}.getType();

        try {
            List<Course> courses = new Gson().fromJson(json, listType);

            response.getData().setCourses(courses);

            displayCourses();
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Ocorreu algum problema, tente novamente.",
                    Toast.LENGTH_SHORT
            ).show();
            dialog.dismiss();
        }
    }

    private void displayCourses() {
        RecyclerView rvCourses = (RecyclerView) findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCourses.setItemAnimator(new DefaultItemAnimator());

        CourseAdapter adapter = new CourseAdapter(this, response.getData().getCourses());
        adapter.setOnItemClickListener(this);

        rvCourses.setAdapter(adapter);

        dialog.dismiss();
    }

    @Override
    public void onItemClick(Course c) {
        Toast.makeText(this, c.getFullname(), Toast.LENGTH_SHORT).show();
    }
}