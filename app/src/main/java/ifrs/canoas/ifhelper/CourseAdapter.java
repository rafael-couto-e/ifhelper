package ifrs.canoas.ifhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ifrs.canoas.model.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> implements Filterable {
    private List<Course> courses;
    private List<Course> original;
    private LayoutInflater inflater;
    private OnItemClickListener listener;
    private Filter filter;

    public CourseAdapter(Context context, List<Course> courses) {
        this.courses = courses;
        this.original = new ArrayList<>(courses);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<Course> elements = new ArrayList<>();

                    String query = constraint.toString();

                    FilterResults results = new FilterResults();

                    if (query.isEmpty())
                        elements.addAll(original);
                    else
                        for (Course c : original)
                            if (c.getFullname().toLowerCase().contains(query.toLowerCase()) ||
                                    c.getId().toLowerCase().contains(query.toLowerCase()))
                                elements.add(c);

                    results.values = elements;

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    courses.clear();

                    List<Course> filtered = (List<Course>) results.values;

                    courses.addAll(filtered);

                    notifyDataSetChanged();
                }
            };
        }

        return filter;
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

            if (listener != null)
                this.itemView.setOnClickListener(v -> listener.onItemClick((Course) itemView.getTag()));
        }

        public void loadData(Course course) {
            tvId.setText(course.getId());
            tvName.setText(course.getFullname());
            itemView.setTag(course);
        }
    }
}
