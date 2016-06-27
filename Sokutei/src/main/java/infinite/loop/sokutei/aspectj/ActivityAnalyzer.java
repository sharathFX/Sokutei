package infinite.loop.sokutei.aspectj;

import android.app.Activity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import infinite.loop.sokutei.ActivityMetricHelper;

/**
 * @author Sharath Pandeshwar
 * @since 21/03/16.
 */
@Aspect
public class ActivityAnalyzer {

    private static final String ONCREATE_METHOD = "onCreate";
    private static final String ONSTART_METHOD = "onStart";
    private static final String ONRESUME_METHOD = "onResume";

    private static volatile boolean sIsEnabled = true;

    //*********************************************************************
    // AspectJ related
    //*********************************************************************

    @Pointcut("execution(void *.onCreate(..)) && this(android.app.Activity+)")
    public void onCreateMethod() {
    }

    @Pointcut("execution(void *.onStart(..)) && this(android.app.Activity+)")
    public void onStartMethod() {
    }

    @Pointcut("execution(void *.onResume(..)) && this(android.app.Activity+)")
    public void onResumeMethod() {
    }

    @Around("onCreateMethod() || onStartMethod() || onResumeMethod()")
    public Object logMetrics(ProceedingJoinPoint joinPoint) throws Throwable {

        if (!sIsEnabled) return joinPoint.proceed();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        final Object result;
        if (ONRESUME_METHOD.equals(methodName)) {
            ActivityMetricHelper.getInstance().logPreOnResume((Activity) joinPoint.getTarget());
            result = joinPoint.proceed();
            ActivityMetricHelper.getInstance().logPostOnResume((Activity) joinPoint.getTarget());
        } else if (ONSTART_METHOD.equals(methodName)) {
            ActivityMetricHelper.getInstance().logPreOnStart((Activity) joinPoint.getTarget());
            result = joinPoint.proceed();
            ActivityMetricHelper.getInstance().logPostOnStart((Activity) joinPoint.getTarget());
        } else if (ONCREATE_METHOD.equals(methodName)) {
            ActivityMetricHelper.getInstance().logPreOnCreate((Activity) joinPoint.getTarget());
            result = joinPoint.proceed();
            ActivityMetricHelper.getInstance().logPostOnCreate((Activity) joinPoint.getTarget());
        } else {
            result = null;
        }

        return result;
    }

    //*********************************************************************
    // APIs
    //*********************************************************************

    public static void setEnabled(boolean enabled) {
        ActivityAnalyzer.sIsEnabled = enabled;
    }

    public static boolean isEnabled() {
        return sIsEnabled;
    }

    //*********************************************************************
    // End of the class
    //*********************************************************************

}
