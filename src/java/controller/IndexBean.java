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
    
    private List<Double> listSolArticuloRopa = new ArrayList<>();
    private List<Double> listSolMaterial = new ArrayList<>();
    
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
        
        System.out.println("\nBean: ArticuloRopa:");
        for (ArticuloRopa articuloRopa : listArticuloRopa) {
            System.out.println(articuloRopa.getNombre() + " - " + articuloRopa.getPrecioVenta() + " - " + articuloRopa.getPrecioCosto() + " - " + articuloRopa.getDemanda());
        }
        System.out.println("\nBean: Material:");
        for (Material material : listMaterial) {
            System.out.println(material.getNombre() + " - " + material.getCantidad() + " - " + material.getPrecioCostoPorYarda());
        }
        System.out.println("\nBean: ArticuloRopa Has Material:");
        for (ArticuloRopaHasMaterial itemArticuloRopaHasMaterial : listArticuloRopaHasMaterial) {
            System.out.println(itemArticuloRopaHasMaterial.getArticuloRopa().getArticuloRopaId() + ": " + itemArticuloRopaHasMaterial.getArticuloRopa().getNombre() + " - " + itemArticuloRopaHasMaterial.getMaterial().getMaterialId() + ": " + itemArticuloRopaHasMaterial.getMaterial().getNombre() + " = " + itemArticuloRopaHasMaterial.getCantidad());
        }
        System.out.println("\nBean: Linea:");
        for (Linea linea : listLinea) {
            System.out.println(linea.getNombre());
        }
        /*
        List<Double> listCantidadYarda = new ArrayList<>();
        double[] cantidadYarda = new double[listArticuloRopa.size() * listMaterial.size()];
        
        for (int i = 0; i < listArticuloRopa.size(); i++) {
            for (int j = 0; j < listMaterial.size(); j++) {
            }
        }
        */
        FOLingo foLingo = new FOLingo(listLinea, listArticuloRopa, listMaterial, listArticuloRopaHasMaterial);
        List<List<Double>> listSolucion = foLingo.solve();
        listSolArticuloRopa = listSolucion.get(0);
        listSolMaterial = listSolucion.get(1);
        foLingo = null;

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

    public List<Double> getListSolArticuloRopa() {
        return listSolArticuloRopa;
    }

    public void setListSolArticuloRopa(List<Double> listSolArticuloRopa) {
        this.listSolArticuloRopa = listSolArticuloRopa;
    }

    public List<Double> getListSolMaterial() {
        return listSolMaterial;
    }

    public void setListSolMaterial(List<Double> listSolMaterial) {
        this.listSolMaterial = listSolMaterial;
    }
}
