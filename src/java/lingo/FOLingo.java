/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lingo;

import com.lindo.Lingd15;
import java.util.ArrayList;
import java.util.List;
import model.ArticuloRopa;
import model.Linea;
import model.Material;
/**
 *
 * @author david
 */
public class FOLingo {
    
    double[] costoDisenio = new double[2];
    double[] costoDesfile = new double[2];
    double[] precioArticulo = new double[11];
    double[] costoMOMQ = new double[11];
    double[] demanda = new double[11];
    double[] precioYarda = new double[7];
    double[] matDisp = new double[7];
    double[] cantidadYarda = new double[77];

    double[] solX = new double[11];
    double[] solY = new double[7];
        
    // Load the Lingo JNI interface
    static {
        System.loadLibrary("Lingj64_15");
    }
    
    // Create a new Lingo object, which we will use to interface with Lingo
    Lingd15 lng = new Lingd15();
    
    // Stores the Lingo JNI environment pointer
    Object pLngEnv;
    
    int nLastIterationCount;
    
    /*
    public FOLingo(double[] costoDiseno, double[] costoDesfile, double[] precioArticulo, double[] costoMOMQ,
            double[] demanda, double[] precioYarda, double[] matDisp, double[] cantidadYarda) {
        // Create the Lingo environment.
        // We do this here in the constructor so as not to repeat this
        // for each subsequent solve, improving performance.
        // Be sure to delete the Lingo environment on exit.
        pLngEnv = lng.LScreateEnvLng();
        if (pLngEnv == null) {
            System.out.println("Unable to create Lingo environment");
            return;
        }
        this.costoDisenio = costoDiseno;
        this.costoDesfile = costoDesfile;
        this.precioArticulo = precioArticulo;
        this.costoMOMQ = costoMOMQ;
        this.demanda = demanda;
        this.precioYarda = precioYarda;
        this.matDisp = matDisp;
        this.cantidadYarda = cantidadYarda;
    }
    */
    
    public FOLingo(List<Linea> listLinea, List<ArticuloRopa> listArticuloRopa, List<Material> listMaterial) {
        for (int i = 0; i < costoDisenio.length; i++) {
            costoDisenio[i] = listLinea.get(i).getCostoDiseno();
            costoDesfile[i] = listLinea.get(i).getCostoDesfile();
        }
        for (int i = 0; i < listArticuloRopa.size(); i++) {
            precioArticulo[i] = listArticuloRopa.get(i).getPrecioVenta();
            costoMOMQ[i] = listArticuloRopa.get(i).getPrecioCosto();
            demanda[i] = listArticuloRopa.get(i).getDemanda();
        }
        for (int i = 0; i < listMaterial.size(); i++) {
            precioYarda[i] = listMaterial.get(i).getPrecioCostoPorYarda();
            matDisp[i] = listMaterial.get(i).getCantidad();
        }
        // Create the Lingo environment.
        // We do this here in the constructor so as not to repeat this
        // for each subsequent solve, improving performance.
        // Be sure to delete the Lingo environment on exit.
        pLngEnv = lng.LScreateEnvLng();
        if (pLngEnv == null) {
            System.out.println("Unable to create Lingo environment");
            return;
        }
    }
    
    private static int MySolverCallback(Object pnLng, int iLoc, Object jobj) {
        FOLingo foLingo = (FOLingo) jobj;
        int nIterations[] = new int[1];
        foLingo.lng.LSgetCallbackInfoIntLng(pnLng, Lingd15.LS_IINFO_ITERATIONS_LNG, nIterations);
        return (0);
    }
    
    private static int MyErrorCallback( Object pnLng, int nErrorCode, String jsErrMessage, Object jobj) {
       System.out.println("Lingo error code: " + nErrorCode);
       System.out.println("Lingo error message:\n\n " + jsErrMessage);
       return(0);
    }
    
    public ArrayList<double[]> solve() {
        ArrayList<double[]> listSolucion = new ArrayList<>();
        
        double dStatus[] = new double [1];
        
        try {
            /*
            costoDisenio[0] = 0;
            costoDisenio[1] = 860000;
            
            costoDesfile[0] = 0;
            costoDesfile[1] = 2700000;
            
            precioArticulo[0] = 300;
            precioArticulo[1] = 450;
            precioArticulo[2] = 180;
            precioArticulo[3] = 120;
            precioArticulo[4] = 270;
            precioArticulo[5] = 320;
            precioArticulo[6] = 350;
            precioArticulo[7] = 130;
            precioArticulo[8] = 75;
            precioArticulo[9] = 200;
            precioArticulo[10] = 120;
            
            costoMOMQ[0] = 160;
            costoMOMQ[1] = 150;
            costoMOMQ[2] = 100;
            costoMOMQ[3] = 60;
            costoMOMQ[4] = 120;
            costoMOMQ[5] = 140;
            costoMOMQ[6] = 175;
            costoMOMQ[7] = 60;
            costoMOMQ[8] = 40;
            costoMOMQ[9] = 160;
            costoMOMQ[10] = 90;
            
            demanda[0] = 7000;
            demanda[1] = 4000;
            demanda[2] = 12000;
            demanda[3] = 15000;
            demanda[4] = 2800;
            demanda[5] = 5000;
            demanda[6] = 5500;
            demanda[7] = 0;
            demanda[8] = 0;
            demanda[9] = 6000;
            demanda[10] = 0;
            
            precioYarda[0] = 9;
            precioYarda[1] = 1.5;
            precioYarda[2] = 60;
            precioYarda[3] = 13;
            precioYarda[4] = 2.25;
            precioYarda[5] = 12;
            precioYarda[6] = 2.5;
            
            matDisp[0] = 45000;
            matDisp[1] = 28000;
            matDisp[2] = 9000;
            matDisp[3] = 18000;
            matDisp[4] = 30000;
            matDisp[5] = 20000;
            matDisp[6] = 30000;
            
            cantidadYarda[0] = 3;
            cantidadYarda[1] = 2;
            cantidadYarda[2] = 0;
            cantidadYarda[3] = 0;
            cantidadYarda[4] = 0;
            cantidadYarda[5] = 0;
            cantidadYarda[6] = 0;
            cantidadYarda[7] = 0;
            cantidadYarda[8] = 0;
            cantidadYarda[9] = 1.5;
            cantidadYarda[10] = 0;
            cantidadYarda[11] = 0;
            cantidadYarda[12] = 0;
            cantidadYarda[13] = 0;
            cantidadYarda[14] = 0;
            cantidadYarda[15] = 0;
            cantidadYarda[16] = 0;
            cantidadYarda[17] = 1.5;
            cantidadYarda[18] = 0;
            cantidadYarda[19] = 0;
            cantidadYarda[20] = 0;
            cantidadYarda[21] = 0;
            cantidadYarda[22] = 0;
            cantidadYarda[23] = 0;
            cantidadYarda[24] = 0.5;
            cantidadYarda[25] = 0;
            cantidadYarda[26] = 0;
            cantidadYarda[27] = 0;
            cantidadYarda[28] = 0;
            cantidadYarda[29] = 1.5;
            cantidadYarda[30] = 0;
            cantidadYarda[31] = 0;
            cantidadYarda[32] = 2;
            cantidadYarda[33] = 0;
            cantidadYarda[34] = 0;
            cantidadYarda[35] = 2.5;
            cantidadYarda[36] = 1.5;
            cantidadYarda[37] = 0;
            cantidadYarda[38] = 0;
            cantidadYarda[39] = 0;
            cantidadYarda[40] = 0;
            cantidadYarda[41] = 0;
            cantidadYarda[42] = 0;
            cantidadYarda[43] = 2;
            cantidadYarda[44] = 0;
            cantidadYarda[45] = 0;
            cantidadYarda[46] = 0;
            cantidadYarda[47] = 3;
            cantidadYarda[48] = 0;
            cantidadYarda[49] = 0;
            cantidadYarda[50] = 0;
            cantidadYarda[51] = 0;
            cantidadYarda[52] = 0;
            cantidadYarda[53] = 0;
            cantidadYarda[54] = 0;
            cantidadYarda[55] = 1.5;
            cantidadYarda[56] = 0;
            cantidadYarda[57] = 0;
            cantidadYarda[58] = 0;
            cantidadYarda[59] = 0;
            cantidadYarda[60] = 0;
            cantidadYarda[61] = 0;
            cantidadYarda[62] = 0.5;
            cantidadYarda[63] = 0;
            cantidadYarda[64] = 0;
            cantidadYarda[65] = 0;
            cantidadYarda[66] = 0;
            cantidadYarda[67] = 0;
            cantidadYarda[68] = 1.5;
            cantidadYarda[69] = 0;
            cantidadYarda[70] = 0;
            cantidadYarda[71] = 0;
            cantidadYarda[72] = 0;
            cantidadYarda[73] = 0;
            cantidadYarda[74] = 1.5;
            cantidadYarda[75] = 0;
            cantidadYarda[76] = 0;
            */
        } catch ( Exception e) {
            System.out.println("Invalid staffing needs data.");
            return null;
        }
       
       // open a log file
       int nErr = lng.LSopenLogFileLng( pLngEnv, "lingo.log");
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSopenLogFileLng() error: " + nErr);
          return null;
       }
       
       // pass Lingo the name of the solver callback function...
       nErr = lng.LSsetCallbackSolverLng( pLngEnv, "MySolverCallback", this);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetCallbackSolverLng() error");
          return null;
       }
       
       // ...and the error callback function
       nErr = lng.LSsetCallbackErrorLng( pLngEnv, "MyErrorCallback", this);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetCallbackErrorLng() error");
          return null;
       }
       
       // pass lingo a pointer to the DAYS set
       
       int[] nPointersNow = new int[1];
       
       // pass lingo a pointer to the staffing needs
       nErr = lng.LSsetPointerLng( pLngEnv, costoDisenio, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
           System.out.println("LSsetPointerLng() error");
           return null;
       }
       
       // pass pointers to output areas
       nErr = lng.LSsetPointerLng( pLngEnv, costoDesfile, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, precioArticulo, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, costoMOMQ, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, demanda, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, precioYarda, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, matDisp, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, cantidadYarda, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, solX, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, solY, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       nErr = lng.LSsetPointerLng( pLngEnv, dStatus, nPointersNow);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println("LSsetPointerLng() error");
          return null;
       }
       
       // construct the script
       // echo input to log file
       String sScript = "SET ECHOIN 1" + "\n";
       
       // load the model from disk
       sScript = sScript + "TAKE proyectoio.lng" + "\n";
       
       // view the formulation
       sScript = sScript + "LOOK ALL" + "\n";
       
       // solve
       sScript = sScript + "GO" + "\n";
       
       // exit script processor
       sScript = sScript + "QUIT" + "\n";
       
       // run the script
       dStatus[0] = -1;
       nLastIterationCount = -1;
       nErr = lng.LSexecuteScriptLng( pLngEnv, sScript);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println( "***LSexecuteScriptLng error***: " + nErr);
          return null;
       }
       
       // clear the pointers to force update of local arrays
       // ***NOTE*** solution won't get passed to local arrays until
       // LSclearPointersLng is called!!!
       nErr = lng.LSclearPointersLng( pLngEnv);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println( "***LSclearPointerLng() error***: " + nErr);
          return null;
       }
       
       // check the solution status
       if ( dStatus[0] != lng.LS_STATUS_GLOBAL_LNG)
        System.out.println( "***Unable to Solve*** dStatus:" + dStatus[0]);
       
       // close Lingo's log file
       nErr = lng.LScloseLogFileLng( pLngEnv);
       if ( nErr != lng.LSERR_NO_ERROR_LNG )
       {
          System.out.println( "***LScloseLogFileLng() error***: " + nErr);
          return null;
       }
       
       /*
       System.out.println("SolX:");
       for (int i = 0; i < solX.length; i++) {
           System.out.println("SolX[" + i + "] = " + solX[i]);
       }
       
       System.out.println("SolY:");
       for (int i = 0; i < solY.length; i++) {
           System.out.println("SolY[" + i + "] = " + solY[i]);
       }
       */
       
       listSolucion.add(solX);
       listSolucion.add(solY);
       return listSolucion;
    }
}
