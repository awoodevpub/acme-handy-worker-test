
package converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorsementRepository;
import domain.Endorsement;

@Component
@Transactional
public class StringToEndorsementConverter implements Converter<String, Endorsement> {

	@Autowired
	EndorsementRepository	endorsementRepository;


	@Override
	public Endorsement convert(final String text) {
		Endorsement result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.endorsementRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
