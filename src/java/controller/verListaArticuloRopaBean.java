/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOArticuloRopa;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.ArticuloRopa;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author david
 */
@ManagedBean
@ViewScoped
public class verListaArticuloRopaBean {

    private List<ArticuloRopa> listArticuloRopa;
    
    private DAOArticuloRopa daoArticuloRopa;
    
    public verListaArticuloRopaBean() {
        daoArticuloRopa = new DAOArticuloRopa();
        
        listArticuloRopa = daoArticuloRopa.getListArticuloRopa();
    }
    
    public void onRowEdit(RowEditEvent event) {
        double nuevoPrevioVenta = ((ArticuloRopa) event.getObject()).getPrecioVenta();
        double nuevoPrecioCosto = ((ArticuloRopa) event.getObject()).getPrecioCosto();
        ArticuloRopa editadoArticuloRopa = (ArticuloRopa) event.getObject();
        editadoArticuloRopa.setPrecioVenta(nuevoPrevioVenta);
        editadoArticuloRopa.setPrecioCosto(nuevoPrecioCosto);
        daoArticuloRopa.update(editadoArticuloRopa);
        FacesMessage msg = new FacesMessage("Editado", ((ArticuloRopa) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", ((ArticuloRopa) event.getObject()).getPrecioVenta() +"");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<ArticuloRopa> getListArticuloRopa() {
        return listArticuloRopa;
    }

    public void setListArticuloRopa(List<ArticuloRopa> listArticuloRopa) {
        this.listArticuloRopa = listArticuloRopa;
    }
}
