package jay.util;

import java.io.Serializable;

/**
 * This class represents a data, which can be created, updated or deleted.
 * 
 * A typical usage of this API is as shown below
 * 
 * <pre>
 *    List&lt;Data&lt;Person>> people = database.getPeople();
 *    
 *    for(Data&lt;Person> e: people) {
 *        Person person = e.getValue();
 *        if ("Gandhi".equals(person.getName())) {
 *           person.setOccupation("Politics");
 *           e.setState(Data.State.UPDATED));
 *        } if ("Nehru".equals(person.getName())) {
 *           e.setState(Data.State.DELETED);
 *        }
 *    }
 *    
 *    Person dravid = new Person();
 *    dravid.setName("Dravid");
 *    dravid.setOccupation("Cricket");
 *    
 *    Data&lt;Person> data = new Data&lt;Person>(dravid, Data.State.CREATED);
 *    people.add(data);
 *    
 *    database.updatePeople(people);
 * </pre>
 * 
 * @param <T> The type of the value this data carries.
 */
public class Data<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * This enumeration represents the various states of the Data.
     */
    public static enum State {
        /**
         * Denotes that the value held by {@link Data} has been newly created. 
         */
        CREATED, 
        /**
         * Denotes that the value held by {@link Data} has been updated/modified.
         */
        UPDATED,
        /**
         * Denotes that the value held by {@link Data} has been deleted.
         */
        DELETED,
        /**
         * Denotes that the value held by {@link Data} has been initialized. 
         * This is the default state of a {@link Data} instance.
         */
        INITIALIZED
    }
    /**
     * The value held by the Data instance.
     */
    private T value;
    /**
     * The state of the value.
     */
    private State state;
    /**
     * Constructs an instance of Data with value as <i><b>null</b></i> and state as {@link State#INITIALIZED}.
     */
    public Data() {
        this(null);
    }
    /**
     * Constructs an instance of Data with the given value and state as {@link State#INITIALIZED}.
     * 
     * @param value The initial value of this Data.
     */
    public Data(T value) {
        this(value, null);
    }
    /**
     * Constructs an instance of Data with the given value and state.
     * 
     * @param value The initial value of this Data.
     * @param state The state of the Data. If null, it will be considered as {@link State#INITIALIZED}.
     */
    public Data(T value, State state) {
        super();
        this.value = value;
        this.state = state==null?State.INITIALIZED:state;
    }

    /**
     * To get the value held by this Data instance.
     * @return The value held by this Data instance.
     */
    public T getValue() {
        return value;
    }

    /**
     * To set the value to be held by this Data instance.
     * @param value The value to be held by this Data instance.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * To get the state of the Data. 
     * @return The state of the Data.
     */
    public State getState() {
        return state;
    }

    /**
     * To set the state of the Data.
     * @param state The state of the Data. If null, it will be considered as {@link State#INITIALIZED}.
     */
    public void setState(State state) {
        this.state = state==null?State.INITIALIZED:state;
    }
}
