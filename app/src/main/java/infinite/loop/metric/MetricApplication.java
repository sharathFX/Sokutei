package infinite.loop.metric;


import android.app.Application;

import infinite.loop.sokutei.Sokutei;

public class MetricApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Sokutei.init(this);
        }
    }
}
