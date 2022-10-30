package es.unican.is.appgasolineras.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import es.unican.is.appgasolineras.model.Gasolinera;

public class Filters {

    private static final String DIESELA = "dieselA";
    private static final String NORMAL95 = "normal95";
    private static final String NORMAL95E10 = "normal95E10";
    private static final String NORMAL95E5P = "normal95E5p";
    private static final String NORMAL98E5 = "normal98E5";
    private static final String NORMAL98E10 = "normal98E10";
    private static final String DIESELP = "dieselP";
    private static final String DIESELB = "dieselB";
    private static final String BIOETANOL = "bioEtanol";
    private static final String BIODIESEL = "bioDiesel";
    private static final String GLP = "glp";
    private static final String GASC = "gasC";
    private static final String GASL = "gasL";
    private static final String H2 = "h2";

    private static final String AVIA = "AVIA";
    private static final String CAMPSA = "CAMPSA";
    private static final String CARREFOUR = "CARREFOUR";
    private static final String CEPSA = "CEPSA";
    private static final String GALP = "GALP";
    private static final String PETRONOR = "PETRONOR";
    private static final String REPSOL = "REPSOL";
    private static final String SHELL = "SHELL";

    private static double max = Double.MIN_VALUE;

    private Filters() {
        // No hace nada
    }

    public static List<Gasolinera> filtraTipo(List<Gasolinera> data, String tipoCombustible) {
        List<Gasolinera> listaDevolver;
        switch (tipoCombustible) {
            case DIESELA:
                listaDevolver = data.stream().filter(g -> !g.getDieselA().equals("")).collect(Collectors.toList());
                break;
            case NORMAL95:
                listaDevolver = data.stream().filter(g -> !g.getNormal95().equals("")).collect(Collectors.toList());
                break;
            case NORMAL95E10:
                listaDevolver = data.stream().filter(g -> !g.getGasolina95E10().equals("")).collect(Collectors.toList());
                break;
            case NORMAL95E5P:
                listaDevolver = data.stream().filter(g -> !g.getNormal95Prem().equals("")).collect(Collectors.toList());
                break;
            case NORMAL98E5:
                listaDevolver = data.stream().filter(g -> !g.getGasolina98E5().equals("")).collect(Collectors.toList());
                break;
            case NORMAL98E10:
                listaDevolver = data.stream().filter(g -> !g.getGasolina98E10().equals("")).collect(Collectors.toList());
                break;
            case DIESELB:
                listaDevolver = data.stream().filter(g -> !g.getDieselB().equals("")).collect(Collectors.toList());
                break;
            case DIESELP:
                listaDevolver = data.stream().filter(g -> !g.getDieselPrem().equals("")).collect(Collectors.toList());
                break;
            case BIOETANOL:
                listaDevolver = data.stream().filter(g -> !g.getBioetanol().equals("")).collect(Collectors.toList());
                break;
            case BIODIESEL:
                listaDevolver = data.stream().filter(g -> !g.getBiodiesel().equals("")).collect(Collectors.toList());
                break;
            case GLP:
                listaDevolver = data.stream().filter(g -> !g.getGasLicPet().equals("")).collect(Collectors.toList());
                break;
            case GASC:
                listaDevolver = data.stream().filter(g -> !g.getGasNatComp().equals("")).collect(Collectors.toList());
                break;
            case GASL:
                listaDevolver = data.stream().filter(g -> !g.getGasNatLic().equals("")).collect(Collectors.toList());
                break;
            case H2:
                listaDevolver = data.stream().filter(g -> !g.getHidrogeno().equals("")).collect(Collectors.toList());
                break;
            default:
                return data;
        }
        return listaDevolver;
    }

    public static List<Gasolinera> filtraPrecio(List<Gasolinera> data, String maxPrecio, String tipoCombustible) {
        if (maxPrecio.equals("")) {
            return data;
        }
        List<Gasolinera> listaDevolver;
        BigDecimal actual = new BigDecimal(maxPrecio).setScale(3, RoundingMode.UP);
        switch (tipoCombustible) {
            case DIESELA:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getDieselA().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case NORMAL95:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getNormal95().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case NORMAL95E10:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getGasolina95E10().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case NORMAL95E5P:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getNormal95Prem().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case NORMAL98E5:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getGasolina98E5().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case NORMAL98E10:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getGasolina98E10().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case DIESELB:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getDieselB().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case DIESELP:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getDieselPrem().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case BIOETANOL:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getBioetanol().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case BIODIESEL:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getBiodiesel().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case GLP:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getGasLicPet().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case GASC:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getGasNatComp().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case GASL:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getGasNatLic().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            case H2:
                listaDevolver = data.stream().filter(g ->
                        (new BigDecimal(g.getHidrogeno().replace(',', '.'))).compareTo(actual) <= 0).collect(Collectors.toList());
                break;
            default:
                listaDevolver = data.stream().filter(g -> {
                    BigDecimal min;
                    Boolean result = false;
                    if (g.getNormal95().equals("") && g.getDieselA().equals("")) {
                        return false;
                    } else if (g.getDieselA().equals("")) {
                        min = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                    } else if (g.getNormal95().equals("")) {
                        min = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                    } else {
                        BigDecimal gas = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                        BigDecimal diesel = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                        if (gas.compareTo(diesel) >= 0) {
                            min = new BigDecimal(diesel.toString()).setScale(3, RoundingMode.UP);
                        } else {
                            min = new BigDecimal(gas.toString()).setScale(3, RoundingMode.UP);
                        }
                    }
                    if (min.compareTo(actual) <= 0) {
                        result = true;
                    }
                    return result;
                }).collect(Collectors.toList());
        }
        return listaDevolver;
    }

    public static List<Gasolinera> filtraMarca(List<Gasolinera> data, String marca) {
        List<Gasolinera> listaDevolver;
        switch (marca) {
            case AVIA:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(AVIA)).collect(Collectors.toList());
                break;
            case CAMPSA:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(CAMPSA)).collect(Collectors.toList());
                break;
            case CARREFOUR:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(CARREFOUR)).collect(Collectors.toList());
                break;
            case CEPSA:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(CEPSA)).collect(Collectors.toList());
                break;
            case GALP:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(GALP)).collect(Collectors.toList());
                break;
            case PETRONOR:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(PETRONOR)).collect(Collectors.toList());
                break;
            case REPSOL:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(REPSOL)).collect(Collectors.toList());
                break;
            case SHELL:
                listaDevolver = data.stream().filter(g -> g.getRotulo().equals(SHELL)).collect(Collectors.toList());
                break;
            default:
                return data;
        }
        return listaDevolver;
    }

    public static String maximoEntreTodas(List<Gasolinera> data, String tipoCombustible) {
        if (data.isEmpty()) {
            return "0.00";
        }
        Double maximum;
        switch (tipoCombustible) {
            case DIESELA:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getDieselA().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case NORMAL95:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getNormal95().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case NORMAL95E10:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getGasolina95E10().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case NORMAL95E5P:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getNormal95Prem().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case NORMAL98E5:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getGasolina98E5().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case NORMAL98E10:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getGasolina98E10().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case DIESELB:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getDieselB().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case DIESELP:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getDieselPrem().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case BIOETANOL:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getBioetanol().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case BIODIESEL:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getBiodiesel().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case GLP:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getGasLicPet().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case GASC:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getGasNatComp().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case GASL:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getGasNatLic().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            case H2:
                maximum = data.stream().mapToDouble(g -> Double.parseDouble(g.getHidrogeno().replace(',', '.'))).max().getAsDouble();
                max = mayor(maximum, max);
                break;
            default:
                for (Gasolinera g : data) {
                    if (g.getNormal95() != null && !g.getNormal95().equals("")
                            && g.getDieselA() != null && !g.getDieselA().equals("")) {
                        Double gas = Double.parseDouble(g.getNormal95().replace(',', '.'));
                        Double die = Double.parseDouble(g.getDieselA().replace(',', '.'));
                        maximum = Double.max(gas, die);
                        max = mayor(maximum, max);
                    }
                }
        }
        return String.valueOf(max);
    }

    private static Double mayor(Double primerNum, Double segundoNum) {
        if (primerNum > segundoNum) {
            return primerNum;
        } else {
            return segundoNum;
        }
    }

}
