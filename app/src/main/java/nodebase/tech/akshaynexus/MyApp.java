package nodebase.tech.akshaynexus;


import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "MONOSPACE", "NexaBold.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}
