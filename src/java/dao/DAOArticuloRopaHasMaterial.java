/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.ArticuloRopa;
import model.ArticuloRopaHasMaterial;
import model.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 *
 * @author david
 */
public class DAOArticuloRopaHasMaterial extends ADAO_crud<Object> implements Serializable {
    
    public List<ArticuloRopaHasMaterial> getListArticuloRopaHasMaterial() {
        List<ArticuloRopaHasMaterial> listArticuloRopaHasMaterial = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM ArticuloRopaHasMaterial as arhm inner join fetch arhm.articuloRopa as ar inner join fetch arhm.material as m");
            listArticuloRopaHasMaterial = (List<ArticuloRopaHasMaterial>) query.list();
        } catch(Exception e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return listArticuloRopaHasMaterial;
    }
    
    public List<ArticuloRopa> getListArticuloRopa() {
        List<ArticuloRopa> listArticuloRopa = new ArrayList<>();
        return listArticuloRopa;
    }
}
