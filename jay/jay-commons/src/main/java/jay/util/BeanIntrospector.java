package jay.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class BeanIntrospector {
	private static final Map<Class<?>,List<PropertyDescriptor>> beanDefRegistry = new WeakHashMap<Class<?>, List<PropertyDescriptor>>();

    private BeanIntrospector()
    {
    }

    /**
	 * Get the bean definition for the given bean type.
	 * 
	 * @param beanType The bean class or type
	 * @return the bean definition for the given bean type
	 * @throws IntrospectionException
	 */
	public static synchronized List<PropertyDescriptor> getPropertyDescriptors(Class<?> beanType) throws IntrospectionException
	{
		List<PropertyDescriptor> beanDefinition;
		beanDefinition = beanDefRegistry.get(beanType);
		if (beanDefinition!=null) {
			return beanDefinition;
		}
		
		beanDefinition = new ArrayList<PropertyDescriptor>();
		beanDefRegistry.put(beanType, beanDefinition);

		Method[] methods = beanType.getMethods();
		
		Method setter;
        Method getter;
        String setterName;
		StringBuffer methodName = new StringBuffer();
		for (Method method : methods) {
			methodName.setLength(0);
			methodName.append(method.getName());
			// Is the method a getter ?
			if (methodName.indexOf("get") == 0
					|| methodName.indexOf("is") == 0) {
				Class<?> returnType = method.getReturnType();
				
					Class<?> [] paramTypes = method.getParameterTypes();
					
					if (paramTypes.length==0) {
						
						
						// Does the getter has equivalent setter ?
						if (methodName.charAt(0) == 'i') {
							methodName.delete(0, 2);
							methodName.insert(0, "set");
						} else {
							methodName.setCharAt(0, 's');
						}
						
						setterName = methodName.toString();
						try {
							setter = beanType.getMethod(setterName, returnType);
						} catch (Exception e) {
							continue;
						}
						getter = method;
						if (getter != null && setter != null) {
							methodName.setCharAt(3, Character
									.toLowerCase(methodName.charAt(3)));
							// get the property name
							CharSequence sequence = methodName.subSequence(3, methodName.length());
							StringBuffer buffer = new StringBuffer(sequence);
							buffer.setCharAt(0, Character.toLowerCase(buffer.charAt(0)));
							
							String propertyName = buffer.toString();
							
							beanDefinition.add(new ExtendedPropertyDescriptor(propertyName, getter, setter, returnType));
						}
						
					}
			}
		}

		return beanDefinition;
	}
	private static class ExtendedPropertyDescriptor extends PropertyDescriptor {
		private Class<?> propertyType;
		private Method readMethod;
		private Method writeMethod;

		private ExtendedPropertyDescriptor(String propertyName,
				Class<?> beanClass, Class<?> actualPropertyType)
				throws IntrospectionException {
			super(propertyName, beanClass);
			this.propertyType = actualPropertyType;
		}


		private ExtendedPropertyDescriptor(String propertyName,
				Class<?> beanClass, String readMethodName,
				String writeMethodName, Class<?> actualPropertyType)
				throws IntrospectionException {
			super(propertyName, beanClass, readMethodName, writeMethodName);
			this.propertyType = actualPropertyType;
		}


		private ExtendedPropertyDescriptor(String propertyName,
				Method readMethod, Method writeMethod,
				Class<?> actualPropertyType)
				throws IntrospectionException {
			super(propertyName, readMethod, writeMethod);
			this.propertyType = actualPropertyType;
		}

		@Override
		public Method getReadMethod() {
			return readMethod;
		}

		@Override
		public void setReadMethod(Method readMethod) {
			this.readMethod = readMethod;
		}

		@Override
		public Method getWriteMethod() {
			return writeMethod;
		}

		@Override
		public void setWriteMethod(Method writeMethod) {
			this.writeMethod = writeMethod;
		}


		@Override
        public Class<?> getPropertyType() {
			return propertyType;
		}

	}
}
