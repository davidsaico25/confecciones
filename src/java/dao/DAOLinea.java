/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import model.HibernateUtil;
import model.Linea;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author david
 */
public class DAOLinea extends ADAO_crud<Object> implements Serializable {
    
    public List<Linea> getListLinea() {
        List<Linea> listLinea = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Linea as l");
            listLinea = (List<Linea>) query.list();
        } catch(Exception e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return listLinea;
    }
}
