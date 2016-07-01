/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOMaterial;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Material;

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

    public List<Material> getListMaterial() {
        return listMaterial;
    }

    public void setListMaterial(List<Material> listMaterial) {
        this.listMaterial = listMaterial;
    }
}
