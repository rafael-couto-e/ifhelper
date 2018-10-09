package ifrs.canoas.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable{
    private String id;
    private String fullname;

    public Course() {
    }

    public Course(String id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    protected Course(Parcel in) {
        id = in.readString();
        fullname = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fullname);
    }
}
