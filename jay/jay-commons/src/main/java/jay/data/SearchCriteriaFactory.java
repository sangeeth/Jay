package jay.data;

public interface SearchCriteriaFactory {
    public <A extends SearchCriteria> A createSearchCriteria(Class<A> clazz,
                                                             SearchCriteriaData searchCriteriaData);
}
