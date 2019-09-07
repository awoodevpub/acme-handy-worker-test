package converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OblemicRepository;
import domain.Oblemic;

@Component
@Transactional
public class StringToOblemicConverter implements Converter<String, Oblemic> {

	@Autowired
	OblemicRepository	oblemicRepository;


	@Override
	public Oblemic convert(final String text) {
		Oblemic result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.oblemicRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}

