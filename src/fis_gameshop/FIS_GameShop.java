package fis_gameshop;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import interfaz.GameShopUI;

public class FIS_GameShop {

    public static void main(String[] args) {
        GameShopUI p = new GameShopUI();
        p.setVisible(true);
    }
    
    
    public String calcularPuntajeRecomendacion(double precio, int calificacion, double duracion){
        String fileName = "src/fis_gameshop/FCL_GameShop.fcl";
        
        FIS fis = FIS.load(fileName,true);

        if (fis == null){
            System.err.println("No se puede cargar el archivo '"+fileName+"'");
            return "Error al cargar el sistema difuso";
        }
        
        fis.setVariable("precio", precio);
        fis.setVariable("calificacion",calificacion);
        fis.setVariable("duracion",duracion);

        fis.evaluate();

        double salidaRecomendacion = fis.getVariable("puntajeRecomendacion").getValue();
        double salidaCalidadPrecio = fis.getVariable("calidadPrecio").getValue();

        // Obtener grados de pertenencia
        double pertenenciaBajo = fis.getVariable("puntajeRecomendacion").getMembership("bajo");
        double pertenenciaMedio = fis.getVariable("puntajeRecomendacion").getMembership("medio");
        double pertenenciaAlto = fis.getVariable("puntajeRecomendacion").getMembership("alto");
        double pertenenciaMuyAlto = fis.getVariable("puntajeRecomendacion").getMembership("muyAlto");


        double pertenenciaBaja = fis.getVariable("calidadPrecio").getMembership("baja");
        double pertenenciaMedia = fis.getVariable("calidadPrecio").getMembership("media");
        double pertenenciaAlta = fis.getVariable("calidadPrecio").getMembership("alta");
        double pertenenciaMuyAlta = fis.getVariable("calidadPrecio").getMembership("muyAlta");

        
        String conjuntoMayorRecomendacion = "";
        double gradoMayorRecomendacion = 0;
        
        String conjuntoMayorCalidadPrecio = "";
        double gradoMayorCalidadPrecio = 0;

        // Determinar el conjunto con mayor grado de pertenencia de puntajeRecomendacion
        if (pertenenciaBajo >= pertenenciaMedio && pertenenciaBajo >= pertenenciaAlto && pertenenciaBajo >= pertenenciaMuyAlto) {
            conjuntoMayorRecomendacion = "bajo";
            gradoMayorRecomendacion = pertenenciaBajo;
        } else if (pertenenciaMedio >= pertenenciaBajo && pertenenciaMedio >= pertenenciaAlta && pertenenciaMedio >= pertenenciaMuyAlto) {
            conjuntoMayorRecomendacion = "medio";
            gradoMayorRecomendacion = pertenenciaMedio;
        } else if (pertenenciaAlto >= pertenenciaBajo && pertenenciaAlto >= pertenenciaMedio && pertenenciaAlto >= pertenenciaMuyAlto) {
            conjuntoMayorRecomendacion = "alto";
            gradoMayorRecomendacion = pertenenciaAlto;
        }
        else {
            conjuntoMayorRecomendacion = "muyAlto";
            gradoMayorRecomendacion = pertenenciaMuyAlto;
        }
        

        

        // Determinar el conjunto con mayor grado de pertenencia de puntajeRecomendacion
        if (pertenenciaBaja >= pertenenciaMedia && pertenenciaBaja >= pertenenciaAlta && pertenenciaBaja >= pertenenciaMuyAlta) {
            conjuntoMayorCalidadPrecio = "baja";
            gradoMayorCalidadPrecio= pertenenciaBaja;
        } else if (pertenenciaMedia >= pertenenciaBaja && pertenenciaMedia >= pertenenciaAlta && pertenenciaMedia >= pertenenciaMuyAlta) {
            conjuntoMayorCalidadPrecio = "media";
            gradoMayorCalidadPrecio = pertenenciaMedia;
        } else if (pertenenciaAlta >= pertenenciaBaja && pertenenciaAlta >= pertenenciaMedia && pertenenciaAlta >= pertenenciaMuyAlta) {
            conjuntoMayorCalidadPrecio = "alta";
            gradoMayorCalidadPrecio = pertenenciaAlta;
        }
        else {
            conjuntoMayorCalidadPrecio = "muyAlta";
            gradoMayorCalidadPrecio = pertenenciaMuyAlta;
        }
        
        
        JFuzzyChart.get().chart(fis.getFunctionBlock("recomendacion"));
        return String.format("Se obtiene un puntaje de recomendacion de: %.1f\nLa cual es: %s\nCon un grado de pertenencia de:%.2f.\nSe tiene una relacion calidad-precio de: %.1f\nLa cual es: %s\nCon un grado de pertenencia de: %.2f", salidaRecomendacion, conjuntoMayorRecomendacion, gradoMayorRecomendacion, salidaCalidadPrecio, conjuntoMayorCalidadPrecio, gradoMayorCalidadPrecio);

        
    }
    
}
