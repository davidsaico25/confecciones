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
    
    //lista de Articulos de Ropa
    private List<ArticuloRopa> listArticuloRopa;
    //lista de Materiales
    private List<Material> listMaterial;
    //matriz de Articulos de Ropa con Materiales
    private List<ArticuloRopaHasMaterial> listArticuloRopaHasMaterial;
    //lista de lineas
    private List<Linea> listLinea;
    
    //listas solucion devueltas por Lingo
    private List<Double> listSolArticuloRopa;
    private List<Double> listSolMaterial;
    
    //listas solucion para enviar a las tablas en las vistas
    //las clases son declaradas al final de la clase
    private List<SolucionArticuloRopa> listSolucionArticuloRopa;
    private List<SolucionMaterial> listSolucionMaterial;
    
    //declara los dao
    private DAOArticuloRopa daoArticuloRopa;
    private DAOMaterial daoMaterial;
    private DAOArticuloRopaHasMaterial dAOArticuloRopaHasMaterial;
    private DAOLinea daoLinea;
    
    public IndexBean() {
        //instancia los objetos dao
        daoArticuloRopa = new DAOArticuloRopa();
        daoMaterial = new DAOMaterial();
        dAOArticuloRopaHasMaterial = new DAOArticuloRopaHasMaterial();
        daoLinea = new DAOLinea();
        
        //carga las listas con la informacion de la bd
        listArticuloRopa = daoArticuloRopa.getListArticuloRopa();
        listMaterial = daoMaterial.getListMaterial();
        listArticuloRopaHasMaterial = dAOArticuloRopaHasMaterial.getListArticuloRopaHasMaterial();
        listLinea = daoLinea.getListLinea();
        
        //prepara para instanciar e iniciar la clase donde se ejecutara el modelo Lingo
        FOLingo foLingo = new FOLingo(listLinea, listArticuloRopa, listMaterial, listArticuloRopaHasMaterial);
        List<List<Double>> listSolucion = foLingo.solve();
        //se carga los resultados del modelo a las listas
        listSolArticuloRopa = listSolucion.get(0);
        listSolMaterial = listSolucion.get(1);
        foLingo = null;
    }
    
    public int getListMaterialSize() {
        return listMaterial.size();
    }
    
    public double getCantidadMaterialxArticuloRopa(ArticuloRopa articuloRopa, Material material) {
        //para llenar la tabla matriz Articulo de Ropa con Material
        //devuelve la cantidad de material por el articulo de ropa o cero (0) si es que no se usa nada del material
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

    public List<SolucionArticuloRopa> getListSolucionArticuloRopa() {
        //crea la lista con objetos de la nueva clase SolucionArticuloRopa la cual tendra el objeto ArticuloRopa
        //con el resultado que devolvera el modelo Lingo, luego se envia la lista a la tabla de la vista
        listSolucionArticuloRopa = new ArrayList<>();
        for (int i = 0; i < listArticuloRopa.size(); i++) {
            SolucionArticuloRopa solucionArticuloRopa = new SolucionArticuloRopa();
            //se agrega el objeto Articulo Ropa
            solucionArticuloRopa.setArticuloRopa(getListArticuloRopa().get(i));
            //se le agrega el resultado enviado por el modelo Lingo
            solucionArticuloRopa.setCantidad(getListSolArticuloRopa().get(i));
            listSolucionArticuloRopa.add(solucionArticuloRopa);
        }
        return listSolucionArticuloRopa;
    }

    public void setListSolucionArticuloRopa(List<SolucionArticuloRopa> listSolucionArticuloRopa) {
        this.listSolucionArticuloRopa = listSolucionArticuloRopa;
    }

    public List<SolucionMaterial> getListSolucionMaterial() {
        listSolucionMaterial = new ArrayList<>();
        for (int i = 0; i < listMaterial.size(); i++) {
            SolucionMaterial solucionMaterial = new SolucionMaterial();
            solucionMaterial.setMaterial(getListMaterial().get(i));
            solucionMaterial.setCantidad(getListSolMaterial().get(i));
            listSolucionMaterial.add(solucionMaterial);
        }
        return listSolucionMaterial;
    }

    public void setListSolucionMaterial(List<SolucionMaterial> listSolucionMaterial) {
        this.listSolucionMaterial = listSolucionMaterial;
    }
    
    public class SolucionArticuloRopa {
        
        private ArticuloRopa articuloRopa;
        private double cantidad;

        public ArticuloRopa getArticuloRopa() {
            return articuloRopa;
        }

        public void setArticuloRopa(ArticuloRopa articuloRopa) {
            this.articuloRopa = articuloRopa;
        }

        public double getCantidad() {
            return cantidad;
        }

        public void setCantidad(double cantidad) {
            this.cantidad = cantidad;
        }
    }
    
    public class SolucionMaterial {
        
        private Material material;
        private double cantidad;

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public double getCantidad() {
            return cantidad;
        }

        public void setCantidad(double cantidad) {
            this.cantidad = cantidad;
        }
    }
}
