package omsu.softwareengineering.data.repository.methods;

import omsu.softwareengineering.data.repository.FindException;

public interface IFindByIDMethod<T> {
    T findByID(String id) throws FindException;
}
