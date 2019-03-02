package com.flx.eng.iv.metrics.utils;

import com.flx.eng.iv.metrics.request.CreateMetericRequest;

public class MSUtils {
	public static int counter = 1;

	public static int getID() {
		return counter++;
	}

	public static String stackTraceToString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void validateCreateMetricRequest(CreateMetericRequest req) throws MSException {
		if (req == null || req.getMetric() == null || req.getMetric().trim().isEmpty() || req.getValues() == null
				|| req.getValues().length == 0) {
			throw new MSException(MSConstants.BAD_REQUEST);
		}
	}

	public static void validateCreateMetricRequest(String metric, float value) throws MSException {
		if (metric == null || metric.trim().isEmpty() || value == 0) {
			throw new MSException(MSConstants.BAD_REQUEST);
		}
	}

	public static float getMedian(float a[], int n) {
		// check for even case
		if (n % 2 != 0)
			return (float) a[n / 2];

		return (float) (a[(n - 1) / 2] + a[n / 2]) / 2.0f;
	}

	public static float getMean(float a[], int n) {
		float sum = 0;
		for (int i = 0; i < n; i++)
			sum += a[i];

		return (float) sum / (float) n;
	}
}
