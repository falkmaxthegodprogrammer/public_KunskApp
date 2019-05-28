package pvt19grupp1.kunskapp.com.kunskapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class QuizSession implements Parcelable {

    private String title;
    private String quiz_session_id;


    public QuizSession(String title, String chatroom_id) {
        this.title = title;
        this.quiz_session_id = chatroom_id;
    }

    public QuizSession() {

    }

    protected QuizSession(Parcel in) {
        title = in.readString();
        quiz_session_id = in.readString();
    }

    public static final Creator<QuizSession> CREATOR = new Creator<QuizSession>() {
        @Override
        public QuizSession createFromParcel(Parcel in) {
            return new QuizSession(in);
        }

        @Override
        public QuizSession[] newArray(int size) {
            return new QuizSession[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChatroom_id() {
        return quiz_session_id;
    }

    public void setChatroom_id(String chatroom_id) {
        this.quiz_session_id = chatroom_id;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "title='" + title + '\'' +
                ", chatroom_id='" + quiz_session_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(quiz_session_id);
    }
}