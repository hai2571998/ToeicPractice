package thanhhai.com.toeicpractice.FCM;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token= FirebaseInstanceId.getInstance().getToken();
        luuTokenVaoCSDLRieng(token);
    }

    private void luuTokenVaoCSDLRieng(String token) {
        new FirebaseIDTask().execute(token);
    }
}
