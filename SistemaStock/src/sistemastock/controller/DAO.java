/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemastock.controller;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Tke Kaiser
 */
public interface DAO<T, Id extends Serializable> {
    
    public void persist(T t);
	public T getById(Id id);
	public List<T> getAll();
	public void update(T t);
	public void delete(T t);
	public void deleteAll();
    
}