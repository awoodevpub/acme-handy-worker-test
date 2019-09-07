
package tests;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;

import utilities.JsonToJavaClass;
import utilities.internal.SchemaPrinter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import domain.Section;
import domain.Tutorial;

public class JsonTestAPlus {

	public static Collection<Section> crear4Section() {
		// Section 1
		final Section section1 = new Section();
		section1.setTitle("Título 1");
		section1.setText("Esto es una prueba del texto de la sección 1");
		final Collection<String> picturesSection1 = new HashSet<>();
		section1.setPictures(picturesSection1);
		section1.setNumber(1);
		section1.setId(1);
		section1.setVersion(1);
		// Section 2
		final Section section2 = new Section();
		section2.setTitle("Título 2");
		section2.setText("Esto es una prueba del texto de la sección 2");
		final Collection<String> picturesSection2 = new HashSet<>();
		section2.setPictures(picturesSection2);
		section2.setNumber(2);
		section1.setId(2);
		section1.setVersion(2);
		// Section 3
		final Section section3 = new Section();
		section3.setTitle("Título 3");
		section3.setText("Esto es una prueba del texto de la sección 3");
		final Collection<String> picturesSection3 = new HashSet<>();
		section3.setPictures(picturesSection3);
		section3.setNumber(3);
		section1.setId(3);
		section1.setVersion(3);
		// Section 4
		final Section section4 = new Section();
		section4.setTitle("Título 4");
		section4.setText("Esto es una prueba del texto de la sección 4");
		final Collection<String> picturesSection4 = new HashSet<>();
		section4.setPictures(picturesSection4);
		section4.setNumber(4);
		section1.setId(4);
		section1.setVersion(4);
		// Add sections
		final Collection<Section> sections = new HashSet<>();
		sections.add(section1);
		sections.add(section2);
		sections.add(section3);
		sections.add(section4);
		return sections;
	}

	public static Tutorial crearTutorial() throws ParseException {
		final Tutorial t = new Tutorial();
		// Atributtes tutorial
		t.setTittle("Tutorial falso");
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		t.setLastUpdated(df.parse("20/04/2018 12:42"));
		t.setSummary("Prueba");
		t.setId(1);
		t.setVersion(1);
		final Collection<String> pictures = new HashSet<>();
		t.setPictures(pictures);
		t.setSections(JsonTestAPlus.crear4Section());
		return t;

	}

	public static void main(final String[] args) throws JsonParseException, JsonMappingException, IOException, ParseException {
		// Create an object tutorial
		final Tutorial tutorial = JsonTestAPlus.crearTutorial();

		// Create a json
		final String json = JsonToJavaClass.javaToJson(tutorial);

		// Reconvert json into java object
		final Tutorial tutorialByJson = JsonToJavaClass.jsonToJava(json, Tutorial.class);
		SchemaPrinter.print(tutorialByJson);

		// Compare both objects
		System.out.print("Are they equals? ");
		if (tutorial.equals(tutorialByJson))
			System.out.print("Yes");
		else
			System.out.print("No");
	}
}
