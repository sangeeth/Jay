package jay.data.exception;

import jay.lang.IFaultCode;

public enum SearchErrorCode implements IFaultCode {
    UNSUPPORTED_SEARCH_CRITERIA,

    INVALID_SORT_FIELD,

    QUERY_PARAM_MISSING,

    SEARCH_CRITERIA_NULL,

    SEARCH_CRITERIA_BEAN_NOT_FOUND,

    SEARCH_CRITERIA_INVALID
}
