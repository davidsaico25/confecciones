/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAOArticuloRopa;
import dao.DAOArticuloRopaHasMaterial;
import dao.DAOMaterial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.ArticuloRopa;
import model.ArticuloRopaHasMaterial;
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
    
    private List<Material> listMaterialxArticuloRopa;
    
    private DAOArticuloRopa daoArticuloRopa;
    private DAOMaterial daoMaterial;
    private DAOArticuloRopaHasMaterial dAOArticuloRopaHasMaterial;
    
    public IndexBean() {
        daoArticuloRopa = new DAOArticuloRopa();
        daoMaterial = new DAOMaterial();
        dAOArticuloRopaHasMaterial = new DAOArticuloRopaHasMaterial();
        
        listArticuloRopa = daoArticuloRopa.getListArticuloRopa();//new ArrayList<>();
        listMaterial = daoMaterial.getListMaterial();//new ArrayList<>();
        listArticuloRopaHasMaterial = dAOArticuloRopaHasMaterial.getListArticuloRopaHasMaterial();
        /*
        for (ArticuloRopaHasMaterial itemArticuloRopaHasMaterial : listArticuloRopaHasMaterial) {
            ArticuloRopa articuloRopa = itemArticuloRopaHasMaterial.getArticuloRopa();
            if (!listArticuloRopa.contains(articuloRopa)) {
                listArticuloRopa.add(articuloRopa);
            }
            Material material = itemArticuloRopaHasMaterial.getMaterial();
            if (!listMaterial.contains(material)) {
                listMaterial.add(material);
            }
        }
        */
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
