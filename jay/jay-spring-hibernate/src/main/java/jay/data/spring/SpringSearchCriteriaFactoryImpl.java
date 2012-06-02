package jay.data.spring;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import jay.lang.CommonFaultCode;
import jay.lang.InvalidArgumentException;
import jay.lang.UncheckedException;
import jay.lang.annotation.Id;
import jay.data.AbstractSearchCriteria;
import jay.data.PageCriteria;
import jay.data.SearchCriteria;
import jay.data.SearchCriteriaData;
import jay.data.SearchCriteriaFactory;
import jay.data.exception.SearchErrorCode;
import jay.data.exception.UnsupportedSearchCriteriaException;
import jay.util.NamedValue;
import jay.util.LangUtil;
import java.util.logging.Logger;

public final class SpringSearchCriteriaFactoryImpl implements SearchCriteriaFactory, ApplicationContextAware, BeanFactoryPostProcessor
{
    private static final Logger logger = Logger.getLogger(SpringSearchCriteriaFactoryImpl.class.getName());

    private ApplicationContext applicationContext;

    private final Map<String, Map<Class<? extends SearchCriteria>, String>> criteriaNameBeanNameMap;

    public SpringSearchCriteriaFactoryImpl()
    {
        this.criteriaNameBeanNameMap = new TreeMap<String, Map<Class<? extends SearchCriteria>, String>>();
    }

    @Override
    public <A extends SearchCriteria> A createSearchCriteria(Class<A> clazz,
            SearchCriteriaData searchCriteriaData)
    {
        if (searchCriteriaData == null)
        {
            logger.finest("Search criteria is null");
            throw new InvalidArgumentException(SearchErrorCode.SEARCH_CRITERIA_NULL);
        }

        String id = searchCriteriaData.getId();

        List<NamedValue> properties = searchCriteriaData.getProperties();
        Map<String, Serializable> propertyMap = new HashMap<String, Serializable>();
        for (NamedValue property : properties)
        {
            propertyMap.put(property.getName(), parse(property.getValue()));
        }

        if (LangUtil.isEmpty(id))
        {
            logger.finest("Search criteria id is empty");
            throw new InvalidArgumentException(SearchErrorCode.UNSUPPORTED_SEARCH_CRITERIA);
        }

        Map<Class<? extends SearchCriteria>, String> typeBeanNameMap = this.criteriaNameBeanNameMap.get(id);
        String beanName = null;

        if(typeBeanNameMap != null){
            beanName = typeBeanNameMap.get(clazz);
        }
        if (beanName == null && typeBeanNameMap != null)
        {
            for (Map.Entry<Class<? extends SearchCriteria>, String> e : typeBeanNameMap.entrySet())
            {
                Class<? extends SearchCriteria> criteriaType = e.getKey();
                String criteriaBeanName = e.getValue();
                if (clazz.isAssignableFrom(criteriaType))
                {
                    beanName = criteriaBeanName;
                    break;
                }
            }
        }

        if(beanName == null){
            logger.finest("Could'nt find the search criteria bean entry in map");
            throw new InvalidArgumentException(SearchErrorCode.SEARCH_CRITERIA_BEAN_NOT_FOUND);
        }
        //TODO all these beans must not be singleton
        //put comments in spring file to ensure that people do not change setting for these beans
        A instance = (A) this.applicationContext.getBean(beanName);

        if (instance != null)
        {
            try
            {
                BeanUtilsBean.getInstance().populate(instance, propertyMap);

                PageCriteria pageCriteria = searchCriteriaData.getPageCriteria();
                if (pageCriteria != null && instance instanceof AbstractSearchCriteria)
                {
                    ((AbstractSearchCriteria) instance).setPageCriteria(pageCriteria);
                }
            }
            catch (Exception e)
            {
                logger.finest(String.format("Exception in populating search criteria : %s", e.getMessage()));
                throw new UncheckedException(CommonFaultCode.UNKNOWN_ERROR, e);
            }
        } else {
            logger.finest("Failed to look up search criteria bean");
            throw new UnsupportedSearchCriteriaException();
        }
        return instance;
    }

    private Serializable parse(String value) {
       if(!LangUtil.isEmpty(value) && (value.indexOf(",") != -1)){
           return  (HashSet<Long>)LangUtil.parseLongSet(value);
       }
       return value;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        String[] names = beanFactory.getBeanNamesForType(SearchCriteria.class);
        if (names != null)
        {
            for (String name : names)
            {
                Class<? extends SearchCriteria> type = beanFactory.getType(name);
                Id id = type.getAnnotation(Id.class);
                if (id != null)
                {
                    String criteriaName = id.value();

                    Map<Class<? extends SearchCriteria>, String> criteriaTypeBeanNameMap = this.criteriaNameBeanNameMap.get(criteriaName);
                    if (criteriaTypeBeanNameMap == null)
                    {
                        criteriaTypeBeanNameMap = new HashMap<Class<? extends SearchCriteria>, String>();
                        this.criteriaNameBeanNameMap.put(criteriaName, criteriaTypeBeanNameMap);
                    }
                    criteriaTypeBeanNameMap.put(type, name);
                }
            }
            logger.info(String.format("SearchCriteria-BeanName map: {0}", this.criteriaNameBeanNameMap));
        }
    }

}
