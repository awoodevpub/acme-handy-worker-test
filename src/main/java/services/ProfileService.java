
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfileRepository;
import domain.Profile;

@Service
@Transactional
public class ProfileService {

	// Managed repository
	@Autowired
	private ProfileRepository	profileRepository;


	// Supporting services

	// Simple CRUD methods
	public Profile create() {
		Profile result;

		result = new Profile();

		return result;
	}

	public Collection<Profile> findAll() {
		Collection<Profile> result;

		result = this.profileRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Profile findOne(final int profileId) {
		Assert.isTrue(profileId != 0);

		Profile result;

		result = this.profileRepository.findOne(profileId);
		Assert.notNull(result);

		return result;
	}

	public Profile save(final Profile profile) {
		Assert.notNull(profile);

		Profile result;

		result = this.profileRepository.save(profile);

		return result;
	}

	public void delete(final Profile profile) {
		Assert.notNull(profile);
		Assert.isTrue(profile.getId() != 0);
		Assert.isTrue(this.profileRepository.exists(profile.getId()));

		this.profileRepository.delete(profile);
	}

	// Other business methods

}
