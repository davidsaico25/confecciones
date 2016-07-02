/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOMaterial;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Material;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author david
 */
@ManagedBean
@ViewScoped
public class verListaMaterial {

    private List<Material> listMaterial;
    
    private DAOMaterial daoMaterial;
    
    public verListaMaterial() {
        daoMaterial = new DAOMaterial();
        
        listMaterial = daoMaterial.getListMaterial();
    }
    
    public void onRowEdit(RowEditEvent event) {
        double nuevoPrevioCostoPorYarda = ((Material) event.getObject()).getPrecioCostoPorYarda();
        Material editadoMaterial = (Material) event.getObject();
        editadoMaterial.setPrecioCostoPorYarda(nuevoPrevioCostoPorYarda);
        daoMaterial.update(editadoMaterial);
        FacesMessage msg = new FacesMessage("Editado", ((Material) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", ((Material) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Material> getListMaterial() {
        return listMaterial;
    }

    public void setListMaterial(List<Material> listMaterial) {
        this.listMaterial = listMaterial;
    }
}
