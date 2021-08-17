package com.ventas.ventas;

import java.util.Base64;
/**
 * @deprecated Pasamos a trabajar las imagenes como url 
 * <p>Clase para poder guardar los bytes de una imagen a un string</p>
 */
public class ImageUtil {
    public String getImgData(byte[] byteData) {
        return Base64.getMimeEncoder().encodeToString(byteData);
    }
}