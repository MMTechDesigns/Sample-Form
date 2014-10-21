package com.mmtech.mysample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class MyEntityManager {
    EntityManagerFactory factory;
    EntityManager manager;
    boolean is_manager_open;

    public MyEntityManager() {
        is_manager_open = false;
    }
    
    /**
     * Use the Persistence Unit for the Entity Manager Factory 
     */
    public void openManager(){
        this.factory = Persistence.createEntityManagerFactory("com.mmtech.mysample");
        this.manager = factory.createEntityManager();
        is_manager_open = true;
    }
    
    /**
     * Creates if necessary the table "employee".
     */
    public void checkDBTablesExist(){
        Query createdb = manager.createNativeQuery("CREATE TABLE IF NOT EXISTS employee (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "FIRSTNAME TEXT not null,"
                + "LASTNAME TEXT not null,"
                + "AGE int not null);");
        EntityTransaction createT = manager.getTransaction();
        createT.begin();
        createdb.executeUpdate();
        createT.commit();
    }
    
    /**
     * This a self creating auto increment to receive next id to satisfy non null id in Entity Class
     * @param tablename
     * @return 
     */
    public int getNextClassIdforTable(String tablename){
        int next_id= -90;
        String qryString = String.format("SELECT MAX(ID) FROM %s",tablename);
        Query all = manager.createNativeQuery(qryString);
        List mylist = all.getResultList();
        Iterator<Object> iterator = mylist.iterator();
        while (iterator.hasNext()) {
            next_id = (Integer.valueOf(iterator.next().toString())) + 1;
        }
        return next_id;
    }
    
    /**
     * Persist entity object between class and db(executes a insert query)
     * @param newemployee 
     */
    public void addNewEmployee(Employee newemployee){

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(newemployee);
        transaction.commit();
    }
    
    public ArrayList<Employee> fetchEmployeesToStringList(){
        ArrayList<Employee> returnList = new ArrayList<>();
        Query q = manager.createQuery("SELECT e FROM Employee e");
        java.util.List results = q.getResultList();
        for (Object c : results) {
                Employee eachone = (Employee)c;
                returnList.add(eachone);
        }
        return returnList;
    }
    
    /**
     * Splits up as parameters to use in update query: table/columns-new values/object id
     * @param tablename
     * @param setinfo
     * @param theid
     * @return a string to update UI as message to user
     */
    public String updateTableWithInfo(String tablename,String setinfo,int theid){
        
        int rows;
        String u_query = String.format("update %s %s where ID=%s",tablename,setinfo,theid);
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Query updateresult = manager.createNativeQuery(u_query);
        rows = updateresult.executeUpdate();
        transaction.commit();
        
        if (rows > 0){
            return "Updated";
        }else{
            return "Failed";
        }
    }    
    
      /**
     * Uses parameters in delete query: table/object id/name of deleted item
     * @param tablename
     * @param theid
     * @param oldname
     * @return a string to update UI as message to user
     */
    public String deleteItemInTableWithId(String tablename,int theid,String oldname){
        
        int rows;
        String u_query = String.format("delete from %s where ID=%s",tablename,theid);
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Query updateresult = manager.createNativeQuery(u_query);
        rows = updateresult.executeUpdate();
        transaction.commit();
        
        if (rows > 0){
            return oldname+" has been deleted";
        }else{
            return "Failed to delete "+oldname;
        }
    }    
    
    public void closeManager(){
        manager.close();
        factory.close();
        is_manager_open = false;
    }
    
}
