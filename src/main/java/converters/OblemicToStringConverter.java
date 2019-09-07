
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Oblemic;

@Component
@Transactional
public class OblemicToStringConverter implements Converter<Oblemic, String> {

	@Override
	public String convert(final Oblemic oblemic) {
		String result;

		if (oblemic == null)
			result = null;
		else
			result = String.valueOf(oblemic.getId());
		return result;
	}

}
