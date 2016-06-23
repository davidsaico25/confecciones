/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.ArticuloRopa;
import model.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author david
 */
public class DAOArticuloRopa extends ADAO_crud<Object> implements Serializable {
    
    public List<ArticuloRopa> getListArticuloRopa() {
        List<ArticuloRopa> listArticuloRopa = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM ArticuloRopa ar");
            listArticuloRopa = (List<ArticuloRopa>) query.list();
        } catch(Exception e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return listArticuloRopa;
    }
    
    public List<List<Double>> getListPreciosReporteLingo() {
        List<List<Double>> listPreciosReporteLingo = new ArrayList<>();
        List<Double> listPrecioVenta = new ArrayList<>();
        List<Double> listPrecioCosto = new ArrayList<>();
        List<Double> listDemanda = new ArrayList<>();
        List<ArticuloRopa> listArticuloRopa = getListArticuloRopa();
        for (ArticuloRopa itemArticuloRopa : listArticuloRopa) {
            listPrecioVenta.add(itemArticuloRopa.getPrecioVenta());
            listPrecioCosto.add(itemArticuloRopa.getPrecioCosto());
            listDemanda.add(itemArticuloRopa.getDemanda());
        }
        listPreciosReporteLingo.add(listPrecioVenta);
        listPreciosReporteLingo.add(listPrecioCosto);
        listPreciosReporteLingo.add(listDemanda);
        return listPreciosReporteLingo;
    }
}
