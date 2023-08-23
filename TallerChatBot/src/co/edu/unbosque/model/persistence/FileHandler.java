package co.edu.unbosque.model.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileHandler {

	private static File archivo;

	public FileHandler() {
	}

	public static Properties cargarPropiedades(String nombre) {
		archivo = new File("src/co/edu/unbosque/util/" + nombre);
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(archivo));
		} catch (IOException e) {
			System.out.println("Error al cargar archivo propiedades");
			e.printStackTrace();
		}
		return prop;
	}

}
