package jay.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Tree<T extends Serializable> implements Collection<Tree<T>>, Serializable {
       private static final long serialVersionUID = 1L;    
    private T value;
    
    private List<Tree<T>> children;
    
    public Tree() {
        this.children = new ArrayList<Tree<T>>();
    }
    
    public T getValue() {
        return value;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public void setValue(T value) {
        this.value = value;
    }

    
    // Implement methods of Collection interface
    @Override
    public boolean add(Tree<T> e) {
        return this.children.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends Tree<T>> c) {
        return this.children.addAll(c);
    }

    @Override
    public void clear() {
        for(Tree<T> e:children) {
            e.clear();
        }
        this.children.clear();
    }

    @Override
    public boolean contains(Object o) {
        
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.children.isEmpty();
    }

    @Override
    public Iterator<Tree<T>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
