package infinite.loop.sokutei;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Pack200;

import infinite.loop.sokutei.models.Measurement;

/**
 * @author Sharath Pandeshwar
 * @since 21/03/16.
 */
public class ActivityMetricHelper implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = "ActivityMetricHelper";
    private static ActivityMetricHelper sInstance;
    private Map<String, Measurement> mMetricsMap = new HashMap<>();

    public synchronized static ActivityMetricHelper getInstance() {
        if (sInstance == null) {
            return sInstance = new ActivityMetricHelper();
        }
        return sInstance;
    }

    //*********************************************************************
    // APIs
    //*********************************************************************

    public void logPreOnCreate(Activity target) {
        Log.v(TAG, "on logPreOnCreate of " + target.getLocalClassName());
        Measurement measurement = getMeasurementForActivity(target);
        measurement.preCreateTimestamp = System.nanoTime();
    }

    public void logPostOnCreate(Activity target) {
        Log.v(TAG, "on logPostOnCreate of " + target.getLocalClassName());
        Measurement measurement = getMeasurementForActivity(target);
        measurement.postCreateTimestamp = System.nanoTime();
    }

    public void logPreOnStart(Activity target) {
        Log.v(TAG, "on logPreOnStart of " + target.getLocalClassName());
        Measurement measurement = getMeasurementForActivity(target);
        measurement.preStartTimestamp = System.nanoTime();
    }

    public void logPostOnStart(Activity target) {
        Log.v(TAG, "on logPostOnStart of " + target.getLocalClassName());
        Measurement measurement = getMeasurementForActivity(target);
        measurement.postStartTimestamp = System.nanoTime();
    }

    public void logPreOnResume(Activity target) {
        Log.v(TAG, "on logPreOnResume of " + target.getLocalClassName());
        Measurement measurement = getMeasurementForActivity(target);
        measurement.preResumeTimestamp = System.nanoTime();
    }

    public void logPostOnResume(Activity target) {
        Log.v(TAG, "on logPostOnResume of " + target.getLocalClassName());
        Measurement measurement = getMeasurementForActivity(target);
        measurement.postResumeTimestamp = System.nanoTime();
    }

    public void logOnPaused(Activity activity) {
        Log.v(TAG, "on logOnPaused of " + activity.getLocalClassName());
    }

    public void logOnStopped(Activity activity) {
        Log.v(TAG, "on logOnStopped of " + activity.getLocalClassName());
    }

    public void logOnDestroyed(Activity activity) {
        Log.v(TAG, "on logOnDestroyed of " + activity.getLocalClassName());
    }

    //*********************************************************************
    // Interface implementations
    //*********************************************************************

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        logPostOnCreate(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        logPostOnStart(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        logPostOnResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        logOnPaused(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        logOnStopped(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        logOnDestroyed(activity);
    }


    public void logMeasures() {
        for (Object value : mMetricsMap.values()) {
            Measurement measurement = (Measurement) value;
            Log.v(TAG, measurement.getReportStatement());
        }
    }

    //*********************************************************************
    // Utility methods
    //*********************************************************************

    private Measurement getMeasurementForActivity(Activity activity) {
        String activityFullName = activity.getPackageName() + "." + activity.getLocalClassName();
        Measurement measurement = mMetricsMap.get(activityFullName);
        if (measurement == null) {
            measurement = new Measurement();
            measurement.activityClass = activity.getClass();
            mMetricsMap.put(activityFullName, measurement);
        }

        return measurement;
    }

    //*********************************************************************
    // End of the class
    //*********************************************************************
}
