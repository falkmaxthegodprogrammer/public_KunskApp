package pvt19grupp1.kunskapp.com.kunskapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {

    boolean isCorrect;
    String answer;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    protected Answer(Parcel in) {
        answer = in.readString();
        isCorrect = in.readByte() != 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    @Override
    public String toString() {
        return "Choices [isCorrect=" + isCorrect + ", choice=" + answer
                + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }

    public String getAnswerText() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}