package alexroh.notion.myparcelable;

import android.os.Parcel;
import android.os.Parcelable;

// Parcel은 SimpleData 객체 안의 데이터를 다른 데 전달할 때 사용되는 객체
class SimpleData implements Parcelable {

    int number;
    String message;

    public SimpleData(int number, String message) {
        this.number = number;
        this.message = message;
    }

    // Parcel 안에 들어가 있는 데이터를 복원하여 SimpleData를 만드는 생성자
    public SimpleData(Parcel src){
        number = src.readInt();
        message = src.readString();
    }

    // Parcelable을 구현할 때는 Creator 변수를 필수적으로 선언해주어야 함
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public SimpleData createFromParcel(Parcel src){
            return new SimpleData(src); // 두번째 생성자가 호출되면서 객체를 반환함
        }
        public SimpleData[] newArray(int size){
            return new SimpleData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // SimpleData를 Parcel 객체로 변환하는 메서드
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(message);
    }
}
