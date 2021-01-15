package co.com.mirecarga.core.api;

import com.google.gson.annotations.SerializedName;

/**
 * Clase base para elementos con BoundingBox.
 */
public class DatosBoundingBox {
    /**
     * Bounding box mínimo x.
     */
    @SerializedName("bbox_minx")
    private double bboxMinX;
    /**
     * Bounding box mínimo y.
     */
    @SerializedName("bbox_miny")
    private double bboxMinY;
    /**
     * Bounding box máximo x.
     */
    @SerializedName("bbox_maxx")
    private double bboxMaxX;
    /**
     * Bounding box máximo y.
     */
    @SerializedName("bbox_maxy")
    private double bboxMaxY;

    /**
     * Regresa el campo bboxMinX.
     *
     * @return el valor de bboxMinX
     */
    public double getBboxMinX() {
        return bboxMinX;
    }

    /**
     * Establece el valor del campo bboxMinX.
     *
     * @param bboxMinX el valor a establecer
     */
    public void setBboxMinX(final double bboxMinX) {
        this.bboxMinX = bboxMinX;
    }

    /**
     * Regresa el campo bboxMinY.
     *
     * @return el valor de bboxMinY
     */
    public double getBboxMinY() {
        return bboxMinY;
    }

    /**
     * Establece el valor del campo bboxMinY.
     *
     * @param bboxMinY el valor a establecer
     */
    public void setBboxMinY(final double bboxMinY) {
        this.bboxMinY = bboxMinY;
    }

    /**
     * Regresa el campo bboxMaxX.
     *
     * @return el valor de bboxMaxX
     */
    public double getBboxMaxX() {
        return bboxMaxX;
    }

    /**
     * Establece el valor del campo bboxMaxX.
     *
     * @param bboxMaxX el valor a establecer
     */
    public void setBboxMaxX(final double bboxMaxX) {
        this.bboxMaxX = bboxMaxX;
    }

    /**
     * Regresa el campo bboxMaxY.
     *
     * @return el valor de bboxMaxY
     */
    public double getBboxMaxY() {
        return bboxMaxY;
    }

    /**
     * Establece el valor del campo bboxMaxY.
     *
     * @param bboxMaxY el valor a establecer
     */
    public void setBboxMaxY(final double bboxMaxY) {
        this.bboxMaxY = bboxMaxY;
    }
}
