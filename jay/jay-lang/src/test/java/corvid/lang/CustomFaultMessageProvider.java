package corvid.lang;

import java.util.Locale;

import jay.lang.AbstractFaultMessageProvider;
import jay.lang.Fault;
import jay.lang.IFaultMessage;
import jay.lang.Resource;

public class CustomFaultMessageProvider extends AbstractFaultMessageProvider {
	private static final String DESCRIPTION = "%s.description";
	private static final String CAUSE = "%s.cause";
	private static final String ACTION = "%s.action";
	
	@Override
	public IFaultMessage getMessage(Fault fault, Locale locale, Object... messageArgs) {
		SomeFaultMessage faultMessage = new SomeFaultMessage();
		
		Resource resourceBundle = super.getResource(fault, locale);
		
		String fqn = fault.getFQN();

		String key = String.format(DESCRIPTION, fqn);
		String description = resourceBundle.getString(key, key);
		faultMessage.setDescription(description);
		
		key = String.format(CAUSE, fqn);
		String cause = resourceBundle.getString(key, key);
		faultMessage.setCause(cause);
		
		key = String.format(ACTION, fqn);
		String action = resourceBundle.getString(key, key);
		faultMessage.setCause(action);
		
		faultMessage.setFormattedString(String.format("%s-%s: %s\nCause - %s\nAction - %s\n",fault.getCategoryId(),fault.getId(), description, cause, action));			
		
		return faultMessage;
	}
}
