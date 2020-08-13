package alexroh.notion.myimagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String urlStr;
    private ImageView imageView;

    // 해시 테이블을 사용하여 url과 비트맵을 매핑해 놓음 -> 이후 삭제할 수 있도록 함
    private static HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();

    public ImageLoadTask(String urlStr, ImageView imageView){
        // 넘겨받은 url에서 이미지를 다운받음
        this.urlStr = urlStr;
        // 다운로드 받은 이미지를 넘겨받은 이미지뷰에 설정함
        this.imageView = imageView;
    }

    @Override
    // 첫번째로 실행됨
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    // 두번째로 실행됨
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        try {
            // 이미 해시 테이블 안에 해당 url에 해당하는 이미지가 있는지 검색
            if(bitmapHashMap.containsKey(urlStr)){
                Bitmap oldBitmap = bitmapHashMap.remove(urlStr);
                if(oldBitmap != null) {
                    // 이미 이미지가 있다면 메모리에서 제거함
                    oldBitmap.recycle();
                }
            }
            URL url = new URL(urlStr);
            // url로 접속하여 이미지 스트림을 받고, decodeStream으로 비트맵으로 변환
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            // 현재 비트맵 이미지를 해시 테이블에 넣음
            bitmapHashMap.put(urlStr, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    // 세번째로 실행됨 -
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    // 네번째로 실행됨 - doInBackground의 리턴값이 넘어옴
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate(); // 이미지 뷰를 다시 그림
    }

}
