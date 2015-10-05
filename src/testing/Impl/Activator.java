package testing.Impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import datalogger.DataLogger;

public class Activator implements BundleActivator {

	private DataLoggerImpl dli;

	@Override
	public void start(BundleContext context) {
		try {
			dli = new DataLoggerImpl();
			System.out.println("Starting Pressure Driver bundle");
			context.registerService(DataLogger.class.getName(), dli, null);
		} catch (Exception E) {
			E.printStackTrace();
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		dli.stop();
		ServiceReference<?> serviceReference = context.
				  getServiceReference(DataLogger.class.getName());
		context.ungetService(serviceReference);
		dli = null;
	}

}
