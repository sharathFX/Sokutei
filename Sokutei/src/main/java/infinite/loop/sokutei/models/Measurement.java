package infinite.loop.sokutei.models;

import java.util.concurrent.TimeUnit;

/**
 * @author Sharath Pandeshwar
 * @since 21/03/16.
 */
public class Measurement {

    public Class activityClass;
    public long preCreateTimestamp = -1;
    public long postCreateTimestamp = -1;
    public long preStartTimestamp = -1;
    public long postStartTimestamp = -1;
    public long preResumeTimestamp = -1;
    public long postResumeTimestamp = -1;


    public long getCreateTimeInMilliSeconds() {
        if (postCreateTimestamp == -1 || preCreateTimestamp == -1) {
            return -1;
        }

        return TimeUnit.NANOSECONDS.toMillis(postCreateTimestamp - preCreateTimestamp);
    }

    public long getStartTimeInMilliSeconds() {
        if (postStartTimestamp == -1 || preStartTimestamp == -1) {
            return -1;
        }
        return TimeUnit.NANOSECONDS.toMillis(postStartTimestamp - preStartTimestamp);
    }

    public long getResumeTimeInMilliSeconds() {
        if (postResumeTimestamp == -1 || preResumeTimestamp == -1) {
            return -1;
        }

        return TimeUnit.NANOSECONDS.toMillis(postResumeTimestamp - preResumeTimestamp);
    }


    public String getReportStatement() {
        String log =
                "Activity: " + activityClass + "\n" +
                        "OnCreate time: " + getCreateTimeInMilliSeconds() + "ms" + "\n" +
                        "OnStart time: " + getStartTimeInMilliSeconds() + "ms" + "\n" +
                        "OnResume time: " + getResumeTimeInMilliSeconds() + "ms" + "\n";

        return log;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "activityClass=" + activityClass +
                ", preCreateTimestamp=" + preCreateTimestamp +
                ", postCreateTimestamp=" + postCreateTimestamp +
                ", preStartTimestamp=" + preStartTimestamp +
                ", postStartTimestamp=" + postStartTimestamp +
                ", preResumeTimestamp=" + preResumeTimestamp +
                ", postResumeTimestamp=" + postResumeTimestamp +
                '}';
    }
}
