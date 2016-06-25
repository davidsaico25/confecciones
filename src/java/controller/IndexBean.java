/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOArticuloRopa;
import dao.DAOArticuloRopaHasMaterial;
import dao.DAOLinea;
import dao.DAOMaterial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lingo.FOLingo;
import model.ArticuloRopa;
import model.ArticuloRopaHasMaterial;
import model.Linea;
import model.Material;

/**
 *
 * @author david
 */
@ManagedBean
@ViewScoped
public class IndexBean implements Serializable {
    
    private List<ArticuloRopa> listArticuloRopa;
    private List<Material> listMaterial;
    private List<ArticuloRopaHasMaterial> listArticuloRopaHasMaterial;
    private List<Linea> listLinea;
    
    private DAOArticuloRopa daoArticuloRopa;
    private DAOMaterial daoMaterial;
    private DAOArticuloRopaHasMaterial dAOArticuloRopaHasMaterial;
    private DAOLinea daoLinea;
    
    public IndexBean() {
        daoArticuloRopa = new DAOArticuloRopa();
        daoMaterial = new DAOMaterial();
        dAOArticuloRopaHasMaterial = new DAOArticuloRopaHasMaterial();
        daoLinea = new DAOLinea();
        
        listArticuloRopa = daoArticuloRopa.getListArticuloRopa();
        listMaterial = daoMaterial.getListMaterial();
        listArticuloRopaHasMaterial = dAOArticuloRopaHasMaterial.getListArticuloRopaHasMaterial();
        listLinea = daoLinea.getListLinea();
        FOLingo foLingo = new FOLingo(listLinea, listArticuloRopa, listMaterial);
        ArrayList<double[]> listSolucion = new ArrayList<>();
        listSolucion = foLingo.solve();
        System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + listSolucion.size());
    }
    
    public int getListMaterialSize() {
        return listMaterial.size();
    }
    
    public double getCantidadMaterialxArticuloRopa(ArticuloRopa articuloRopa, Material material) {
        for (ArticuloRopaHasMaterial itemArticuloRopaHasMaterial : listArticuloRopaHasMaterial) {
            int articuloRopaId = itemArticuloRopaHasMaterial.getArticuloRopa().getArticuloRopaId();
            int materialId = itemArticuloRopaHasMaterial.getMaterial().getMaterialId();
            if(Objects.equals(articuloRopa.getArticuloRopaId(), articuloRopaId) && Objects.equals(material.getMaterialId(), materialId)) {
                return itemArticuloRopaHasMaterial.getCantidad();
            }
        }
        return 0;
    }

    public List<ArticuloRopa> getListArticuloRopa() {
        return listArticuloRopa;
    }

    public void setListArticuloRopa(List<ArticuloRopa> listArticuloRopa) {
        this.listArticuloRopa = listArticuloRopa;
    }
    
    public List<Material> getListMaterial() {
        return listMaterial;
    }

    public void setListMaterial(List<Material> listMaterial) {
        this.listMaterial = listMaterial;
    }

    public List<ArticuloRopaHasMaterial> getListArticuloRopaHasMaterial() {
        return listArticuloRopaHasMaterial;
    }

    public void setListArticuloRopaHasMaterial(List<ArticuloRopaHasMaterial> listArticuloRopaHasMaterial) {
        this.listArticuloRopaHasMaterial = listArticuloRopaHasMaterial;
    }
}
