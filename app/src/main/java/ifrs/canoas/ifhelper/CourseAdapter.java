package ifrs.canoas.ifhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ifrs.canoas.model.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private Context context;
    private List<Course> courses;
    private LayoutInflater inflater;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CourseViewHolder viewHolder = new CourseViewHolder(
                inflater.inflate(viewType, parent, false)
        );

        viewHolder.loadComponents();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        holder.loadData(courses.get(position));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.course_row;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvName;

        public CourseViewHolder(View itemView) {
            super(itemView);
        }

        public void loadComponents() {
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }

        public void loadData(Course course) {
            tvId.setText(course.getId());
            tvName.setText(course.getFullname());
        }
    }
}
