package infinite.loop.sokutei;

import android.app.Application;
import android.content.Context;

import infinite.loop.sokutei.aspectj.ActivityAnalyzer;


/**
 * @author Sharath Pandeshwar
 * @since 21/03/16.
 */
public class Sokutei {

    static volatile Sokutei sSokutei;
    private Context mContext;
    private boolean mEnableActivityMetrics;

    //*********************************************************************
    // Initiallisers/builders
    //*********************************************************************

    public static Sokutei init(Context context) {
        Builder sokuteiBuilder = new Builder(context).enableActivityMetrics(true);
        return init(sokuteiBuilder);
    }

    public static Sokutei init(Builder builder) {
        return init(builder.build());
    }

    public static Sokutei init(Sokutei sokutei) {
        if (sSokutei == null) {
            synchronized (Sokutei.class) {
                if (sSokutei == null) {
                    setupSokutei(sokutei);
                }
            }
        }

        return sSokutei;
    }

    public static Sokutei singleton() {
        if (sSokutei == null) {
            throw new IllegalStateException("Must Initialize Sokutei before using singleton()");
        } else {
            return sSokutei;
        }
    }

    Sokutei(Context context) {
        this.mContext = context;
    }

    //*********************************************************************
    // APIs
    //*********************************************************************

    public void logMeasures(){
        ActivityMetricHelper.getInstance().logMeasures();
    }

    //*********************************************************************
    // Helper methods
    //*********************************************************************

    private static void setupSokutei(Sokutei sokutei) {
        sSokutei = sokutei;
        sSokutei.setupMetrics();
    }

    private void setupMetrics() {
        ActivityAnalyzer.setEnabled(mEnableActivityMetrics);
        /*if (mEnableActivityMetrics) {
            ActivityMetricHelper activityMetricHelper = ActivityMetricHelper.getInstance();
            ((Application) mContext.getApplicationContext()).registerActivityLifecycleCallbacks(activityMetricHelper);
        }*/
    }


    //*********************************************************************
    // Private classes
    //*********************************************************************

    public static class Builder {
        private final Context context;
        private boolean enableActivityMetrics = true;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            } else {
                this.context = context.getApplicationContext();
            }
        }

        public Builder enableActivityMetrics(boolean enable) {
            this.enableActivityMetrics = enable;
            return this;
        }


        public Sokutei build() {
            Sokutei sokutei = new Sokutei(context);
            sokutei.mEnableActivityMetrics = this.enableActivityMetrics;
            return sokutei;
        }
    }


    //*********************************************************************
    // End of the class
    //*********************************************************************

}
