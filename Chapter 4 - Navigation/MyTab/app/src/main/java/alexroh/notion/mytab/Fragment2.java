package alexroh.notion.mytab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트는 액티비티 위에 올라가야만 동작하므로 바로 붙이지 않게 attachToRoot = false
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        return rootView;
    }
}
