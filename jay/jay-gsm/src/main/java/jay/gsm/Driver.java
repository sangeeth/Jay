package jay.gsm;

import java.util.Properties;

public interface Driver {
	Connection connect(String port, Properties properties) throws DriverException;
}
